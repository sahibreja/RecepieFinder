package com.example.recepiefinder.Repository;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.recepiefinder.Model.Root;
import com.example.recepiefinder.utill.RecipeApiService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeRepository {
    private static final String BASE_URL = "https://api.edamam.com/api/";
    private RecipeApiService apiService;
    private Application application;
    private MutableLiveData<Root> rootMutableLiveData;

    public RecipeRepository(Application application) {
        this.application = application;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(RecipeApiService.class);
        rootMutableLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<Root> getRecipesLiveData(){
        return rootMutableLiveData;
    }

    public void getRecipes(String query) {
        Call<Root> call = apiService.getRecipes("public", query, "a5104ba2", "ff4b5af32a3a896a9282434be2077a90");
        call.enqueue(new Callback<Root>() {
            @Override
            public void onResponse(@NonNull Call<Root> call, @NonNull Response<Root> response) {
                if (response.isSuccessful()) {
                    rootMutableLiveData.setValue(response.body());
                } else {
                    Log.e("RecipeRepository", "Error: " + response.code() + ", " + response.message());
                    Toast.makeText(application, "Error Loading 1", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Root> call, @NonNull Throwable t) {
                Log.e("RecipeRepository", "Failure: " + t.getMessage(), t);
                Toast.makeText(application, "Error Loading 2", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
