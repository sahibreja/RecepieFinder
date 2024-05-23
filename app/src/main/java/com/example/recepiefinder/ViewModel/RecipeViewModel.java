package com.example.recepiefinder.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.example.recepiefinder.Model.Root;
import com.example.recepiefinder.Model.User;
import com.example.recepiefinder.Repository.RecipeRepository;
import com.example.recepiefinder.Repository.UserRepository;

public class RecipeViewModel extends AndroidViewModel {
    private MutableLiveData<Root> recipes;
    private RecipeRepository repository;
    private UserRepository userRepository;

    public RecipeViewModel(Application application) {
        super(application);
        recipes = new MutableLiveData<>();
        repository = new RecipeRepository(application);
        userRepository = new UserRepository();
    }

    public MutableLiveData<Root> getRecipes() {
        return repository.getRecipesLiveData();
    }

    public void fetchRecipes(String query) {
        repository.getRecipes(query);
    }

    public MutableLiveData<User> getUserData(String userId){
        return userRepository.getUserMutableLiveData(userId);
    }
}
