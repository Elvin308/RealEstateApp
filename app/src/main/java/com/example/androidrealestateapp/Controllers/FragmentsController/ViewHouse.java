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

public class ViewHouse extends AppCompatActivity {

    private ConnectionClass connectionClass;
    private boolean success = false; // boolean
    String address;
    String email;
    int houseID;
    String userEmail;
    Boolean returnInfo;
    int verify=0;
    String type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_house);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.appColor)));

        houseID = getIntent().getIntExtra("PropertyId",0);
        userEmail = getIntent().getStringExtra("UserEmail");
        boolean manageHouse = getIntent().getBooleanExtra("Manage",true);
        returnInfo = getIntent().getBooleanExtra("Return",false);
        type = getIntent().getStringExtra("type");

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


        ImageView favorite = findViewById(R.id.favorite);
        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewHouse.CheckFavorite checkFavorite = new CheckFavorite(ViewHouse.this);
                checkFavorite.execute();
            }
        });



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

        ViewHouse.FirstCheckFavorite firstCheckFavorite = new FirstCheckFavorite(ViewHouse.this);
        firstCheckFavorite.execute();


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

        if(!manageHouse)
        {
            Button sendRequest = findViewById(R.id.OptionButton);
            sendRequest.setText("Send Request");
            sendRequest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Dialog RequestDialog=new Dialog(v.getContext());
                    RequestDialog.setContentView(R.layout.request_sender_dialog);
                    RequestDialog.setTitle("Request");
                    RequestDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                    EditText message = RequestDialog.findViewById(R.id.messageRequest);
                    message.setText("Hello, I am interested in the property located at "+ address);

                    RequestDialog.show();
                    RequestDialog.getWindow().setLayout((6 * width)/7, ViewGroup.LayoutParams.WRAP_CONTENT);

                    Button send = RequestDialog.findViewById(R.id.Send);
                    send.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View c) {

                            String sender=userEmail;
                            String reciever = email;
                            String theMessage = message.getText().toString();
                            int PropertyID=houseID;
                            String values="'"+theMessage+"','"+sender+"',"+PropertyID+",'"+reciever+"'";
                            ViewHouse.SendRequest sendRequest = new SendRequest(ViewHouse.this);
                            sendRequest.execute(values);
                            RequestDialog.cancel();
                        }
                    });
                }
            });
        }
        else
        {
            Button modify = findViewById(R.id.OptionButton);
            modify.setText("More Options");
            modify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Dialog Options=new Dialog(v.getContext());
                    Options.setContentView(R.layout.more_options_dialog);
                    Options.setTitle("Options");
                    Options.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    Options.show();
                    Options.getWindow().setLayout((6 * width)/7, ViewGroup.LayoutParams.WRAP_CONTENT);


                    Button delete=Options.findViewById(R.id.delete);
                    Button sold_rented= Options.findViewById(R.id.store);

                    Options.setOnDismissListener(dismissView ->{
                        verify=0;
                    });

                    delete.setOnClickListener(deleteView->{
                        verify++;
                        if(verify < 2) {
                            Toast.makeText(deleteView.getContext(), "Are you sure you want to delete?\nPress again to verify", Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(deleteView.getContext(), "Deleted", Toast.LENGTH_SHORT).show();
                            //Write code to delete here

                            String value = String.valueOf(houseID);
                            ViewHouse.DeleteHouse deleteHouse = new ViewHouse.DeleteHouse();
                            deleteHouse.execute(value);

                            Options.dismiss();
                            finish();
                        }
                    });

                    sold_rented.setOnClickListener(storeView ->{
                        /**OPEN NEW DIALOG**/
                        Options.dismiss();

                        Dialog storeDialog=new Dialog(v.getContext());
                        storeDialog.setContentView(R.layout.price_sold);
                        storeDialog.setTitle("Options");
                        storeDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        storeDialog.show();
                        storeDialog.getWindow().setLayout((6 * width)/7, ViewGroup.LayoutParams.WRAP_CONTENT);

                        EditText priceSoldField = storeDialog.findViewById(R.id.priceSoldInputField);
                        Button enterPriceSold = storeDialog.findViewById(R.id.EnterPriceSold);

                        enterPriceSold.setOnClickListener(updateView ->{

                            String[] updateValue = {String.valueOf(houseID),priceSoldField.getText().toString(),type};
                            ViewHouse.Updatehouse updatehouse = new ViewHouse.Updatehouse();
                            updatehouse.execute(updateValue);
                            storeDialog.dismiss();
                            finish();
                        });
                    });

                    //add code to modify house info
                }
            });
        }

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

    private class CheckFavorite extends AsyncTask<String, String, String> {
        ProgressDialog progress;
        String reply = "Not Found";

        private WeakReference<Context> contextReference;
        CheckFavorite(Context context) {
            contextReference = new WeakReference<>(context);
        }

        @Override
        protected void onPreExecute() //Starts the progress dailog
        {
            //progress = ProgressDialog.show(ViewHouse.this, "Synchronising", "RecyclerView Loading! Please Wait...", true);
        }
        @Override
        protected String doInBackground(String... strings) {
            try {
                Connection conn = connectionClass.CONN(); //Connection Object
                if (conn == null) {
                    success = false;
                } else {
                    String resetQuery = "SELECT * FROM Favorites where email ='" + userEmail + "' and propertyId = " + houseID + ";";
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(resetQuery);
                    if (rs.next()) {
                        String query2 = "DELETE FROM Favorites WHERE email='" + userEmail + "' AND PropertyID =" + houseID + ";";
                        Statement stmt2 = conn.createStatement();
                        stmt2.executeUpdate(query2);
                        reply = "Found";
                    } else {
                        String query2 = "INSERT INTO Favorites(Email,PropertyID) VALUES ('" + userEmail + "'," + houseID + ");";
                        Statement stmt2 = conn.createStatement();
                        stmt2.executeUpdate(query2);
                        reply = "Not Found";
                    }
                }
            } catch (Exception e) {
                reply = "Not Found";
            }

            return reply;
        }
        @Override
        protected void onPostExecute(String msg) // disimissing progress dialoge, showing error and setting up my listview
        {
            AppCompatActivity context = (AppCompatActivity) contextReference.get();
            if(context != null) {
                ImageView favorite = context.findViewById(R.id.favorite);
                if(reply.equals("Not Found"))
                {
                    favorite.setImageResource(R.drawable.ic_baseline_favorite_24px);
                }
                else
                {
                    favorite.setImageResource(R.drawable.ic_baseline_favorite_border_24px);
                }

            }
            // progress.dismiss();
        }
    }

    private class FirstCheckFavorite extends AsyncTask<String, String, String> {
        ProgressDialog progress;
        String reply = "Not Found";

        private WeakReference<Context> contextReference;
        FirstCheckFavorite(Context context) {
            contextReference = new WeakReference<>(context);
        }

        @Override
        protected void onPreExecute() //Starts the progress dailog
        {
            //progress = ProgressDialog.show(ViewHouse.this, "Synchronising", "RecyclerView Loading! Please Wait...", true);
        }
        @Override
        protected String doInBackground(String... strings) {
            try {
                Connection conn = connectionClass.CONN(); //Connection Object
                if (conn == null) {
                    success = false;
                } else {
                    String resetQuery = "SELECT * FROM Favorites where email ='" + userEmail + "' and propertyId = " + houseID + ";";
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(resetQuery);
                    if (rs.next()) {
                        reply = "Found";
                    } else {
                        reply = "Not Found";
                    }
                }
            } catch (Exception e) {
                reply = "Not Found";
            }

            return reply;
        }
        @Override
        protected void onPostExecute(String msg) // disimissing progress dialoge, showing error and setting up my listview
        {
            AppCompatActivity context = (AppCompatActivity) contextReference.get();
            if(context != null) {
                ImageView favorite = context.findViewById(R.id.favorite);
                if(reply.equals("Not Found"))
                {
                    favorite.setImageResource(R.drawable.ic_baseline_favorite_border_24px);
                }
                else
                {
                    favorite.setImageResource(R.drawable.ic_baseline_favorite_24px);
                }

            }
            // progress.dismiss();
        }
    }


    static private class SendRequest extends AsyncTask<String, String, String> {
        ProgressDialog progress;
        Boolean completed = false;


        private WeakReference<Context> contextReference;
        SendRequest(Context context) {
            contextReference = new WeakReference<>(context);
        }

        @Override
        protected void onPreExecute() //Starts the progress dailog
        {
            //progress = ProgressDialog.show(ViewHouse.this, "Synchronising", "RecyclerView Loading! Please Wait...", true);
        }
        @Override
        protected String doInBackground(String... strings) {
            String values = strings[0];
            try
            {
                ConnectionClass connectionClass = new ConnectionClass(); // Connection Class Initialization
                Connection conn = (Connection) connectionClass.CONN(); //Connection Object
                Statement stmt = conn.createStatement();
                String query = "INSERT INTO Request(Message,SEmail,PropertyID,REmail) VALUES("+values+");";
                stmt.executeUpdate(query);

                conn.close();

                completed = true;
            }
            catch (SQLException e)
            {
                completed = false;
                Log.e("ERORR",e.getMessage());
            }

            return "Completed";
        }
        @Override
        protected void onPostExecute(String msg) // disimissing progress dialoge, showing error and setting up my listview
        {
            AppCompatActivity context = (AppCompatActivity) contextReference.get();
            if(context != null) {
               if(completed)
               {
                   Toast.makeText(context, "Message Sent", Toast.LENGTH_SHORT).show();
               }
               else
               {
                   Toast.makeText(context, "You already sent a message before, please wait for a call back", Toast.LENGTH_LONG).show();
               }
            }
            // progress.dismiss();
        }
    }


    static private class DeleteHouse extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() //Starts the progress dailog
        {

        }
        @Override
        protected String doInBackground(String... strings) {
            String value = strings[0];
            try
            {
                ConnectionClass connectionClass = new ConnectionClass(); // Connection Class Initialization
                Connection conn = (Connection) connectionClass.CONN(); //Connection Object
                Statement stmt = conn.createStatement();
                String query = "DELETE FROM listing WHERE PropertyID='"+value+"';";
                stmt.executeUpdate(query);

                conn.close();


            }
            catch (SQLException e)
            {

                Log.e("ERORR",e.getMessage());
            }

            return "Completed";
        }
        @Override
        protected void onPostExecute(String msg) // disimissing progress dialoge, showing error and setting up my listview
        {

        }
    }


    static private class Updatehouse extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() //Starts the progress dailog
        {

        }
        @Override
        protected String doInBackground(String... strings) {
            String id = strings[0];
            String price = strings[1];
            String listing = strings[2];

            try
            {
                ConnectionClass connectionClass = new ConnectionClass(); // Connection Class Initialization
                Connection conn = (Connection) connectionClass.CONN(); //Connection Object
                Statement stmt = conn.createStatement();
                String query = "UPDATE listing SET pricesold = "+price+",enddate = getDate()";

                if(listing.equals("Selling"))
                {
                    query +=", listingtype = 'Sold' ";
                }
                else if(listing.equals("Renting"))
                {
                    query +=", listingtype = 'Rented' ";
                }

                query += "WHERE propertyID = '"+id+"';";

                stmt.executeUpdate(query);

                conn.close();


            }
            catch (SQLException e)
            {

                Log.e("ERORR",e.getMessage());
            }

            return "Completed";
        }
        @Override
        protected void onPostExecute(String msg) // disimissing progress dialoge, showing error and setting up my listview
        {

        }
    }

}
