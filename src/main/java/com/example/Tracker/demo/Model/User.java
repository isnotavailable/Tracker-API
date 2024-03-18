package com.example.Tracker.demo.Model;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private  String username;

    @OneToOne(mappedBy = "user")
    private Device device;

    public User(){}

    @JsonCreator
    public User(@JsonProperty("username") String username) {
        this.username = username;
    }

    public User(String username, Device device) {
        this.username = username;
        this.device = device;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
