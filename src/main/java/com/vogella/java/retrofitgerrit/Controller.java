package com.vogella.java.retrofitgerrit;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Controller implements Callback<List<Change>> {

    static final String BASE_URL = "https://api.github.com/users/";
    private String githubuser;

    public void start(String githubuser) {
        this.githubuser = githubuser + "/";
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL + this.githubuser)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        GitHubAPI gerritAPI = retrofit.create(GitHubAPI.class);

        Call<List<Change>> call = gerritAPI.loadChanges("status:open");
        call.enqueue(this);

    }

    @Override
    public void onResponse(Call<List<Change>> call, Response<List<Change>> response) {
        if (response.isSuccessful()) {
            List<Change> repositories = response.body();
            if (repositories != null && !repositories.isEmpty()) {
                for (Change repository : repositories) {
                    System.out.println("Repository name: " + repository.getName());
                    System.out.println("Description: " + repository.getDescription());
                    System.out.println("Stargazers: " + repository.getStargazers_Count());
                    System.out.println("-----------------------------------------");
                }
            } else {
                System.out.println("No repositories found.");
            }
        } else {
            System.out.println("Error: " + response.code() + " " + response.message());
        }
    }


    @Override
    public void onFailure(Call<List<Change>> call, Throwable t) {
        t.printStackTrace();
    }
}