package com.example.project1.dto;

public class CreateUserRequest {
    private String username;
    private String email;
    private String password;

    public CreateUserRequest() {}

    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }

    public void setUsername(String username) { this.username = username; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
}
