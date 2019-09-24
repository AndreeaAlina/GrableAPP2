package com.example.grableapp;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ListActivity extends AppCompatActivity  {

    ArrayList<String> arrayList = new ArrayList<>();


    ArrayAdapter<String> arrayAdapter;

    Map<String, String> shoppingList = new HashMap<String, String>();

    Button add_btn;
    Button finish_btn;
    TextView text_view;
    ImageView done;
    ImageView done_circle;
    AnimatedVectorDrawableCompat avd;
    AnimatedVectorDrawable avd2;
    TextView thank_you_text;

    private ListView lvProduct;
    private ProductListAdapter adapter;
    private List<Product> mProductList;
    private int number_of_products = 0;
    private double total = 0.0;


    public void addItemToList(String name, double price, String description)
    {
//        if (mProductList.isEmpty()) {
//            mProductList.add(new Product(1, name, price, description));
//        }
//        else{
//            int id = mProductList.get(mProductList.size()).getId();
//            mProductList.add(new Product(id+1, name, price, description));
//        }
        //nu merge varianta de mai sus
        //trebuie adaugat id-ul ultimului element din lista + 1

        mProductList.add(new Product(1, name, price, description));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        add_btn = findViewById(R.id.list_btn);
        finish_btn = findViewById(R.id.finish_shopping);
        text_view = findViewById(R.id.textviewlist);
        done = findViewById(R.id.done);
        done_circle = findViewById(R.id.markfundal);
        thank_you_text = findViewById(R.id.thankyoutext);


        final ImageView loading_animation = findViewById(R.id.loadinganimation);
        final ImageView payment_view = findViewById(R.id.animationview);
        final TextView plsw_wait = findViewById(R.id.pleasewaitvew);
        final TextView processing = findViewById(R.id.proccesingview);

        loading_animation.setAlpha(1f);
        payment_view.setAlpha(1f);
        plsw_wait.setAlpha(1f);
        processing.setAlpha(1f);

        loading_animation.animate().translationYBy(-1000f);
        payment_view.animate().translationYBy(-1000f);
        plsw_wait.animate().translationYBy(-1000f);
        processing.animate().translationYBy(-1000f);
        thank_you_text.animate().translationYBy(1000);




        lvProduct = (ListView)findViewById(R.id.listview_produsct);

        mProductList = new ArrayList<>();


        //Add items to list
        //Se poate din database
/*
        mProductList.add(new Product(1, "Kefir", 12, "Covalact, 20% grasime"));
        mProductList.add(new Product(2, "Tastatura Asus", 300, "Tastatura Gaming Asus Cerberus"));
        mProductList.add(new Product(3, "iPhone5s", 1000, "Apple iPhone5s 16GB"));
        mProductList.add(new Product(4, "Paine VelPitar", 3.5, "Paine VelPitar Alba feliata"));
        mProductList.add(new Product(5, "iPhone6s", 1000, "Apple iPhone6s 16GB"));
        mProductList.add(new Product(6, "iPhone7", 1500, "Apple iPhone7 16GB"));
        mProductList.add(new Product(7, "iPhone7Plus", 2000, "Apple iPhone7Plus 16GB"));
        mProductList.add(new Product(8, "iPhone8", 2500, "Apple iPhone8 16GB"));
        mProductList.add(new Product(9, "iPhoneX", 4000, "Apple iPhoneX 16GB"));
        mProductList.add(new Product(10, "iPhone11", 6000, "Apple iPhone11 16GB"));
        addItemToList("Carne", 20.5, "Piept de pui");
*/

        ///more to add


        ///init adapter
        adapter = new ProductListAdapter(getApplicationContext(), mProductList);
        lvProduct.setAdapter(adapter);

//        lvProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                //ceva
//                //functie de delete item
//                Toast.makeText(getApplicationContext(), "Clicked product id =" + view.getTag(), Toast.LENGTH_LONG).show();
//            }
//        });


        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListActivity.this, ScannerActivity.class);
                //startActivity(intent);
                mProductList.add(new Product(1, "Kefir", 12, "Covalact, 20% grasime"));
                number_of_products++;
                text_view.setAlpha(0f);
                adapter.notifyDataSetChanged();
            }
        });

        finish_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //Toast.makeText(ListActivity.this, "Mai mult cod", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(ListActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Confirm finish shopping?");
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AlertDialog.Builder build2 = new AlertDialog.Builder(ListActivity.this);
                        build2.setTitle("Choose payment method");
                        build2.setPositiveButton("Credit card", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialogInterface, int i) {
                                for (Product item : mProductList) {
                                    total += item.getPrice();
                                    //Intent intent = new Intent(ListActivity.this, CardPaymentActivity.class);
                                    //startActivity(intent);
                                }
                                String totall = total + "";
                                lvProduct.setAlpha(0f);
                                add_btn.setAlpha(0f);
                                finish_btn.setAlpha(0f);
                                //Handler handler2 = new Handler();
                                loading_animation.animate().translationYBy(1000f).setDuration(500);
                                payment_view.animate().translationYBy(1000f).setDuration(500);
                                plsw_wait.animate().translationYBy(1000f).setDuration(500);
                                processing.animate().translationYBy(1000f).setDuration(500);


                                Handler handler2 = new Handler();
                                handler2.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        loading_animation.animate().rotation(3600).setDuration(5000);
                                    }
                                }, 500);

                                //loading_animation.animate().rotation(3600).setDuration(5000);
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        // Do something after 5s = 5000ms
                                        loading_animation.setAlpha(0f);
                                        payment_view.setAlpha(0f);
                                        plsw_wait.setAlpha(0f);
                                        processing.setAlpha(0f);

                                    }
                                }, 7000);

                                Handler handler3 = new Handler();
                                handler3.postDelayed(new Runnable() {

                                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                                    @Override
                                    public void run() {
                                        done_circle.setAlpha(1f);
                                        Drawable drawable = done.getDrawable();
                                        if (drawable instanceof AnimatedVectorDrawableCompat)
                                        {
                                            avd = (AnimatedVectorDrawableCompat) drawable;
                                            avd.start();
                                        }
                                        else if (drawable instanceof AnimatedVectorDrawable)
                                        {
                                            avd2 = (AnimatedVectorDrawable) drawable;
                                            avd2.start();
                                        }
                                    }
                                }, 7500);

                                Handler handler4 = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        thank_you_text.animate().translationYBy(-1000).setDuration(300);
                                    }
                                }, 8000);

                            }
                        });
                        build2.setNegativeButton("Generate QR code", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                for (Product item : mProductList) {
                                    total += item.getPrice();
                                }
                                String totall = total + "";
                                Intent intent = new Intent(ListActivity.this, QRActivity.class);
                                intent.putExtra("key", totall);
                                startActivity(intent);

                            }
                        });
                        build2.show();
                    }
                });
                    builder.show();
            }
        });

    }

}

