package com.example.sneakerreleasecountdown;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    private static Logger logger = Logger.getLogger("LOG_TAG");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_releases);

        findViewById(R.id.backArrow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AllReleasesActivity.super.onBackPressed();
            }
        });

        mSneakerList = new ArrayList<>();

        try {
            mSneakerList.add(new Sneaker("Air Jordan 5 Fire Red", stringToDate("May 2 2020 10 am"), R.drawable.jordan_pic_1));
            mSneakerList.add(new Sneaker("Air Jordan 1 Game Royal", stringToDate("May 9 2020 10 am"), R.drawable.jordan_pic_2));
            mSneakerList.add(new Sneaker("Air Jordan 4 Pine Green", stringToDate("May 16 2020 10 am"), R.drawable.jordan_pic_3));
            mSneakerList.add(new Sneaker("Air Jordan 11 Low Concord Sketch", stringToDate("May 22 2020 10 am"), R.drawable.jordan_pic_4));
            mSneakerList.add(new Sneaker("Air Jordan 11 Low White/Red", stringToDate("May 23 2020 10 am"), R.drawable.jordan_pic_5));
            mSneakerList.add(new Sneaker("Air Jordan 13 Flint", stringToDate("May 30 2020 10 am"), R.drawable.last_jordan_pic));
        } catch (ParseException e) {
            logger.log(Level.WARNING, e.toString());
        }

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mSneakerListAdapter = new SneakerListAdapter(this, mSneakerList, this);
        mRecyclerView.setAdapter(mSneakerListAdapter);

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
        Sneaker sneaker = mSneakerList.get(position);

        Intent intent = new Intent(AllReleasesActivity.this, SneakerDetailActivity.class);
        intent.putExtra("SNEAKER_NAME", sneaker.getName());
        intent.putExtra("SNEAKER_DATE", sneaker.getReleaseDate());
        intent.putExtra("SNEAKER_IMAGE_RESOURCE", sneaker.getImageResource());
        startActivity(intent);
    }
}
