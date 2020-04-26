package com.example.sneakerreleasecountdown;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AllReleasesActivity extends AppCompatActivity implements SneakerListAdapter.SneakerItemListener {
    RecyclerView mRecyclerView;
    SneakerListAdapter mSneakerListAdapter;
    List<Sneaker> mSneakerList;
    public static final String SHARED_PREFERENCES = "SHARED_PREFERENCES";
    private static Logger logger = Logger.getLogger("LOG_TAG");
    SharedPreferences mPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_releases);
        mPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        isFirstTime();

        findViewById(R.id.backArrow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AllReleasesActivity.super.onBackPressed();
            }
        });

        mSneakerList = getSneakers(mPreferences);
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mSneakerListAdapter = new SneakerListAdapter(this, mSneakerList, this);
        mRecyclerView.setAdapter(mSneakerListAdapter);
    }


    private void isFirstTime() {
        boolean isFirstTime = mPreferences.getBoolean("IS_FIRST_TIME_USE", true);

        if (isFirstTime) {
            populateLocalStorage();
            SharedPreferences.Editor editor = mPreferences.edit();
            editor.putBoolean("IS_FIRST_TIME_USE", false);
            editor.apply();
        }
    }


    public static String getFormattedDate(Date date) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d \nMMM");
        return simpleDateFormat.format(date);
    }

    @SuppressLint("SimpleDateFormat")
    private Date stringToDate(String dateString) throws ParseException {
        return new SimpleDateFormat("MMM d yyyy hh aaa").parse(dateString);
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(AllReleasesActivity.this, SneakerDetailActivity.class);
        intent.putExtra("SNEAKER_ID", mSneakerList.get(position).getSneakerID());
        startActivity(intent);
    }

    void populateLocalStorage() {
        List<Sneaker> sneakerList = new ArrayList<>();

        try {
            sneakerList.add(new Sneaker("Air Jordan 5 Fire Red", stringToDate("May 2 2020 10 am"), R.drawable.jordan_pic_1, false, 0));
            sneakerList.add(new Sneaker("Air Jordan 1 Game Royal", stringToDate("May 9 2020 10 am"), R.drawable.jordan_pic_2, false, 1));
            sneakerList.add(new Sneaker("Air Jordan 4 Pine Green", stringToDate("May 16 2020 10 am"), R.drawable.jordan_pic_3, false, 2));
            sneakerList.add(new Sneaker("Air Jordan 11 Low Concord Sketch", stringToDate("May 22 2020 10 am"), R.drawable.jordan_pic_4, false, 3));
            sneakerList.add(new Sneaker("Air Jordan 11 Low White/Red", stringToDate("May 23 2020 10 am"), R.drawable.jordan_pic_5, false, 4));
            sneakerList.add(new Sneaker("Air Jordan 13 Flint", stringToDate("May 30 2020 10 am"), R.drawable.last_jordan_pic, false, 5));
        } catch (ParseException e) {
            logger.log(Level.WARNING, e.toString());
        }

        saveLocally(sneakerList, mPreferences);
    }

    public static void saveLocally(List<Sneaker> sneakerList, SharedPreferences preferences) {
        Gson gson = new Gson();
        String json = gson.toJson(sneakerList);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("SNEAKER_LIST", json);
        editor.apply();
    }

    public static List<Sneaker> getSneakers(SharedPreferences preferences) {
        Gson gson = new Gson();
        String json = preferences.getString("SNEAKER_LIST", "");
        Type type = new TypeToken<List<Sneaker>>() {
        }.getType();
        return gson.fromJson(json, type);
    }
}
