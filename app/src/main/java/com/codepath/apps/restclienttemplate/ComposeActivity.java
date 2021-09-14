package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ComposeActivity extends AppCompatActivity {

    public static final int MAX_TWEENT_LENGTH = 140;

    EditText etCompose;

    Button btnTweet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

        etCompose = findViewById(R.id.etCompose);

        btnTweet = findViewById(R.id.btnTweet);

        //set a click listener on button

        btnTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tweet_content = etCompose.getText().toString();

                if (tweet_content.isEmpty()) {

                    Toast.makeText(ComposeActivity.this, "Sorry, your tweet cannot be empty.", Toast.LENGTH_SHORT).show();

                    return;

                }

                if (tweet_content.length() > MAX_TWEENT_LENGTH) {

                    Toast.makeText(ComposeActivity.this, "Sorry, your tweet is too long.", Toast.LENGTH_LONG).show();

                    return;

                }

                Toast.makeText(ComposeActivity.this, tweet_content, Toast.LENGTH_LONG).show();

                //make an API call to Twitter to publish the tweet




            }
        });


    }
}