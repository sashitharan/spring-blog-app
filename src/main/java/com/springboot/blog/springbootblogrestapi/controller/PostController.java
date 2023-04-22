package com.springboot.blog.springbootblogrestapi.controller;

import com.springboot.blog.springbootblogrestapi.payload.PostDTO;
import com.springboot.blog.springbootblogrestapi.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @PostMapping("createPost")
//    RequestBody converts JSON to Java object.
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO) {

        PostDTO createdPost = postService.createPost(postDTO);
        return ResponseEntity.ok(createdPost);
//       return new ResponseEntity<>(postService.createPost(postDTO), HttpStatus.CREATED);
    }

    @GetMapping("getAll")
    public List<PostDTO> getAllPosts() {
        return postService.getAllPosts();
    }


//     Get post by id
//    http://{{localhost}}/api/posts/1
    @GetMapping("/{id}")
    public ResponseEntity<PostDTO>getPostById(@PathVariable(name="id") long id){
        return ResponseEntity.ok(postService.getPostById(id));

    }

//    http://{{localhost}}/api/posts/2
    @PutMapping("/{id}")
    public ResponseEntity<PostDTO>updatePostById(@RequestBody PostDTO postDTO, @PathVariable(name="id") long id){
        return ResponseEntity.ok(postService.updatePostById(postDTO,id));

    }

//  http://{{localhost}}/api/posts/1
    @DeleteMapping("/{id}")
    public ResponseEntity<String>deletePostById(@PathVariable(name="id") long id){
        String s = postService.deletePostById(id);
        return new ResponseEntity<>(s, HttpStatus.OK);

    }



}
