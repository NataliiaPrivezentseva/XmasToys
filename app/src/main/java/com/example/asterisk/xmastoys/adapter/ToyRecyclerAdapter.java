package com.example.asterisk.xmastoys.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asterisk.xmastoys.OneToyActivity;
import com.example.asterisk.xmastoys.R;
import com.example.asterisk.xmastoys.model.Toy;

import java.util.ArrayList;
import java.util.List;

public class ToyRecyclerAdapter extends RecyclerView.Adapter<ToyRecyclerAdapter.MyViewHolder>{

    private Context mContext;
    private List<Toy> mToyCollection;

    public ToyRecyclerAdapter(Context context, List<Toy> toyCollection) {
        mContext = context;
        mToyCollection = toyCollection;
    }

    @Override
    public int getItemCount() {
        return mToyCollection.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.item_cardview_toy,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.toyNameTextView.setText(mToyCollection.get(position).getmToyName());
        holder.toyYearTextView.setText(mToyCollection.get(position).getmYear());
        holder.pictureImageView.setImageResource(mToyCollection.get(position).getmImageResourceId());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext,OneToyActivity.class);

                // passing data to the book activity
                intent.putExtra("toyName",mToyCollection.get(position).getmToyName());
                intent.putExtra("toyYear",mToyCollection.get(position).getmYear());
                intent.putExtra("toyPicture",mToyCollection.get(position).getmImageResourceId());
                intent.putExtra("toyStory", mToyCollection.get(position).getmStory());

                // start the activity
                mContext.startActivity(intent);
            }
        });
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView toyNameTextView;
        ImageView pictureImageView;
        TextView toyYearTextView;
        CardView cardView;

        MyViewHolder(View itemView) {
            super(itemView);

            toyNameTextView = itemView.findViewById(R.id.toy_name_text_view);
            pictureImageView = itemView.findViewById(R.id.toy_picture_image_view);
            toyYearTextView = itemView.findViewById(R.id.toy_year_text_view);
            cardView = itemView.findViewById(R.id.card_view);
        }
    }

}