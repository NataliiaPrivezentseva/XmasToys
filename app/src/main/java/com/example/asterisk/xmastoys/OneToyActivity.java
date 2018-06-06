package com.example.asterisk.xmastoys;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

public class OneToyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_toy);

        TextView toyName = findViewById(R.id.name_text_view);
        toyName.setText((String) getIntent().getExtras().get("toyName"));

        TextView toyYear = findViewById(R.id.year_text_view);
        toyYear.setText((String) getIntent().getExtras().get("toyYear"));

        ImageView toyPicture = findViewById(R.id.picture_image_view);
        toyPicture.setImageResource((int) getIntent().getExtras().get("toyPicture"));

        TextView toyStory = findViewById(R.id.story_text_view);
        toyStory.setText((String) getIntent().getExtras().get("toyStory"));

/*        //todo implement Parsable to Toy(?)
https://stackoverflow.com/questions/34264306/java-lang-runtimeexception-parcel-unable-to-marshal-value-com-package-nutritio

        int position = (int)getIntent().getExtras().get("position");
        ArrayList<Toy> toyCollection = (ArrayList<Toy>)getIntent().getExtras().get("toyCollection");
        toyName.setText(toyCollection.get(position).getmToyName());*/
    }
}