package com.codemayur.transactions_routine.rest.interceptor;

import com.codemayur.transactions_routine.core.util.AppUtil;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import java.lang.reflect.Type;
import java.util.Objects;

@Slf4j
@ControllerAdvice
public class CustomRequestBodyAdviceAdapter extends RequestBodyAdviceAdapter {

    @Override
    public boolean supports(final @NonNull MethodParameter methodParameter,
                            final @NonNull Type type,
                            final @NonNull Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public @NonNull Object afterBodyRead(final @NonNull Object requestBody,
                                         final @NonNull HttpInputMessage inputMessage,
                                         final @NonNull MethodParameter parameter,
                                         final @NonNull Type targetType,
                                         final @NonNull Class<? extends HttpMessageConverter<?>> converterType) {
        log.info("CustomRequestBodyAdviceAdapter::afterBodyRead: {}", AppUtil.toJsonIgnoreException(requestBody));
        return super.afterBodyRead(requestBody, inputMessage, parameter, targetType, converterType);
    }

    @Override
    public Object handleEmptyBody(final @Nullable Object requestBody,
                                  final @NonNull HttpInputMessage inputMessage,
                                  final @NonNull MethodParameter parameter,
                                  final @NonNull Type targetType,
                                  final @NonNull Class<? extends HttpMessageConverter<?>> converterType) {
        if (Objects.nonNull(requestBody)) {
            log.info("CustomRequestBodyAdviceAdapter::handleEmptyBody: {}", AppUtil.toJsonIgnoreException(requestBody));
        }
        return super.handleEmptyBody(requestBody, inputMessage, parameter, targetType, converterType);
    }

}
