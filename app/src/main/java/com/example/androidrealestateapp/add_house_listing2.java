package com.example.androidrealestateapp;

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

public class add_house_listing2 extends Fragment implements AdapterView.OnItemSelectedListener{
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_add_house_listing_2, container, false);

        Button back = (Button) view.findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
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
            }

        });
        Button finish = (Button) view.findViewById(R.id.FinishButton);
        finish.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Fragment fragmentA = getActivity().getSupportFragmentManager().findFragmentByTag("toAddHouse2");
                Fragment fragmentB = getActivity().getSupportFragmentManager().findFragmentByTag("toAddHouse1");
                if (fragmentA == null) {

                }
                else
                {
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.remove(fragmentA);
                    fragmentTransaction.remove(fragmentB);
                    fragmentTransaction.commit();
                    fragmentManager.popBackStack();
                    fragmentManager.popBackStack();
                }
            }

        });

        Spinner heating= view.findViewById(R.id.HeatingSpinner);
        Spinner distribution= view.findViewById(R.id.HeatingDistributionSpinner);

        ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(this.getContext(),R.array.Heating,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        heating.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapterDis= ArrayAdapter.createFromResource(this.getContext(),R.array.Distribution,android.R.layout.simple_spinner_item);
        adapterDis.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        distribution.setAdapter(adapterDis);

        return view;
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
