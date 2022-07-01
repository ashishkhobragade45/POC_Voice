package com.example.poc_voice.network;

import com.example.poc_voice.model.ModelClass;
import com.example.poc_voice.model.M_Token;
import com.example.poc_voice.model.M_main;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api{
    String BASE_URL = "https://api-staging.dabadu.ai/";
    @GET("marvel")
    Call<ModelClass> getsuperHeroes();

    @Headers("Content-Type: text/plain")
    @POST("accessToken") // specify the sub url for our base url
    Call<String> gettoken(@Query("identity") String user);


    @POST("/dealers/api/auth/signin") // specify the sub url for our base url
    Call<M_main> gettokennew(@Body JsonObject user);


    @GET("messaging/api/phone/generate") // specify the sub url for our base url
    Call<M_Token> gettokennewsub(@Header("Authorization")String value);




}
