package precisioninfomatics.servicefirst.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import precisioninfomatics.servicefirst.DAO.VisitDAO;
import precisioninfomatics.servicefirst.Fragments.GeneralClaim;
import precisioninfomatics.servicefirst.Fragments.LocalVendor;
import precisioninfomatics.servicefirst.Fragments.Resources;
import precisioninfomatics.servicefirst.Fragments.Visit;
import precisioninfomatics.servicefirst.Networks.CustomerBranchNetwork;
import precisioninfomatics.servicefirst.Networks.MapFile;
import precisioninfomatics.servicefirst.R;

public class IncidentView extends AppCompatActivity{


    private Integer GeneralClaimID,IsLocalVendor,VisitLocalVendor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incident_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        int IncidentID = 0;
        int installationcall = 0;
        Bundle bundle = getIntent().getExtras();
        String inccode = null;
        if (bundle != null) {
            GeneralClaimID = bundle.getInt("generalclaim");
            IncidentID = bundle.getInt("id");
            IsLocalVendor = bundle.getInt("localvendor");
            inccode = bundle.getString("inscode");
            installationcall = bundle.getInt("installationcall");
        }
        VisitDAO visitDAO = new VisitDAO(this);
        VisitLocalVendor = visitDAO.IsLocalVendorRequired(IncidentID);
        if (installationcall == 1) {
            new CustomerBranchNetwork(this, IncidentID);
        }
        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        ViewPager mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        getSupportActionBar().setTitle(inccode);
        PagerTabStrip pagerTabStrip = findViewById(R.id.pager_title_strip);
        pagerTabStrip.setTabIndicatorColor(Color.parseColor("#fff000"));
        MapFile mapFile = new MapFile();
        mapFile.PostFile(this);
         if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(IncidentView.this, Incident.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            });
        }
    }

    public   class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        private SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new precisioninfomatics.servicefirst.Fragments.IncidentView();
                case 1:
                    return new Resources();
                case 2:
                    return new Visit();
                case 3:
                    if(GeneralClaimID==1) {
                        return new GeneralClaim();
                    }
                case 4:
                    if(IsLocalVendor>=1||VisitLocalVendor>=1){
                        return new LocalVendor();
                    }
            }
            return null;
        }
        @Override
        public int getCount() {
            // Show 3 total pages.
            if(GeneralClaimID==1 && ((IsLocalVendor>=1)||(VisitLocalVendor>=1))) {
                return 5;
            }
            else if(GeneralClaimID==1||((IsLocalVendor>=1)||(VisitLocalVendor>=1))){
                return 4;
            }
            else {
                return 3;
            }
        }
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "INFORMATION";
                case 1:
                    return "RESOURCES";
                case 2:
                    return "VISITS";
                case 3:
                    if(GeneralClaimID==1){
                        return "GENERAL CLAIM";
                    }
                case 4:
                    if(((IsLocalVendor>=1)||(VisitLocalVendor>=1))){
                        return "LOCAL VENDOR";
                    }
            }
            return null;
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(IncidentView.this, Incident.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY |Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
