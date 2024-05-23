package com.example.recepiefinder.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.recepiefinder.Model.Recipe;
import com.example.recepiefinder.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecepieAdapter extends RecyclerView.Adapter<RecepieAdapter.ViewHolder> {
    private Context context;
    private List<Recipe> recipeList;

    public RecepieAdapter(Context context, List<Recipe> recipeList) {
        this.context = context;
        this.recipeList = recipeList;
    }

    public void setRecipeList(List<Recipe> recipeList) {
        this.recipeList = recipeList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RecepieAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recepie_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecepieAdapter.ViewHolder holder, int position) {
        Recipe recipe = recipeList.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;

        viewHolder.recipeName.setText(recipe.getLabel());
        viewHolder.timeView.setText(convertTimeToString(recipe.getTotalTime()));
        viewHolder.caloriesView.setText(convertCaloriesToString(recipe.getCalories()));
        Glide.with(context).load(recipe.getImage()).into(viewHolder.recipeImage);


    }

    @SuppressLint("DefaultLocale")
    private String convertCaloriesToString(double calories) {
        if (calories >= 1000) {
            float kiloCalories = (float) (calories / 1000.0);
            return String.format("%.2f", kiloCalories);
        }else{
            return String.format("%.2f",calories);
        }
    }

    @SuppressLint("DefaultLocale")
    private String convertTimeToString(long totalTime) {
        if(totalTime >= 60){
            float hours = (float) (totalTime / 60.0);
            int minutes = (int) (totalTime % 60);
            return String.format("%.2f", hours) + " hours";
        }else{
            return String.valueOf(totalTime) + " mins";
        }
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView recipeImage;
        private TextView recipeName;
        private TextView timeView;
        private TextView caloriesView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeImage = itemView.findViewById(R.id.imageView);
            recipeName = itemView.findViewById(R.id.recepieLebel);
            timeView = itemView.findViewById(R.id.time_value);
            caloriesView = itemView.findViewById(R.id.calories_value);
        }
    }
}
