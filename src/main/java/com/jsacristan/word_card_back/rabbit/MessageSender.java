package com.jsacristan.word_card_back.rabbit;

import com.jsacristan.word_card_back.config.Config;
import com.jsacristan.word_card_back.utils.CorrelationIdUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

/**
 * Sends messages to RabbitMQ queues.
 */
@Component
public class MessageSender {

    /**
     * The RabbitTemplate used to interact with RabbitMQ.
     */
    private final RabbitTemplate rabbitTemplate;

    /**
     * The configuration for RabbitMQ.
     */
    @Autowired
    public Config configuration;

    /**
     * Utility class for handling correlation IDs.
     */
    @Autowired
    private CorrelationIdUtils correlationIdUtils;

    /**
     * Constructs a new MessageSender.
     *
     * @param rabbitTemplate the RabbitTemplate to use
     */
    @Autowired
    public MessageSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * Sends a message to the queue for saving a card.
     *
     * @param message the message to send
     */
    public void sendSaveCardToQueue(final Message message) {
        String correlationId = correlationIdUtils.getActualCorrelationIdOrNew();
        message.getMessageProperties().setHeader(Config.CORRELATION_ID_HEADER_NAME, correlationId);
        message.getMessageProperties().setCorrelationId(correlationId);
        this.rabbitTemplate.convertAndSend(configuration.getExchangeSaveCard(), this.configuration.getRoutingKeySaveCard(), message);
    }

    /**
     * Sends a message to the queue for saving a deck.
     *
     * @param message the message to send
     * @return the correlation ID of the message
     */
    public String sendSaveDeckToQueue(final Message message) {
        String correlationId = correlationIdUtils.getActualCorrelationIdOrNew();
        message.getMessageProperties().setHeader(Config.CORRELATION_ID_HEADER_NAME, correlationId);
        message.getMessageProperties().setCorrelationId(correlationId);
        this.rabbitTemplate.convertAndSend(configuration.getExchangeSaveDeck(), this.configuration.getRoutingKeySaveDeck(), message);
        return correlationId;
    }

    /**
     * Sends a reprocessed message with an optional expiration time.
     *
     * @param exchange the exchange to send the message to
     * @param message the message to send
     * @param routingKey the routing key to use
     * @param expiration the expiration time for the message
     */
    public void sendReprocessedMessage(String exchange, Message message, String routingKey, String expiration) {
        if (expiration != null) {
            message.getMessageProperties().setExpiration(expiration);
        }
        message.getMessageProperties().setHeader(this.configuration.CORRELATION_ID_HEADER_NAME, correlationIdUtils.getActualCorrelationIdOrNew());
        message.getMessageProperties().setContentType(MediaType.APPLICATION_JSON_VALUE);
        this.rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }

    /**
     * Sends a message to the retry queue for saving a deck.
     *
     * @param object the message to send
     */
    public void sendSaveDeckToQueueRetry(final Message object) {
        this.rabbitTemplate.convertAndSend(configuration.getExchangeSaveDeck(), this.configuration.getRoutingRetryKeySaveDeck(), object);
    }

    /**
     * Sends a message to the error queue for saving a deck.
     *
     * @param object the message to send
     */
    public void sendSaveDeckToQueueError(final Message object) {
        this.rabbitTemplate.convertAndSend(configuration.getExchangeSaveDeck(), this.configuration.getRoutingErrorKeySaveDeck(), object);
    }

    /**
     * Sends a message to the retry queue for saving a card.
     *
     * @param object the message to send
     */
    public void sendSaveCardToQueueRetry(final Message object) {
        this.rabbitTemplate.convertAndSend(configuration.getExchangeSaveCard(), this.configuration.getRoutingRetryKeySaveCard(), object);
    }

    /**
     * Sends a message to the error queue for saving a card.
     *
     * @param object the message to send
     */
    public void sendSaveCardToQueueError(final Message object) {
        this.rabbitTemplate.convertAndSend(configuration.getExchangeSaveCard(), this.configuration.getRoutingErrorKeySaveCard(), object);
    }

    // Other methods for sending reprocessed messages and handling retries and errors omitted for brevity
}
