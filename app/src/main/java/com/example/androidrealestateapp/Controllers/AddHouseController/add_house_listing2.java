package com.example.androidrealestateapp.Controllers.AddHouseController;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
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

import com.example.androidrealestateapp.Models.ConnectionClass;
import com.example.androidrealestateapp.R;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

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

                Boolean continueQuery = true;

                String FireplaceStr = (fire.isChecked())?"True":"False";
                String BasementStr = (Basement.isChecked())?"True":"False";
                String MainStreetStr = (main.isChecked())?"True":"False";
                String PoolStr = (Pool.isChecked())?"True":"False";
                String BeachHouseStr = (beach.isChecked())?"True":"False";
                String AirConditionStr = (ac.isChecked())?"True":"False";
                String RentSpaceStr = (rent.isChecked())?"True":"False";
                int sqfeetStr = 0;
                int lotStr = 0;
                int yearInt = 1000;

                String heatingStr = heating.getSelectedItem().toString();
                String distributionStr = distribution.getSelectedItem().toString();


                if(sqfeet.getText().toString().trim().length()>1)
                {
                    sqfeetStr = Integer.valueOf(sqfeet.getText().toString());

                    if(lot.getText().toString().trim().length()>1)
                    {
                        lotStr = Integer.valueOf(lot.getText().toString());

                        if(year.getText().toString().trim().length()==4)
                        {
                            if(Integer.parseInt(year.getText().toString()) > 1700)
                            {
                                yearInt = Integer.parseInt(year.getText().toString());
                                continueQuery = true;
                            }
                            else { year.setError("Must be a valid year after 1700"); continueQuery = false; }
                        }
                        else { year.setError("Must be a valid year after 1700"); continueQuery = false; }
                    }
                    else { lot.setError("Cannot be empty"); continueQuery = false; }
                }
                else { sqfeet.setError("Cannot be empty"); continueQuery = false; }


                if(continueQuery)
                {

                    try
                    {

                        ConnectionClass connectionClass = new ConnectionClass(); // Connection Class Initialization
                        Connection conn = (Connection) connectionClass.CONN(); //Connection Object
                        Statement stmt = conn.createStatement();
                        String thisT="insert into Listing(Email,StreetName,City,State,ZipCode,Price,NumOfFloors,NumOfBath,NumOfBed,NumOfGarages,ListingType,Fireplace,Basement,MainStHouse,Pool,BeachHouse,AirCondition,RentSpace,SqFt,LotSize,YearBuilt,HeatingSystem,DistributionSystem) values ('StarrynightFSN@gmail.com','#### 159 Street','Queens','NY','11122','100000','1','2','3','1','Selling','True','True','False','False','True','True','False','100','200','1995','Boiler','Steam Radiant');";


                        // the mysql insert statement
                        String query = "insert into Listing(Email,StreetName,City,State,ZipCode,Price,NumOfFloors,NumOfBath,NumOfBed,NumOfGarages,ListingType,Fireplace,Basement,MainStHouse,Pool,BeachHouse,AirCondition,RentSpace,SqFt,LotSize,YearBuilt,HeatingSystem,DistributionSystem) ";


                        query+= "VALUES ('"+ bundle.getString("Email") + "', '" + bundle.getString("Street") +
                                "', '" + bundle.getString("City") + "', '" + bundle.getString("State") + "', '" +
                                bundle.getString("Zip") + "', " + bundle.getDouble("Price") + ", " +bundle.getDouble("Floors")+
                                ", "+ bundle.getDouble("Bath") + ", " + bundle.getDouble("Bed") + ", " +
                                bundle.getDouble("Garage") + ", '" + bundle.getString("ListingType")+"'" ;

                        query +=  ", '" + FireplaceStr + "', '" + BasementStr + "', '" + MainStreetStr + "', '" + PoolStr +
                                "', '" + BeachHouseStr + "', '" + AirConditionStr + "', '" + RentSpaceStr + "', '" +
                                sqfeetStr + "', '" + lotStr + "', " + yearInt + ", '" + heatingStr + "', '" +
                                distributionStr + "');";


                        stmt.executeUpdate(query);


                        /** Tried using this query as well, is having problems storing to image to database
                        String pic = bundle.getString("picture");
                        PreparedStatement newQuery=conn.prepareStatement("INSERT INTO Pictures(PropertyId,Pic) VALUES(?,?)");
                        newQuery.setInt(1,1);
                        newQuery.setString(2,pic); //there is a problem here, it keeps failing when trying to insert the string 'pic'.... other strings work though
                        int i=newQuery.executeUpdate();
                        **/


                        /*
                        String pictureQuery = "insert into housePictures(PROPERTYID,PIC) ";
                        byte[] byteArray;
                        String encodedImage;
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        Bitmap image = bundle.getParcelable("housepic");
                        image.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
                        byteArray = byteArrayOutputStream.toByteArray();
                        encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);

                        pictureQuery += "VALUES (1, " + encodedImage + ");";


                        stmt.executeQuery(pictureQuery);*/



                        Log.e("ERORR","Worked");
                        conn.close();

                    }
                    catch (Exception f)
                    {
                        Log.e("DATABASE ERORR",f.getMessage());
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
