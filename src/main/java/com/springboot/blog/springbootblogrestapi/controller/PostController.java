package com.springboot.blog.springbootblogrestapi.controller;

import com.springboot.blog.springbootblogrestapi.payload.PostDTO;
import com.springboot.blog.springbootblogrestapi.payload.PostResponse;
import com.springboot.blog.springbootblogrestapi.service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.springboot.blog.springbootblogrestapi.utils.AppConstants.*;

@RestController
@RequestMapping("api/posts")
public class PostController {

    private PostService postService;
//    if we use interface, then we are making implementation as loose coupling instead of tight coupling.

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }


    //    http://localhost:8080/api/posts/createPost
    @PreAuthorize("hasRole('ADMIN')") //only admin can access this api
    @PostMapping("createPost")
//    RequestBody converts JSON to Java object.
    public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostDTO postDTO) {

        PostDTO createdPost = postService.createPost(postDTO);
        return ResponseEntity.ok(createdPost);
//       return new ResponseEntity<>(postService.createPost(postDTO), HttpStatus.CREATED);
    }


    @GetMapping("getAll")
    public List<PostDTO> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("getAllByPage")
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
    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable(name = "id") long id) {
        return ResponseEntity.ok(postService.getPostById(id));

    }


    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PostDTO> updatePostById(@RequestBody PostDTO postDTO, @PathVariable(name = "id") long id) {
        return ResponseEntity.ok(postService.updatePostById(postDTO, id));

    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePostById(@PathVariable(name = "id") long id) {
        String s = postService.deletePostById(id);
        return new ResponseEntity<>(s, HttpStatus.OK);

    }

    @GetMapping("/getPostByCategories/{id}")
    public ResponseEntity<List<PostDTO>> getPostByCategories(@PathVariable("id") Long categoryId){
        List<PostDTO> postByCategory = postService.getPostByCategory(categoryId);
        return ResponseEntity.ok(postByCategory);

    }


}
