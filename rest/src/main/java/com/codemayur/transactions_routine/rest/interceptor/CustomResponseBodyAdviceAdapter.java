package com.codemayur.transactions_routine.rest.interceptor;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@Slf4j
@ControllerAdvice
public class CustomResponseBodyAdviceAdapter implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(final @NonNull MethodParameter methodParameter,
                            final @NonNull Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(final @Nullable Object response,
                                  final @NonNull MethodParameter methodParameter,
                                  final @NonNull MediaType mediaType,
                                  final @NonNull Class<? extends HttpMessageConverter<?>> aClass,
                                  final @NonNull ServerHttpRequest serverHttpRequest,
                                  final @NonNull ServerHttpResponse serverHttpResponse) {

        if (serverHttpRequest.getURI().toString().contains("prometheus") ||
                serverHttpRequest.getURI().toString().contains("api-docs") ||
                serverHttpRequest.getURI().toString().contains("actuator") ||
                serverHttpRequest.getURI().toString().contains("swagger")) {
            return response;
        }
        log.info("CustomResponseBodyAdviceAdapter::beforeBodyWrite: URI: {}", serverHttpRequest.getURI());
        return response;
    }

}
