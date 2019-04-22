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
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidrealestateapp.Models.ConnectionClass;
import com.example.androidrealestateapp.Models.RequestClass;
import com.example.androidrealestateapp.R;
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

public class RequestList extends Fragment {
    private ArrayList<RequestClass> itemArrayList;  //List items Array
    private RequestList.MyAppAdapter myAppAdapter; //Array Adapter
    private RecyclerView recyclerView; //RecyclerView
    private RecyclerView.LayoutManager mLayoutManager;
    private boolean success = false; // boolean
    private ConnectionClass connectionClass; //Connection Class Variable
    Bundle bundleRequestList;
    int width;
    String phoneNumber;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_request_list, container, false);

        bundleRequestList= getArguments();

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        width = metrics.widthPixels;




        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView); //Recylcerview Declaration
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(mLayoutManager);

        connectionClass = new ConnectionClass(); // Connection Class Initialization
        itemArrayList = new ArrayList<RequestClass>(); // Arraylist Initialization

        // Calling Async Task
        RequestList.SyncData orderData = new RequestList.SyncData();
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
            progress = ProgressDialog.show(RequestList.this.getContext(), "Synchronising",
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
                    String query = "SELECT TOP 30 Id, FirstName,LastName,Phone,SEmail, PropertyId, Message, Cdate FROM Request INNER JOIN Users ON Request.SEmail=Users.Email WHERE REMAIL='"+bundleRequestList.getString("UserEmail")+"' order by Cdate desc;";
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if (rs != null) // if resultset not null, I add items to itemArraylist using class created
                    {
                        while (rs.next())
                        {
                            try {
                                itemArrayList.add(new RequestClass(rs.getInt("Id"),rs.getString("SEmail"),rs.getInt("PropertyId"),rs.getString("Message"),rs.getString("FirstName"),rs.getString("LastName"),rs.getString("Phone"),rs.getString("Cdate")));
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
            Toast.makeText(RequestList.this.getContext(), msg + "", Toast.LENGTH_SHORT).show();
            if (success == false)
            {
            }
            else {
                try
                {
                    myAppAdapter = new RequestList.MyAppAdapter(itemArrayList , RequestList.this);
                    recyclerView.setAdapter(myAppAdapter);
                } catch (Exception ex)
                {

                }

            }
        }
    }

    public class MyAppAdapter extends RecyclerView.Adapter<RequestList.MyAppAdapter.ViewHolder> {
        private List<RequestClass> values;
        public Context context;



        public class ViewHolder extends RecyclerView.ViewHolder
        {
            // public image title and image url
            TextView Name;
            TextView Date;
            public View layout;


            public ViewHolder(View v)
            {
                super(v);
                layout = v;
                Name = v.findViewById(R.id.Name);
                Date = v.findViewById(R.id.Date);
            }

        }

        // Constructor
        public MyAppAdapter(ArrayList<RequestClass> myDataset, RequestList context)
        {
            values = myDataset;
            this.context = context.getContext();
        }

        // Create new views (invoked by the layout manager) and inflates
        @Override
        public RequestList.MyAppAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            // create a new view
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View v = inflater.inflate(R.layout.activity_request_content, parent, false);
            RequestList.MyAppAdapter.ViewHolder vh = new RequestList.MyAppAdapter.ViewHolder(v);
            return vh;
        }

        // Binding items to the view
        @Override
        public void onBindViewHolder(RequestList.MyAppAdapter.ViewHolder holder, final int position) {

            final RequestClass RequestClass = values.get(position);
            String name= RequestClass.getFirstName()+" "+RequestClass.getLastName();
            String email= RequestClass.getSEmail();
            String phone= RequestClass.getPhone();
            phoneNumber = phone;
            String message= RequestClass.getMessage();
            String date = RequestClass.getDate();

            holder.Name.setText(name);
            holder.Date.setText(date);


            holder.layout.setOnClickListener(v->{
                Dialog RequestDialog=new Dialog(getContext());
                RequestDialog.setContentView(R.layout.request_detail_dialog);
                RequestDialog.setTitle("Request Detail");
                RequestDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                RequestDialog.show();
                RequestDialog.getWindow().setLayout((6 * width)/7, ViewGroup.LayoutParams.WRAP_CONTENT);

                TextView Name = RequestDialog.findViewById(R.id.Name);
                TextView Email = RequestDialog.findViewById(R.id.Email);
                TextView Phone = RequestDialog.findViewById(R.id.Phone);
                TextView Message = RequestDialog.findViewById(R.id.Message);
                TextView Date = RequestDialog.findViewById(R.id.Date);

                Name.setText(name);
                Email.setText(email);
                Phone.setText(phone);
                Message.setText(message);
                Date.setText(date);

                Button ViewProperty = RequestDialog.findViewById(R.id.ViewProperty);
                ViewProperty.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getActivity(), ViewHouse.class).putExtra("PropertyId",RequestClass.getPropertyID()));
                    }
                });

                Button call = RequestDialog.findViewById(R.id.CALL);
                call.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        makePhoneCall();
                    }
                });

                Button text = RequestDialog.findViewById(R.id.TEXT);
                text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sendText();
                    }
                });

            });

        }


        // get item count returns the list item count
        @Override
        public int getItemCount() {
            return values.size();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 1)
        {
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                makePhoneCall();
            }
            else
            {
                Toast.makeText(getContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void makePhoneCall(){
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CALL_PHONE},1);
        }
        else
        {
            startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null)));
        }
    }

    public void sendText(){
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.SEND_SMS},1);
        }
        else
        {
            Intent smsIntent = new Intent(Intent.ACTION_SENDTO);
            smsIntent.addCategory(Intent.CATEGORY_DEFAULT);
            //smsIntent.setType("vnd.android-dir/mms-sms");
            smsIntent.setData(Uri.parse("sms:" + phoneNumber));
            startActivity(smsIntent);
        }
    }
}
