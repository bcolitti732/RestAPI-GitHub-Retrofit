package com.vogella.java.retrofitgerrit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GitHubAPI {
    @GET("repos")
    Call<List<Change>> loadChanges(@Query("q") String status);
}
