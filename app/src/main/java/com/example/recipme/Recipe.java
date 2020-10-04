package com.example.recipme;

public class Recipe{

    public String title;
    public String ingredients;
    public String instructions;
    public String description;
    public String prepTime;

    public String imagePath1;
    public String imagePath2;
    public String imagePath3;

    public Recipe() {
    }

    public Recipe(String title, String prepTime, String description, String ingredients,
    String instructions, String imagePath1, String imagePath2, String imagePath3) {
        this.title = title;
        this.title = title;
        this.prepTime = prepTime;
        this.description = description;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.imagePath1 = imagePath1;
        this.imagePath2 = imagePath2;
        this.imagePath3 = imagePath3;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrepTime() {
        return prepTime;
    }

    public void setPrepTime(String prepTime) {
        this.prepTime = prepTime;
    }

    public String getImagePath1() {
        return imagePath1;
    }

    public void setImagePath1(String imagePath1) {
        this.imagePath1 = imagePath1;
    }

    public String getImagePath2() {
        return imagePath2;
    }

    public void setImagePath2(String imagePath2) {
        this.imagePath2 = imagePath2;
    }

    public String getImagePath3() {
        return imagePath3;
    }

    public void setImagePath3(String imagePath3) {
        this.imagePath3 = imagePath3;
    }

}//Recipe
