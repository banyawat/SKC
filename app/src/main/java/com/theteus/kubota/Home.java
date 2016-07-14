package com.theteus.kubota;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.theteus.kubota.AccountModule.Account;
import com.theteus.kubota.AccountModule.AccountDetailMain;
import com.theteus.kubota.ActivitiesModule.Activities;
import com.theteus.kubota.ActivitiesModule.ActivitiesDetailMain;
import com.theteus.kubota.ChassisModule.Chassis;
import com.theteus.kubota.ChassisModule.ChassisDetailMain;
import com.theteus.kubota.ContactModule.Contact;
import com.theteus.kubota.ContactModule.ContactDetailMain;
import com.theteus.kubota.LeadModule.Lead;
import com.theteus.kubota.LeadModule.LeadDetailMain;
import com.theteus.kubota.SKCModule.SKC;
import com.theteus.kubota.SKCModule.SKCDetailMain;
import com.theteus.kubota.SettingModule.SettingsActivity;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemLongClickListener;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity implements OnMenuItemClickListener, OnMenuItemLongClickListener {
    private final static String FEED_ACTIVITY_TITLE = "Siam Kubota Corp. CRM";
    private final static String SKC_ACTIVITY_TITLE = "SKC Contact";
    private final static String CONTACT_ACTIVITY_TITLE = "Contact";
    private final static String LEAD_ACTIVITY_TITLE = "Lead";
    private final static String ACTIVITIES_ACTIVITY_TITLE = "Activity";
    private final static String ACCOUNT_ACTIVITY_TITLE = "Account";
    private final static String CHASSIS_ACTIVITY_TITLE = "Chassis";

    private ViewPager mPager;
    private ScreenSlidePagerAdapter mPagerAdapter;
    int pageID=0, lastPage=0;

    private FragmentManager fragmentManager;
    private ContextMenuDialogFragment mMenuDialogFragment;
    private List<MenuObject> menuList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT>9){
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        setContentView(R.layout.activity_home);

        initToolbar();
        menuList = getMenuObjects();
        initMenuFragment();
        initViewPager();
        getSupportActionBar();
    }

    public void initViewPager(){
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPagerAdapter.clearPage();
        mPagerAdapter.addPage(new Feed());
        mPager.setAdapter(mPagerAdapter);
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    private void initToolbar() {
        fragmentManager = getSupportFragmentManager();
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if(mToolbar!=null) {
            setSupportActionBar(mToolbar);
            mToolbar.setNavigationIcon(R.drawable.ic_reorder);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (fragmentManager.findFragmentByTag(ContextMenuDialogFragment.TAG) == null) {
                        mMenuDialogFragment.show(fragmentManager, ContextMenuDialogFragment.TAG);
                    }
                }
            });
        }
        else
            finish();
    }
    private void initMenuFragment() {
        MenuParams menuParams = new MenuParams();
        menuParams.setActionBarSize((int) getResources().getDimension(R.dimen.tool_bar_height));
        menuParams.setMenuObjects(menuList);
        menuParams.setClosableOutside(true);
        mMenuDialogFragment = ContextMenuDialogFragment.newInstance(menuParams);
        mMenuDialogFragment.setItemClickListener(this);
        mMenuDialogFragment.setItemLongClickListener(this);
    }
    private List<MenuObject> getMenuObjects() {
        List<MenuObject> menuObjects = new ArrayList<>();

        MenuObject home = new MenuObject("Home");
        home.setResource(R.drawable.ic_home);
        home.setBgColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));

        MenuObject SKCcon = new MenuObject("SKC Contact");
        SKCcon.setResource(R.drawable.ic_perm_contact_calendar);

        MenuObject contact = new MenuObject("Contact");
        contact.setResource(R.drawable.ic_add_shopping_cart);

        MenuObject lead = new MenuObject("Leads");
        lead.setResource(R.drawable.ic_accessibility);

        MenuObject activity = new MenuObject("Activity");
        activity.setResource(R.drawable.ic_event_note);

        MenuObject account = new MenuObject("Account");
        account.setResource(R.drawable.ic_supervisor_account);

        MenuObject chasis = new MenuObject("Chassis");
        chasis.setResource(R.drawable.ic_action_bus);

        MenuObject logout = new MenuObject("Log out");
        logout.setResource(R.drawable.ic_highlight_off);

        menuObjects.add(home);
        menuObjects.add(SKCcon);
        menuObjects.add(contact);
        menuObjects.add(lead);
        menuObjects.add(activity);
        menuObjects.add(account);
        menuObjects.add(chasis);
        menuObjects.add(logout);
        return menuObjects;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMenuItemClick(View clickedView, int position) {
        if(position == lastPage)
            return;
        pageID = position;
        switch (pageID) {
            case 0:
                goHome();
                break;
            case 1:
                mPagerAdapter.clearPage();
                mPagerAdapter.addPage(new SKCDetailMain());
                mPagerAdapter.addPage(new SKC());
                mPager.setCurrentItem(0);
                if(getSupportActionBar()!=null)
                    getSupportActionBar().setTitle(SKC_ACTIVITY_TITLE);
                break;
            case 2:
                mPagerAdapter.clearPage();
                mPagerAdapter.addPage(new ContactDetailMain());
                mPagerAdapter.addPage(new Contact());
                mPager.setCurrentItem(0);
                if(getSupportActionBar()!=null)
                    getSupportActionBar().setTitle(CONTACT_ACTIVITY_TITLE);
                break;
            case 3:
                mPagerAdapter.clearPage();
                mPagerAdapter.addPage(new LeadDetailMain());
                mPagerAdapter.addPage(new Lead());
                mPager.setCurrentItem(0);
                if(getSupportActionBar()!=null)
                    getSupportActionBar().setTitle(LEAD_ACTIVITY_TITLE);
                break;
            case 4:
                mPagerAdapter.clearPage();
                mPagerAdapter.addPage(new ActivitiesDetailMain());
                mPagerAdapter.addPage(new Activities());
                mPager.setCurrentItem(0);
                if(getSupportActionBar()!=null)
                    getSupportActionBar().setTitle(ACTIVITIES_ACTIVITY_TITLE);
                break;
            case 5:
                mPagerAdapter.clearPage();
                mPagerAdapter.addPage(new AccountDetailMain());
                mPagerAdapter.addPage(new Account());
                if(getSupportActionBar()!=null)
                    getSupportActionBar().setTitle(ACCOUNT_ACTIVITY_TITLE);
                break;
            case 6:
                mPagerAdapter.clearPage();
                mPagerAdapter.addPage(new ChassisDetailMain());
                mPagerAdapter.addPage(new Chassis());
                if(getSupportActionBar()!=null)
                    getSupportActionBar().setTitle(CHASSIS_ACTIVITY_TITLE);
                break;
            case 7:
                Toast.makeText(getApplicationContext(), "Logging Out", Toast.LENGTH_LONG).show();
                finish();
                break;
            default:
                break;
        }
        mPagerAdapter.notifyDataSetChanged();
        menuList.get(lastPage).setBgColor(0);
        menuList.get(position).setBgColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
        initMenuFragment();
        lastPage=position;
    }
    @Override
    public void onMenuItemLongClick(View clickedView, int position) {
    }

    @Override
    public void onBackPressed() {
        if (getSupportActionBar() != null) {
            if(getSupportActionBar().getTitle()!=FEED_ACTIVITY_TITLE){
                if(mPager.getCurrentItem()==0)
                    goHome();
                else
                    mPager.setCurrentItem(mPager.getCurrentItem()-1);
            }
            else {
                super.onBackPressed();
            }
        }
    }

    public void goHome() {
        mPagerAdapter.clearPage();
        mPagerAdapter.addPage(new Feed());
        mPagerAdapter.notifyDataSetChanged();
        menuList.get(lastPage).setBgColor(0);
        menuList.get(0).setBgColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
        initMenuFragment();
        if(getSupportActionBar()!=null)
            getSupportActionBar().setTitle(FEED_ACTIVITY_TITLE);
        lastPage=0;
    }

    //For Fragment to use
    public ScreenSlidePagerAdapter getmPagerAdapter() {
        return mPagerAdapter;
    }
    public ViewPager getmPager() { return mPager; }
    public void changeMenu(int position) {
        pageID = position;
        menuList.get(lastPage).setBgColor(0);
        menuList.get(position).setBgColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
        initMenuFragment();
        lastPage=position;

        if(getSupportActionBar() != null) {
            switch(position) {
                case 1:
                    getSupportActionBar().setTitle(SKC_ACTIVITY_TITLE);
                    break;
                case 2:
                    getSupportActionBar().setTitle(CONTACT_ACTIVITY_TITLE);
                    break;
                case 3:
                    getSupportActionBar().setTitle(LEAD_ACTIVITY_TITLE);
                    break;
                case 4:
                    getSupportActionBar().setTitle(ACTIVITIES_ACTIVITY_TITLE);
                    break;
                case 5:
                    getSupportActionBar().setTitle(ACCOUNT_ACTIVITY_TITLE);
                    break;
                case 6:
                    getSupportActionBar().setTitle(CHASSIS_ACTIVITY_TITLE);
                    break;
                default:
                    break;
            }
        }
    }
}
