package com.sg.notifylinkclient;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Notify {
    @SerializedName("id")
    public Long id;
    @SerializedName("name")
    public String name;
    @SerializedName("createdAt")
    public String createdAt;
    @SerializedName("notificationDate")
    public String notificationDate;
    @SerializedName("description")
    public String description;
    @SerializedName("creator")
    public String creator;
    @SerializedName("active")
    public boolean active;
    @SerializedName("friends")
    public List<Friend> friends;

}
