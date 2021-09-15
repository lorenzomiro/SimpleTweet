package com.codepath.apps.restclienttemplate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class TimelineActivity extends AppCompatActivity {

    //tags for failure/success

    public static final String TAG = "TimelineActivity";

    private final int REQUEST_CODE = 20;

    //create instances of client + recycler view

    TwitterClient client;

    RecyclerView rvTweets;

    List<Tweet> tweet_list;

    TweetsAdapter adapter;

    SwipeRefreshLayout swipe_container;

    EndlessRecyclerViewScrollListener scrollListener;

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

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        rvTweets.setLayoutManager(layoutManager);

        rvTweets.setAdapter(adapter);

        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view){

                Log.i(TAG, "onLoadMore: " + page);

                loadMoreData();

            }

        };

        //add scroll listener -> recyclerview

        rvTweets.addOnScrollListener(scrollListener);

        populateHomeTimeline();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflate the menu, which adds items to action bar if present

        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.compose) {

            //compose icon has been selected

            //navigate to compose activity

            Intent i = new Intent(this, ComposeActivity.class);

            startActivityForResult(i, REQUEST_CODE);

            return true;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {

            //get intent's data (tweet)

            Tweet tweet = Parcels.unwrap(data.getParcelableExtra("tweet"));

            //update RV w/new tweet

            //modify data source of tweets

            tweet_list.add(0, tweet);

            //update the adapter

            adapter.notifyItemInserted(0);

            rvTweets.smoothScrollToPosition(0);


        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void loadMoreData() {
        // Send an API request to retrieve appropriate paginated data

        client.getNextPageOfTweets(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {

                Log.i(TAG, "loadMoreData onSuccess!" + json.toString());

                // Deserialize and construct new model objects from the API response

                JSONArray jsonArray = json.jsonArray;

                try {

                    List<Tweet> tweets = Tweet.fromJsonArray(jsonArray);

                    // Append the new data objects to the existing set of items inside the array of items

                    // Notify the adapter of the new items made with `notifyItemRangeInserted()`

                    adapter.addAll(tweets);

                } catch (JSONException e) {

                    e.printStackTrace();

                }

            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {

                Log.e(TAG, "loadMoreData onFailure :( ," + throwable);

            }
        }, tweet_list.get(tweet_list.size() - 1).id);

    }


    private void loadNextDataFromAPI() {
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