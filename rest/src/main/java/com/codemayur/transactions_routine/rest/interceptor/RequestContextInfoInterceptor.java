package com.codemayur.transactions_routine.rest.interceptor;

import com.codemayur.transactions_routine.core.util.AppUtil;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Component
public class RequestContextInfoInterceptor implements HandlerInterceptor {

    private static final String API_DOCS = "api-docs";
    private static final String SWAGGER = "swagger";

    @Override
    public boolean preHandle(final @NonNull HttpServletRequest request,
                             final @NonNull HttpServletResponse response,
                             final @NonNull Object handler) {
        if (!request.getRequestURI().contains(API_DOCS) && !request.getRequestURI().contains(SWAGGER)) {
            AppUtil.setRequestLogId(UUID.randomUUID().toString());
            log.info("RequestContextInfoInterceptor::preHandle: {} {}?{}",
                     request.getMethod(),
                     request.getRequestURI(),
                     request.getQueryString());
        }
        return true;
    }

    @Override
    public void postHandle(final @NonNull HttpServletRequest request,
                           final @NonNull HttpServletResponse response,
                           final @NonNull Object handler,
                           final @Nullable ModelAndView modelAndView) {
        if (request.getRequestURI().contains(API_DOCS) || request.getRequestURI().contains(SWAGGER)) {
            return;
        }
        if (response.getStatus() <= 399) {
            log.info("RequestContextInfoInterceptor::postHandle: {} {} - {}",
                     request.getMethod(),
                     request.getRequestURI(),
                     response.getStatus());
        } else {
            log.error("RequestContextInfoInterceptor::postHandle: {} {} - {}",
                      request.getMethod(),
                      request.getRequestURI(),
                      response.getStatus());
        }
    }

    @Override
    public void afterCompletion(final @NonNull HttpServletRequest request,
                                final @NonNull HttpServletResponse response,
                                final @NonNull Object handler,
                                final @Nullable Exception ex) {
        if (request.getRequestURI().contains(API_DOCS) || //
                request.getRequestURI().contains(SWAGGER)) {
            return;
        }
        if (Objects.nonNull(ex)) {
            log.info("RequestContextInfoInterceptor::afterCompletion: exception: {}", ex.getMessage());
        }
        AppUtil.clearMDCValues();
    }

}
