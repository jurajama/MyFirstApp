package com.example.myfirstapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("/todos")
        // API's endpoints
    Call<List<ApiTestResponse>> getTodoList();
}
