package com.example.androidrealestateapp;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
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

public class List_of_Houses extends Fragment {
    private ArrayList<HouseClass> itemArrayList;  //List items Array
    private List_of_Houses.MyAppAdapter myAppAdapter; //Array Adapter
    private RecyclerView recyclerView; //RecyclerView
    private RecyclerView.LayoutManager mLayoutManager;
    private boolean success = false; // boolean
    private ConnectionClass connectionClass; //Connection Class Variable

    Button filters;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_list_of__houses, container, false);


        filters=view.findViewById(R.id.filter);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;

        filters.setOnClickListener( v ->{
            //add dialog for filters and then modify the result

            Dialog filterDialog=new Dialog(this.getContext());
            filterDialog.setContentView(R.layout.filter_dialog);
            filterDialog.setTitle("Filters");
            filterDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            filterDialog.show();
            filterDialog.getWindow().setLayout((6 * width)/7, ViewGroup.LayoutParams.WRAP_CONTENT);

            Spinner bathroom =filterDialog.findViewById(R.id.filterBathroom);
            Spinner bedroom =filterDialog.findViewById(R.id.filterBedroom);
            Spinner garage =filterDialog.findViewById(R.id.filterGarage);
            Spinner floor =filterDialog.findViewById(R.id.filterFloor);
            Spinner rentSale =filterDialog.findViewById(R.id.filterRentSale);
            Button enter=filterDialog.findViewById(R.id.filterEnter);
            Button clear=filterDialog.findViewById(R.id.filterClear);

            ArrayAdapter<CharSequence> adapterBathroom= ArrayAdapter.createFromResource(this.getContext(),R.array.FilterBathroom,android.R.layout.simple_spinner_item);
            adapterBathroom.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            bathroom.setAdapter(adapterBathroom);

            ArrayAdapter<CharSequence> adapterBedroom= ArrayAdapter.createFromResource(this.getContext(),R.array.FilterBedroom,android.R.layout.simple_spinner_item);
            adapterBedroom.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            bedroom.setAdapter(adapterBedroom);


            ArrayAdapter<CharSequence> adapterGarageFloor= ArrayAdapter.createFromResource(this.getContext(),R.array.FilterNumber,android.R.layout.simple_spinner_item);
            adapterGarageFloor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            garage.setAdapter(adapterGarageFloor);
            floor.setAdapter(adapterGarageFloor);


            ArrayAdapter<CharSequence> adapterRentSale= ArrayAdapter.createFromResource(this.getContext(),R.array.FilterRentSale,android.R.layout.simple_spinner_item);
            adapterRentSale.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            rentSale.setAdapter(adapterRentSale);

            enter.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    //Need to make sure that the dialog remembers the values set so next time dialog is created values are still same
                    //Need to make the recycle view refresh and give results that go along with the filters
                    filterDialog.cancel();
                }
            });
            clear.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    filterDialog.cancel();
                }
            });
        });


        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView); //Recylcerview Declaration
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(mLayoutManager);

        connectionClass = new ConnectionClass(); // Connection Class Initialization
        itemArrayList = new ArrayList<HouseClass>(); // Arraylist Initialization

        // Calling Async Task
        List_of_Houses.SyncData orderData = new List_of_Houses.SyncData();
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
            progress = ProgressDialog.show(List_of_Houses.this.getContext(), "Synchronising",
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
                    String query = "SELECT PropertyID, StreetName,City,State,Zipcode,Price,NumOfBed,NumOfBath,NumOfGarages FROM Listing";
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if (rs != null) // if resultset not null, I add items to itemArraylist using class created
                    {
                        while (rs.next())
                        {
                            try {
                                itemArrayList.add(new HouseClass(rs.getInt("PropertyId"),rs.getString("StreetName"),rs.getString("City"),rs.getString("State"),rs.getString("Zipcode"),rs.getDouble("Price"),rs.getDouble("NumOfBed"),rs.getDouble("NumOfBath"),rs.getDouble("NumOfGarages")));
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
            Toast.makeText(List_of_Houses.this.getContext(), msg + "", Toast.LENGTH_LONG).show();
            if (success == false)
            {
            }
            else {
                try
                {
                    myAppAdapter = new List_of_Houses.MyAppAdapter(itemArrayList , List_of_Houses.this);
                    recyclerView.setAdapter(myAppAdapter);
                } catch (Exception ex)
                {

                }

            }
        }
    }

    public class MyAppAdapter extends RecyclerView.Adapter<List_of_Houses.MyAppAdapter.ViewHolder> {
        private List<HouseClass> values;
        public Context context;

        public class ViewHolder extends RecyclerView.ViewHolder
        {
            // public image title and image url
            public TextView addressHolder;
            public TextView priceValue;
            public TextView additionalInfo;
            public ImageView imageView;
            public View layout;

            public ViewHolder(View v)
            {
                super(v);
                layout = v;
                addressHolder = (TextView) v.findViewById(R.id.address);
                imageView = (ImageView) v.findViewById(R.id.imageView);
                priceValue =  v.findViewById(R.id.price);
                additionalInfo = v.findViewById(R.id.additional);
            }
        }

        // Constructor
        public MyAppAdapter(ArrayList<HouseClass> myDataset, List_of_Houses context)
        {
            values = myDataset;
            this.context = context.getContext();
        }

        // Create new views (invoked by the layout manager) and inflates
        @Override
        public List_of_Houses.MyAppAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            // create a new view
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View v = inflater.inflate(R.layout.list_content, parent, false);
            List_of_Houses.MyAppAdapter.ViewHolder vh = new List_of_Houses.MyAppAdapter.ViewHolder(v);
            return vh;
        }

        // Binding items to the view
        @Override
        public void onBindViewHolder(List_of_Houses.MyAppAdapter.ViewHolder holder, final int position) {

            final HouseClass HouseClass = values.get(position);
            String houseAdress= HouseClass.getStreetName()+", "+HouseClass.getCity()+", "+HouseClass.getState()+", "+HouseClass.getZipCode();

            double amount = HouseClass.getPrice();
            DecimalFormat formatter = new DecimalFormat("#,###.00");
            String price= "$"+formatter.format(amount);

            String additonal = "Beds: "+HouseClass.getNumOfBed()+"     Baths: "+HouseClass.getNumOfBath()+"     Garages: "+HouseClass.getNumOfGarages();
            holder.addressHolder.setText(houseAdress);
            holder.priceValue.setText(price);
            holder.additionalInfo.setText(additonal);
            //Picasso.get().load(HouseClass.getImg()).into(holder.imageView);
            Picasso.get().load("https://static.dezeen.com/uploads/2017/08/clifton-house-project-architecture_dezeen_hero-1.jpg").into(holder.imageView);
        }

        // get item count returns the list item count
        @Override
        public int getItemCount() {
            return values.size();
        }

    }
}