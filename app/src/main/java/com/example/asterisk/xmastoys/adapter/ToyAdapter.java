package com.example.asterisk.xmastoys.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asterisk.xmastoys.R;
import com.example.asterisk.xmastoys.model.Toy;

import java.util.ArrayList;

public class ToyAdapter extends BaseAdapter {

    private ArrayList<Toy> mToyCollection;

    private LayoutInflater mInflater;

    public ToyAdapter(Context context, ArrayList<Toy> toyCollection) {
        mToyCollection = toyCollection;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mToyCollection.size();
    }

    @Override
    public Object getItem(int position) {
        return mToyCollection.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View gridItemView = convertView;
        if (gridItemView == null) {
            gridItemView = mInflater.inflate(R.layout.item_cardview_toy, parent, false);
        }

        ImageView pictureImageView = gridItemView.findViewById(R.id.picture_image_view);
        TextView toyNameTextView = gridItemView.findViewById(R.id.toy_name_text_view);
        TextView toyYearTextView = gridItemView.findViewById(R.id.toy_year_text_view);

        Toy currentToy = (Toy) getItem(position);
        pictureImageView.setImageResource(currentToy.getmImageResourceId());
        toyNameTextView.setText(currentToy.getmToyName());
        toyYearTextView.setText(currentToy.getmYear());

        return gridItemView;
    }
}
