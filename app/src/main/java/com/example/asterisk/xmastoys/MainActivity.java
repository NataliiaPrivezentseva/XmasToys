package com.example.asterisk.xmastoys;

import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.widget.GridView;

import com.example.asterisk.xmastoys.adapter.ToyAdapter;
import com.example.asterisk.xmastoys.model.Toy;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_toy_fab);

//        gridview.setOnItemClickListener(new OnItemClickListener() {
//            public void onItemClick(AdapterView<?> parent, View v,
//                                    int position, long id) {
//                Toast.makeText(HelloGridView.this, "" + position,
//                        Toast.LENGTH_SHORT).show();
//            }
//        });
    }
}
