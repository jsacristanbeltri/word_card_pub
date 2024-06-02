package com.jsacristan.word_card_back.rabbit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsacristan.word_card_back.config.Config;
import com.jsacristan.word_card_back.dtos.CreateCardRequestDto;
import com.jsacristan.word_card_back.dtos.CreateDeckRequestDto;
import com.jsacristan.word_card_back.services.CardService;
import com.jsacristan.word_card_back.services.DeckService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Listens for messages from RabbitMQ and processes them accordingly.
 */
@Component
public class MessageListener {

    /**
     * Logger for logging message events.
     */
    private static Logger log = LoggerFactory.getLogger(MessageListener.class);

    /**
     * The configuration for RabbitMQ.
     */
    @Autowired
    private Config configuration;

    /**
     * ObjectMapper for converting JSON messages to Java objects.
     */
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Service for handling deck operations.
     */
    @Autowired
    private DeckService deckService;

    /**
     * Service for handling card operations.
     */
    @Autowired
    private CardService cardService;

    /**
     * MessageSender for sending messages to RabbitMQ queues.
     */
    @Autowired
    private MessageSender messageSender;

    /**
     * Listens for messages to save a card from RabbitMQ.
     * @param message the incoming message from RabbitMQ
     */
    @RabbitListener(queues = "${queue.saveCard}")
    public void saveCardFromRabbit(Message message) {
        log.info("IN queue.saveCard, message in: " + message);
        try {
            CreateCardRequestDto cardRequest = objectMapper.readValue(message.getBody(), CreateCardRequestDto.class);
            log.info("IN queue.saveCard, card: " + cardRequest);
            cardService.save(cardRequest);
            log.info("OUT queue.saveCard, message in: " + message);
        } catch (Exception e) {
            log.error(e.getMessage());
            this.messageSender.sendSaveCardToQueueRetry(message);
        }
    }

    /**
     * Listens for messages to save a deck from RabbitMQ.
     * @param message the incoming message from RabbitMQ
     */
    @RabbitListener(queues = "${queue.saveDeck}")
    public void saveDeckFromRabbit(Message message) {
        log.info(String.format("IN queue.saveDeck, message in: %s ", message));
        try {
            CreateDeckRequestDto deckRequestDto = objectMapper.readValue(message.getBody(), CreateDeckRequestDto.class);
            deckService.save(deckRequestDto);
        } catch (Exception e) {
            log.error(String.format("Error saving deck from message %s error: ", message, e.getMessage()));
            this.messageSender.sendSaveDeckToQueueRetry(message);
        }
    }

    /**
     * Processes failed messages to save a card, requeuing them with a retry count.
     * @param failedMessage the failed message from RabbitMQ
     */
    @RabbitListener(queues = "${queue.saveCard.retry}")
    public void processFailedMessagesRequeue_Card(Message failedMessage) {
        Integer retriesCnt = (Integer) failedMessage.getMessageProperties()
                .getHeaders().get(this.configuration.HEADER_X_RETRIES_COUNT);
        if (retriesCnt == null) retriesCnt = 1;
        if (retriesCnt > this.configuration.MAX_RETRIES_COUNT) {
            log.info("Sending message to the parking lot queue");
            this.messageSender.sendSaveCardToQueueError(failedMessage);
            return;
        }

        log.info("Retrying message for the {} time", retriesCnt);
        failedMessage.getMessageProperties()
                .getHeaders().put(this.configuration.HEADER_X_RETRIES_COUNT, ++retriesCnt);
        this.messageSender.sendSaveCardToQueue(failedMessage);
    }

    /**
     * Processes failed messages to save a deck, requeuing them with a retry count.
     * @param failedMessage the failed message from RabbitMQ
     */
    @RabbitListener(queues = "${queue.saveDeck.retry}")
    public void processFailedMessagesRequeu_Deck(Message failedMessage) {
        Integer retriesCnt = (Integer) failedMessage.getMessageProperties()
                .getHeaders().get(this.configuration.HEADER_X_RETRIES_COUNT);
        if (retriesCnt == null) retriesCnt = 1;
        if (retriesCnt > this.configuration.MAX_RETRIES_COUNT) {
            log.info("Sending message to the parking lot queue");
            this.messageSender.sendSaveDeckToQueueError(failedMessage);
            return;
        }

        log.info("Retrying message for the {} time", retriesCnt);
        failedMessage.getMessageProperties()
                .getHeaders().put(this.configuration.HEADER_X_RETRIES_COUNT, ++retriesCnt);
        this.messageSender.sendSaveDeckToQueue(failedMessage);
    }
}
