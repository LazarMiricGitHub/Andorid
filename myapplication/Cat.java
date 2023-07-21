package com.example.myapplication;

import org.json.JSONObject;

public class Cat {
    String description;
    int number;

    public Cat(String description, int number){
        this.description = description;
        this.number = number;
    }

    public Cat() {

    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public static Cat parseJSONObject(JSONObject object){
        Cat cat = new Cat();

        try{
            if(object.has("fact")){
                cat.setDescription(object.getString("fact"));
            }

            if(object.has("length")){
                cat.setNumber(object.getInt("length"));
            }

        } catch (Exception e){

        }
        return  cat;
    }
}
