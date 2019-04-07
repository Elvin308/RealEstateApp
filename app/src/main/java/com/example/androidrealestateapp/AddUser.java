package com.example.androidrealestateapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.sql.Connection;
import java.sql.Statement;

public class AddUser extends AppCompatActivity {
    FirebaseUser user;

    EditText Fname;
    EditText Lname;
    EditText Phone;

    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        activity = this;

        user = FirebaseAuth.getInstance().getCurrentUser();

        Button confirm = findViewById(R.id.confirm);

        EditText email = findViewById(R.id.newUserEmail);
        disableEditText(email);

        Fname = findViewById(R.id.newUserFName);
        Lname = findViewById(R.id.newUserLName);
        Phone = findViewById(R.id.newUserPhone);

        ImageView profilePic = findViewById(R.id.pic);
        Uri photoUrl = user.getPhotoUrl();
        Picasso.get()
                .load(photoUrl)
                .resize(300, 300)
                .centerCrop().placeholder(R.drawable.ic_usericon)
                .error(R.drawable.ic_usericon)
                .transform(new CircleTransform())
                .into(profilePic);

        String fullName = user.getDisplayName();
        String[] splitName = fullName.split("\\s+");

        email.setText(user.getEmail());
        if(splitName.length>=2)
        {
            Fname.setText(splitName[0]);
            Lname.setText(splitName[1]);
        }
        Phone.setText(user.getPhoneNumber());

        confirm.setOnClickListener(v->{
            Boolean continueNext = true;

             String phoneNum = Phone.getText().toString().trim();
             boolean isNumber = android.text.TextUtils.isDigitsOnly(phoneNum);


             String addEmail = email.getText().toString().trim();
             String addFName = Fname.getText().toString().trim();
             String addLName = Lname.getText().toString().trim();

             if(addEmail.length() <= 0)
             {
                 email.setError("Cannot be empty");
                 continueNext = false;
             }
             if(addFName.trim().length() <= 0)
             {
                 Fname.setError("Cannot be empty");
                 continueNext = false;
             }
             if(addLName.trim().length() <= 0)
             {
                 Lname.setError("Cannot be empty");
                 continueNext = false;
             }
             if(!isNumber || phoneNum.trim().length() < 10 || phoneNum.trim().length() > 11)
             {
                 Phone.setError("Need to enter a phone number");
                 continueNext = false;
             }
             if(continueNext)
             {
                 try
                 {
                     ConnectionClass connectionClass = new ConnectionClass(); // Connection Class Initialization
                     Connection conn = (Connection) connectionClass.CONN(); //Connection Object
                     Statement stmt = conn.createStatement();

                     // the mysql insert statement
                     String query = "Insert into Users (email,firstname,lastname,phone) values ('"+addEmail+"','"+addFName+"','"+addLName+"','"+phoneNum+"');";

                     stmt.executeQuery(query);

                     conn.close();
                 }
                 catch (Exception e)
                 {
                     Toast.makeText(AddUser.this, "Could not be added, try again later",Toast.LENGTH_SHORT);
                 }

                 startActivity(new Intent(this, Navigation.class));
                 finish();
             }
        });

    }

    private void disableEditText(EditText editText) {
        editText.setFocusable(false);
        editText.setEnabled(false);
        editText.setCursorVisible(false);
        editText.setKeyListener(null);
        //editText.setBackgroundColor(Color.TRANSPARENT);
    }


}
