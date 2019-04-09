package com.example.androidrealestateapp;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

public class ManageHouse extends Fragment {
    private ArrayList<HouseClass> itemArrayList;  //List items Array
    private ManageHouse.MyAppAdapter myAppAdapter; //Array Adapter
    private RecyclerView recyclerView; //RecyclerView
    private RecyclerView.LayoutManager mLayoutManager;
    private boolean success = false; // boolean
    private ConnectionClass connectionClass; //Connection Class Variable
    Bundle bundleManageHouse;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manage_house, container, false);


        bundleManageHouse= getArguments();

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;




        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView); //Recylcerview Declaration
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(mLayoutManager);

        connectionClass = new ConnectionClass(); // Connection Class Initialization
        itemArrayList = new ArrayList<HouseClass>(); // Arraylist Initialization

        // Calling Async Task
        ManageHouse.SyncData orderData = new ManageHouse.SyncData();
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
            progress = ProgressDialog.show(ManageHouse.this.getContext(), "Synchronising",
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
                    String query = "SELECT PropertyID, StreetName,City,State,Zipcode,Price,NumOfFloors,NumOfBed,NumOfBath,NumOfGarages,ListingType FROM Listing WHERE Email='"+bundleManageHouse.getString("UserEmail")+"'";
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if (rs != null) // if resultset not null, I add items to itemArraylist using class created
                    {
                        while (rs.next())
                        {
                            try {
                                itemArrayList.add(new HouseClass(rs.getInt("PropertyId"),rs.getString("StreetName"),rs.getString("City"),rs.getString("State"),rs.getString("Zipcode"),rs.getDouble("Price"),rs.getDouble("NumOfFloors"),rs.getDouble("NumOfBed"),rs.getDouble("NumOfBath"),rs.getDouble("NumOfGarages"),rs.getString("ListingType")));
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
            Toast.makeText(ManageHouse.this.getContext(), msg + "", Toast.LENGTH_SHORT).show();
            if (success == false)
            {
            }
            else {
                try
                {
                    myAppAdapter = new ManageHouse.MyAppAdapter(itemArrayList , ManageHouse.this);
                    recyclerView.setAdapter(myAppAdapter);
                } catch (Exception ex)
                {

                }

            }
        }
    }

    public class MyAppAdapter extends RecyclerView.Adapter<ManageHouse.MyAppAdapter.ViewHolder> {
        private List<HouseClass> values;
        public Context context;



        public class ViewHolder extends RecyclerView.ViewHolder
        {
            // public image title and image url
            public TextView addressHolder;
            public TextView priceValue;
            public TextView additionalInfo;
            public ImageView imageView;
            public TextView listing;
            public View layout;


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
        public MyAppAdapter(ArrayList<HouseClass> myDataset, ManageHouse context)
        {
            values = myDataset;
            this.context = context.getContext();
        }

        // Create new views (invoked by the layout manager) and inflates
        @Override
        public ManageHouse.MyAppAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            // create a new view
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View v = inflater.inflate(R.layout.list_content, parent, false);
            ManageHouse.MyAppAdapter.ViewHolder vh = new ManageHouse.MyAppAdapter.ViewHolder(v);
            return vh;
        }

        // Binding items to the view
        @Override
        public void onBindViewHolder(ManageHouse.MyAppAdapter.ViewHolder holder, final int position) {

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
                startActivity(new Intent(getActivity(), ViewHouse.class).putExtra("PropertyId",HouseClass.getPropertyID()));
                //Toast.makeText(context,"You selected "+HouseClass.getPropertyID(),Toast.LENGTH_SHORT).show();
            });

        }

        // get item count returns the list item count
        @Override
        public int getItemCount() {
            return values.size();
        }

    }
}
