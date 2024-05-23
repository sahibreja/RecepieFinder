package com.example.recepiefinder.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class Recipe {
    @SerializedName("label")
    private String label;

    @SerializedName("image")
    private String image;

    @SerializedName("totalTime")
    private long totalTime;

    @SerializedName("calories")
    private double calories;
    @SerializedName("ingredientLines")
    private List<String> ingredientLines;

    @SerializedName("ingredients")
    private List<Ingredient> ingredientList;

    @SerializedName("instructions")
    private List<String> instructions;


    public Recipe() {
    }

    public Recipe(String label, String image,long totalTime,double calories, List<String> ingredientLines,List<Ingredient> ingredientList,List<String> instructions) {
        this.label = label;
        this.image = image;
        this.totalTime = totalTime;
        this.calories = calories;
        this.ingredientLines = ingredientLines;
        this.ingredientList = ingredientList;
        this.instructions = instructions;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<String> getIngredientLines() {
        return ingredientLines;
    }


    public void setIngredientLines(List<String> ingredientLines) {
        this.ingredientLines = ingredientLines;
    }

    public List<Ingredient> getIngredientList() {
        return ingredientList;
    }

    public void setIngredientList(List<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }

    public long getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(long totalTime) {
        this.totalTime = totalTime;
    }

    public List<String> getInstructions() {
        return instructions;
    }

    public void setInstructions(List<String> instructions) {
        this.instructions = instructions;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }
}
