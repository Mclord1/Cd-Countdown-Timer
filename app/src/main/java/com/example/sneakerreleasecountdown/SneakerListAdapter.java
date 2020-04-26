package com.example.sneakerreleasecountdown;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SneakerListAdapter extends RecyclerView.Adapter<SneakerListAdapter.SneakerListViewHolder> {
    private LayoutInflater mLayoutInflater;
    private List<Sneaker> mSneakerList;
    private SneakerItemListener mSneakerItemListener;

    public SneakerListAdapter(Context context, List<Sneaker> sneakers, SneakerItemListener sneakerItemListener) {
        mLayoutInflater = LayoutInflater.from(context);
        mSneakerList = sneakers;
        mSneakerItemListener = sneakerItemListener;
    }

    class SneakerListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView sneakerName;
        TextView sneakerReleaseDate;
        SneakerItemListener mSneakerItemListener;

        SneakerListViewHolder(@NonNull View itemView, SneakerItemListener sneakerItemListener) {
            super(itemView);
            sneakerName = itemView.findViewById(R.id.sneakerItemName);
            sneakerReleaseDate = itemView.findViewById(R.id.sneakerItemDate);
            mSneakerItemListener = sneakerItemListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mSneakerItemListener.onItemClick(getAdapterPosition());
        }
    }


    @NonNull
    @Override
    public SneakerListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.release_item, parent, false);
        return new SneakerListViewHolder(view, mSneakerItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SneakerListViewHolder holder, int position) {
        Sneaker currentSneaker = mSneakerList.get(position);
        holder.sneakerName.setText(currentSneaker.getName());

        String sneakerDate = AllReleasesActivity.getFormattedDate(currentSneaker.getReleaseDate());
        holder.sneakerReleaseDate.setText(sneakerDate);
    }

    @Override
    public int getItemCount() {
        if (mSneakerList == null) {
            return 0;
        } else {
            return mSneakerList.size();
        }
    }


    public interface SneakerItemListener {
        void onItemClick(int position);
    }
}
