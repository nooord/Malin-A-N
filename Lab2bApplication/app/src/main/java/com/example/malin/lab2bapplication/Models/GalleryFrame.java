package com.example.malin.lab2bapplication.Models;



public class GalleryFrame {

    //Initializes variables
    private String url;
    private String name;
    private String age;
    private String description;

    //Set the variables
    public GalleryFrame(String url, String name, String age, String description){
        this.url = url;
        this.name = name;
        this.age = age;
        this.description = description;

    }
    //Get the URL and return the value.
    public String getUrl() {
        return url;
    }

    //Get the NAME and return the value.
    public String getName() {
        return name;
    }

    //Get the AGE and return the value.
    public String getAge() {return age; }

    public String getDescription() {
        return description;
    }


}
