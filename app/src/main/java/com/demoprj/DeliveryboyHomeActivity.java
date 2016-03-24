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
import com.demoprj.DeliveryBoyApp.DeliveryDetailsFragmentDeliveryBoy;
import com.demoprj.DeliveryBoyApp.FragmentDeliveryBoy.AmountExpenseDetailsFragment;
import com.demoprj.DeliveryBoyApp.FragmentDeliveryBoy.ExpenseFragment;
import com.demoprj.DeliveryBoyApp.FragmentDeliveryBoy.GasBookFragment;
import com.demoprj.DeliveryBoyApp.FragmentDeliveryBoy.TotalAmountDayFragmentDeliveryBoy;
import com.demoprj.DeliveryBoyApp.ParticularDeliveryDetailActivityofDeliveryBoy;
import com.demoprj.DeliveryBoyApp.SegmentDeliveryBoy.DeliverySegmentofDeliveryBoy;
import com.demoprj.DeliveryBoyApp.SegmentDeliveryBoy.UndeliverySegmentofDeliveryBoy;
import com.demoprj.Fragment.CustomerDetailsFragment;
import com.demoprj.Fragment.DeliveryDetailsFragment;
import com.demoprj.Fragment.PriceChangeFragment;
import com.demoprj.Fragment.QuickBookFragment;
import com.demoprj.Segment.DeliverySegment;
import com.demoprj.Segment.UndeliverySegment;
import com.parse.ParseUser;

public class DeliveryboyHomeActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

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
        setupViewPager(viewPagerAdmin);
        viewPagerAdmin.addOnPageChangeListener(this);
        tabLayoutAdmin = (TabLayout) findViewById(R.id.tabsAdmin);
        tabLayoutAdmin.setupWithViewPager(viewPagerAdmin);
    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapterAdmin adapter = new ViewPagerAdapterAdmin(getSupportFragmentManager());
        adapter.addFragment(new DeliveryDetailsFragmentDeliveryBoy(), "Delivery Detail");
        cus = new CustomerDetailsFragment();
        adapter.addFragment(cus, "Customer Details");
        adapter.addFragment(new GasBookFragment(), "Quick Book");
        adapter.addFragment(new ExpenseFragment(), "Expense");
        adapter.addFragment(new AmountExpenseDetailsFragment(), "Amount");
        viewPager.setAdapter(adapter);
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
        AddPerson = menu.findItem(R.id.menu_add);
        refresh = menu.findItem(R.id.menu_refresh);
        search.setVisible(true);
        AddPerson.setVisible(false);
        refresh.setVisible(false);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_refresh:
                CustomerDetailsFragment.LoadCustomerInfo();
                break;
            case R.id.menu_search:
                if(pageSwiperPosition==0) {
                    if (isSearchVisible) {
                        if (DeliveryDetailsFragmentDeliveryBoy.isDelivered) {
                            DeliverySegmentofDeliveryBoy.txtDeliverySearch.setText("");
                            DeliverySegmentofDeliveryBoy.llDeliverySearch.setVisibility(View.GONE);
                        } else {
                            UndeliverySegmentofDeliveryBoy.txtDeliverySearch.setText("");
                            UndeliverySegmentofDeliveryBoy.llDeliverySearch.setVisibility(View.GONE);
                        }
                        isSearchVisible = false;
                    } else {
                        if (DeliveryDetailsFragmentDeliveryBoy.isDelivered) {
                            DeliverySegmentofDeliveryBoy.txtDeliverySearch.setText("");
                            DeliverySegmentofDeliveryBoy.llDeliverySearch.setVisibility(View.VISIBLE);
                        } else {
                            UndeliverySegmentofDeliveryBoy.txtDeliverySearch.setText("");
                            UndeliverySegmentofDeliveryBoy.llDeliverySearch.setVisibility(View.VISIBLE);
                        }
                        isSearchVisible = true;
                    }
                }else{
                    if (isSearchVisible) {
                        CustomerDetailsFragment.txtSearch.setText("");
                        isSearchVisible = false;
                        CustomerDetailsFragment.llSearch.setVisibility(View.GONE);
                    } else {
                        CustomerDetailsFragment.txtSearch.setText("");
                        isSearchVisible = true;
                        CustomerDetailsFragment.llSearch.setVisibility(View.VISIBLE);
                    }
                }
                break;
            case R.id.menu_logout:
                ParseUser.logOut();
                Intent next = new Intent(this,MainActivity.class);
                startActivity(next);
                finish();
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

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        pageSwiperPosition = position;
        if(isSearchVisible) {
            CustomerDetailsFragment.llSearch.setVisibility(View.GONE);
            CustomerDetailsFragment.txtSearch.setText("");
            isSearchVisible = false;
        }
        if(position==0){
            refresh.setVisible(false);
        }
        if(position>=2){
            search.setVisible(false);
            refresh.setVisible(false);
        }else{
            search.setVisible(true);
        }
        if(position==1){
            refresh.setVisible(true);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
