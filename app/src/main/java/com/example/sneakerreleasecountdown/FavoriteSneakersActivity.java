package com.example.sneakerreleasecountdown;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FavoriteSneakersActivity extends AllReleasesActivity implements SneakerListAdapter.SneakerItemListener {
    RecyclerView mRecyclerView;
    SneakerListAdapter mSneakerListAdapter;
    List<Sneaker> mSneakerList;
    List<Sneaker> favoriteSneakers;
    public static final String SHARED_PREFERENCES = "SHARED_PREFERENCES";
    SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_sneakers);
        mPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);

        findViewById(R.id.backArrow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        mSneakerList = getSneakers(mPreferences);
        favoriteSneakers = new ArrayList<>();

        for (Sneaker sneaker : mSneakerList) {
            if (sneaker.isFavorite()) {
                favoriteSneakers.add(sneaker);
            }
        }

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mSneakerListAdapter = new SneakerListAdapter(this, favoriteSneakers, this);
        mSneakerListAdapter.notifyDataSetChanged();
        mRecyclerView.setAdapter(mSneakerListAdapter);
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(FavoriteSneakersActivity.this, SneakerDetailActivity.class);
        intent.putExtra("SNEAKER_ID", favoriteSneakers.get(position).getSneakerID());
        startActivity(intent);
    }
}
