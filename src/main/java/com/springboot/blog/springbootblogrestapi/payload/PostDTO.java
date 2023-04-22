package com.springboot.blog.springbootblogrestapi.payload;

import lombok.Data;

@Data
public class PostDTO {

    private Long id;
    private String title;
    private String description;
    private String content;

    @Override
    public String toString() {
        return "Convert post entity to DTO ----> PostDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
