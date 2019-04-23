package com.example.androidrealestateapp.Controllers.FragmentsController;

import android.app.Activity;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidrealestateapp.Models.ConnectionClass;
import com.example.androidrealestateapp.Models.HouseClass;
import com.example.androidrealestateapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.ref.WeakReference;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;

public class ViewHouseSecondModel extends AppCompatActivity {

    private ConnectionClass connectionClass;
    private boolean success = false; // boolean
    String address;
    String email;
    int houseID;
    String userEmail;
    Boolean returnInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_house_second_model);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.appColor)));

        houseID = getIntent().getIntExtra("PropertyId",0);
        userEmail = getIntent().getStringExtra("UserEmail");
        boolean manageHouse = getIntent().getBooleanExtra("Manage",true);
        returnInfo = getIntent().getBooleanExtra("Return",false);

        //connection to database variable
        connectionClass = new ConnectionClass(); // Connection Class Initialization

        //house to hold variables for selected house
        HouseClass house;

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        int newHeight =(int)Math.round(height/3.5);


        ImageView Image = findViewById(R.id.image);
        Picasso.get().load("https://static.dezeen.com/uploads/2017/08/clifton-house-project-architecture_dezeen_hero-1.jpg").resize(width,newHeight).into(Image);




        //connect to database
        TextView year = (TextView)findViewById(R.id.YearText);
        TextView garage = (TextView)findViewById(R.id.GargeText);
        TextView lot = (TextView)findViewById(R.id.LotText);
        TextView air = (TextView)findViewById(R.id.AirText);
        TextView pool = (TextView)findViewById(R.id.PoolText);
        TextView heat = (TextView)findViewById(R.id.HeatText);
        TextView beach = (TextView)findViewById(R.id.BeachText);
        TextView basement = (TextView)findViewById(R.id.BaseText);
        TextView mainST = (TextView)findViewById(R.id.MainText);
        TextView fire = (TextView)findViewById(R.id.FireText);
        TextView rent = (TextView)findViewById(R.id.RentText);
        TextView distSytem = (TextView)findViewById(R.id.DistText);
        TextView Size = findViewById(R.id.Size);
        TextView Price = findViewById(R.id.infoPrice);
        TextView QuickInfo = findViewById(R.id.QuickInfo);
        TextView Address = findViewById(R.id.AddressText);
        TextView SaleRentText = findViewById(R.id.SaleRentText);

        try {
            Connection conn = connectionClass.CONN(); //Connection Object
            if (conn == null) {
                success = false;
            } else {
                String resetQuery = "SELECT * FROM Listing WHERE PropertyID = " + String.valueOf(houseID) + ";";
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(resetQuery);
                if (rs.next()) {

                    try {

                        double amount = rs.getDouble("Price");
                        DecimalFormat formatter = new DecimalFormat("#,###.00");
                        String price = "$" + formatter.format(amount);
                        Price.setText(price);

                        String quickInfo = String.valueOf(rs.getDouble("NumOfFloors")) + " Floors | " + String.valueOf(rs.getDouble("NumOfBed")) + " Beds | " + String.valueOf(rs.getDouble("NumOfBath")) + " Baths ";
                        QuickInfo.setText(quickInfo);

                        email = rs.getString("Email");

                        address = rs.getString("StreetName") + ", " + rs.getString("City") + ", " + rs.getString("State") + ", " + rs.getString("Zipcode");
                        Address.setText(address);

                        String saleRentText = "";
                        if (rs.getString("ListingType").equals("Selling")) {
                            saleRentText = "  House for Sale";
                        } else {
                            saleRentText = "  House for Rent";
                        }
                        SaleRentText.setText(saleRentText);

                        year.setText(String.valueOf(rs.getInt("YearBuilt")));
                        garage.setText(String.valueOf(rs.getDouble("NumOfGarages")));
                        Size.setText(rs.getString("SqFt"));
                        lot.setText(rs.getString("LotSize"));
                        air.setText((rs.getBoolean("AirCondition")) ? "Yes" : "No");
                        pool.setText((rs.getBoolean("Pool")) ? "Yes" : "No");
                        heat.setText(rs.getString("HeatingSystem"));
                        beach.setText((rs.getBoolean("BeachHouse")) ? "Yes" : "No");
                        basement.setText((rs.getBoolean("Basement")) ? "Yes" : "No");
                        mainST.setText((rs.getBoolean("MainStHouse")) ? "Yes" : "No");
                        fire.setText((rs.getBoolean("Fireplace")) ? "Yes" : "No");
                        rent.setText((rs.getBoolean("RentSpace")) ? "Yes" : "No");
                        distSytem.setText(rs.getString("DistributionSystem"));

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    success = true;
                } else {
                }
            }
        } catch (Exception e) {
            //make a toast
        }


        Address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * Finding the address through google maps
                 **/
                Uri gmmIntentUri = Uri.parse("geo:0,0?q="+Address.getText().toString());//1600 Amphitheatre Parkway, Mountain+View, California");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                }
            }
        });


    }

    @Override
    public void onBackPressed() {
        if(returnInfo) {
            Intent returnIntent = new Intent();
            setResult(Activity.RESULT_CANCELED, returnIntent);
            finish();
        }
        else
        {
            finish();
        }
    }

}
