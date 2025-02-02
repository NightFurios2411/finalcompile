package com.example.pratical_testing_1.dataBind.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class User {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String username;
    private String password;
    private String email;
    private String profilePic; // Store URI or Base64 String

    // Constructor
    public User(String username, String password, String email, String profilePic) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.profilePic = profilePic;
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getProfilePic() { return profilePic; }
    public void setProfilePic(String profilePic) { this.profilePic = profilePic; }
}

