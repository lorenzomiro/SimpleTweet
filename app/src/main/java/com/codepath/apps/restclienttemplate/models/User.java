package com.codepath.apps.restclienttemplate.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
@Entity
public class User {

    @ColumnInfo
    @PrimaryKey
    @NonNull
    public long id;

    @ColumnInfo
    public String name;

    @ColumnInfo
    public String screen_name;

    @ColumnInfo
    public String profile_image_url;

    //empty constructor needed by the Parceler library
    public User() {}

    public static User fromJson(JSONObject jsonObject) throws JSONException {

        User user = new User();

        user.id = jsonObject.getLong("id");

        user.name = jsonObject.getString("name");

        user.screen_name = jsonObject.getString("screen_name");

        user.profile_image_url = jsonObject.getString("profile_image_url_https");

        return user;

    }
    public static List<User> fromTweetList(List<Tweet> tweets){
        List<User> users = new ArrayList<>();
        for(int i =0; i <tweets.size(); i++){
            users.add(tweets.get(i).user);
        }
        return users;
    }
    public static List<Tweet> fromJsonArray(JSONArray jsonArray) throws JSONException {

        List<Tweet> tweets = new ArrayList<>();

        for (int p = 0; p < jsonArray.length(); p++) {

            tweets.add(Tweet.fromJson(jsonArray.getJSONObject(p)));

        }

        return tweets;

    }

}
