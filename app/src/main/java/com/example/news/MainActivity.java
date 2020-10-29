package com.example.news;

import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ViewPager viewPager;
    TabLayout tabLayout;

    private HeadlineFragment headlineFragment;
    private BusinessFragment businessFragment;
    private HealthFragment healthFragment;
    private SportsFragment sportsFragment;
    private EntertainmentFragment entertainmentFragment;
    private ScienceFragment scienceFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        setSupportActionBar(toolbar);

        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);

        /**(2) Fragments created now create fragment Objects**/
        headlineFragment = new HeadlineFragment();
        businessFragment = new BusinessFragment();
        healthFragment = new HealthFragment();
        sportsFragment = new SportsFragment();
        entertainmentFragment = new EntertainmentFragment();
        scienceFragment = new ScienceFragment();

        /**(3) Now we will set up our tab layout with the view pager.
         * We also need an Adapter for our ViewPager **/
        tabLayout.setupWithViewPager(viewPager);

        /**(4)Our ViewPager Adapter work has started.
         * It will be a separate class which will be inside our MainActivity**/
        viewPagerAdapter viewPagerAdapter = new viewPagerAdapter(getSupportFragmentManager(), 0);

        /**(9) Now we pass object of our Business and Headline fragment**/
        viewPagerAdapter.addFragment(headlineFragment, "Headline");
        viewPagerAdapter.addFragment(businessFragment, "Business");
        viewPagerAdapter.addFragment(healthFragment, "health");
        viewPagerAdapter.addFragment(sportsFragment, "Sports");
        viewPagerAdapter.addFragment(entertainmentFragment, "Entertainment");
        viewPagerAdapter.addFragment(scienceFragment, "Science");

        /**(10) Our ViewPagerAdapter is ready, now we set the Adapter**/
        viewPager.setAdapter(viewPagerAdapter);

        /**(11) We can pass Fragment icons dynamically too**/
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_newspaper);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_briefcase);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_hospital);
        tabLayout.getTabAt(3).setIcon(R.drawable.ic_runer_silhouette_running_fast);
        tabLayout.getTabAt(4).setIcon(R.drawable.ic_tv);
        tabLayout.getTabAt(5).setIcon(R.drawable.ic_science);

    }


}