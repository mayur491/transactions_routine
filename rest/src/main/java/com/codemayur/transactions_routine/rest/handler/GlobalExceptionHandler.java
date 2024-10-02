package com.codemayur.transactions_routine.rest.handler;

import com.codemayur.transactions_routine.core.dto.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {Throwable.class})
    @ResponseBody
    protected ResponseEntity<?> handleConflict(Throwable ex) {
        log.error("GlobalExceptionHandling", ex);
        final BaseException baseException = BaseException.builder()
                                                         .errorCode("GEH50001")
                                                         .errorMessage("Internal Server Error")
                                                         .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body(baseException);
    }

}
