package com.tin.user.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class User extends BaseModel {

    private String username;
    private String email;
    private String password;

    @OneToMany(cascade = CascadeType.ALL,  fetch = FetchType.EAGER)
    private List<Session> sessions;
}
