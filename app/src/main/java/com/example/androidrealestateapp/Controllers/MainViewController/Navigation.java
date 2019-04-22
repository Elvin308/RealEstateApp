package com.example.androidrealestateapp.Controllers.MainViewController;

import android.net.Uri;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androidrealestateapp.Controllers.AddHouseController.add_house_listing;
import com.example.androidrealestateapp.Controllers.FragmentsController.List_of_Houses;
import com.example.androidrealestateapp.Controllers.FragmentsController.ManageHouse;
import com.example.androidrealestateapp.Controllers.FragmentsController.RequestList;
import com.example.androidrealestateapp.Models.CircleTransform;
import com.example.androidrealestateapp.R;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class Navigation extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        View headView = navigationView.getHeaderView(0);
        TextView userName=headView.findViewById(R.id.UserName);
        ImageView userImage=headView.findViewById(R.id.UserImage);
        TextView userEmail=headView.findViewById(R.id.UserEmail);

        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            userName.setText(name);
            String email = user.getEmail();
            userEmail.setText(email);
            Uri photoUrl = user.getPhotoUrl();
            Picasso.get()
                    .load(photoUrl)
                    .resize(300, 250)
                    .centerCrop().placeholder(R.drawable.ic_usericon)
                    .error(R.drawable.ic_usericon)
                    .transform(new CircleTransform())
                    .into(userImage);
        }


        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new List_of_Houses(), "ListHouses").commit();
            navigationView.setCheckedItem(R.id.nav_ListHouse);
        }


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_Profile) {
            return true;
        }
        if(id == R.id.action_logOut)
        {
            AuthUI.getInstance().signOut(this).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    startActivity(new Intent(Navigation.this,LogIn.class));
                    finish();
                }
            });
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_AddHouse) {
            Fragment fragmentA = getSupportFragmentManager().findFragmentByTag("toAddHouse1");
            if (fragmentA == null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new add_house_listing(),"toAddHouse1").addToBackStack(null).commit();
            }
            else{ //fragment exist
                getSupportFragmentManager().beginTransaction().remove(fragmentA).commitNow();
                getSupportFragmentManager().popBackStack();
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragmentA,"ManageHouse").addToBackStack(null).commit();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new add_house_listing(),"toAddHouse1").addToBackStack(null).commit();
            }
        } else if (id == R.id.nav_ListHouse) {
            Fragment fragmentA = getSupportFragmentManager().findFragmentByTag("ListHouses");
            if (fragmentA == null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new List_of_Houses(),"ListHouses").addToBackStack(null).commit();
            }
            else{ //fragment exist
                getSupportFragmentManager().beginTransaction().remove(fragmentA).commitNow();
                getSupportFragmentManager().popBackStack();
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragmentA,"ManageHouse").addToBackStack(null).commit();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new List_of_Houses(),"ListHouses").addToBackStack(null).commit();
            }

        } else if (id == R.id.nav_ManageHouses) {
            Fragment fragmentA = getSupportFragmentManager().findFragmentByTag("ManageHouses");
            Bundle bundleManageHouse = new Bundle();
            bundleManageHouse.putString("UserEmail",user.getEmail().toLowerCase());
            if (fragmentA == null) {
                Fragment newFrag = new ManageHouse();
                newFrag.setArguments(bundleManageHouse);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, newFrag,"ManageHouses").addToBackStack(null).commit();
            }
            else{ //fragment exist
                getSupportFragmentManager().beginTransaction().remove(fragmentA).commitNow();
                getSupportFragmentManager().popBackStack();
                Fragment newFrag = new ManageHouse();
                newFrag.setArguments(bundleManageHouse);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, newFrag,"ManageHouses").addToBackStack(null).commit();
            }

        } else if (id == R.id.nav_ViewRequest) {
            Fragment fragmentA = getSupportFragmentManager().findFragmentByTag("RequestList");
            Bundle bundleManageHouse = new Bundle();
            bundleManageHouse.putString("UserEmail",user.getEmail().toLowerCase());
            if (fragmentA == null) {
                Fragment newFrag = new RequestList();
                newFrag.setArguments(bundleManageHouse);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, newFrag,"RequestList").addToBackStack(null).commit();
            }
            else{ //fragment exist
                getSupportFragmentManager().beginTransaction().remove(fragmentA).commitNow();
                getSupportFragmentManager().popBackStack();
                Fragment newFrag = new RequestList();
                newFrag.setArguments(bundleManageHouse);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, newFrag,"RequestList").addToBackStack(null).commit();
            }
        } else if (id == R.id.nav_Statistics) {

        } else if (id == R.id.nav_Favorites) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
