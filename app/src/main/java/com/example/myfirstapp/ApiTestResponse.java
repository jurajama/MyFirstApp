package com.example.myfirstapp;

import com.google.gson.annotations.SerializedName;
import java.util.HashMap;
import java.util.Map;

public class ApiTestResponse {
    // POJO class to get the data from web api
    // Corresponds to test API https://jsonplaceholder.typicode.com/todos/
    // Using Retrofit 2.x GET example of https://abhiandroid.com/programming/retrofit as model

    @SerializedName("userId")
    private int userId;

    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("completed")
    private Boolean completed;

    // properties not matching to defined variables will be stored in this map
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
