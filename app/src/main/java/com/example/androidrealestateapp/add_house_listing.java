package com.example.androidrealestateapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class add_house_listing extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_house_listing);

        Button next = (Button) findViewById(R.id.NextButton);
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                setContentView(R.layout.activity_add_house_listing_2);
                Button cancel = (Button) findViewById(R.id.CancelButton);
                cancel.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        finish();
                    }

                });
                Button finish = (Button) findViewById(R.id.finishButton);
                finish.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        finish();
                    }

                });
            }

        });

        Button cancel = (Button) findViewById(R.id.CancelButton);
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                finish();
            }

        });
    }
}
