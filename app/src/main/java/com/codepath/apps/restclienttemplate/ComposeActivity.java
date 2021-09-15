package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONException;
import org.parceler.Parcels;

import okhttp3.Headers;

public class ComposeActivity extends AppCompatActivity {

    public static final String TAG = "ComposeActivity";

    public static final int MAX_TWEET_LENGTH = 140;

    EditText etCompose;

    Button btnTweet;

    TwitterClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

        client = TwitterApp.getRestClient(this);

        etCompose = findViewById(R.id.etCompose);

        btnTweet = findViewById(R.id.btnTweet);

        //set a click listener on button

        btnTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String tweet_content = etCompose.getText().toString();

                if (tweet_content.isEmpty()) {

                    Toast.makeText(ComposeActivity.this, "Sorry, your tweet cannot be empty.", Toast.LENGTH_SHORT).show();

                    return;

                }

                if (tweet_content.length() > MAX_TWEET_LENGTH) {

                    Toast.makeText(ComposeActivity.this, "Sorry, your tweet is too long.", Toast.LENGTH_LONG).show();

                    return;

                }

                Toast.makeText(ComposeActivity.this, tweet_content, Toast.LENGTH_LONG).show();

                //make an API call to Twitter to publish the tweet

                client.publishTweet(tweet_content, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Headers headers, JSON json) {

                        Log.i(TAG, "onSuccess to publish tweet");

                        try {

                            Tweet tweet = Tweet.fromJson(json.jsonObject);

                            Log.i(TAG, "Published tweet says: " + tweet);

                            Intent i = new Intent();

                            i.putExtra("tweet", Parcels.wrap(tweet));

                            //set result code + bundle response dat

                            setResult(RESULT_OK, i);

                            //close activity, pass data to parent

                            finish();

                        } catch (JSONException e) {

                            e.printStackTrace();

                        }

                    }

                    @Override
                    public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {

                        Log.e(TAG, "onFailure to publish tweet", throwable);

                    }

                });

            }

        });


    }
}