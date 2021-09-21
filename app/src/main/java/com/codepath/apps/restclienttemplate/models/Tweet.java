package com.codepath.apps.restclienttemplate.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.codepath.apps.restclienttemplate.TimeFormatter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
@Entity(foreignKeys = @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "user_id"))
public class Tweet {

    @ColumnInfo
    public String body;

    @ColumnInfo
    public String createdAt;

    @PrimaryKey
    @ColumnInfo
    @NonNull
    public long id;

    @ColumnInfo
    public long user_id;

    @Ignore
    public User user;

    //empty constructor needed by the Parceler library
    public Tweet() {}

    @ColumnInfo
    public String time_stamp;

    public static Tweet fromJson(JSONObject jsonObject) throws JSONException {

        Tweet tweet = new Tweet();

        tweet.body = jsonObject.getString("text");

        tweet.createdAt = jsonObject.getString("created_at");

        tweet.id = jsonObject.getLong("id");

        User user = User.fromJson(jsonObject.getJSONObject("user"));

        tweet.user = user;

        tweet.user_id = user.id;

        tweet.time_stamp = getFormattedTimestamp(tweet.createdAt);

        return tweet;

    }

    public static String getFormattedTimestamp(String createdAt) {

        return TimeFormatter.getTimeDifference(createdAt);

    }

    public static List<Tweet> fromJsonArray(JSONArray jsonArray) throws JSONException {

        List<Tweet> tweets = new ArrayList<>();

        for (int n = 0; n < jsonArray.length(); n++) {

            tweets.add(Tweet.fromJson(jsonArray.getJSONObject(n)));

        }

        return tweets;

    }
}
