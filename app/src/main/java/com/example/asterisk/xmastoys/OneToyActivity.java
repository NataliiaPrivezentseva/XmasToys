package com.example.asterisk.xmastoys;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class OneToyActivity extends AppCompatActivity {

    private FirebaseStorage storage = FirebaseStorage.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_toy);

        TextView toyName = findViewById(R.id.name_text_view);
        toyName.setText((String) getIntent().getExtras().get("toyName"));

        TextView toyYear = findViewById(R.id.year_text_view);
        toyYear.setText((String) getIntent().getExtras().get("toyYear"));

        ImageView toyPicture = findViewById(R.id.picture_image_view);
        int toyPictureId = (int) getIntent().getExtras().get("toyPictureId");
        if (toyPictureId != 0) {
            toyPicture.setImageResource(toyPictureId);
        } else {
            String path = (String) getIntent().getExtras().get("toyPath");
            if(!TextUtils.isEmpty(path)) {
                StorageReference toypicturesRef = storage.getReference(path);
                Log.i("GLIDE", toypicturesRef.toString());
                GlideApp.with(this)
                        .load(toypicturesRef)
                        .into(toyPicture);
            }
        }

        TextView toyStory = findViewById(R.id.story_text_view);
        toyStory.setText((String) getIntent().getExtras().get("toyStory"));
    }
}