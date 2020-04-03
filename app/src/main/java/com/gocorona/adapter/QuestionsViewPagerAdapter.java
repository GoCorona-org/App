package com.gocorona.adapter;


import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.gocorona.fragments.BaseQuestionsFargment;

import java.util.ArrayList;
import java.util.List;

public class QuestionsViewPagerAdapter extends FragmentPagerAdapter {

    private List<FragmentModelHolder> mFragmentsList = new ArrayList<>();

    public QuestionsViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentsList.get(position).getFragment();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentsList.get(position).getTitle();
    }

    @Override
    public int getCount() {
        return mFragmentsList.size();
    }

    public void addFragments(FragmentModelHolder modelHolder) {
        mFragmentsList.add(modelHolder);
    }

    public static class FragmentModelHolder {
        private BaseQuestionsFargment fragment;
        private String title;

        public FragmentModelHolder(BaseQuestionsFargment fragment, String title) {
            this.fragment = fragment;
            this.title = title;
        }

        public BaseQuestionsFargment getFragment() {
            return fragment;
        }

        public String getTitle() {
            return title;
        }
    }

}
