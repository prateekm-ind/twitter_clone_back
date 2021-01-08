package com.example.twitter.clone.exception;

import com.example.twitter.clone.dto.ErrorDetailsDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import java.util.Date;

@ControllerAdvice
@RestController
public class GlobalControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(TwitterAppRuntimeException.class)
    public final ResponseEntity<ErrorDetailsDto> handleRuntimeException(TwitterAppRuntimeException exception, WebRequest request){
        //ErrorDetailsDto detailsDto= new ErrorDetailsDto(new Date(), exception.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorResposeBodyBuilder(new Date(), HttpStatus.INTERNAL_SERVER_ERROR,exception.getMessage(),request.getDescription(false)),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CustomExpiredJwtExceptionHandler.class)
    public final ResponseEntity<ErrorDetailsDto> handleExpiredJwtException(CustomExpiredJwtExceptionHandler exception, WebRequest request){
        //ErrorDetailsDto detailsDto= new ErrorDetailsDto(new Date(), exception.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorResposeBodyBuilder(new Date(), HttpStatus.BAD_REQUEST,exception.getMessage(),request.getDescription(false)),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomExceptionHandler.class)
    public final ResponseEntity<ErrorDetailsDto> handleException(CustomExceptionHandler exception, WebRequest request){
        return new ResponseEntity<>(errorResposeBodyBuilder(new Date(),HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), request.getDescription(false)), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ErrorDetailsDto errorResposeBodyBuilder(Date date, HttpStatus httpStatus, String message, String url){
        return ErrorDetailsDto.builder()
                .details(url)
                .status(httpStatus)
                .message(message)
                .timeStamp(date).build();
    }
}

