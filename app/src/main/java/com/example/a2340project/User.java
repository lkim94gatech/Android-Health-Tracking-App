package com.example.a2340project;

/**
 * this class creates user objects which can be stored in the Firebase
 */
public class User {
    private String username;
    private String password;

    // constructor
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    // i did not add getters and setters because of security. I'm not sure if they are needed.
}
