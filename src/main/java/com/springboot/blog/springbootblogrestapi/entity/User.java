package com.springboot.blog.springbootblogrestapi.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(nullable = false,unique = true)
    private String username;

    @Column(nullable = false,unique = true)
    private String email;

    @Column(nullable = false)
    private String password;


//    Fetch type EAGER means whenever we load user entity, we also load its role
//    Cascade type all means it defines the set of cascadable operations that are propagated to the associated entity.
//    The value cascade=ALL is equivalent to cascade={PERSIST, MERGE, REMOVE, REFRESH, DETACH}.
//    So when we do any actions to parent, the child also will change.

//    @JoinTable = create a third table = join table with name ("user_roles")
//    this annotation has join column attribute which creates the new column (user_id) which is a foreign key which is referenced to the id of this Users table.

//    To introduce another foreign key to this table, we use the inverseJoinColumn where the (foreign key) role_id of the "user_roles" table is referenced from this table id as primary key.

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id",referencedColumnName = "id")
    )
    private Set<Role> roles;

}
