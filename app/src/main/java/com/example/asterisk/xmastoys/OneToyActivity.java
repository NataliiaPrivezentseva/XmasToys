package com.example.asterisk.xmastoys;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class OneToyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_toy);

        TextView toyName = findViewById(R.id.name_text_view);

/*        //todo implement Parsable to Toy(?)
https://stackoverflow.com/questions/34264306/java-lang-runtimeexception-parcel-unable-to-marshal-value-com-package-nutritio

        int position = (int)getIntent().getExtras().get("position");
        ArrayList<Toy> toyCollection = (ArrayList<Toy>)getIntent().getExtras().get("toyCollection");
        toyName.setText(toyCollection.get(position).getmToyName());*/

        toyName.setText((String) getIntent().getExtras().get("toyName"));

    }
}