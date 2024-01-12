package com.vmg.ibo.core.config.handler;

import com.vmg.ibo.core.base.Result;
import com.vmg.ibo.core.config.exception.GlobalException;
import com.vmg.ibo.core.config.exception.ServiceValidationException;
import com.vmg.ibo.core.config.exception.WebServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@ControllerAdvice
@SuppressWarnings({"rawtypes", "unchecked"})
public class GlobalExceptionHandler {
    protected static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Autowired
    private MessageSource messageSource;

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler({BindException.class})
    public ResponseEntity<?> handleValidationExceptions(BindException ex) {
        LOG.debug("GlobalExceptionHandler => ResponseEntity: ", ex);
        String errorMessage = ex.getBindingResult().getAllErrors().stream()
                .map(error -> {
                    if (error instanceof FieldError) {
                        return ((FieldError) error).getDefaultMessage();
                    } else {
                        return error.getDefaultMessage();
                    }
                })
                .collect(Collectors.joining(", "));
        return new ResponseEntity<>(
                Result.error(
                        HttpStatus.CONFLICT.value(),
                        errorMessage
                ),
                HttpStatus.OK
        );
    }

    @ExceptionHandler(WebServiceException.class)
    public Object handleException(WebServiceException ex, HttpServletRequest request, HttpServletResponse response) {
        LOG.debug("GlobalExceptionHandler => ResponseEntity: ", ex);
        Result rs = Result.error(ex.getHttpStatus(), GlobalException.format(ex.getMessage()));
        HttpStatus httpStatus = HttpStatus.valueOf(rs.getCode());
        return new ResponseEntity(rs, httpStatus);
    }

    @ExceptionHandler(ServiceValidationException.class)
    public ResponseEntity<?> handleServiceValidationException(ServiceValidationException exception) {
        return new ResponseEntity<>(
                Result.result(HttpStatus.CONFLICT.value(), null, exception.getErrors()),
                HttpStatus.OK
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleServiceValidationException(HttpMessageNotReadableException exception) {
        return new ResponseEntity<>(
                Result.result(409, "Dữ liệu nhập không đúng định dạng", null),
                HttpStatus.OK
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception exception) {
        LOG.debug("GlobalExceptionHandler => ResponseEntity: ", exception.getMessage());
        return new ResponseEntity<>(
                Result.result(HttpStatus.BAD_REQUEST.value(), "Xảy ra lỗi!", null),
                HttpStatus.BAD_REQUEST
        );
    }
}
