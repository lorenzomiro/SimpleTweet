package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class TimelineActivity extends AppCompatActivity {

    //tags for failure/success

    public static final String TAG = "TimelineActivity";

    //create instances of client + recycler view

    TwitterClient client;

    RecyclerView rvTweets;

    List<Tweet> tweet_list;

    TweetsAdapter adapter;

    SwipeRefreshLayout swipe_container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        client = TwitterApp.getRestClient(this);

        swipe_container = findViewById(R.id.swipe_container);

        // Configure the refreshing colors
        swipe_container.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        swipe_container.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.i(TAG, "fetching new data...");

                populateHomeTimeline();

            }
        });

        //Find recycler view

        rvTweets = findViewById(R.id.rvTweets);

        //Initialize tweet lists + adapter

        tweet_list = new ArrayList<>();

        adapter = new TweetsAdapter(this, tweet_list);

        //setup recycler view (layout manager + adapter)

        rvTweets.setLayoutManager(new LinearLayoutManager(this));

        rvTweets.setAdapter(adapter);

        populateHomeTimeline();

    }

    private void populateHomeTimeline() {
        client.getHometimeLine(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.i(TAG, "onSuccess!" + json.toString());

                JSONArray jsonArray = json.jsonArray;

                try {

                    adapter.clear();

                    adapter.addAll(Tweet.fromJsonArray(jsonArray));

                    //notify adapter that data has changed

                    adapter.notifyDataSetChanged();

                    // call setRefreshing(false) to signal refresh has finished

                    swipe_container.setRefreshing(false);

                } catch (JSONException e) {

                    Log.e(TAG, "Json exception", e);

                }

            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.e(TAG, "onFailure." + response, throwable);
            }
        });


    }
}