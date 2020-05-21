package com.example.ppm.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice//all exceptions before being thrown comes here for aadvice
@RestController
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

}
