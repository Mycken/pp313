package com.example.pp313.model;


import javax.persistence.*;
import java.util.List;

public class UserDTO {

    private long id;
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private List<String> roles;

    public UserDTO() {
    }

    public List<String> getRoles() {
        return roles;
    }

    public UserDTO(long id, String username, String firstName, String lastName, String email, List<String> roles) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.roles = roles;
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {return password;}

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
