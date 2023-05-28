package com.springboot.blog.springbootblogrestapi.service.impl;

import com.springboot.blog.springbootblogrestapi.entity.Category;
import com.springboot.blog.springbootblogrestapi.entity.Comment;
import com.springboot.blog.springbootblogrestapi.entity.Post;
import com.springboot.blog.springbootblogrestapi.exception.ResourceNotFoundException;
import com.springboot.blog.springbootblogrestapi.payload.PostDTO;
import com.springboot.blog.springbootblogrestapi.payload.PostResponse;
import com.springboot.blog.springbootblogrestapi.repository.CategoryRepository;
import com.springboot.blog.springbootblogrestapi.repository.PostRepository;
import com.springboot.blog.springbootblogrestapi.service.PostService;
import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;
    private ModelMapper mapper;

    private CategoryRepository categoryRepository;


//        Constructor based dependency injection (Best Practice),
//    If you have only 1 constructor in the class, we can omit the @Autowired.
    public PostServiceImpl(PostRepository postRepository, ModelMapper mapper, CategoryRepository categoryRepository) {
        this.postRepository = postRepository;
        this.mapper = mapper;
        this.categoryRepository = categoryRepository;
    }



    @Override
    public PostDTO createPost(PostDTO postDTO) {

        Category category = categoryRepository.findById(postDTO.getCategoryId()).orElseThrow(() -> new ResourceNotFoundException("Category", "id", postDTO.getCategoryId()));

        //Convert DTO to entity
        Post post = mapPostDTOtoPostEntity(postDTO);
        post.setCategory(category);
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
    public PostResponse getAllPostsByPagination(int pageNumber, int pageSize, String sortBy, String sortDir) {

        //Sort object in ascending and or descending order
        Sort sortOrder = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending(): Sort.by(sortBy).descending();


//        Create Pageable instance
//        Pageable pageable = PageRequest.of(pageNumber,pageSize);
//        Pageable pageable = PageRequest.of(pageNumber,pageSize, Sort.by(sortBy));
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sortOrder);

        Page<Post> postsFromDatabase = postRepository.findAll(pageable);

        //Get content from page objects
        List<Post> listOfPosts = postsFromDatabase.getContent();

        //convert list of posts to a list of postDTOs
        List<PostDTO> collectedPostDTOsList = listOfPosts.stream().map(post -> mapPostEntityFromDatabaseToPostDTO(post)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(collectedPostDTOsList);
        postResponse.setPageNo(postsFromDatabase.getNumber());
        postResponse.setPageSize(postsFromDatabase.getSize());
        postResponse.setTotalPages(postsFromDatabase.getTotalPages());
        postResponse.setTotalElements(postsFromDatabase.getTotalElements());
        postResponse.setSortOrder(sortDir);
        postResponse.setLast(postsFromDatabase.isLast());
        return postResponse;


        /*
        Example
        GET http://{{localhost}}/api/posts/getAllByPage?pageNo=1&pageSize=2
        {
              {
                "content": [
                    {
                        "id": 13,
                        "title": "Test 1",
                        "description": "Dogs and Cats",
                        "content": "Animals"
                    },
                    {
                        "id": 14,
                        "title": "Test 123",
                        "description": "Dogs and Cats",
                        "content": "Animals"
                    }
                ],
                "pageNo": 1,
                "pageSize": 2,
                "totalElements": 14,
                "totalPages": 7,
                "last": false
            }
        }
         */

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

        Category category = categoryRepository.findById(postDTO.getCategoryId()).orElseThrow(() -> new ResourceNotFoundException("Category", "id", postDTO.getId()));

        // update that field from the database with the id.
        post.setContent(postDTO.getContent());
        post.setTitle(postDTO.getTitle());
        post.setDescription(postDTO.getDescription());
        post.setCategory(category);
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

    @Override
    public List<PostDTO> getPostByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","id",categoryId));
        List<Post>posts = postRepository.findByCategoryId(category.getId());

        //convert list of post jpa entities into list of post DTO.
        return posts.stream().map((postJPAEntities) -> mapper.map(postJPAEntities, PostDTO.class)).collect(Collectors.toList());
    }


    private PostDTO mapPostEntityFromDatabaseToPostDTO(Post postFromDatabase){
        //Convert post entity to DTO
        PostDTO postDTO = mapper.map(postFromDatabase, PostDTO.class);

//        PostDTO postDTO = new PostDTO();
//        postDTO.setId(postFromDatabase.getId());
//        postDTO.setContent(postFromDatabase.getContent());
//        postDTO.setDescription(postFromDatabase.getDescription());
//        postDTO.setTitle(postFromDatabase.getTitle());
        System.out.println(postDTO.toString());
        return postDTO;
    }


    private Post mapPostDTOtoPostEntity(PostDTO postDTOfromUI) {
        //Convert DTO to post entity
        Post post = mapper.map(postDTOfromUI, Post.class);

//        Post post = new Post();
//        post.setTitle(postDTOfromUI.getTitle());
//        post.setDescription(postDTOfromUI.getDescription());
//        post.setTitle(postDTOfromUI.getTitle());
//        post.setContent(postDTOfromUI.getContent());
        System.out.println(post.toString());
        return post;
    }

}
