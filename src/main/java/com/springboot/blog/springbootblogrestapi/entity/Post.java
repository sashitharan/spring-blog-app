package com.springboot.blog.springbootblogrestapi.entity;

import lombok.*;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "posts", uniqueConstraints = {@UniqueConstraint(columnNames = {"title"})})

//If don't provide @Table, then JPA will automatically provide name of table as the class.
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    //if don't provide then JPA is smart enough to take the name from the parameters.
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "content", nullable = false)
    private String content;


    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL,orphanRemoval = true)
    //bidirectional (only one relationship with post)
    //set to prevent duplicates. (list allow duplicates)
    private Set<Comment>comments = new HashSet<>(); //PostDTO will have comments too, and modelmapper helps us map it.


    @Override
    public String toString() {
        return "Convert DTO to Post Entity ----> Post{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    public String toStringForPostDeletion() {
        return "Deleting this post...{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
