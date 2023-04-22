package com.springboot.blog.springbootblogrestapi.exception;

import com.springboot.blog.springbootblogrestapi.entity.ErrorDetails;
import com.springboot.blog.springbootblogrestapi.payload.ErrorDetailsDTO;
import com.springboot.blog.springbootblogrestapi.repository.ErrorDetailRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice //we use this annotation to handle exceptions globally.
public class GlobalExceptionHandler {

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


}
