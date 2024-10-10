package com.codemayur.transactions_routine.core.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

@Slf4j
public class AppUtil {

    public static final String REQUEST_LOG_ID = "requestLogId";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private AppUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static String toJsonIgnoreException(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception ex) {
            log.error("AppUtil::toJsonIgnoreException Error: {}", ex.getMessage());
        }
        return "";
    }

    public static String getRequestLogId() {
        return MDC.get(REQUEST_LOG_ID);
    }

    public static void setRequestLogId(String requestLogId) {
        MDC.put(REQUEST_LOG_ID, requestLogId);
    }

    public static void clearMDCValues() {
        MDC.remove(REQUEST_LOG_ID);
    }

}
