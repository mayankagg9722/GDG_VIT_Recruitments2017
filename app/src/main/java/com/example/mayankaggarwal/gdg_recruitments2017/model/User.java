package com.example.mayankaggarwal.gdg_recruitments2017.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by mayankaggarwal on 27/01/17.
 */

public class User extends RealmObject {

    @PrimaryKey
    private int id;

    private String website;

    private String username;

    private String password;


    // Standard getters & setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}