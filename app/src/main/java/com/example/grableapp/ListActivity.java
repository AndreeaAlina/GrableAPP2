package com.example.grableapp;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;


public class ListActivity extends AppCompatActivity  {

    ArrayList<String> arrayList = new ArrayList<>();


    ArrayAdapter<String> arrayAdapter;

    Button add_btn;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        ListView listView = findViewById(R.id.listView);

        add_btn = findViewById(R.id.list_btn);

        arrayList.add("Supa la plic");
        arrayList.add("Coca-Cola");
        arrayList.add("Apa plata");

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();


        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListActivity.this, ScannerActivity.class);
                startActivity(intent);
            }
        });


    }

}
