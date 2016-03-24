package com.demoprj;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.demoprj.Adapter.ViewPagerAdapterAdmin;
import com.demoprj.Fragment.LastTransactionDetailsFragment;
import com.demoprj.Fragment.CustomerDetailInfoFragment;
import com.demoprj.GsonModel.CustomerDetail;
import com.demoprj.GsonModel.PACK.CustomerDetailModle;


public class ParticularCustomerDetailInfo extends AppCompatActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {

    private Toolbar toolbarParticularInfo;
    private TabLayout tabLayoutParticularInfo;
    private ViewPager viewpagerParticularInfo;
    public static int pageSwiperPosition = 0 ;
    CustomerDetailInfoFragment cus;
    public static int CustomerId;
    public static String ObjectId;
    public static Button btnEditCustomerDetail;
    public static CustomerDetailModle customerDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_particular_customer_detail_info);
        Bundle b = getIntent().getExtras();
        CustomerId = b.getInt("CustomerId");
        ObjectId = b.getString("ObjectId");
        customerDetail = (CustomerDetailModle) b.getSerializable("CustomerDetail");
        toolbarParticularInfo = (Toolbar) findViewById(R.id.toolbarParticularInfo);
        setSupportActionBar(toolbarParticularInfo);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewpagerParticularInfo = (ViewPager) findViewById(R.id.viewpagerParticularInfo);
        btnEditCustomerDetail = (Button) findViewById(R.id.btnEditCustomerDetail);
        viewpagerParticularInfo.addOnPageChangeListener(this);
        setupViewPager(viewpagerParticularInfo);

        tabLayoutParticularInfo = (TabLayout) findViewById(R.id.tabsParticularInfo);
        tabLayoutParticularInfo.setupWithViewPager(viewpagerParticularInfo);
        btnEditCustomerDetail.setOnClickListener(this);
    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapterAdmin adapter = new ViewPagerAdapterAdmin(getSupportFragmentManager());
        adapter.addFragment(new CustomerDetailInfoFragment(), "Customer Detail");
        adapter.addFragment(new LastTransactionDetailsFragment(), "Last Transaction Detail");
        viewPager.setAdapter(adapter);
    }
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if(position==0){
            btnEditCustomerDetail.setVisibility(View.VISIBLE);
        }else{
            btnEditCustomerDetail.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        CustomerDetailInfoFragment.Edit();
        btnEditCustomerDetail.setVisibility(View.GONE);
    }
}
