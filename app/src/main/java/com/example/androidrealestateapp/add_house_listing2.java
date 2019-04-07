package com.example.androidrealestateapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

public class add_house_listing2 extends Fragment implements AdapterView.OnItemSelectedListener{
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_add_house_listing_2, container, false);

        Bundle bundle = getArguments();

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



        Spinner heating= view.findViewById(R.id.HeatingSpinner);
        Spinner distribution= view.findViewById(R.id.HeatingDistributionSpinner);

        ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(this.getContext(),R.array.Heating,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        heating.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapterDis= ArrayAdapter.createFromResource(this.getContext(),R.array.Distribution,android.R.layout.simple_spinner_item);
        adapterDis.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        distribution.setAdapter(adapterDis);

        CheckBox fire = (CheckBox) view.findViewById(R.id.checkBoxFire);
        CheckBox Basement = ((CheckBox) view.findViewById(R.id.checkBoxBasement));
        CheckBox Pool = (CheckBox) view.findViewById(R.id.checkBoxPool);
        CheckBox main = ((CheckBox) view.findViewById(R.id.checkBoxMainST));
        CheckBox beach = ((CheckBox) view.findViewById(R.id.checkBoxBeach));
        CheckBox ac = ((CheckBox) view.findViewById(R.id.checkBoxCool));
        CheckBox rent = ((CheckBox) view.findViewById(R.id.checkBoxPlusRent));


        EditText sqfeet = (view.findViewById(R.id.sqfeet));
        EditText lot = (view.findViewById(R.id.lotsize));
        EditText year = view.findViewById(R.id.yearbuilt);

        TextView test = view.findViewById(R.id.test);


        Button finish = (Button) view.findViewById(R.id.FinishButton);
        finish.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                String FireplaceStr = (fire.isChecked())?"True":"False";
                String BasementStr = (Basement.isChecked())?"True":"False";
                String MainStreetStr = (main.isChecked())?"True":"False";
                String PoolStr = (Pool.isChecked())?"True":"False";
                String BeachHouseStr = (beach.isChecked())?"True":"False";
                String AirConditionStr = (ac.isChecked())?"True":"False";
                String RentSpaceStr = (rent.isChecked())?"True":"False";
                String sqfeetStr="N/A";
                String lotStr = "N/A";
                int yearInt = 0;

                String heatingStr = heating.getSelectedItem().toString();
                String distributionStr = distribution.getSelectedItem().toString();


                if(sqfeet.getText().toString().trim().length()>0)
                {
                    sqfeetStr = sqfeet.toString();
                }
                if(lot.getText().toString().trim().length()>0)
                {
                    lotStr = lot.toString();
                }
                if(year.getText().toString().trim().length()>0)
                {
                    yearInt = Integer.parseInt(year.getText().toString());
                }


                try
                {

                    ConnectionClass connectionClass = new ConnectionClass(); // Connection Class Initialization
                    Connection conn = (Connection) connectionClass.CONN(); //Connection Object
                    Statement stmt = conn.createStatement();


                    // the mysql insert statement
                    String query = "INSERT INTO Listing (Email, StreetName, City, State, ZipCode, Price, NumOfFloors, NumOfBath, NumOfBed, NumOfGarages, ListingType, Fireplace, Basement, MainStHouse, Pool, BeachHouse, AirCondition, RentSpace, SqFt, LotSize, YearBuilt, HeatingSystem, DistributionSystem) ";
                    query += "VALUES ('";
                    query+= bundle.getString("Email") + "', '" + bundle.getString("Street") + "', '"
                            + bundle.getString("City") + "', '" + bundle.getString("State") + "', '" +
                            bundle.getString("Zip") + "', " + bundle.getDouble("Price") + ", " +bundle.getDouble("Floors")+", "+
                            bundle.getDouble("Bath") + ", " + bundle.getDouble("Bed") + ", " + bundle.getDouble("Garage") + ", '" +
                            bundle.getString("ListingType") + "', '" + FireplaceStr + "', '" + BasementStr + "', '" +
                            MainStreetStr + "', '" + PoolStr + "', '" + BeachHouseStr + "', '" +
                            AirConditionStr + "', '" + RentSpaceStr + "', '" + sqfeetStr + "', '" +
                            lotStr + "', " + yearInt + ", '" + heatingStr + "', '" +
                            distributionStr + "');";


                    stmt.executeQuery(query);

                    conn.close();
                }
                catch (Exception e)
                {
                    Toast.makeText(getContext(), "Got an exception!",Toast.LENGTH_SHORT);
                }



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

        return view;
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
