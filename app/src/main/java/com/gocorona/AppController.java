package com.gocorona;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import simplifii.framework.utility.Preferences;

public class AppController extends MultiDexApplication {
    private static AppController instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Preferences.initSharedPreferences(this);
//        Branch.setPlayStoreReferrerCheckTimeout(0);
//        Branch.getAutoInstance(this);
        MultiDex.install(this);
//        Fabric.with(this, new Crashlytics());
//        FirebaseApp.initializeApp(this);
    }
}
