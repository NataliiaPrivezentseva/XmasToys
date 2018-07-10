package com.example.asterisk.xmastoys;

        import android.content.Intent;
        import android.os.Bundle;
        import android.support.annotation.Nullable;
        import android.support.design.widget.FloatingActionButton;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.Toolbar;
        import android.util.Log;
        import android.view.View;
        import android.widget.EditText;
        import android.widget.ImageView;
        import android.widget.Toast;

        import com.example.asterisk.xmastoys.model.Toy;
        import com.google.android.gms.tasks.OnSuccessListener;
        import com.google.firebase.firestore.DocumentReference;
        import com.google.firebase.firestore.FirebaseFirestore;

        import java.util.HashMap;
        import java.util.Map;

public class AddToyActivity extends AppCompatActivity {

    EditText newToyName;
    EditText newToyYear;
    EditText newToyStory;
    ImageView defaultPicture;
    int newImageResourceId;

    Toy newToy;

    private DocumentReference mDocRef = FirebaseFirestore.getInstance().document("toyCollection/toy");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_toy);

        Toolbar myToolbar = findViewById(R.id.my_add_toolbar);
        if (myToolbar != null) {
            setSupportActionBar(myToolbar);
            myToolbar.setTitle(R.string.add_toy);
        }

        //todo for editing an existing toy
        if (getIntent().getExtras() != null){

        }

        FloatingActionButton fab = findViewById(R.id.done_toy_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addIntent = new Intent(AddToyActivity.this, MainActivity.class);

                //todo here should be code that add new toy to the Cloud Firebase
                //todo before writing into Cloud Firebase we need to check all entered data
                initialUiSetup();


                // start the activity, which will show an updated list of toys
                startActivity(addIntent);
            }
        });
    }

    public void initialUiSetup(){
        newToyName = findViewById(R.id.name_edit_text_view);
        newToyYear = findViewById(R.id.year_edit_text_view);
        newToyStory = findViewById(R.id.story_edit_text_view);
        defaultPicture = findViewById(R.id.default_picture_image_view);
    }

    //todo this method is not finished yet
    public boolean validateInput(){
        if(newToyName.getText().toString().isEmpty()){
            Toast.makeText(AddToyActivity.this, "You should provide the name of the toy!",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        if(newToyYear.getText().toString().isEmpty()){
            Toast.makeText(AddToyActivity.this, "You should provide the year for the toy!",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

}