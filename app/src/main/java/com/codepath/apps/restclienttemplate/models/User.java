package com.codepath.apps.restclienttemplate.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class User {

    public String name;

    public String screen_name;

    public String profile_image_url;

    public static User fromJson(JSONObject jsonObject) throws JSONException {

        User user = new User();

        user.name = jsonObject.getString("name");

        user.screen_name = jsonObject.getString("screen_name");

        user.profile_image_url = jsonObject.getString("profile_image_url_https");

        return user;

    }

    public static List<Tweet> fromJsonArray(JSONArray jsonArray) throws JSONException {

        List<Tweet> tweets = new ArrayList<>();

        for (int p = 0; p < jsonArray.length(); p++) {

            tweets.add(Tweet.fromJson(jsonArray.getJSONObject(p)));

        }

        return tweets;

    }

}
