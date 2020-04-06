package com.gocorona;

import android.app.Activity;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;

import com.gocorona.activity.AppBaseActivity;
import com.gocorona.activity.LoginActivity;
import com.gocorona.activity.RegisterActivity;
import com.gocorona.fragments.CoronCheckDialog;
import com.gocorona.fragments.DrawerFragment;
import com.gocorona.fragments.QuestionsMainFargment;
import com.gocorona.listeners.ChangeFragmentListner;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;

import simplifii.framework.activity.BaseActivity;
import simplifii.framework.receivers.AppBrodcastReciver;
import simplifii.framework.utility.AppConstants;
import simplifii.framework.utility.LocalHelperUtility;
import simplifii.framework.utility.Preferences;

public class MainActivity extends AppBaseActivity implements DrawerLayout.DrawerListener, ChangeFragmentListner {
    private DrawerLayout drawerLayout;
    private FragmentManager fragmentManager;
    private boolean isDrawerOpen;
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setOnClickListener(R.id.rl_drawer, R.id.ll_checkup);
        initViews();
        initToolBar("");
//        if (!Preferences.isUserDeviceInfoSaveed()){
//            sendNotificationId();
//        }

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.rl_drawer:
                drawerOperation();
                break;
            case R.id.ll_checkup:
                addFragment(new QuestionsMainFargment(), true);
//                showCheckupDialog();
                break;
        }
    }

    private void showCheckupDialog() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (fragment != null && fragment instanceof QuestionsMainFargment) {
            return;
        }
        CoronCheckDialog instance = CoronCheckDialog.getInstance(this);
        instance.setStyle( DialogFragment.STYLE_NORMAL, R.style.You_Dialog );
        instance.show(getSupportFragmentManager(), " ");
    }

    private void initViews() {
        initToolBar(getString(R.string.app_name));
        drawerLayout = findViewById(R.id.main_drawer_layout);
        drawerLayout.addDrawerListener(this);
        fragmentManager = getSupportFragmentManager();
        addDrawerFragment();
    }

    private void addDrawerFragment() {
        DrawerFragment drawerFragment = DrawerFragment.getInstance(this, drawerLayout);
        fragmentManager.beginTransaction().replace(R.id.main_navigation_view, drawerFragment).commit();
    }

    @Override
    protected int getHomeIcon() {
        return R.mipmap.icon_m;
    }

    @Override
    protected void onHomePressed() {
//        drawerOperation();
    }

    private void drawerOperation() {
        if (isDrawerOpen) {
            drawerLayout.closeDrawer(Gravity.RIGHT);
        } else {
            drawerLayout.openDrawer(Gravity.RIGHT);
        }
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (fragment != null && fragment instanceof QuestionsMainFargment) {
            getSupportFragmentManager().popBackStack();
            return;
        }

        if (isDrawerOpen) {
            drawerLayout.closeDrawer(Gravity.LEFT);
            isDrawerOpen = false;
            return;
        } else {
//            if (FeedHolderFragment.tabPoition > 0) {
//                AppBrodcastReciver.sendBroadcast(this, AppConstants.ACTION.HOME);
//                return;
//            }
            if (doubleBackToExitPressedOnce) {
                finish();
                return;
            }else {
                this.doubleBackToExitPressedOnce = true;
                showToast(getString(R.string.back_press));
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        doubleBackToExitPressedOnce = false;
                    }
                }, 5000);
            }
        }


    }


    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {

    }

    @Override
    public void onDrawerOpened(View drawerView) {
        isDrawerOpen = true;
    }

    @Override
    public void onDrawerClosed(View drawerView) {
        isDrawerOpen = false;
    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }

    @Override
    public void addFragment(Fragment fragment, boolean b) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        }
        if (b) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.replace(R.id.fragment_container, fragment).commit();
        drawerLayout.closeDrawer(Gravity.RIGHT);

    }

    @Override
    public void removeAllFragment() {
        if (getSupportFragmentManager().findFragmentById(R.id.fragment_container) != null) {
            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                for (int i = 0; i < getSupportFragmentManager().getBackStackEntryCount(); i++) {
                    getSupportFragmentManager().popBackStack();
                }
            }
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu_main, menu);
//        MenuItem item = menu.findItem(R.id.item_join_us);
//        item.setActionView(R.layout.menu_item_view_join_us);
//        if (Preferences.isUserLoggerIn()) {
//            item.setVisible(false);
//        }else {
//            showCaseViewNew(item.getActionView());
//        }
//        item.getActionView().findViewById(R.id.tv_menu).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
//                startActivityForResult(intent, AppConstants.REQUEST_CODES.REGISTER);
//            }
//        });
//
//        return true;
//    }
//    private void showCaseViewNew(View actionView) {
//        if (!Preferences.isFirstTimeShowcaseOpened()) {
//            ShowcaseView showcaseView = new ShowcaseView.Builder(this)
//                    .setTarget(new ViewTarget(actionView.findViewById(R.id.tv_menu)))
//                    .setContentTitle(getString(R.string.join_us))
//                    .setContentText(getString(R.string.join_us_showcase))
//                    .setStyle(R.style.CustomShowcaseTheme2)
//                    .build();
//            showcaseView.setHideOnTouchOutside(true);
//            showcaseView.setButtonText(getString(R.string.close));
////            showcaseView.hideButton();
//            Preferences.saveData(Preferences.FIRST_SHOWCASE, true);
//        }
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        switch (item.getItemId()) {
//            case R.id.item_language_english:
//                if (LocalHelperUtility.getLanguage().equalsIgnoreCase(AppConstants.LANGUAGE.ENGLISH))
//                    break;
//                LocalHelperUtility.setLocale(this, AppConstants.LANGUAGE.ENGLISH);
//                this.onLabelChange();
//                break;
//            case R.id.item_language_punjabi:
//                if (LocalHelperUtility.getLanguage().equalsIgnoreCase(AppConstants.LANGUAGE.PUNJABI))
//                    break;
//                LocalHelperUtility.setLocale(this, AppConstants.LANGUAGE.PUNJABI);
//                this.onLabelChange();
//                break;
//            case R.id.item_join_us:
//                Intent intent = new Intent(this, RegisterActivity.class);
//                startActivityForResult(intent, AppConstants.REQUEST_CODES.REGISTER);
//
//                break;
//
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case AppConstants.REQUEST_CODES.REGISTER:
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        String stringExtra = data.getStringExtra(AppConstants.BUNDLE_KEYS.TO_LOGIN);
                        if (!TextUtils.isEmpty(stringExtra)) {
                            startLoginActivityForResult();
                            return;
                        }
                    }
                    invalidateOptionsMenu();
                    initViews();
                }
                break;
            case AppConstants.REQUEST_CODES.LOGIN:
                if (resultCode == RESULT_OK) {
//                    sendNotificationId();
                    invalidateOptionsMenu();
                    initViews();
                }
                break;
        }
    }

    private void startLoginActivityForResult() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivityForResult(intent, AppConstants.REQUEST_CODES.LOGIN);
    }

    @Override
    public void onLabelChange() {
        super.onLabelChange();
        Configuration config = new Configuration();
        ((Activity) this)
                .getBaseContext()
                .getResources()
                .updateConfiguration(config, ((ContextWrapper) this).getBaseContext().getResources().getDisplayMetrics());
        initViews();
    }


}
