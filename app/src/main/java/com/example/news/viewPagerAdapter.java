package com.example.news;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * (5) ViewPagerAdapter class created inside MainActivity
 **/
public class viewPagerAdapter extends FragmentPagerAdapter {

    /**
     * fragments will store all Fragments in this list
     **/
    private List<Fragment> fragments = new ArrayList<>();
    /**
     * fragmentTitle will store all Fragment Title (that are visible on the TabLayout)
     **/
    private List<String> fragmentTitle = new ArrayList<>();

    public viewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    /**
     * (8) It will add the New Fragments and its Title
     **/
    public void addFragment(Fragment fragment, String title) {
        fragments.add(fragment);
        fragmentTitle.add(title);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        /**Return total number of fragments inside our viewPagerAdapter**/
        return fragments.size();
    }

    /**
     * (7) Here we override the existing getPageTitle method to provide our fragmentTitle
     **/
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        //It will get title from our list.
        return fragmentTitle.get(position);
    }
}