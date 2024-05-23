package com.example.recepiefinder;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.recepiefinder.Adapter.RecepieAdapter;
import com.example.recepiefinder.Model.Hit;
import com.example.recepiefinder.Model.Recipe;
import com.example.recepiefinder.Model.Root;
import com.example.recepiefinder.Model.User;
import com.example.recepiefinder.ViewModel.AuthViewModel;
import com.example.recepiefinder.ViewModel.RecipeViewModel;
import com.example.recepiefinder.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private RecipeViewModel viewModel;
    private ActivityMainBinding binding;
    private RecepieAdapter recepieAdapter;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
        observer();
        onButtonClick();
    }

    private void init(){
        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(getApplication())).get(RecipeViewModel.class);
        initResultFetch();
        userId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

        recepieAdapter = new RecepieAdapter(this,new ArrayList<>());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        binding.recyclerView.setLayoutManager(gridLayoutManager);
        binding.recyclerView.setAdapter(recepieAdapter);
    }

    private void onButtonClick(){
       binding.searchBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String query = binding.searchEditTxt.getText().toString().trim();
               if(query.isEmpty()){
                   Toast.makeText(MainActivity.this, "Please enter a search query", Toast.LENGTH_SHORT).show();
               }else{
                   showLoading();
                   viewModel.fetchRecipes(query);
               }
           }
       });

       binding.searchEditTxt.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence s, int start, int count, int after) {

           }

           @Override
           public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() <=0 ){
                    initResultFetch();
                }
           }
           @Override
           public void afterTextChanged(Editable s) {

           }
       });

       binding.searchEditTxt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
           @Override
           public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
               if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                   // Handle the search action
                   String query = binding.searchEditTxt.getText().toString().trim();
                   showLoading();
                   viewModel.fetchRecipes(query);
                   return true;
               }
               return false;
           }
       });
    }
    private void observer(){
        viewModel.getRecipes().observe(this, new Observer<Root>() {
            @Override
            public void onChanged(Root root) {
                closeLoading();
                if (root != null && root.getHits() != null && !root.getHits().isEmpty()) {
                    List<Recipe> recipes = new ArrayList<>();
                    for (Hit hit : root.getHits()) {
                        recipes.add(hit.getRecipe());
                    }
                    if(!recipes.isEmpty()){
                        closeNoResultFound();
                        recepieAdapter.setRecipeList(recipes);
                    }else{
                        showNoResultFound();
                    }
                }else{
                    showNoResultFound();
                }
            }
        });

        viewModel.getUserData(userId).observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                updateUserUi(user);
            }
        });
    }

    private void updateUserUi(User user) {
       String firstName = user.getUserName().split(" ")[0];
       String nameText = "Hello, "+firstName;
       binding.userName.setText(nameText);
    }

    private void showNoResultFound(){
        binding.noResultLayout.setVisibility(View.VISIBLE);
    }
    private void closeNoResultFound(){
        binding.noResultLayout.setVisibility(View.GONE);
    }

    private void showLoading(){
        binding.loadingLayout.setVisibility(View.VISIBLE);
        binding.recyclerView.setVisibility(View.INVISIBLE);
        closeNoResultFound();
    }
    private void closeLoading(){
        binding.loadingLayout.setVisibility(View.GONE);
        binding.recyclerView.setVisibility(View.VISIBLE);
    }

    private void initResultFetch(){
        String[] recipeNames = {
                "Spicy Thai Basil Chicken",
                "Creamy Garlic Parmesan Risotto",
                "Moroccan Lamb Tagine",
                "Lemon Herb Roasted Salmon",
                "Coconut Curry Shrimp",
                "Honey Mustard Glazed Pork Chops",
                "Mushroom and Spinach Stuffed Chicken",
                "Mexican Street Corn Salad",
                "Szechuan Beef Stir-Fry",
                "Butternut Squash Soup",
                "Maple Pecan Crusted Tilapia",
                "Quinoa and Black Bean Stuffed Peppers",
                "Bacon Wrapped Jalapeño Poppers",
                "Balsamic Glazed Brussels Sprouts",
                "Greek Yogurt Chicken Salad",
                "Avocado Toast with Poached Egg",
                "Teriyaki Chicken Lettuce Wraps",
                "Chocolate Lava Cake",
                "Blueberry Lemon Scones",
                "Classic French Onion Soup"
        };
        showLoading();
        Random random = new Random();
        int index = random.nextInt(recipeNames.length);
        String recipeName = recipeNames[index];
        viewModel.fetchRecipes(recipeName);

    }
}