package com.theteus.kubota;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.theteus.kubota.skcmodule.ContactSKC;
import com.theteus.kubota.skcmodule.ContactSKCAddForm;
import com.theteus.kubota.LeadModule.Lead;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private final static String SKC_ACTIVITY_TITLE = "SKC Title";
    private final static String CONTACT_ACTIVITY_TITLE = "Contact";
    private final static String LEAD_ACITIVITY_TITLE = "Lead";
    private final static String ACTIVITIES_ACITIVITY_TITLE = "Activity";
    private final static String FEED_ACITIVITY_TITLE = "Siam Kubota Corp. CRM";
    private final static String ACCOUNT_ACITIVITY_TITLE = "Acitivity";
    private final static String CHASIS_ACITIVITY_TITLE = "Chassis";

    private ViewPager mPager;
    private ScreenSlidePagerAdapter mPagerAdapter;

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
        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this);
        }

        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
    }

    /*@Override
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
        int pageID = item.getItemId();
        switch (pageID) {
            case (R.id.menu_item_skc):
                mPagerAdapter.clearPage();
                ContactSKC cSKC = new ContactSKC();
                cSKC.addView(mPager);
                mPagerAdapter.addPage(cSKC);
                mPagerAdapter.addPage(new ContactSKCAddForm());
                getSupportActionBar().setTitle(SKC_ACTIVITY_TITLE);
                break;
            case (R.id.menu_item_contact):
                mPagerAdapter.clearPage();
                mPagerAdapter.addPage(new Contact());
                getSupportActionBar().setTitle(CONTACT_ACTIVITY_TITLE);
                break;
            case (R.id.menu_item_lead):
                mPagerAdapter.clearPage();
                mPagerAdapter.addPage(new Lead());
                getSupportActionBar().setTitle(LEAD_ACITIVITY_TITLE);
                break;
            case (R.id.menu_item_account):
                mPagerAdapter.clearPage();
                mPagerAdapter.addPage(new Account());
                getSupportActionBar().setTitle(ACCOUNT_ACITIVITY_TITLE);
                break;
            case (R.id.menu_item_activity):
                mPagerAdapter.clearPage();
                mPagerAdapter.addPage(new Activities());
                getSupportActionBar().setTitle(ACTIVITIES_ACITIVITY_TITLE);
                break;
            case (R.id.menu_item_chasis):
                mPagerAdapter.clearPage();
                mPagerAdapter.addPage(new Chasis());
                getSupportActionBar().setTitle(CHASIS_ACITIVITY_TITLE);

                break;
            case (R.id.logout_action):
                Toast.makeText(getApplicationContext(), "Logging Out", Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }

        mPagerAdapter.notifyDataSetChanged();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }

    public void goHome(View v) {
        mPagerAdapter.clearPage();
        mPagerAdapter.addPage(new Feed());
        mPagerAdapter.notifyDataSetChanged();
        getSupportActionBar().setTitle(FEED_ACITIVITY_TITLE);
    }
}
