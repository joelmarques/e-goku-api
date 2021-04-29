package br.com.abasteceai.user;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Getter@Setter
@EqualsAndHashCode(of = "username")
@Document(collection = "users")
public class UserModel {

    @Id
    private String id;

    @CreatedDate
    private LocalDateTime createdDate;

    @CreatedBy
    private String createdBy;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    @LastModifiedBy
    private String lastModifiedBy;

    private String name;
    private String email;
    private String phone;

    @Indexed(name = "username_idx", unique = true)
    private String username;
    private String password;
    private boolean active;
    private List<String> roles;
}