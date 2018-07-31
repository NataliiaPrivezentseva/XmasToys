package com.example.asterisk.xmastoys;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.asterisk.xmastoys.adapter.ToyRecyclerAdapter;
import com.example.asterisk.xmastoys.model.Toy;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private ToyRecyclerAdapter myAdapter;
    private List<Toy> toyCollection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        //todo maybe finish() should be called before starting new activity?
        if (auth.getCurrentUser() == null) {
            startActivity(new Intent(MainActivity.this, LoginChoiceActivity.class));
            finish();
        } else {

            // Now we are setting the view for logged in user
            setContentView(R.layout.activity_main);

            Toolbar myToolbar = findViewById(R.id.my_toolbar);
            if (myToolbar != null) {
                setSupportActionBar(myToolbar);
            }

            // Get current user
            FirebaseUser user = auth.getCurrentUser();
            String userId = user.getUid();

            toyCollection = new ArrayList<>();

            FloatingActionButton fab = findViewById(R.id.add_toy_fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent addIntent = new Intent(MainActivity.this, AddToyActivity.class);
                    // start the activity
                    startActivity(addIntent);
                }
            });

            RecyclerView myrv = findViewById(R.id.my_recycler_view);
            myAdapter = new ToyRecyclerAdapter(this, toyCollection);

            myrv.setLayoutManager(new GridLayoutManager(this, 1));
            myrv.setAdapter(myAdapter);

            FirebaseFirestore db = FirebaseFirestore.getInstance();

            db.collection("users").document(userId).collection("toyCollection").get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            if (!queryDocumentSnapshots.isEmpty()) {
                                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                for (DocumentSnapshot doc : list) {
                                    Toy toy = doc.toObject(Toy.class);
                                    if (toy != null) {
                                        if (toy.getmImageResourceId() == 0) {
                                            toy.setmImageResourceId(R.drawable.toy);
                                        }
                                        toyCollection.add(toy);
                                    }
                                }
                                myAdapter.notifyDataSetChanged();
                            }
                        }
                    });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.logout:
                logOut();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void logOut(){
        auth.signOut();
        //closing activity
        finish();
        //starting login activity
        startActivity(new Intent(MainActivity.this, LoginChoiceActivity.class));
    }
}