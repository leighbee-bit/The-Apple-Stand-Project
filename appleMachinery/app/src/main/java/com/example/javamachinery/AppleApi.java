package com.example.javamachinery;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface AppleApi {
    @GET("/api/apples")
    Call<List<Apple>> getAllApples();

    @POST("/api/apples")
    Call<Apple> addApple(@Body Apple apple);

    @DELETE("/api/apples/{id}")
    Call<Void> deleteApple(@Path("id") Long id);

    @PUT("/api/apples/{id}")
    Call<Apple> updateApple(
            @Path("id") Long id,
            @Body Apple apple
    );
}