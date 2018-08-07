package com.example.asterisk.xmastoys;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asterisk.xmastoys.model.Toy;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class OneToyActivity extends AppCompatActivity {

    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private Toy currentToy;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_toy);

        TextView toyName = findViewById(R.id.name_text_view);
        TextView toyYear = findViewById(R.id.year_text_view);
        TextView toyStory = findViewById(R.id.story_text_view);
        ImageView toyPicture = findViewById(R.id.picture_image_view);

        if (getIntent().getExtras() != null) {
            currentToy = (Toy) getIntent().getExtras().get("toy");
            assert currentToy != null;

            toyName.setText(currentToy.getmToyName());
            toyYear.setText(currentToy.getmYear());
            toyStory.setText(currentToy.getmStory());

            int toyPictureId = currentToy.getmImageResourceId();
            if (toyPictureId != 0) {
                toyPicture.setImageResource(toyPictureId);
            } else {
                String path = currentToy.getmPath();
                if (!TextUtils.isEmpty(path)) {
                    StorageReference toypicturesRef = storage.getReference(path);
                    Log.i("GLIDE", toypicturesRef.toString());
                    GlideApp.with(this)
                            .load(toypicturesRef)
                            .into(toyPicture);
                }
            }
        }

            FloatingActionButton fab = findViewById(R.id.edit_toy_fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent editIntent = new Intent(OneToyActivity.this, AddToyActivity.class);
                    editIntent.putExtra("toy", currentToy);
                    startActivity(editIntent);
                }
            });
        }
    }