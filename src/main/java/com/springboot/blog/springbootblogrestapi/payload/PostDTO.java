package com.springboot.blog.springbootblogrestapi.payload;

import lombok.Data;

import java.util.Set;

@Data
public class PostDTO {

    private Long id;
    private String title;
    private String description;
    private String content;

    //display comments in the post DTO
    private Set<CommentDTO>comments;

    @Override
    public String toString() {
        return "Convert post entity to DTO ----> PostDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", content='" + content + '\'' +
                ", comments=" + comments +
                '}';
    }
}
