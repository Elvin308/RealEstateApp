package com.example.androidrealestateapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class add_house_listing extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_house_listing);

        Spinner bathroom= findViewById(R.id.BathroomSpinner);
        Spinner bedroom= findViewById(R.id.BedroomSpinner);
        Spinner garage= findViewById(R.id.GarageSpinner);
        Spinner floor= findViewById(R.id.FloorSpinner);
        Spinner rentSale= findViewById(R.id.RentSaleSpinner);
        Spinner state= findViewById(R.id.StateSpinner);

        ArrayAdapter<CharSequence> adapterDecimal= ArrayAdapter.createFromResource(this,R.array.numbersDecimal,android.R.layout.simple_spinner_item);
        adapterDecimal.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bathroom.setAdapter(adapterDecimal);
        bedroom.setAdapter(adapterDecimal);

        ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(this,R.array.numbers,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        garage.setAdapter(adapter);
        floor.setAdapter(adapter);


        ArrayAdapter<CharSequence> adapterRentSale= ArrayAdapter.createFromResource(this,R.array.RentSale,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        rentSale.setAdapter(adapterRentSale);

        ArrayAdapter<CharSequence> adapterState= ArrayAdapter.createFromResource(this,R.array.states,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        state.setAdapter(adapterState);

        bathroom.setOnItemSelectedListener(this);
        bedroom.setOnItemSelectedListener(this);
        garage.setOnItemSelectedListener(this);
        floor.setOnItemSelectedListener(this);
        rentSale.setOnItemSelectedListener(this);
        state.setOnItemSelectedListener(this);

        Button next = (Button) findViewById(R.id.NextButton);
        next.setOnClickListener(v->{
            startActivity(new Intent(this, add_house_listing2.class));
            finish();
        });
        //next.setOnClickListener(v-> startActivity(new Intent(this, add_house_listing2.class)));

        Button cancel = (Button) findViewById(R.id.CancelButton);
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                finish();
            }

        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
