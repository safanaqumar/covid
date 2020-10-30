package com.example.covid;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdaptor extends FragmentPagerAdapter {
public final List<Fragment> fragmentList = new ArrayList<>();
private final List<String> FragmentListTitles = new ArrayList<>();
public ViewPagerAdaptor(FragmentManager fm){
    super(fm);
}

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return FragmentListTitles.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return FragmentListTitles.get(position);
    }
    public void Addfragment(Fragment fragment,String Title){
    fragmentList.add(fragment);
    FragmentListTitles.add(Title);
    }
}
