package com.example.tuseventos.requests;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface NoticiasService {

    @POST("eventos/get_articles/")
    @FormUrlEncoded
    Call<String> get_articles(@Field("data") JSONObject data);

    @POST("eventos/add_favorite_article/")
    @FormUrlEncoded
    Call<String> add_favorite_article(@Field("data") JSONObject data);

    @POST("eventos/remove_favorite_article/")
    @FormUrlEncoded
    Call<String> remove_favorite_article(@Field("data") JSONObject data);

    @POST("eventos/add_remindme_article/")
    @FormUrlEncoded
    Call<String> add_remindme_article(@Field("data") JSONObject data);

    @POST("eventos/remove_remindme_article/")
    @FormUrlEncoded
    Call<String> remove_remindme_article(@Field("data") JSONObject data);

    @POST("eventos/get_article_types/")
    @FormUrlEncoded
    Call<String> get_article_types(@Field("data") JSONObject data);

    @POST("eventos/get_favorite_articles/")
    @FormUrlEncoded
    Call<String> get_favorite_articles(@Field("data") JSONObject data);

}
