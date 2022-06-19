package com.example.tuseventos.requests;

import org.json.JSONObject;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UserService {

    @POST("usuarios/login/")
    @FormUrlEncoded
    Call<String> login(@Field("data") JSONObject data);

    @POST("usuarios/logout/")
    @FormUrlEncoded
    Call<String> logout(@Field("data") JSONObject data);

    @POST("usuarios/register/")
    @FormUrlEncoded
    Call<String> register(@Field("data") JSONObject data);

    @POST("/usuarios/change_credentials/")
    @FormUrlEncoded
    Call<String> change_credentials(@Field("data") JSONObject data);

    @POST("/usuarios/change_profile_picture/")
    @Multipart
    Call<String> change_profile_picture(
            @Part MultipartBody.Part data,
            @Part MultipartBody.Part file
    );

    @POST("/usuarios/remove_profile_picture/")
    @FormUrlEncoded
    Call<String> remove_profile_picture(@Field("data") JSONObject data);

    @POST("/usuarios/get_self_profile_picture/")
    @FormUrlEncoded
    Call<String> get_self_profile_picture(@Field("data") JSONObject data);
}
