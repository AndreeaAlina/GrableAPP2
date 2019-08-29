package com.example.grableapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class OffersActivity extends AppCompatActivity {

    ArrayList<String> offersList = new ArrayList<>();

    public void addItemToOffers(String item) {
        offersList.add(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers);

        ListView offersListView = findViewById(R.id.offersList);

        ArrayList<String> offersList = new ArrayList<>();
        addItemToOffers("Apa Dorna");
        addItemToOffers("Bere Timisoreana");
        addItemToOffers("Rosii Cherry");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, offersList);
        offersListView.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();



    }



}
