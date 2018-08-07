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

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class OneToyActivity extends AppCompatActivity {

    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private TextView toyName;
    private TextView toyYear;
    private TextView toyStory;

    private int toyPictureId;
    private String path;
    private String documentID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_toy);

        toyName = findViewById(R.id.name_text_view);
        toyName.setText((String) getIntent().getExtras().get("toyName"));

        toyYear = findViewById(R.id.year_text_view);
        toyYear.setText((String) getIntent().getExtras().get("toyYear"));

        ImageView toyPicture = findViewById(R.id.picture_image_view);
        toyPictureId = (int) getIntent().getExtras().get("toyPictureId");
        if (toyPictureId != 0) {
            toyPicture.setImageResource(toyPictureId);
        } else {
            path = (String) getIntent().getExtras().get("toyPath");
            if(!TextUtils.isEmpty(path)) {
                StorageReference toypicturesRef = storage.getReference(path);
                Log.i("GLIDE", toypicturesRef.toString());
                GlideApp.with(this)
                        .load(toypicturesRef)
                        .into(toyPicture);
            }
        }

        toyStory = findViewById(R.id.story_text_view);
        toyStory.setText((String) getIntent().getExtras().get("toyStory"));

        documentID = (String) getIntent().getExtras().get("documentID");

        FloatingActionButton fab = findViewById(R.id.edit_toy_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editIntent = new Intent(OneToyActivity.this, AddToyActivity.class);

                editIntent.putExtra("toyName",toyName.getText());
                editIntent.putExtra("toyYear",toyYear.getText());
                editIntent.putExtra("toyStory",toyStory.getText());
                editIntent.putExtra("toyPictureId",toyPictureId);
                editIntent.putExtra("toyPath", path);
                editIntent.putExtra("documentID", documentID);
                // start the activity
                startActivity(editIntent);
            }
        });
    }
}