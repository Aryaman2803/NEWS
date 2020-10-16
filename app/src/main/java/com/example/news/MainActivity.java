package com.example.news;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ViewPager viewPager;
    TabLayout tabLayout;

    private HeadlineFragment headlineFragment;
    private BusinessFragment businessFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = findViewById(R.id.view_pager);
        toolbar = findViewById(R.id.toolbar);
        tabLayout = findViewById(R.id.tab_layout);

        /**(2) Fragments created now create fragment Objects**/
        headlineFragment = new HeadlineFragment();
        businessFragment = new BusinessFragment();

        /**(3) Now we will set up our tab layout with the view pager.
         * We also need an Adapter for our ViewPager **/
        tabLayout.setupWithViewPager(viewPager);

        /**(4)Our ViewPager Adapter work has started.
         * It will be a separate class which will be inside our MainActivity**/
        viewPagerAdapter viewPagerAdapter = new viewPagerAdapter(getSupportFragmentManager(), 0);

        /**(9) Now we pass object of our Business and Headline fragment**/
        viewPagerAdapter.addFragment(headlineFragment, "Headline");
        viewPagerAdapter.addFragment(businessFragment, "Business");


        /**(10) Our ViewPagerAdapter is ready, now we set the Adapter**/
        viewPager.setAdapter(viewPagerAdapter);

        /**(11) We can pass Fragment icons dynamically too**/
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_baseline_forum_24);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_baseline_business_24);

    }


}