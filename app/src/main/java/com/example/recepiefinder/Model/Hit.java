package com.example.recepiefinder.Model;

import com.google.gson.annotations.SerializedName;

public  class Hit {
    @SerializedName("recipe")
    private Recipe recipe;

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}
