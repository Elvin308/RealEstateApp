package com.example.androidrealestateapp.Controllers.FragmentsController;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidrealestateapp.Models.ConnectionClass;
import com.example.androidrealestateapp.Models.HouseClass;
import com.example.androidrealestateapp.Models.RequestClass;
import com.example.androidrealestateapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class FavoriteList extends Fragment {
    private ArrayList<HouseClass> itemArrayList;  //List items Array
    private FavoriteList.MyAppAdapter myAppAdapter; //Array Adapter
    private RecyclerView recyclerViewFavorite; //RecyclerView
    private RecyclerView.LayoutManager mLayoutManager;
    private boolean success = false; // boolean
    private ConnectionClass connectionClass; //Connection Class Variable

    FirebaseUser user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_favorite_list, container, false);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;

        recyclerViewFavorite = (RecyclerView) view.findViewById(R.id.recyclerViewFavorite); //Recylcerview Declaration
        recyclerViewFavorite.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this.getContext());
        recyclerViewFavorite.setLayoutManager(mLayoutManager);

        connectionClass = new ConnectionClass(); // Connection Class Initialization
        itemArrayList = new ArrayList<HouseClass>(); // Arraylist Initialization

        // Calling Async Task
        FavoriteList.SyncData orderData = new FavoriteList.SyncData();
        orderData.execute("");

        return view;
    }

    // Async Task has three overrided methods,
    private class SyncData extends AsyncTask<String, String, String>
    {
        String msg = "Internet/DB_Credentials/Windows_FireWall_TurnOn Error, See Android Monitor in the bottom For details!";
        ProgressDialog progress;

        @Override
        protected void onPreExecute() //Starts the progress dailog
        {
            progress = ProgressDialog.show(FavoriteList.this.getContext(), "Synchronising",
                    "RecyclerView Loading! Please Wait...", true);
        }

        @Override
        protected String doInBackground(String... strings)  // Connect to the database, write query and add items to array list
        {
            try
            {
                Connection conn = connectionClass.CONN(); //Connection Object
                if (conn == null)
                {
                    success = false;
                }
                else {
                    // Change below query according to your own database.
                    user = FirebaseAuth.getInstance().getCurrentUser();
                    String query = "SELECT TOP 50 Listing.PropertyID, StreetName,City,State,Zipcode,Price,NumOfFloors,NumOfBed,NumOfBath,NumOfGarages,ListingType FROM Listing INNER JOIN Favorites ON Listing.PropertyID=Favorites.PropertyID WHERE Favorites.email = '"+user.getEmail()+"' AND Listing.enddate IS NULL ORDER BY NEWID();";
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if (rs != null) // if resultset not null, I add items to itemArraylist using class created
                    {
                        while (rs.next())
                        {
                            try {
                                itemArrayList.add(new HouseClass(rs.getInt("PropertyId"),rs.getString("StreetName"),rs.getString("City"),rs.getString("State"),rs.getString("Zipcode"),rs.getDouble("Price"), rs.getDouble("NumOfFloors"),rs.getDouble("NumOfBed"),rs.getDouble("NumOfBath"),rs.getDouble("NumOfGarages"),rs.getString("ListingType")));
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                        msg = "Found";
                        success = true;
                    } else {
                        msg = "No Data found!";
                        success = false;
                    }
                }
            } catch (Exception e)
            {
                e.printStackTrace();
                Writer writer = new StringWriter();
                e.printStackTrace(new PrintWriter(writer));
                msg = writer.toString();
                success = false;
            }
            return msg;
        }

        @Override
        protected void onPostExecute(String msg) // disimissing progress dialoge, showing error and setting up my listview
        {
            progress.dismiss();
            Toast.makeText(FavoriteList.this.getContext(), msg + "", Toast.LENGTH_SHORT).show();
            if (success == false)
            {
            }
            else {
                try
                {
                    if(myAppAdapter==null)
                    {
                        myAppAdapter = new FavoriteList.MyAppAdapter(itemArrayList , FavoriteList.this);
                    }
                    else
                    {
                        myAppAdapter.notifyDataSetChanged();
                    }
                    recyclerViewFavorite.setAdapter(myAppAdapter);
                } catch (Exception ex)
                {

                }

            }
        }
    }

    public class MyAppAdapter extends RecyclerView.Adapter<FavoriteList.MyAppAdapter.ViewHolder> {
        private List<HouseClass> values;
        public Context context;

        public class ViewHolder extends RecyclerView.ViewHolder
        {
            // public image title and image url
            TextView addressHolder;
            TextView priceValue;
            TextView additionalInfo;
            ImageView imageView;
            TextView listing;
            View layout;

            public ViewHolder(View v)
            {
                super(v);
                layout = v;
                addressHolder = (TextView) v.findViewById(R.id.address);
                imageView = (ImageView) v.findViewById(R.id.imageView);
                priceValue =  v.findViewById(R.id.price);
                additionalInfo = v.findViewById(R.id.additional);
                listing = v.findViewById(R.id.Listing);
            }
        }

        // Constructor
        public MyAppAdapter(ArrayList<HouseClass> myDataset, FavoriteList context)
        {
            values = myDataset;
            this.context = context.getContext();
        }

        // Create new views (invoked by the layout manager) and inflates
        @Override
        public FavoriteList.MyAppAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            // create a new view
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View v = inflater.inflate(R.layout.list_content, parent, false);
            FavoriteList.MyAppAdapter.ViewHolder vh = new FavoriteList.MyAppAdapter.ViewHolder(v);
            return vh;
        }

        // Binding items to the view
        @Override
        public void onBindViewHolder(FavoriteList.MyAppAdapter.ViewHolder holder, final int position) {

            final HouseClass HouseClass = values.get(position);
            String houseAdress= HouseClass.getStreetName()+", "+HouseClass.getCity()+", "+HouseClass.getState()+", "+HouseClass.getZipCode();

            double amount = HouseClass.getPrice();
            DecimalFormat formatter = new DecimalFormat("#,###.00");
            String price= "$"+formatter.format(amount);

            String additonal = "Floors: "+HouseClass.getNumOfFloors()+"     Beds: "+HouseClass.getNumOfBed()+"     Baths: "+HouseClass.getNumOfBath()+"     Garages: "+HouseClass.getNumOfGarages();
            holder.addressHolder.setText(houseAdress);
            holder.priceValue.setText(price);
            holder.additionalInfo.setText(additonal);
            holder.listing.setText(HouseClass.getListingType());
            //Picasso.get().load(HouseClass.getImg()).into(holder.imageView);
            Picasso.get().load("https://static.dezeen.com/uploads/2017/08/clifton-house-project-architecture_dezeen_hero-1.jpg").into(holder.imageView);

            holder.layout.setOnClickListener(v->{
                Intent Detail = new Intent(getActivity(), ViewHouse.class).putExtra("PropertyId",HouseClass.getPropertyID()).putExtra("UserEmail",user.getEmail()).putExtra("Manage",false).putExtra("Return",true);
                startActivityForResult(Detail,1);
                //Toast.makeText(context,"You selected "+HouseClass.getPropertyID(),Toast.LENGTH_SHORT).show();
            });
        }

        // get item count returns the list item count
        @Override
        public int getItemCount() {
            return values.size();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            itemArrayList.clear();
            FavoriteList.SyncData orderData = new FavoriteList.SyncData();
            orderData.execute("");
        }
    }


    private static String removeLastChar(String str) {
        return str.substring(0, str.length() - 1);
    }
}