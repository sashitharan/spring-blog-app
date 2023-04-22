package com.springboot.blog.springbootblogrestapi.controller;

import com.springboot.blog.springbootblogrestapi.payload.ErrorDetailsDTO;
import com.springboot.blog.springbootblogrestapi.service.ErrorDetailService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/errors")
public class ErrorDetailController {

    private ErrorDetailService errorDetailService;

    public ErrorDetailController(ErrorDetailService errorDetailService) {
        this.errorDetailService = errorDetailService;
    }

    @GetMapping("getAll")
    public List<ErrorDetailsDTO> getAllError() {
        return errorDetailService.getAllError();
    }
}
