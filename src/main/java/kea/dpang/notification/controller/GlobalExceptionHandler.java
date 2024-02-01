package kea.dpang.notification.controller;

import kea.dpang.notification.base.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(MailException.class)
    private ResponseEntity<ErrorResponse> handleMailException(MailException ex, WebRequest request) {
        ErrorResponse errorMessage = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ex.getMessage() != null ? ex.getMessage() : "세부 정보가 제공되지 않았습니다",
                HttpStatus.INTERNAL_SERVER_ERROR.name(),
                request.getDescription(false),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

