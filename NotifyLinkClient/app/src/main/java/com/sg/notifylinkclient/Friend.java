package com.sg.notifylinkclient;

import com.google.gson.annotations.SerializedName;

public class Friend {
    @SerializedName("id")
    public  long id;
    @SerializedName("userAlfa")
    public String userAlfa;
    @SerializedName("name")
    public  String name;
    @SerializedName("consent")
    public  boolean consent;
}
