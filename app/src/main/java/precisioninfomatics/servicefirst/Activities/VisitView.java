package precisioninfomatics.servicefirst.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import precisioninfomatics.servicefirst.Fragments.DetailVisit;
import precisioninfomatics.servicefirst.Fragments.SerialNumberMap;
import precisioninfomatics.servicefirst.Fragments.Spares;
import precisioninfomatics.servicefirst.Networks.VisitViewData;
import precisioninfomatics.servicefirst.R;

public class VisitView extends AppCompatActivity {

  private   int isInstallationCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visit_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Visit Information");
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(VisitView.this, IncidentView.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });
        }
        Intent intent = getIntent();
        int IncidentID = 0;
        int webID = 0;
        if (intent != null) {
            isInstallationCall = intent.getIntExtra("installationcall", 0);
            IncidentID = intent.getIntExtra("incidentid", 0);
            webID = intent.getIntExtra("webid", 0);
        }
        VisitViewData visitViewData = new VisitViewData();
        visitViewData.VisitView(this, IncidentID, webID,1,null);

        ViewPager mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        PagerTabStrip pagerTabStrip = findViewById(R.id.pager_title_strip);
        pagerTabStrip.setTabIndicatorColor(Color.parseColor("#fff000"));

    }

    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        private SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position) {
                case 0:
                    return new DetailVisit();
                case 1:
                    return new Spares();
                case 2:
                    if (isInstallationCall == 1) {
                        return new SerialNumberMap();
                    }
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            if (isInstallationCall == 1) {
                return 3;
            } else {
                return 2;
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Visit Information";
                case 1:
                    return "Spare Information";
                case 2:
                    if (isInstallationCall == 1) {
                        return "Item Serial Number Map";
                    }

            }
            return null;
        }
    }

}
