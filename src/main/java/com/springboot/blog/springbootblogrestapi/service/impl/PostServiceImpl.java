package com.springboot.blog.springbootblogrestapi.service.impl;

import com.springboot.blog.springbootblogrestapi.entity.Post;
import com.springboot.blog.springbootblogrestapi.exception.ResourceNotFoundException;
import com.springboot.blog.springbootblogrestapi.payload.PostDTO;
import com.springboot.blog.springbootblogrestapi.repository.PostRepository;
import com.springboot.blog.springbootblogrestapi.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

    @Autowired
//    Constructor based dependency injection (Best Practice),
//    If have only 1 constructor in the class, we can omit the @Autowired.
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostDTO createPost(PostDTO postDTO) {

        //Convert DTO to entity
        Post post = mapPostDTOtoPostEntity(postDTO);
        Post savedPostInDatabase = postRepository.save(post); // save to Repository

        //convert Entity to DTO to pass controller to pass to UI
        return mapPostEntityFromDatabaseToPostDTO(savedPostInDatabase);
    }

    @Override
    public List<PostDTO> getAllPosts() {
        List<Post> postsFromDatabase = postRepository.findAll();

        //convert list of posts to a list of postDTOs
        List<PostDTO> collect = postsFromDatabase.stream().map(post -> mapPostEntityFromDatabaseToPostDTO(post)).collect(Collectors.toList());
        return collect;
    }


    @Override
    public PostDTO getPostById(long id) {
        Post post  = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post","id",id));
        return mapPostEntityFromDatabaseToPostDTO(post);


    }

    @Override
    public PostDTO updatePostById(PostDTO postDTO, long id) {
        //get post entity with the id from the database
        Post post  = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post","id",id));

        // update that field from the database with the id.
        post.setContent(postDTO.getContent());
        post.setTitle(postDTO.getTitle());
        post.setDescription(postDTO.getDescription());
        Post savedPost = postRepository.save(post);
        return mapPostEntityFromDatabaseToPostDTO(savedPost);

    }


    @Override
    public String deletePostById(long id) {
        //get post entity with the id from the database
        Post postToBeDeleted  = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post","id",id));
        System.out.println(postToBeDeleted.toStringForPostDeletion());

        // delete that field from the database with the id.

        postRepository.delete(postToBeDeleted);
        System.out.println("Post Deleted Successfully");
        return "Post Deleted Successfully";

    }


    private PostDTO mapPostEntityFromDatabaseToPostDTO(Post postFromDatabase){
        //Convert post entity to DTO
        PostDTO postDTO = new PostDTO();
        postDTO.setId(postFromDatabase.getId());
        postDTO.setContent(postFromDatabase.getContent());
        postDTO.setDescription(postFromDatabase.getDescription());
        postDTO.setTitle(postFromDatabase.getTitle());
        System.out.println(postDTO.toString());
        return postDTO;
    }


    private Post mapPostDTOtoPostEntity(PostDTO postDTOfromUI) {
        //Convert DTO to post entity
        Post post = new Post();
        post.setTitle(postDTOfromUI.getTitle());
        post.setDescription(postDTOfromUI.getDescription());
        post.setTitle(postDTOfromUI.getTitle());
        post.setContent(postDTOfromUI.getContent());
        System.out.println(post.toString());
        return post;
    }

}
