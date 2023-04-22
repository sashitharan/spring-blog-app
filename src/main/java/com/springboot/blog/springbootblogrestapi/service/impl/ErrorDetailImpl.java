package com.springboot.blog.springbootblogrestapi.service.impl;

import com.springboot.blog.springbootblogrestapi.entity.ErrorDetails;
import com.springboot.blog.springbootblogrestapi.payload.ErrorDetailsDTO;
import com.springboot.blog.springbootblogrestapi.repository.ErrorDetailRepository;
import com.springboot.blog.springbootblogrestapi.service.ErrorDetailService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ErrorDetailImpl implements ErrorDetailService {

    private ErrorDetailRepository errorDetailRepository;
    private ModelMapper modelMapper;

    public ErrorDetailImpl(ErrorDetailRepository errorDetailRepository, ModelMapper modelMapper) {
        this.errorDetailRepository = errorDetailRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ErrorDetailsDTO> getAllError() {
        List<ErrorDetails> postsFromDatabase = errorDetailRepository.findAll();

        //convert to DTOs
        List<ErrorDetailsDTO> collect = postsFromDatabase.stream().map(post -> mapPostEntityFromDatabaseToPostDTO(post)).collect(Collectors.toList());
        return collect;
    }

    private ErrorDetailsDTO mapPostEntityFromDatabaseToPostDTO(ErrorDetails postFromDatabase){
        //Convert post entity to DTO
        ErrorDetailsDTO errorDetailsDTO = modelMapper.map(postFromDatabase, ErrorDetailsDTO.class);
        System.out.println(errorDetailsDTO.toString());
        return errorDetailsDTO;
    }


    private ErrorDetails mapPostDTOtoPostEntity(ErrorDetailsDTO postDTOfromUI) {
        //Convert DTO to post entity
        ErrorDetails errorDetails = modelMapper.map(postDTOfromUI, ErrorDetails.class);
        System.out.println(errorDetails.toString());
        return errorDetails;
    }


}

