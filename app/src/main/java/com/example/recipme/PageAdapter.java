package com.example.recipme;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class PageAdapter extends FragmentPagerAdapter {

    private int numOfTabs;

    PageAdapter(FragmentManager fm, int numOfTabs) {
    super(fm);
    this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position){
        switch (position){
            case 0:
                return new Overview_Fragment();
            case 1:
                return new Ingredients_Fragment();
            case 2:
                return new Instructions_Fragment();
            default :
                return null;
        }
    }//getItem

    @Override
    public int getCount(){
        return numOfTabs;
    }

}//PageAdapter
