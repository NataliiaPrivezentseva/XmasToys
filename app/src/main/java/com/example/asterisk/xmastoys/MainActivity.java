package com.example.asterisk.xmastoys;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.asterisk.xmastoys.adapter.ToyRecyclerAdapter;
import com.example.asterisk.xmastoys.model.Toy;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        //todo maybe finish() should be called before starting new activity?
        if (auth.getCurrentUser() == null) {
            startActivity(new Intent(MainActivity.this, LoginChoiceActivity.class));
            finish();
        }

        // Now we are setting the view for logged in user
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        if (myToolbar != null) {
            setSupportActionBar(myToolbar);
        }

        // Get current user
        FirebaseUser user = auth.getCurrentUser();

        //todo заменить на другой вид коллекции
        final ArrayList<Toy> toyCollection = new ArrayList<>();
        toyCollection.add(new Toy("Такси изобилия", "2017/2018",
                "За этой игрушкой пришлось ходить трижды: первые два раза киоск на ярмарке" +
                        "уже закрылся. А на третий раз игрушки на витрине не оказалось. Думала, " +
                        "что закончились... А на самом деле продавец отложила для меня последний" +
                        "экземпляр.", R.drawable.taxi));
        toyCollection.add(new Toy("Не такси", "2016/2017",
                "А тут должна быть другая история", R.drawable.taxi));
        toyCollection.add(new Toy("Третья игрушка", "2016/2017",
                "А тут должна быть другая история", R.drawable.taxi));
        toyCollection.add(new Toy("Четвертая игрушка", "2016/2017",
                "А тут должна быть другая история", R.drawable.taxi));
        toyCollection.add(new Toy("Пятая игрушка", "2016/2017",
                "А тут должна быть другая история", R.drawable.taxi));
        toyCollection.add(new Toy("Шестая игрушка", "2016/2017",
                "А тут должна быть другая история", R.drawable.taxi));
        toyCollection.add(new Toy("Седьмая игрушка", "2016/2017",
                "А тут должна быть другая история", R.drawable.taxi));

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
        ToyRecyclerAdapter myAdapter = new ToyRecyclerAdapter(this, toyCollection);

//        int amountOfColumns;
//        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
//            amountOfColumns = 1;
//        } else {
//            amountOfColumns = 2;
//        }
        myrv.setLayoutManager(new GridLayoutManager(this, 1));
//        myrv.addItemDecoration(new GridItemSpacingDecoration(amountOfColumns, pdToPx(10), false));
        myrv.setAdapter(myAdapter);
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

    /**
     * Converting dp to pixel
     */
    private int pdToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    class GridItemSpacingDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridItemSpacingDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }
}