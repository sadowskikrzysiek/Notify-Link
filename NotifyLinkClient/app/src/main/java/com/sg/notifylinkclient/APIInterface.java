package com.sg.notifylinkclient;


import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

interface APIInterface {

    @POST("api/restLogin")
    Call<UserToken> doLogin(@Body User user);

    @POST("api/restGetNotifications")
    Call<List<Notify>> doGetNotifications(@Body UserToken userToken);
}