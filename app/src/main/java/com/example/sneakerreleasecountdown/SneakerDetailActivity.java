package com.example.sneakerreleasecountdown;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import cn.iwgang.countdownview.CountdownView;

public class SneakerDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sneaker_detail);

        Intent intent = getIntent();
        String sneakerName = intent.getStringExtra("SNEAKER_NAME");
        Date sneakerDate = (Date) intent.getSerializableExtra("SNEAKER_DATE");
        int sneakerImageResource = intent.getIntExtra("SNEAKER_IMAGE_RESOURCE", -1);

        TextView sneakerNameView = findViewById(R.id.sneakerName);
        TextView sneakerDateView = findViewById(R.id.sneakerReleaseDate);
        ImageView sneakerImageView = findViewById(R.id.sneakerImage);

        sneakerNameView.setText(sneakerName);
        sneakerDateView.setText(getFormattedDate(sneakerDate));
        sneakerImageView.setImageResource(sneakerImageResource);

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
}
