package com.example.recepiefinder.utill;

import com.example.recepiefinder.Model.Root;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RecipeApiService {
    @GET("recipes/v2")
    Call<Root> getRecipes(
            @Query("type") String type,
            @Query("q") String query,
            @Query("app_id") String appId,
            @Query("app_key") String appKey
    );
}

