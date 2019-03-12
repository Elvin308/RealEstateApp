package com.example.androidrealestateapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class add_house_listing2 extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_house_listing_2);
        Button cancel = (Button) findViewById(R.id.CancelButton);
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                finish();
            }

        });
        Button finish = (Button) findViewById(R.id.FinishButton);
        finish.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                finish();
            }

        });

        Spinner heating= findViewById(R.id.HeatingSpinner);
        Spinner distribution= findViewById(R.id.HeatingDistributionSpinner);

        ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(this,R.array.Heating,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        heating.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapterDis= ArrayAdapter.createFromResource(this,R.array.Distribution,android.R.layout.simple_spinner_item);
        adapterDis.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        distribution.setAdapter(adapterDis);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
