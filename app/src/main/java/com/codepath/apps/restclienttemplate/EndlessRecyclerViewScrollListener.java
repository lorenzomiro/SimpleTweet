package com.codepath.apps.restclienttemplate;

import java.text.SimpleDateFormat;
import android.text.format.DateUtils;

import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.util.Locale;

public class EndlessRecyclerViewScrollListener extends RecyclerView.OnScrollListener {
    public String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }
}
