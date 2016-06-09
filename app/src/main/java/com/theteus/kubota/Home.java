package com.theteus.kubota;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.theteus.kubota.skcmodule.ContactSKC;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    FragmentTransaction transaction;
    private int pageID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        if (drawer != null) {
            drawer.addDrawerListener(toggle);
        }
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if(navigationView!=null){
            navigationView.setNavigationItemSelectedListener(this);
        }
        transaction = getSupportFragmentManager().beginTransaction();
        Feed mainFragment  = new Feed();
        transaction.add(R.id.main_fragment_container, mainFragment);
        transaction.commit();

        FloatingActionButton fabButton = (FloatingActionButton)findViewById(R.id.add_fab);

    }

   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        pageID = item.getItemId();
        switch (pageID) {
            case (R.id.menu_item_skc) :
                transactionReplace(new ContactSKC());
                break;
            case (R.id.menu_item_contact):
                transactionReplace(new Contact());
                break;
            case (R.id.menu_item_lead):
                transactionReplace(new Lead());
                break;
            case (R.id.menu_item_account):
                transactionReplace(new Account());
                break;
            case (R.id.menu_item_activity):
                transactionReplace(new Activities());
                break;
            case (R.id.menu_item_chasis):
                transactionReplace(new Chasis());
                break;
            case (R.id.logout_action) :
                Toast.makeText(getApplicationContext(), "Logging Out", Toast.LENGTH_LONG).show();
                break;
            default: break;
        }
        return false;
    }

    public void goHome(View v){
        transactionReplace(new Feed());
    }

    public void transactionReplace(android.support.v4.app.Fragment fragment){
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_fragment_container, fragment);
        transaction.commit();
    }
}
