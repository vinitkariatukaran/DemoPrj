package com.demoprj;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.demoprj.Adapter.ViewPagerAdapterAdmin;
import com.demoprj.Fragment.CustomerDetailsFragment;
import com.demoprj.Fragment.DeliveryDetailsFragment;
import com.demoprj.Fragment.ExpenseDetailsFragment;
import com.demoprj.Fragment.PriceChangeFragment;
import com.demoprj.Fragment.QuickBookFragment;
import com.demoprj.Segment.DeliverySegment;
import com.demoprj.Segment.UndeliverySegment;
import com.parse.ParseUser;

public class AdminHomeActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener, ViewPager.OnPageChangeListener, View.OnClickListener {

    private Toolbar toolbarAdmin;
    private TabLayout tabLayoutAdmin;
    private ViewPager viewPagerAdmin;
    Button ibAddCustomer;
    Button ibsearch;
    MenuItem search;
    MenuItem refresh;
    MenuItem AddPerson;
    public static int pageSwiperPosition = 0 ;
    public static boolean isSearchVisible = false;
    CustomerDetailsFragment cus;
    boolean doubleBackToExitPressedOnce = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        toolbarAdmin = (Toolbar) findViewById(R.id.toolbarAdmin);
        ibAddCustomer = (Button) findViewById(R.id.ibAddCustomer);
        ibsearch = (Button) findViewById(R.id.ibsearch);
        ibAddCustomer.setOnClickListener(this);
        ibsearch.setOnClickListener(this);
        setSupportActionBar(toolbarAdmin);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPagerAdmin = (ViewPager) findViewById(R.id.viewpagerAdmin);
        viewPagerAdmin.addOnPageChangeListener(this);
        setupViewPager(viewPagerAdmin);

        tabLayoutAdmin = (TabLayout) findViewById(R.id.tabsAdmin);
        tabLayoutAdmin.setOnTabSelectedListener(this);
        tabLayoutAdmin.setupWithViewPager(viewPagerAdmin);
    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapterAdmin adapter = new ViewPagerAdapterAdmin(getSupportFragmentManager());
        adapter.addFragment(new QuickBookFragment(), "Quick Book");
        cus = new CustomerDetailsFragment();
        adapter.addFragment(cus, "Customer Details");
        adapter.addFragment(new DeliveryDetailsFragment(), "Delivery Details");
        adapter.addFragment(new PriceChangeFragment(), "Change Price");
        adapter.addFragment(new ExpenseDetailsFragment(), "Amount Detail");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        Log.i("ssss", "ssss");
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        Log.i("uuuu","uuuu");
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
        Log.i("rrrrrr","rrrr");
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        pageSwiperPosition = position;
        if(position==1){
//            AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) toolbarAdmin.getLayoutParams();
//            ibAddCustomer.setVisibility(View.VISIBLE);
            if(!cus.isPaused) {
                search.setVisible(true);
                AddPerson.setVisible(true);
                refresh.setVisible(true);
            }
        }else if(position==2){
            search.setVisible(true);
            refresh.setVisible(false);
            AddPerson.setVisible(false);
        }
        else{
//            AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) toolbarAdmin.getLayoutParams();
//            params.setScrollFlags(0);
//            ibAddCustomer.setVisibility(View.GONE);
            if(isSearchVisible) {
                CustomerDetailsFragment.llSearch.setVisibility(View.GONE);
                CustomerDetailsFragment.txtSearch.setText("");
                isSearchVisible = false;
            }
            CustomerDetailsFragment.llSearch.setVisibility(View.GONE);
            search.setVisible(false);
            AddPerson.setVisible(false);
            refresh.setVisible(false);
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibAddCustomer :
                Intent next = new Intent(this, AddCustomerDetail.class);
                startActivity(next);
                break;
            case R.id.ibsearch:

                break;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        // Inflate menu to add items to action bar if it is present.
        inflater.inflate(R.menu.menu_customer_detail_info, menu);
        search = menu.findItem(R.id.menu_search);
        refresh = menu.findItem(R.id.menu_refresh);
        AddPerson = menu.findItem(R.id.menu_add);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_refresh:
                CustomerDetailsFragment.LoadCustomerInfo();
                break;
            case R.id.menu_logout:
                ParseUser.logOut();
                Intent next1 = new Intent(this,MainActivity.class);
                startActivity(next1);
                finish();
                break;

            case R.id.menu_add:
                Intent next = new Intent(this, AddCustomerDetail.class);
                startActivity(next);
                break;
            case R.id.menu_search:
                if(pageSwiperPosition==1) {
                    if (isSearchVisible) {
                        CustomerDetailsFragment.txtSearch.setText("");
                        isSearchVisible = false;
                        CustomerDetailsFragment.llSearch.setVisibility(View.GONE);
                    } else {
                        CustomerDetailsFragment.txtSearch.setText("");
                        isSearchVisible = true;
                        CustomerDetailsFragment.llSearch.setVisibility(View.VISIBLE);
                    }
                }else if(pageSwiperPosition == 2){
                    if (isSearchVisible) {
                        if(DeliveryDetailsFragment.isDelivered) {
                            DeliverySegment.txtDeliverySearch.setText("");
                            DeliverySegment.llDeliverySearch.setVisibility(View.GONE);
                        }else{
                            UndeliverySegment.txtDeliverySearch.setText("");
                            UndeliverySegment.llDeliverySearch.setVisibility(View.GONE);
                        }
                        isSearchVisible = false;
                    } else {
                        if(DeliveryDetailsFragment.isDelivered) {
                            DeliverySegment.txtDeliverySearch.setText("");
                            DeliverySegment.llDeliverySearch.setVisibility(View.VISIBLE);
                        }else{
                            UndeliverySegment.txtDeliverySearch.setText("");
                            UndeliverySegment.llDeliverySearch.setVisibility(View.VISIBLE);
                        }
                        isSearchVisible = true;
                    }
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}
