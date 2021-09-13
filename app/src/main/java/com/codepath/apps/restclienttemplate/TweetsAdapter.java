package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.w3c.dom.Text;

import java.util.List;

public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.ViewHolder>{

    Context context;

    List<Tweet> tweet_list;

    //pass in context and tweet list
    public TweetsAdapter(Context context, List<Tweet> tweet_list) {

        this.context = context;

        this.tweet_list = tweet_list;

    }

    //inflate the layout row-by-row
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_tweet, parent, false);

        return new ViewHolder(view);
    }

    //bind values based on element position
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        //get data @ position

        Tweet tweet = tweet_list.get(position);

        //bind data tweet w/viewholder

        holder.bind(tweet);

    }

    // Clean all elements of the recycler
    public void clear() {

        tweet_list.clear();

        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Tweet> tweets) {

        tweet_list.addAll(tweets);

        notifyDataSetChanged();

    }


    @Override
    public int getItemCount() {

        return tweet_list.size();

    }

    //pass in context + list of tweets

    //inflate tweet layout for each row

    //bind values based on position of the element

    //define a viewholder

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivProfileImage;

        TextView tvBody;

        TextView tvScreenName;

        TextView tvTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            //get references to all objects in ViewHolder class

            ivProfileImage = itemView.findViewById(R.id.ivProfileImage);

            tvBody = itemView.findViewById(R.id.tvBody);

            tvScreenName = itemView.findViewById(R.id.tvScreenName);

            tvTime = itemView.findViewById(R.id.tvTime);

        }

        public void bind(Tweet tweet) {

            tvBody.setText(tweet.body);

            tvTime.setText(tweet.time_stamp);

            tvScreenName.setText(tweet.user.screen_name);

            Glide.with(context).load(tweet.user.profile_image_url).into(ivProfileImage);


        }
    }

}
