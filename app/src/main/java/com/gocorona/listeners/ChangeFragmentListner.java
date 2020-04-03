package com.gocorona.listeners;


import androidx.fragment.app.Fragment;

public interface ChangeFragmentListner{
        void addFragment(Fragment fragment, boolean b);
        void removeAllFragment();
    }