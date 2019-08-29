package com.example.grableapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainloginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainlogin);


        Button login_btn = findViewById(R.id.firstpagelogin);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainloginActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });

    }

    public void register(View view) {
        Intent intent = new Intent(MainloginActivity.this, SignUpActivity.class);
        startActivity(intent);
    }

}
