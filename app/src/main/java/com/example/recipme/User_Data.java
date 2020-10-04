package com.example.recipme;

public class User_Data {

    /*
    This class contains data that is used to reference variables between classes.
    I felt as though this was the easiest way I could have implemented fetching certain data >>>
    (By setting a value to a value in this class and referencing it somewhere else (and clearing it later))
    */

    public static String email;
    public static String name;
    public static String user_Experience;

    public static String imagePath1 = null;
    public static String imagePath2 = null;
    public static String imagePath3 = null;
    public static String tempImageLink = null;
    public static Recipe tempEditingRecipe = null;
    public static String tempRecipeOldTitle = null;

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        User_Data.email = email;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        User_Data.name = name;
    }

    public static void clearData(){
        email = null;
        name = null;
        user_Experience = null;
    }

    public static void setImages(int imageNum){

        if(imageNum == 1){
            imagePath1 = tempImageLink;
        }
        if(imageNum == 2){
            imagePath2 = tempImageLink;
        }
        if(imageNum == 3){
            imagePath3 = tempImageLink;
        }
        clearTempImageLink();
    }

    public static void clearImages(){
        imagePath1 = null;
        imagePath2 = null;
        imagePath3 = null;
    }

    public static void clearTempImageLink() {
        tempImageLink = null;
    }

    public static void setTempImageLink(String sentImg) {
        tempImageLink = sentImg;
    }

    public static Recipe getTempEditingRecipe() {
        return tempEditingRecipe;
    }

    public static void setTempEditingRecipe(Recipe tempEditingRecipe) {
        User_Data.tempEditingRecipe = tempEditingRecipe;
        tempRecipeOldTitle = tempEditingRecipe.getTitle();
    }

    public static void clearTempEditingRecipe() {
        User_Data.tempEditingRecipe = null;
        User_Data.tempRecipeOldTitle = null;
    }

    public static String getUser_Experience() {
        return user_Experience;
    }

    public static void setUser_Experience(String user_Experience) {
        User_Data.user_Experience = user_Experience;
    }
}//User_Data
