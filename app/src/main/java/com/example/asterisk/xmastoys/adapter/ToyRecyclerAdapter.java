package com.example.asterisk.xmastoys.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asterisk.xmastoys.GlideApp;
import com.example.asterisk.xmastoys.OneToyActivity;
import com.example.asterisk.xmastoys.R;
import com.example.asterisk.xmastoys.model.Toy;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class ToyRecyclerAdapter extends RecyclerView.Adapter<ToyRecyclerAdapter.MyViewHolder>{

    private Context mContext;
    private List<Toy> mToyCollection;
    private FirebaseStorage storage = FirebaseStorage.getInstance();

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

        int imageResId = mToyCollection.get(position).getmImageResourceId();
        if (imageResId != 0) {
            holder.pictureImageView.setImageResource(imageResId);
        } else {
            String path = mToyCollection.get(position).getmPath();
            if(!TextUtils.isEmpty(path)) {
                StorageReference toypicturesRef = storage.getReference(path);
                Log.i("GLIDE", toypicturesRef.toString());
                GlideApp.with(mContext)
                        .load(toypicturesRef)
                        .into(holder.pictureImageView);
            }
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,OneToyActivity.class);
                intent.putExtra("toy", mToyCollection.get(position));
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