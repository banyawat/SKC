package com.theteus.kubota;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.theteus.kubota.skcmodule.ContactSKC;
import com.theteus.kubota.skcmodule.ContactSKCAddForm;
import com.theteus.kubota.LeadModule.Lead;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemLongClickListener;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity implements OnMenuItemClickListener, OnMenuItemLongClickListener {
    private final static String SKC_ACTIVITY_TITLE = "SKC Title";
    private final static String CONTACT_ACTIVITY_TITLE = "Contact";
    private final static String LEAD_ACITIVITY_TITLE = "Lead";
    private final static String ACTIVITIES_ACITIVITY_TITLE = "Activity";
    private final static String FEED_ACITIVITY_TITLE = "Siam Kubota Corp. CRM";
    private final static String ACCOUNT_ACITIVITY_TITLE = "Account";
    private final static String CHASIS_ACITIVITY_TITLE = "Chassis";

    private ViewPager mPager;
    private ScreenSlidePagerAdapter mPagerAdapter;
    int pageID=0, lastPosition=0;

    ContactSKC cSKC;
    ContactSKCAddForm cSKCAdd;

    private FragmentManager fragmentManager;
    private ContextMenuDialogFragment mMenuDialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        initToolbar();
        initMenuFragment();

        cSKC = new ContactSKC();
        cSKCAdd = new ContactSKCAddForm();

        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                switch(pageID){
                    case 1:
                        if(lastPosition!=position && position==0 && cSKCAdd.getReturnData()!=null) {
                            cSKC.addItem(cSKCAdd.getReturnData());
                            Toast.makeText(getApplicationContext(), "Data added: "+cSKCAdd.getReturnData().getName(),Toast.LENGTH_SHORT).show();
                        }
                        if(position==1 && lastPosition==0)
                            cSKCAdd.clear();
                        break;
                    default:
                        break;
                }
                lastPosition = position;
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
            mToolbar.setNavigationIcon(R.drawable.ic_reorder_white_24dp);
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
        menuParams.setMenuObjects(getMenuObjects());
        menuParams.setClosableOutside(true);
        mMenuDialogFragment = ContextMenuDialogFragment.newInstance(menuParams);
        mMenuDialogFragment.setItemClickListener(this);
        mMenuDialogFragment.setItemLongClickListener(this);
    }

    private List<MenuObject> getMenuObjects() {
        List<MenuObject> menuObjects = new ArrayList<>();

        MenuObject home = new MenuObject("Home");
        home.setResource(R.drawable.ic_home_black_24dp);

        MenuObject SKCcon = new MenuObject("SKC Contact");
        SKCcon.setResource(R.drawable.ic_perm_contact_cal_black_24dp);

        MenuObject contact = new MenuObject("Contact");
        contact.setResource(R.drawable.ic_add_shopping_cart_black_24dp);

        MenuObject lead = new MenuObject("Leads");
        lead.setResource(R.drawable.ic_accessibility_black_24dp);

        MenuObject activity = new MenuObject("Activity");
        activity.setResource(R.drawable.ic_event_black_24dp);

        MenuObject account = new MenuObject("Account");
        account.setResource(R.drawable.ic_supervisor_account_black_24dp);

        MenuObject chasis = new MenuObject("Chassis");
        chasis.setResource(R.drawable.ic_directions_bus_black_24dp);

        MenuObject logout = new MenuObject("Log out");
        logout.setResource(R.drawable.ic_cancel_black_24dp);

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
        Toast.makeText(this, "Clicked on position: " + position, Toast.LENGTH_SHORT).show();
        pageID = position;
        switch (pageID) {
            case 0:
                goHome();
                break;
            case 1:
                mPagerAdapter.clearPage();
                cSKC.setView(mPager);
                cSKCAdd.setView(mPager);
                mPagerAdapter.addPage(cSKC);
                mPagerAdapter.addPage(cSKCAdd);
                if(getSupportActionBar()!=null)
                    getSupportActionBar().setTitle(SKC_ACTIVITY_TITLE);
                break;
            case 2:
                mPagerAdapter.clearPage();
                mPagerAdapter.addPage(new Contact());
                if(getSupportActionBar()!=null)
                    getSupportActionBar().setTitle(CONTACT_ACTIVITY_TITLE);
                break;
            case 3:
                mPagerAdapter.clearPage();
                mPagerAdapter.addPage(new Lead());
                if(getSupportActionBar()!=null)
                    getSupportActionBar().setTitle(LEAD_ACITIVITY_TITLE);
                break;
            case 4:
                mPagerAdapter.clearPage();
                mPagerAdapter.addPage(new Account());
                if(getSupportActionBar()!=null)
                    getSupportActionBar().setTitle(ACCOUNT_ACITIVITY_TITLE);
                break;
            case 5:
                mPagerAdapter.clearPage();
                mPagerAdapter.addPage(new Activities());
                if(getSupportActionBar()!=null)
                    getSupportActionBar().setTitle(ACTIVITIES_ACITIVITY_TITLE);
                break;
            case 6:
                mPagerAdapter.clearPage();
                mPagerAdapter.addPage(new Chasis());
                if(getSupportActionBar()!=null)
                    getSupportActionBar().setTitle(CHASIS_ACITIVITY_TITLE);
                break;
            case 7:
                Toast.makeText(getApplicationContext(), "Logging Out", Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
        mPagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onMenuItemLongClick(View clickedView, int position) {

    }

    @Override
    public void onBackPressed() {
        if (getSupportActionBar() != null) {
            if(getSupportActionBar().getTitle()!=FEED_ACITIVITY_TITLE){
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
    }
}
