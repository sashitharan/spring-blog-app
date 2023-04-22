package com.springboot.blog.springbootblogrestapi.service;
import com.springboot.blog.springbootblogrestapi.payload.PostDTO;
import com.springboot.blog.springbootblogrestapi.payload.PostResponse;

import java.util.List;

public interface PostService {

    PostDTO createPost(PostDTO postDTO);

    List<PostDTO> getAllPosts();

    PostResponse getAllPostsByPagination(int pageNumber, int pageSize,String sortBy, String sortDir);

    PostDTO getPostById(long id);

    PostDTO updatePostById(PostDTO postDTO, long id);

    String deletePostById(long id);


}






/*
In object-oriented programming, an interface is a contract that defines a set of methods that a class should implement.
An interface provides a way for different classes to have a common behavior, without necessarily sharing the same implementation details.

The benefits of using an interface class include:
Abstraction: An interface allows a programmer to create an abstraction over the implementation details of a class.
This means that the user of the interface only needs to know what the interface can do and not how it does it.
This makes the code more modular and easier to maintain.

Polymorphism:
Interfaces allow for polymorphism, which means that objects of different classes can be treated as if they belong to the same class, as long as they implement the same interface.
This makes it possible to write code that is more generic and reusable.

Loose coupling:
Using interfaces reduces the coupling between different classes.
This means that changes to one class do not necessarily affect other classes that use the same interface.
This makes the code more flexible and easier to modify.

Multiple inheritance:
Some programming languages do not allow multiple inheritance of implementation details from multiple classes, but they do allow implementation of multiple interfaces.
This allows a class to implement the behavior of multiple interfaces, without inheriting implementation details from multiple classes.
Overall, using interface classes can help create more flexible, maintainable, and reusable code in object-oriented programming.

 */