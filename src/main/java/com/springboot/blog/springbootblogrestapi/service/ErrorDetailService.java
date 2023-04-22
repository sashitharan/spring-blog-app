package com.springboot.blog.springbootblogrestapi.service;

import com.springboot.blog.springbootblogrestapi.entity.ErrorDetails;
import com.springboot.blog.springbootblogrestapi.payload.ErrorDetailsDTO;

import java.util.List;

public interface ErrorDetailService {

    List<ErrorDetailsDTO> getAllError();
}
