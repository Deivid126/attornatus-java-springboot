package com.attornatus.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

@ControllerAdvice(basePackages = "com.attornatus.demo.controller")
public class ApiControllerAdvice {
    @ResponseBody
    @ExceptionHandler(PessoaNotFoundException.class)
    public ResponseEntity<MessageExceptionHandler> pessoanotfound(PessoaNotFoundException pessoaNotFoundException){
        MessageExceptionHandler error = new MessageExceptionHandler(
                new Date(), HttpStatus.NOT_FOUND.value(),"Pessoa não encontrada");

        return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MessageExceptionHandler> argumentinvalid(MethodArgumentNotValidException exception){
        BindingResult result = exception.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();

        StringBuilder sb = new StringBuilder("Os campos seguintes não podem ser nulos");
        for(FieldError fieldError : fieldErrors){
            sb.append(" | ");
            sb.append(" -> ");
            sb.append(fieldError.getField());
            sb.append(" <- ");
        }

        MessageExceptionHandler error = new MessageExceptionHandler(new Date(),HttpStatus.BAD_REQUEST.value(), sb.toString());

        return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
    }
}
