package com.example.foodliveryapp.ui;

import android.content.Intent;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.foodliveryapp.AvailabilityActivity;
import com.example.foodliveryapp.OpinionActivity;
import com.example.foodliveryapp.OrdersActivity;
import com.example.foodliveryapp.ProfileActivity;
import com.example.foodliveryapp.R;
import com.example.foodliveryapp.RestaurantsListActivity;
import com.example.foodliveryapp.StatsActivity;
import com.example.foodliveryapp.WorkersListActivity;
import com.example.foodliveryapp.log.location.LocationIntent;
import com.example.foodliveryapp.log.session.Session;
import com.google.android.material.navigation.NavigationView;

public class DrawerLayoutBuilder {

    private AppCompatActivity act;
    private DrawerLayout drawerLayout;
    private Toolbar drawerToolbar;
    private ActionBarDrawerToggle drawerToggle;
    private NavigationView navigationView;

    public DrawerLayoutBuilder(AppCompatActivity act) {
        this.act = act;


    }

    public void build(int layoutId, int navViewId, int drawerItemId){
        drawerToolbar = act.findViewById(R.id.drawer_toolbar);
        act.setSupportActionBar(drawerToolbar);
        act.getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        drawerLayout = act.findViewById(layoutId);
        drawerToggle = setupDrawerToggle();
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();

        navigationView = act.findViewById(navViewId);
        setupDrawerContent(navigationView);

        if(drawerItemId != 0) {
            navigationView.getMenu().findItem(drawerItemId).setChecked(true);
        }
        navigationView.setItemIconTintList(null);
    }

    private ActionBarDrawerToggle setupDrawerToggle(){
        return new ActionBarDrawerToggle(act, drawerLayout, drawerToolbar, R.string.drawer_open, R.string.drawer_close);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(item -> {
            selectDrawerItem(item);
            return true;
        });
    }

    private void selectDrawerItem(MenuItem item){

        switch(item.getItemId()){
            case R.id.orders_drawer_item : {
                handleClick(OrdersActivity.class);
                break;
            }
            case R.id.restaurants_drawer_item : {
                handleClick(RestaurantsListActivity.class);
                break;
            }
            case R.id.workers_drawer_item : {
                handleClick(WorkersListActivity.class);
                break;
            }
            case R.id.availability_drawer_item : {
                handleClick(AvailabilityActivity.class);
                break;
            }
            case R.id.opinion_drawer_item : {
                handleClick(OpinionActivity.class);
                break;
            }
            case R.id.profile_drawer_item : {
                handleClick(ProfileActivity.class);
                break;
            }
            case R.id.stats_drawer_item : {
                handleClick(StatsActivity.class);
                break;
            }
            case R.id.logout_drawer_item : {
                Session session = new Session(act);
                session.restartSession();
                act.finishAndRemoveTask();
                act.stopService(LocationIntent.getInstance(act).getLocationService());
                break;
            }
            default : {
                Toast.makeText(act,"else", Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }


    private void handleClick(Class className){
        if(!act.getClass().equals(className)) {
            Intent newIntent = new Intent(act, className);
            act.finishAndRemoveTask();
            act.startActivity(newIntent);
        }else{
            drawerLayout.closeDrawers();
        }
    }

}
