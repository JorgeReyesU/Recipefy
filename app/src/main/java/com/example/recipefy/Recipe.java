package com.example.recipefy;

import android.net.Uri;

import com.example.recipefy.ui.home.HomeFragment;
import com.squareup.picasso.Picasso;

public class Recipe {
    private String name;
    private String imageURL;
    private String key;
    private String description;

    public Recipe(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Recipe(){
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public String toString() {
        return "Nombre: " + name +  '\n' + "Descripción: " + description +  '\n' + "Image: ";
    }
}
