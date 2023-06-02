package com.springboot.blog.springbootblogrestapi.controller;

import com.springboot.blog.springbootblogrestapi.payload.PostDTO;
import com.springboot.blog.springbootblogrestapi.payload.PostDTOV2;
import com.springboot.blog.springbootblogrestapi.payload.PostResponse;
import com.springboot.blog.springbootblogrestapi.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.springboot.blog.springbootblogrestapi.utils.AppConstants.*;

@RestController
@RequestMapping()
@Tag(name = "CRUD REST APIs For Post Resources")

public class PostController {

    private PostService postService;
//    if we use interface, then we are making implementation as loose coupling instead of tight coupling.

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    //    http://localhost:8080/api/posts/createPost
    @SecurityRequirement(name="Bearer Authentication")
    @PreAuthorize("hasRole('ADMIN')") //only admin can access this api
    @PostMapping("/api/v1/posts/createPost")
    @Operation(summary = "Create Post REST API",description = "Create a Post to save post into Database")
    @ApiResponse(responseCode = "201 created",description = "HTTP Status 201 Created")
//    RequestBody converts JSON to Java object.
    public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostDTO postDTO) {

        PostDTO createdPost = postService.createPost(postDTO);
        return ResponseEntity.ok(createdPost);
//       return new ResponseEntity<>(postService.createPost(postDTO), HttpStatus.CREATED);
    }


    @GetMapping("/api/v1/posts/getAll")
    public List<PostDTO> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/api/v1/posts/getAllByPage")
    public PostResponse getAllPostsByPagination(
            @RequestParam(value = "pageNo", defaultValue = DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = DEFAULT_PAGE_SORTED_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = DEFAULT_PAGE_SORTED_DIR, required = false) String sortDir

    ) {
        return postService.getAllPostsByPagination(pageNo, pageSize, sortBy, sortDir);
    }


    //        Get post by id
//    http://{{localhost}}/api/posts/1
    @GetMapping("/api/v1/posts/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable(name = "id") long id) {
        return ResponseEntity.ok(postService.getPostById(id));

    }

    @GetMapping("/api/v2/posts/{id}")
    public ResponseEntity<PostDTOV2> getPostByIdV2(@PathVariable(name = "id") long id) {
        PostDTO postDTO = postService.getPostById(id);
        PostDTOV2 postDTOV2 = new PostDTOV2();
        postDTOV2.setId(postDTO.getId());
        postDTOV2.setTitle(postDTO.getTitle());
        postDTOV2.setContent(postDTO.getContent());
        postDTOV2.setDescription(postDTO.getDescription());

        List<String> tags = new ArrayList<>();
        tags.add("Java");
        tags.add("Springboot");
        tags.add("ReactJs");
        postDTOV2.setTags(tags);
        return ResponseEntity.ok(postDTOV2);

    }


    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/api/v1/posts/{id}")
    public ResponseEntity<PostDTO> updatePostById(@RequestBody PostDTO postDTO, @PathVariable(name = "id") long id) {
        return ResponseEntity.ok(postService.updatePostById(postDTO, id));

    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/api/v1/posts/{id}")
    public ResponseEntity<String> deletePostById(@PathVariable(name = "id") long id) {
        String s = postService.deletePostById(id);
        return new ResponseEntity<>(s, HttpStatus.OK);

    }

    @GetMapping("/api/v1/posts/getPostByCategories/{id}")
    public ResponseEntity<List<PostDTO>> getPostByCategories(@PathVariable("id") Long categoryId){
        List<PostDTO> postByCategory = postService.getPostByCategory(categoryId);
        return ResponseEntity.ok(postByCategory);

    }


}
