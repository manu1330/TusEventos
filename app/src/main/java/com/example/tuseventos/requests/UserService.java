package com.example.tuseventos.requests;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UserService {

    @POST("api/social/login/")
    @FormUrlEncoded
    Call<String> login(@Field("data") JSONObject data);

    @POST("api/social/logout/")
    @FormUrlEncoded
    Call<String> logout(@Field("data") JSONObject data);

    @POST("api/social/register/")
    @FormUrlEncoded
    Call<String> register(@Field("data") JSONObject data);

}
