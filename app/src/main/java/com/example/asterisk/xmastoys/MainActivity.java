package com.example.asterisk.xmastoys;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.asterisk.xmastoys.adapter.ToyRecyclerAdapter;
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
        toyCollection.add(new Toy("Четвертая игрушка", "2016/2017",
                "А тут должна быть другая история", R.drawable.taxi));
        toyCollection.add(new Toy("Пятая игрушка", "2016/2017",
                "А тут должна быть другая история", R.drawable.taxi));
        toyCollection.add(new Toy("Шестая игрушка", "2016/2017",
                "А тут должна быть другая история", R.drawable.taxi));
        toyCollection.add(new Toy("Седьмая игрушка", "2016/2017",
                "А тут должна быть другая история", R.drawable.taxi));

        //todo прописать логику кнопки
        FloatingActionButton fab = findViewById(R.id.add_toy_fab);

        RecyclerView myrv = findViewById(R.id.my_recycler_view);
        ToyRecyclerAdapter myAdapter = new ToyRecyclerAdapter(this,toyCollection);

        int amountOfColumns;
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            amountOfColumns = 2;
        }
        else{
            amountOfColumns = 3;
        }
        myrv.setLayoutManager(new GridLayoutManager(this, amountOfColumns));

        myrv.setAdapter(myAdapter);
    }
}