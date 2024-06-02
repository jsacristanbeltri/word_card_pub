package com.jsacristan.word_card_back.rabbit;

import com.jsacristan.word_card_back.config.Config;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;

/**
 * Configuration class for RabbitMQ setup.
 */
@Configuration
public class RabbitConfig implements RabbitListenerConfigurer {

    /**
     * Configuration instance injected to access RabbitMQ settings.
     */
    @Autowired
    public Config configuration;

    /**
     * Rabbit properties for RabbitMQ setup.
     */
    @Autowired
    private RabbitProperties rabbitProperties;

    /**
     * Primary bean for establishing the connection factory.
     *
     * @return The main connection factory for RabbitMQ.
     */
    @Primary
    @Bean(name="mainConnectionFactory")
    public ConnectionFactory eventConnectionFactory(){
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(this.configuration.getHostName());
        connectionFactory.setPort(this.configuration.getPort());
        connectionFactory.setUsername(this.configuration.getUsername());
        connectionFactory.setPassword(this.configuration.getPassword());
        return connectionFactory;
    }

    /**
     * Configures the Rabbit listeners.
     *
     * @param registrar The registrar for Rabbit listeners.
     */
    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
        registrar.setMessageHandlerMethodFactory(this.messageHandlerMethodFactory());
    }

    /**
     * Creates the message handler method factory.
     *
     * @return The message handler method factory.
     */
    @Bean
    MessageHandlerMethodFactory messageHandlerMethodFactory(){
        final DefaultMessageHandlerMethodFactory messageHandlerMethodFactory = new DefaultMessageHandlerMethodFactory();
        messageHandlerMethodFactory.setMessageConverter(this.consumerJackson2MessageConverter());
        return messageHandlerMethodFactory;
    }

    /**
     * Creates the message converter for consuming JSON messages.
     *
     * @return The message converter for consuming JSON messages.
     */
    @Bean
    public MappingJackson2MessageConverter consumerJackson2MessageConverter(){
        return new MappingJackson2MessageConverter();
    }

    /**
     * Creates the Rabbit template for message publishing.
     *
     * @param connectionFactory The connection factory for RabbitMQ.
     * @return The Rabbit template for message publishing.
     */
    @Bean
    public RabbitTemplate rabbitTemplate(@Qualifier("mainConnectionFactory") final ConnectionFactory connectionFactory){
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(this.producerJackson2MessageConverter());
        rabbitTemplate.setChannelTransacted(true);
        return rabbitTemplate;
    }

    /**
     * Creates the Rabbit admin for declaring queues, exchanges, etc.
     *
     * @param connectionFactory The connection factory for RabbitMQ.
     * @return The Rabbit admin for declaring queues, exchanges, etc.
     */
    @Bean
    public RabbitAdmin rabbitAdmin(@Qualifier("mainConnectionFactory") final ConnectionFactory connectionFactory){
        return new RabbitAdmin(connectionFactory);
    }

    /**
     * Creates the message converter for producing JSON messages.
     *
     * @return The message converter for producing JSON messages.
     */
    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    // Exchanges

    /**
     * Creates the topic exchange for saving cards.
     *
     * @param rabbitAdmin The Rabbit admin for declaring exchanges.
     * @return The topic exchange for saving cards.
     */
    @Bean
    TopicExchange exchangeSaveCard(final RabbitAdmin rabbitAdmin) {
        return ExchangeBuilder.topicExchange(this.configuration.getExchangeSaveCard())
                .durable(true)
                .admins(rabbitAdmin)
                .build();
    }

    /**
     * Creates the topic exchange for saving decks.
     *
     * @param rabbitAdmin The Rabbit admin for declaring exchanges.
     * @return The topic exchange for saving decks.
     */
    @Bean
    public TopicExchange exchangeSaveDeck(final RabbitAdmin rabbitAdmin) {
        return ExchangeBuilder.topicExchange(this.configuration.getExchangeSaveDeck())
                .durable(true)
                .admins(rabbitAdmin)
                .build();
    }

    //--------   Q_saveCard ---------------

    /**
     * Creates the queue for saving cards.
     *
     * @return The queue for saving cards.
     */
    @Bean
    public Queue queueSaveCard() {
        Queue queue = QueueBuilder
                .durable(this.configuration.getQueueSaveCard())
                .deadLetterExchange(this.configuration.getExchangeSaveCard())
                .deadLetterRoutingKey(this.configuration.getRoutingRetryKeySaveCard())
                .build();
        //queue.setAdminsThatShouldDeclare(rabbitAdmin);
        return queue;
    }

    /**
     * Binds the queue for saving cards to the exchange.
     *
     * @param queue          The queue for saving cards.
     * @param exchangeSaveCard   The exchange for saving cards.
     * @return The binding between the queue for saving cards and the exchange for saving cards.
     */
    @Bean
    Binding bindingSaveCard(@Qualifier("queueSaveCard")final Queue queue,final TopicExchange exchangeSaveCard) {
        Binding binding = BindingBuilder.bind(queue).to(exchangeSaveCard).with(this.configuration.getRoutingKeySaveCard());
        //binding.setAdminsThatShouldDeclare(rabbitAdmin);
        return binding;
    }
    //----------------- Q_SaveCard_Retry

    /**
     * Creates the retry queue for saving decks.
     *
     * @return The retry queue for saving decks.
     */
    @Bean
    public Queue queueSaveDeckRetry() {
        Queue queue = QueueBuilder
                .durable(this.configuration.getQueueSaveDeckRetry())
                .deadLetterExchange(this.configuration.getExchangeSaveDeck())
                .deadLetterRoutingKey(this.configuration.getRoutingRetryKeySaveDeck())
                .build();
        //queue.setAdminsThatShouldDeclare(rabbitAdmin);
        return queue;
    }

    /**
     * Binds the retry queue for saving decks to the exchange.
     *
     * @param queue          The retry queue for saving decks.
     * @param exchangeSaveDeck   The exchange for saving decks.
     * @return The binding between the retry queue for saving decks and the exchange for saving decks.
     */
    @Bean
    Binding bindingSaveDeckRetry(@Qualifier("queueSaveDeckRetry")final Queue queue,final TopicExchange exchangeSaveDeck) {
        Binding binding = BindingBuilder.bind(queue).to(exchangeSaveDeck).with(this.configuration.getRoutingRetryKeySaveDeck());
        //binding.setAdminsThatShouldDeclare(rabbitAdmin);
        return binding;
    }

//------------- Q_SaveDeck_Error

    /**
     * Creates the error queue for saving decks.
     *
     * @return The error queue for saving decks.
     */
    @Bean
    public Queue queueSaveDeckError() {
        Queue queue = QueueBuilder.durable(this.configuration.getQueueSaveDeckError()).build();
        //queue.setAdminsThatShouldDeclare(rabbitAdmin);
        return queue;
    }

    /**
     * Binds the error queue for saving decks to the exchange.
     *
     * @param queueSaveDeck          The error queue for saving decks.
     * @param exchangeSaveDeck   The exchange for saving decks.
     * @return The binding between the error queue for saving decks and the exchange for saving decks.
     */
    @Bean
    Binding bindingSaveDeckError(@Qualifier("queueSaveDeckError")final Queue queueSaveDeck,final TopicExchange exchangeSaveDeck) {
        Binding binding = BindingBuilder.bind(queueSaveDeck).to(exchangeSaveDeck).with(this.configuration.getRoutingErrorKeySaveDeck());
        //binding.setAdminsThatShouldDeclare(rabbitAdmin);
        return binding;
    }
}
