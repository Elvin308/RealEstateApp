package com.example.androidrealestateapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidrealestateapp.Controllers.MainViewController.Navigation;
import com.example.androidrealestateapp.Models.CircleTransform;
import com.example.androidrealestateapp.Models.ConnectionClass;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ChangeUser extends AppCompatActivity {
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
        TextView welcome=findViewById(R.id.Welcome);
        TextView changeInfo=findViewById(R.id.ChangeInfo);
        changeInfo.setText("Please update your information before continuing");

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

        try
        {
            ConnectionClass connectionClass = new ConnectionClass(); // Connection Class Initialization
            Connection conn = (Connection) connectionClass.CONN(); //Connection Object
            Statement stmt = conn.createStatement();

            // the mysql insert statement
            String query = "Select * from users where email= '"+user.getEmail().trim()+"';";

            ResultSet rs = stmt.executeQuery(query);
            if (rs != null) {
                while (rs.next()) {
                    Fname.setText(rs.getString("FirstName"));
                    Lname.setText(rs.getString("LastName"));
                    Phone.setText(rs.getString("Phone"));
                }
            }

            welcome.setText("Welcome "+Fname.getText().toString()+" "+Lname.getText().toString());

            conn.close();
        }
        catch (Exception e)
        {
            Toast.makeText(ChangeUser.this, "Could not be found",Toast.LENGTH_SHORT);
            finish();
        }

        email.setText(user.getEmail());


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
                    String query = "UPDATE Users SET firstname='"+addFName+"', lastname='"+addLName+"', phone='"+phoneNum+"' WHERE email = '"+addEmail+"';";
                            //"Insert into Users (email,firstname,lastname,phone) values ('"+addEmail+"','"+addFName+"','"+addLName+"','"+phoneNum+"');";

                    stmt.executeUpdate(query);

                    conn.close();
                }
                catch (Exception e)
                {
                    Toast.makeText(ChangeUser.this, "Could not be changed, try again later",Toast.LENGTH_SHORT);
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
