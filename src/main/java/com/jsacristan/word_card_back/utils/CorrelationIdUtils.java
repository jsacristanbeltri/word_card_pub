package com.jsacristan.word_card_back.utils;

import com.jsacristan.word_card_back.config.Config;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

/**
 * Utility class for managing correlation IDs.
 */
@Component
public class CorrelationIdUtils {

    /**
     * Generates and stores a correlation ID in the provided map and MDC.
     *
     * @param entry The map where the correlation ID might be present.
     * @return The generated or existing correlation ID.
     */
    public String generateAndStoreId(Map<String, String> entry) {
        String correlationId;
        if (entry == null || StringUtils.isBlank(entry.get(Config.CORRELATION_ID_HEADER_NAME))) {
            correlationId = UUID.randomUUID().toString();
            MDC.put(Config.CORRELATION_ID_HEADER_NAME, correlationId);
        } else {
            correlationId = entry.get(Config.CORRELATION_ID_HEADER_NAME);
            MDC.put(Config.CORRELATION_ID_HEADER_NAME, correlationId);
        }
        return correlationId;
    }

    /**
     * Retrieves the current correlation ID from the MDC or generates a new one if none is found.
     *
     * @return The existing or new correlation ID.
     */
    public String getActualCorrelationIdOrNew() {
        String correlationId = MDC.get(Config.CORRELATION_ID_HEADER_NAME);
        if (StringUtils.isBlank(correlationId)) {
            correlationId = UUID.randomUUID().toString();
        }
        return correlationId;
    }

    /**
     * Retrieves the current correlation ID from the MDC.
     *
     * @return The current correlation ID or null if none is found.
     */
    public String getActualCorrelationId() {
        return MDC.get(Config.CORRELATION_ID_HEADER_NAME);
    }

    /**
     * Removes the correlation ID from the MDC.
     */
    public void removeCorrelationId() {
        MDC.remove(Config.CORRELATION_ID_HEADER_NAME);
    }
}
