package com.example.asterisk.xmastoys;

        import android.content.Intent;
        import android.os.Bundle;
        import android.support.annotation.NonNull;
        import android.support.annotation.Nullable;
        import android.support.design.widget.FloatingActionButton;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.Toolbar;
        import android.text.TextUtils;
        import android.util.Log;
        import android.view.View;
        import android.widget.EditText;
        import android.widget.ImageView;
        import android.widget.Toast;

        import com.example.asterisk.xmastoys.model.Toy;
        import com.google.android.gms.tasks.OnFailureListener;
        import com.google.android.gms.tasks.OnSuccessListener;
        import com.google.firebase.firestore.CollectionReference;
        import com.google.firebase.firestore.DocumentReference;
        import com.google.firebase.firestore.FirebaseFirestore;

public class AddToyActivity extends AppCompatActivity {

    EditText newToyName;
    EditText newToyYear;
    EditText newToyStory;
    ImageView defaultPicture;
    int newImageResourceId;

    Toy newToy;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_toy);

        Toolbar myToolbar = findViewById(R.id.my_add_toolbar);
        if (myToolbar != null) {
            setSupportActionBar(myToolbar);
            myToolbar.setTitle(R.string.add_toy);
        }

        db = FirebaseFirestore.getInstance();

        newToyName = findViewById(R.id.name_edit_text_view);
        newToyYear = findViewById(R.id.year_edit_text_view);
        newToyStory = findViewById(R.id.story_edit_text_view);
        defaultPicture = findViewById(R.id.default_picture_image_view);

        //TODO for editing an existing toy
        if (getIntent().getExtras() != null){
            //todo how to find this toy in collection?
        }

        FloatingActionButton fabAddToy = findViewById(R.id.done_toy_fab);
        fabAddToy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String toyName = newToyName.getText().toString().trim();
                String toyYear = newToyYear.getText().toString().trim();
                String toyStory = newToyStory.getText().toString().trim();

                CollectionReference dbToyCollection = db.collection("toyCollection");

                if (validateInput(toyName, toyYear)){
                    newToy = new Toy(toyName, toyYear);

                    if (!TextUtils.isEmpty(toyStory)){
                        newToy.setmStory(toyStory);
                    }

                    //todo think, how to add picture

                    dbToyCollection.add(newToy)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(AddToyActivity.this, R.string.toy_added, Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(AddToyActivity.this, MainActivity.class));
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.e("ADD_TOY", e.getMessage());
                                    Toast.makeText(AddToyActivity.this, R.string.toy_not_added, Toast.LENGTH_LONG).show();
                                }
                            });
                }
            }
        });
    }

    public boolean validateInput(String toyName, String toyYear){
        // Check if name of the toy is empty
        if(TextUtils.isEmpty(toyName)){
            Toast.makeText(AddToyActivity.this, "You should provide the name of the toy!",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        // Check if year is empty
        if(TextUtils.isEmpty(toyYear)){
            Toast.makeText(AddToyActivity.this, "You should provide the year for the toy!",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        // Check if year length is not 4 symbols
        if (toyYear.length() != 4) {
            Toast.makeText(this, R.string.year_length, Toast.LENGTH_LONG).show();
            return false;
        }

        // Check if year length is not a digit
        if (toyYear.matches("\\D") || toyYear.contains(".")){
            Toast.makeText(AddToyActivity.this, "Year should contain only numbers",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}