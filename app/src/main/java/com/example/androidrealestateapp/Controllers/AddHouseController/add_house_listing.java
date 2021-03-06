package com.example.androidrealestateapp.Controllers.AddHouseController;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.androidrealestateapp.Models.ConnectionClass;
import com.example.androidrealestateapp.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import static android.app.Activity.RESULT_OK;

public class add_house_listing extends Fragment implements AdapterView.OnItemSelectedListener {

    FirebaseUser user;
    View view;
    Bitmap housepic;

    Bitmap photo;
    ImageButton AddPicture;
    String picToString;




    private static final int pic_id = 123;
    private static int RESULT_LOAD_IMAGE = 1;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.activity_add_house_listing,container,false);
        user = FirebaseAuth.getInstance().getCurrentUser();

        Spinner bathroom= view.findViewById(R.id.BathroomSpinner);
        Spinner bedroom= view.findViewById(R.id.BedroomSpinner);
        Spinner garage= view.findViewById(R.id.GarageSpinner);
        Spinner floor= view.findViewById(R.id.FloorSpinner);
        Spinner rentSale= view.findViewById(R.id.RentSaleSpinner);
        Spinner state= view.findViewById(R.id.StateSpinner);

        ArrayAdapter<CharSequence> adapterDecimal= ArrayAdapter.createFromResource(this.getContext(),R.array.numbersDecimal,android.R.layout.simple_spinner_item);
        adapterDecimal.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bathroom.setAdapter(adapterDecimal);
        bedroom.setAdapter(adapterDecimal);

        ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(this.getContext(),R.array.numbers,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        garage.setAdapter(adapter);
        floor.setAdapter(adapter);


        ArrayAdapter<CharSequence> adapterRentSale= ArrayAdapter.createFromResource(this.getContext(),R.array.RentSale,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        rentSale.setAdapter(adapterRentSale);

        ArrayAdapter<CharSequence> adapterState= ArrayAdapter.createFromResource(this.getContext(),R.array.states,android.R.layout.simple_spinner_item);
        adapterState.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        state.setAdapter(adapterState);

        bathroom.setOnItemSelectedListener(this);
        bedroom.setOnItemSelectedListener(this);
        garage.setOnItemSelectedListener(this);
        floor.setOnItemSelectedListener(this);
        rentSale.setOnItemSelectedListener(this);
        state.setOnItemSelectedListener(this);

        EditText street = view.findViewById(R.id.StreetName);
        EditText city = view.findViewById(R.id.CityName);
        EditText zip = view.findViewById(R.id.ZipCode);
        EditText price = view.findViewById(R.id.EnterPrice);
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getActivity());

        AddPicture = (ImageButton) view.findViewById(R.id.ADDHouse);
        final PopupMenu dropDownMenu = new PopupMenu(getContext(), AddPicture);



        final Menu menu = dropDownMenu.getMenu();
        menu.add(0, 0, 0, "Gallery");
        menu.add(0, 1, 0, "Camera");
        dropDownMenu.getMenuInflater().inflate(R.menu.popup_menu, menu);
        dropDownMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case 0:
                        /*
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.putExtra(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                        //intent.setAction(Intent.ACTION_GET_CONTENT);
                        //startActivityForResult(Intent.createChooser(intent,"Select Picture"), 1);
                        //housepic = intent.getExtras().getParcelable("data");
                        //File pic = intent.getExtras().getParcelable("data");
                        //AddPicture.setImageBitmap(BitmapFactory.decodeFile(pic.getName()));
                        //AddPicture.setImageBitmap(housepic);

                        Intent gallery_intent = new Intent(Intent.ACTION_PICK);
                        gallery_intent.setType("image/*");
                        startActivityForResult(gallery_intent, pic_id);
                        //startActivityForResult(Intent.createChooser(gallery_intent, "Select Picture"), pic_id);
                        */

                        Intent i = new Intent(
                                Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                        startActivityForResult(i, RESULT_LOAD_IMAGE);

                        return true;

                    case 1:


                        Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(camera_intent, pic_id);


                        return true;
                }
                return false;
            }
        });
        AddPicture.setOnClickListener(v->{

            dropDownMenu.show();

        });

        Button next = (Button) view.findViewById(R.id.NextButton);
        next.setOnClickListener(v->{
            //ADDED NEW***********************************************************************
            boolean continueNext = true;

            if(picToString == null)
            {
                Toast.makeText(this.getContext(),"Need to attach an image",Toast.LENGTH_SHORT).show();
                continueNext = false;
            }
            if(street.getText().toString().trim().length() <= 10)
            {
                street.setError("Enter a house number and street name");
                continueNext = false;
            }
            if(city.getText().toString().trim().length() <= 4)
            {
                city.setError("Enter a city");
                continueNext = false;
            }
            if(price.getText().toString().trim().length() <= 0)
            {
                price.setError("Cannot be empty");
                continueNext = false;
            }
            if(price.getText().toString().trim().length() > 0)
            {
                if(Double.parseDouble(price.getText().toString()) < 1.00 || Double.parseDouble(price.getText().toString()) > 2147000000)
                {
                    price.setError("Enter an amount higher than 1 and lower than 2,147,000,000");
                    continueNext = false;
                }
            }
            if(zip.getText().toString().trim().length() <= 4)
            {
                zip.setError("Enter a valid zipcode");
                continueNext = false;
            }
            if(continueNext) {

                try
                {
                    ConnectionClass connectionClass = new ConnectionClass(); // Connection Class Initialization
                    Connection conn = (Connection) connectionClass.CONN(); //Connection Object
                    Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_READ_ONLY);

                    String query = "Select * from Listing WHERE StreetName = '"+street.getText().toString()+"' AND City = '"+city.getText().toString().trim()+"' AND State = '"+state.getSelectedItem().toString()+"' AND Zipcode = '"+zip.getText().toString()+"';";
                    ResultSet rs = stmt.executeQuery(query);
                    rs.last();
                    int count = rs.getRow();
                    if(count<=0)
                    {
                        conn.close();

                        String City = city.getText().toString().trim();
                        String newCity = City.substring(0, 1).toUpperCase() + City.substring(1);

                        Bundle bundle = new Bundle();
                        bundle.putString("Street", street.getText().toString());
                        bundle.putString("City", newCity);
                        bundle.putString("State", state.getSelectedItem().toString());
                        bundle.putString("Zip", zip.getText().toString());
                        bundle.putDouble("Price", Double.parseDouble(price.getText().toString()));
                        bundle.putDouble("Floors", Double.parseDouble(floor.getSelectedItem().toString()));
                        bundle.putDouble("Bath", Double.parseDouble(bathroom.getSelectedItem().toString()));
                        bundle.putDouble("Bed", Double.parseDouble(bedroom.getSelectedItem().toString()));
                        bundle.putDouble("Garage", Double.parseDouble(garage.getSelectedItem().toString()));
                        bundle.putString("ListingType", rentSale.getSelectedItem().toString());
                        bundle.putString("Email", user.getEmail());
                        bundle.putString("picture",picToString);
                        bundle.putParcelable("housepic", photo);


                        Fragment fragment = new add_house_listing2();
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_container, fragment, "toAddHouse2");
                        fragmentTransaction.addToBackStack(null);
                        fragment.setArguments(bundle);
                        fragmentTransaction.commit();

                    }
                    else
                    {
                        street.setError("Error");
                        city.setError("Error");
                        zip.setError("Error");
                        Toast.makeText(getContext(),"Address already in use",Toast.LENGTH_LONG).show();
                        conn.close();
                    }
                }catch (Exception e)
                {
                    Toast.makeText(getContext(),"Error: please try again",Toast.LENGTH_LONG).show();
                }
            }
        });

       Button cancel= (Button) view.findViewById(R.id.CancelButton);
        cancel.setOnClickListener(v->{
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
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        // Match the request 'pic id with requestCode
        if (requestCode == pic_id) {
            // BitMap is data structure of image file
            // which stor the image in memory
            photo = (Bitmap)data.getExtras().get("data");

            // Set the image in imageview for display
            AddPicture.setImageBitmap(photo);

            picToString = bitmapToBase64(photo);

            return;
        }

        if (resultCode==RESULT_OK) {
            //This is when searching in gallery

            if (data != null) {
                Uri uri = data.getData();
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                try {
                    BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(uri), null, options);
                    options.inJustDecodeBounds = false;
                    photo = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(uri), null, options);
                    AddPicture.setImageBitmap(photo);

                    picToString = bitmapToBase64(photo);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(getActivity(), "Cancelled",
                        Toast.LENGTH_SHORT).show();
            }

        }



    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }


    private String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }
}