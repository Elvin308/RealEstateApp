package com.example.androidrealestateapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class add_house_listing extends Fragment implements AdapterView.OnItemSelectedListener {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.activity_add_house_listing,container,false);

        Spinner bathroom= view.findViewById(R.id.BathroomSpinner);
        Spinner bedroom= view.findViewById(R.id.BedroomSpinner);
        Spinner garage= view.findViewById(R.id.GarageSpinner);
        Spinner floor= view.findViewById(R.id.FloorSpinner);
        Spinner rentSale= view.findViewById(R.id.RentSaleSpinner);
        Spinner state= view.findViewById(R.id.StateSpinner);

        ArrayAdapter<CharSequence> adapterDecimal= ArrayAdapter.createFromResource(this.getContext(),R.array.numbersDecimal,android.R.layout.simple_spinner_item);
        adapterDecimal.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bathroom.setAdapter(adapterDecimal);
        bedroom.setAdapter(adapterDecimal);

        ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(this.getContext(),R.array.numbers,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        garage.setAdapter(adapter);
        floor.setAdapter(adapter);


        ArrayAdapter<CharSequence> adapterRentSale= ArrayAdapter.createFromResource(this.getContext(),R.array.RentSale,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        rentSale.setAdapter(adapterRentSale);

        ArrayAdapter<CharSequence> adapterState= ArrayAdapter.createFromResource(this.getContext(),R.array.states,android.R.layout.simple_spinner_item);
        adapterState.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        state.setAdapter(adapterState);

        bathroom.setOnItemSelectedListener(this);
        bedroom.setOnItemSelectedListener(this);
        garage.setOnItemSelectedListener(this);
        floor.setOnItemSelectedListener(this);
        rentSale.setOnItemSelectedListener(this);
        state.setOnItemSelectedListener(this);

        Button next = (Button) view.findViewById(R.id.NextButton);
        next.setOnClickListener(v->{
            Fragment fragment = new add_house_listing2();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment,"toAddHouse2");
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        });

       Button cancel= (Button) view.findViewById(R.id.CancelButton);
        cancel.setOnClickListener(v->{
            Fragment fragmentA = getActivity().getSupportFragmentManager().findFragmentByTag("toAddHouse1");
            if (fragmentA == null) {

            }
            else
            {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.remove(fragmentA);
                fragmentTransaction.commit();
                fragmentManager.popBackStack();
            }
        });
        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

}
