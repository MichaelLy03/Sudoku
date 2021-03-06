package com.cecs453.sudoku;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private ArrayList<String> mData;
    private ArrayList<String> original;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    MyRecyclerViewAdapter(Context context, ArrayList<String> data, ArrayList<String> original) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.original = original;
    }

    // inflates the cell layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each cell
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (mData.get(position).equals("0")){
            holder.myTextView.setText(" ");
        }
        else{
            holder.myTextView.setText(mData.get(position));
        }
        if(original.get(position).equals("0")){
            if(mData.get(position).equals("0")){
                holder.myTextView.setText(" ");

            }
            else{
                holder.myTextView.setText(mData.get(position));
            }
            holder.myTextView.setTextColor(Color.RED);
        }
        else{
            holder.myTextView.setTextColor(Color.BLACK);
        }
        ArrayList<Integer> theList = new ArrayList<>();
        Collections.addAll(theList,3,12,21,27,36,45,33,42,51,75,66,57);

        if(theList.contains(position) || theList.contains(position-1) || theList.contains(position-2)) {
            holder.myLinearLayout.setBackgroundResource(R.drawable.gray_sudoku_cell);
        } else {
            holder.myLinearLayout.setBackgroundResource(R.drawable.sudoku_cell);
        }
    }


    // total number of cells
    @Override
    public int getItemCount() {
        return mData.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;
        LinearLayout myLinearLayout;
        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.info_text);
            itemView.setOnClickListener(this);
            myLinearLayout = itemView.findViewById(R.id.linear);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    String getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}