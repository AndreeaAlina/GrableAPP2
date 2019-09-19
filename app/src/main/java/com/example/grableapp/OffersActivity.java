package com.example.grableapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;


import java.util.ArrayList;
import java.util.List;

public class OffersActivity extends AppCompatActivity {

    ArrayList<String> arrayList = new ArrayList<>();


    //ArrayAdapter<String> arrayAdapter;
    private ListView lvProduct;
    private ProductListAdapter adapter;
    private List<Product> mProductList;


    public void addItemToList(String name, double price, String description)
    {
        mProductList.add(new Product(1, name, price, description));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers2);

        lvProduct = (ListView)findViewById(R.id.offersList);

        mProductList = new ArrayList<>();

        addItemToList("Apa plata Dorna", 1.99, "Old price: 2.99 RON");
        addItemToList("Redbull", 3.99, "Old price 4.99 RON");

        adapter = new ProductListAdapter(getApplicationContext(), mProductList);
        lvProduct.setAdapter(adapter);
    }

}
