package com.baldacchino_gambadoro.orders_management.Controller;

import com.baldacchino_gambadoro.orders_management.ClassSerializable.HttpError;
import com.baldacchino_gambadoro.orders_management.Exceptions.OrderNotFoundException;
import com.google.gson.Gson;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.naming.ServiceUnavailableException;
import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ControllerException {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value(value = "${KAFKA_TOPIC_LOGGING}")
    private String kafkaTopicLogging;

    //Error 405
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Object> methodArgumentNotValidException(HttpServletRequest request,
                                                                  HttpRequestMethodNotSupportedException ex) {
        HttpError error = new HttpError(HttpStatus.METHOD_NOT_ALLOWED, request);
        kafkaTemplate.send(kafkaTopicLogging, "http_errors", new Gson().toJson(error));
        return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
    }

    //Error 400
    @ExceptionHandler({MethodArgumentNotValidException.class, MissingServletRequestParameterException.class,
            TypeMismatchException.class})
    public ResponseEntity<Object> methodBadRequest(HttpServletRequest request){
        HttpError error = new HttpError(HttpStatus.BAD_REQUEST, request);
        kafkaTemplate.send(kafkaTopicLogging, "http_errors", new Gson().toJson(error));
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    //Error 500
    @ExceptionHandler(ConversionNotSupportedException.class)
    public ResponseEntity<Object> methodInternalServerError(HttpServletRequest request,
                                                            ConversionNotSupportedException ex){
        HttpError error = new HttpError(request, ex);
        kafkaTemplate.send(kafkaTopicLogging, "http_errors", new Gson().toJson(error));
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //Error 503
    @ExceptionHandler(ServiceUnavailableException.class)
    public ResponseEntity<Object> methodServiceUnavaliable(HttpServletRequest request,
                                                           ServiceUnavailableException ex){
        HttpError error = new HttpError(request, ex);
        kafkaTemplate.send(kafkaTopicLogging, "http_errors", new Gson().toJson(error));
        return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
    }

    //Error 404
    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<Object> methodNotFound(HttpServletRequest request, OrderNotFoundException ex){
        HttpError error = new HttpError(HttpStatus.NOT_FOUND, request);
        kafkaTemplate.send(kafkaTopicLogging, "http_errors", new Gson().toJson(error));
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
