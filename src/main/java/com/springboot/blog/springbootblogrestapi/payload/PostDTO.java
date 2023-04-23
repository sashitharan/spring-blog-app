package com.springboot.blog.springbootblogrestapi.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class PostDTO {

    private Long id;

    @NotEmpty
    @Size(min=2,message = "Post title must have at least 2 characters")
    private String title;

    @NotEmpty
    @Size(min = 10, message = "Post Description should have at least 10 characters")
    private String description;

    @NotEmpty
    @NotNull
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
