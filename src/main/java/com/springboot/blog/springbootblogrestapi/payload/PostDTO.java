package com.springboot.blog.springbootblogrestapi.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
@Schema(description = "PostDTO Model Information")
public class PostDTO {

    private Long id;

    @Schema(description = "Blog Post Title")
    @NotEmpty
    @Size(min=2,message = "Post title must have at least 2 characters")
    private String title;

    @Schema(description = "Blog Post Description")
    @NotEmpty
    @Size(min = 10, message = "Post Description should have at least 10 characters")
    private String description;

    @Schema(description = "Blog Post Content")
    @NotEmpty
    @NotNull
    private String content;

    @Schema(description = "Blog Post Comments")
    //display comments in the post DTO
    private Set<CommentDTO>comments;

    @Schema(description = "Blog Post Category")
    private Long categoryId;

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
