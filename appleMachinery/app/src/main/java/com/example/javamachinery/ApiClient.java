package com.example.javamachinery;

/*
 * This class creates and defines a Retrofit object in Android Studio. This is created to
 * deconstruct the JSON objects created by the Spring Boot API so that Studio can understand and
 * manipulate the data. The base URL is the root of the backend, which is literally just the
 * local address of the Android Emulator.
 * **/

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:8080/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}

