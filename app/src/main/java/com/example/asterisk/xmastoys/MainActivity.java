package com.example.asterisk.xmastoys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.asterisk.xmastoys.adapter.ToyAdapter;
import com.example.asterisk.xmastoys.model.Toy;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        if (myToolbar != null){
            setSupportActionBar(myToolbar);
        }

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

        GridView gridview = findViewById(R.id.grid_view);
        gridview.setAdapter(new ToyAdapter(this, toyCollection));

        FloatingActionButton fab = findViewById(R.id.add_toy_fab);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

/*                Intent oneToyIntent = new Intent(MainActivity.this, OneToyActivity.class);
                oneToyIntent.putExtra("position", position);
                oneToyIntent.putExtra("toyCollection", toyCollection);
                startActivity(oneToyIntent);*/

                Intent oneToyIntent = new Intent(v.getContext(), OneToyActivity.class);
                oneToyIntent.putExtra("toyName", toyCollection.get(position).getmToyName());
                startActivity(oneToyIntent);
            }
        });
    }
}