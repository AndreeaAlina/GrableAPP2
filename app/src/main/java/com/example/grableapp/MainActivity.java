package com.example.grableapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

        Button shop;
        Button list;
        Button settings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        shop=(Button)findViewById(R.id.shop_btn);
        list = (Button) findViewById(R.id.list_btn);
        settings = (Button) findViewById(R.id.settingsButton);


        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });



        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Doriti sa cumparati mai mult de 5 produse?");
                builder.setNegativeButton("Nu", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialog.Builder build2 = new AlertDialog.Builder(MainActivity.this);
                        build2.setCancelable(true);
                        build2.setTitle("Puteti incepe sa scanati produsele");
                        build2.setPositiveButton("SCANEAZA", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent myIntent = new Intent(MainActivity.this, ScannerActivity.class);

                                int cart = 0;
                                myIntent.putExtra("cos", cart);
                                startActivity(myIntent);

                            }
                        });
                        build2.setNegativeButton("RENUNTA", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        build2.show();
                    }
                });

                builder.setPositiveButton("Da", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialog.Builder build = new AlertDialog.Builder(MainActivity.this);
                        build.setCancelable(true);
                        build.setTitle("Va rugam sa scanati codul QR atasat cosului dumneavoastra de cumparaturi!");
                        build.setPositiveButton("SCANEAZA", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent myIntent = new Intent(MainActivity.this, ScannerActivity.class);

                                int cart = 1;
                                myIntent.putExtra("cos", cart);
                                startActivity(myIntent);
                            }
                        });
                        build.setNegativeButton("RENUNTA", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        build.show();
                    }
                });

                builder.show();
            }
        });

        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                startActivity(intent);
            }
        });
    }
}
