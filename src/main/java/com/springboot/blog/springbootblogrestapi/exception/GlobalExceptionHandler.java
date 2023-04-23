package com.springboot.blog.springbootblogrestapi.exception;

import com.springboot.blog.springbootblogrestapi.entity.ErrorDetails;
import com.springboot.blog.springbootblogrestapi.payload.ErrorDetailsDTO;
import com.springboot.blog.springbootblogrestapi.repository.ErrorDetailRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice //we use this annotation to handle exceptions globally.
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private ErrorDetailRepository errorDetailRepository;
    private ModelMapper modelMapper;

    public GlobalExceptionHandler(ErrorDetailRepository errorDetailRepository, ModelMapper modelMapper) {
        this.errorDetailRepository = errorDetailRepository;
        this.modelMapper = modelMapper;
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetailsDTO> handleResourceNotFoundException(ResourceNotFoundException exception, WebRequest webRequest) {
        ErrorDetailsDTO errorDetailsDTO = new ErrorDetailsDTO(new Date(), exception.getMessage(), webRequest.getDescription(false));
        System.out.println(errorDetailsDTO.toString());

        // Convert ErrorDetailsDTO to ErrorDetails
        ErrorDetails errorDetails = modelMapper.map(errorDetailsDTO, ErrorDetails.class);

        // Save error details to repository
        errorDetailRepository.save(errorDetails);

        return new ResponseEntity<>(modelMapper.map(errorDetailsDTO, ErrorDetailsDTO.class), HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(BlogAPIException.class)
    public ResponseEntity<ErrorDetailsDTO> handleBlogAPIException(BlogAPIException exception, WebRequest webRequest) {
        ErrorDetailsDTO errorDetailsDTO = new ErrorDetailsDTO(new Date(), exception.getMessage(), webRequest.getDescription(false));

        // Convert ErrorDetailsDTO to ErrorDetails
        ErrorDetails errorDetails = modelMapper.map(errorDetailsDTO, ErrorDetails.class);

        // Save error details to repository
        errorDetailRepository.save(errorDetails);

        return new ResponseEntity<>(errorDetailsDTO, HttpStatus.BAD_REQUEST);
    }


    // global exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetailsDTO> handleGlobalException(Exception exception, WebRequest webRequest) {
        ErrorDetailsDTO errorDetailsDTO = new ErrorDetailsDTO(new Date(), exception.getMessage(), webRequest.getDescription(false));

        // Convert ErrorDetailsDTO to ErrorDetails
        ErrorDetails errorDetails = modelMapper.map(errorDetailsDTO, ErrorDetails.class);

        // Save error details to repository
        errorDetailRepository.save(errorDetails);

        return new ResponseEntity<>(errorDetailsDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    /*
    {
        "timestamp": "2023-04-22T15:18:21.295+00:00",
        "message": "Failed to convert value of type 'java.lang.String' to required type 'long'; For input string: \"292\"\"",
        "details": "uri=/api/posts/292%22"
    }
     */


    // 1st Approach
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult()
                .getAllErrors()
                .forEach((error) -> {
                    String fieldName = ((FieldError) error).getField();
                    String message = error.getDefaultMessage();
                    errors.put(fieldName, message);
                });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }


    // 2nd Approach
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception, WebRequest webRequest){
//        Map<String, String> errors = new HashMap<>();
//        exception.getBindingResult()
//                .getAllErrors()
//                .forEach((error) -> {
//                    String fieldName = ((FieldError) error).getField();
//                    String message = error.getDefaultMessage();
//                    errors.put(fieldName, message);
//                });
//        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
//
//    }




}
