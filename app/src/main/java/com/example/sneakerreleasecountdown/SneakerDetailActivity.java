package com.example.sneakerreleasecountdown;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import cn.iwgang.countdownview.CountdownView;

import static com.example.sneakerreleasecountdown.AllReleasesActivity.SHARED_PREFERENCES;

public class SneakerDetailActivity extends AppCompatActivity {
    SharedPreferences mPreferences;
    private int sneakerId;
    private static final String TAG = "SneakerDetailActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sneaker_detail);
        mPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);

        final Intent intent = getIntent();
        sneakerId = intent.getIntExtra("SNEAKER_ID", -1);

        final Sneaker sneaker = getSneaker(mPreferences);
        Date sneakerDate = sneaker.getReleaseDate();

        TextView sneakerNameView = findViewById(R.id.sneakerName);
        TextView sneakerDateView = findViewById(R.id.sneakerReleaseDate);
        ImageView sneakerImageView = findViewById(R.id.sneakerImage);

        sneakerNameView.setText(sneaker.getName());
        sneakerDateView.setText(getFormattedDate(sneaker.getReleaseDate()));
        sneakerImageView.setImageResource(sneaker.getImageResource());

        findViewById(R.id.backArrow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SneakerDetailActivity.super.onBackPressed();
            }
        });

        TextView sneakerReleaseText = findViewById(R.id.sneakerReleaseDateText);
        long timeNow = dateToTimestamp(Calendar.getInstance().getTime());
        CountdownView mCvCountdownView = findViewById(R.id.countDown);
        long timeStamp = dateToTimestamp(sneakerDate);

        if (timeNow > timeStamp) {
            mCvCountdownView.setVisibility(View.GONE);
            sneakerReleaseText.setText(getResources().getString(R.string.sneaker_released));
            sneakerReleaseText.setTextSize(40);
        } else {
            mCvCountdownView.start(timeStamp - timeNow);
        }

        final ImageView favoriteIcon = findViewById(R.id.favoriteIcon);
        if (sneaker.isFavorite()) {
            favoriteIcon.setImageDrawable(ContextCompat.getDrawable(SneakerDetailActivity.this, R.drawable.ic_favorite));
            Log.d(TAG, "onCreate: true " + sneaker.isFavorite());
        } else {
            favoriteIcon.setImageDrawable(ContextCompat.getDrawable(SneakerDetailActivity.this, R.drawable.ic_favorite_border));
            Log.d(TAG, "onCreate: false " + sneaker.isFavorite());
        }

        favoriteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isFavorite = sneaker.isFavorite();
                List<Sneaker> sneakerList = AllReleasesActivity.getSneakers(mPreferences);

                if (isFavorite) {
                    favoriteIcon.setImageDrawable(ContextCompat.getDrawable(SneakerDetailActivity.this, R.drawable.ic_favorite_border));
                    sneaker.setFavorite(false);

                    for (Sneaker sneaker1 : sneakerList) {
                        if (sneaker1.getSneakerID() == getSneakerPosition(mPreferences)) {
                            sneakerList.set(sneakerId, sneaker);
                        }
                    }

                    LoginActivity.showWarningToast(SneakerDetailActivity.this, "Removed from favorites");
                } else {
                    favoriteIcon.setImageDrawable(ContextCompat.getDrawable(SneakerDetailActivity.this, R.drawable.ic_favorite));
                    sneaker.setFavorite(true);
                    sneakerList.set(getSneakerPosition(mPreferences), sneaker);

                    LoginActivity.showSuccessToast(SneakerDetailActivity.this, "Added to favorites");
                }

                AllReleasesActivity.saveLocally(sneakerList, mPreferences);
            }
        });
    }

    public static String getFormattedDate(Date date) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd',' yyyy   z");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("EST"));
        return simpleDateFormat.format(date);
    }

    private long dateToTimestamp(Date date) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTime(date);
        cal.setTimeZone(TimeZone.getTimeZone("EST"));
        return cal.getTimeInMillis();
    }

    public Sneaker getSneaker(SharedPreferences preferences) {
        Gson gson = new Gson();
        String json = preferences.getString("SNEAKER_LIST", "");
        Type type = new TypeToken<List<Sneaker>>() {
        }.getType();

        Sneaker sneaker = null;

        List<Sneaker> sneakerList = gson.fromJson(json, type);
        for (int i = 0; i < sneakerList.size(); i++) {
            if (sneakerId == sneakerList.get(i).getSneakerID()) {
                sneaker = sneakerList.get(i);
                break;
            }
        }

        return sneaker;
    }

    public int getSneakerPosition(SharedPreferences preferences) {
        Gson gson = new Gson();
        String json = preferences.getString("SNEAKER_LIST", "");
        Type type = new TypeToken<List<Sneaker>>() {
        }.getType();

        int position = 0;

        List<Sneaker> sneakerList = gson.fromJson(json, type);
        for (int i = 0; i < sneakerList.size(); i++) {
            if (sneakerId == sneakerList.get(i).getSneakerID()) {
                position = i;
                break;
            }
        }

        return position;
    }
}
