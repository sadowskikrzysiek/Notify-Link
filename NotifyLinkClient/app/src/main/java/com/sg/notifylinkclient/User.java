package com.sg.notifylinkclient;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("id")
    public Long id;
    @SerializedName("username")
    public String username;
    @SerializedName("password")
    public String password;
    @SerializedName("description")
    public String description;
}
