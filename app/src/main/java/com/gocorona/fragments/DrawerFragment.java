package com.gocorona.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.gocorona.R;
import com.gocorona.activity.ActivityContainer;
import com.gocorona.activity.LoginActivity;
import com.gocorona.activity.RegisterActivity;
import com.gocorona.listeners.ChangeFragmentListner;
import com.gocorona.model.DrawerModel;
import com.gocorona.model.dummy.DrawerDataResponse;
import com.gocorona.model.response.login.UserData;
import com.gocorona.ui.home.HomeFragment;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import simplifii.framework.adapters.CustomListAdapter;
import simplifii.framework.adapters.CustomListAdapterInterface;
import simplifii.framework.receivers.AppBrodcastReciver;
import simplifii.framework.utility.AppConstants;
import simplifii.framework.utility.CollectionUtils;
import simplifii.framework.utility.JsonUtil;
import simplifii.framework.utility.Preferences;
import simplifii.framework.widgets.CustomFontTextView;

public class DrawerFragment extends AppBaseFragment implements CustomListAdapterInterface, AdapterView.OnItemClickListener, ChangeFragmentListner {
    List<DrawerModel> itemsList = new ArrayList<>();
    ListView listView;
    DrawerLayout drawerLayout;
    private CustomListAdapter customAdepter;
    private ChangeFragmentListner changeFragmentListener;
    private View headerView;

    public static DrawerFragment getInstance(ChangeFragmentListner changeFragmentListner, DrawerLayout drawerLayout) {
        DrawerFragment drawerFragment = new DrawerFragment();
        drawerFragment.changeFragmentListener = changeFragmentListner;
        drawerFragment.drawerLayout = drawerLayout;
        return drawerFragment;
    }

    @Override
    public void initViews() {
        headerView = LayoutInflater.from(getActivity()).inflate(R.layout.header_drawer, null);
        initDataToViews();
    }

    private void initDataToViews() {
        setHeaderData();
        setAdapter();
        initDrawerList();
        addHomeFragment();
    }

    private void addHomeFragment() {
        if (changeFragmentListener != null) {
            changeFragmentListener.removeAllFragment();
            changeFragmentListener.addFragment(new HomeFragment(), true);
        }
    }

    private void setAdapter() {
        listView = (ListView) findView(R.id.list_view);
        customAdepter = new CustomListAdapter(getActivity(), R.layout.row_drawer, itemsList, this);
        listView.setAdapter(customAdepter);
        listView.addHeaderView(headerView);
        listView.setOnItemClickListener(this);
        setOnClickListener(R.id.ll_main);
    }

    private void initDrawerList() {
        itemsList.clear();
        try {
            String s = readFromAssets(getActivity(), "drawerdata.json");
            DrawerDataResponse drawerDataResponse = (DrawerDataResponse) JsonUtil.parseJson(s, DrawerDataResponse.class);
            if (drawerDataResponse != null && drawerDataResponse.getData() != null) {
                itemsList.clear();
                itemsList.addAll((Collection<? extends DrawerModel>) drawerDataResponse.getData().getDrawerList());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
//        itemsList.addAll(DrawerDataEnum.getAllDrawerItems());
        if (CollectionUtils.isNotEmpty(itemsList)) {
            for (DrawerModel d : itemsList) {
                if (d.getName().equalsIgnoreCase(getString(R.string.login))) {
                    if (Preferences.isUserLoggerIn()) {
                        d.setName(getString(R.string.logout));
                        d.setIcon("https://firebasestorage.googleapis.com/v0/b/aboutstays-b349f.appspot.com/o/PEP%2Fhome_icons%2Flogout.png?alt=media&token=f15ff329-7e06-43a4-a6ac-ea189c94dd2f");
                        break;
                    }
                }
            }
        }
        customAdepter.notifyDataSetChanged();
    }

    private void setHeaderData() {

        ImageView ivUser = headerView.findViewById(R.id.iv_user_image);
        CustomFontTextView tvUserName = headerView.findViewById(R.id.tv_profile_name);
        CustomFontTextView btnJoinUs = headerView.findViewById(R.id.tv_join_us);
        if (Preferences.isUserLoggerIn()) {
            UserData instance = UserData.getInstance();
            if (!TextUtils.isEmpty(instance.getName())) {
                tvUserName.setText(getString(R.string.welcome) + " " + instance.getName());
            }
            btnJoinUs.setVisibility(View.GONE);
        } else {
            tvUserName.setText(getString(R.string.welcome_guest));
            btnJoinUs.setVisibility(View.VISIBLE);
            btnJoinUs.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), RegisterActivity.class);
                    startActivityForResult(intent, AppConstants.REQUEST_CODES.REGISTER);
                    drawerLayout.closeDrawer(Gravity.RIGHT);
                }
            });
        }
    }
    /*@Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.ll_main:
                break;
        }
    }*/

    @Override
    public int getViewID() {
        return R.layout.fragment_drawer;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        if (position == 0) {
            return;
        }
        int positionData = (int) l;
        if (CollectionUtils.isNotEmpty(itemsList) && itemsList.get(positionData) != null) {

            DrawerModel drawerModel = itemsList.get(positionData);
            Bundle bundle = new Bundle();
            switch (drawerModel.getClickId()) {
                case 1://home
//                    if (FeedHolderFragment.tabPoition != 0) {
//                        AppBrodcastReciver.sendBroadcast(getActivity(), AppConstants.ACTION.HOME);
//                    }
                    drawerLayout.closeDrawer(Gravity.RIGHT);
                    return;
                case 8://login
                    drawerLayout.closeDrawer(Gravity.RIGHT);
                    if (Preferences.isUserLoggerIn()) {
//                        logout();
                        Preferences.removeData(Preferences.LOGIN_KEY);
                        refreshViews();

                        return;
                    }
                    startLoginActivityForResult();
                    return;
                case 7://about_us
//                    bundle.putString(AppConstants.BUNDLE_KEYS.TITLE,getString(R.string.about_us));
//                    ActivityContainer.startActivity(getActivity(), AppConstants.FRAGMENT_TYPES.ABOUT_US, bundle);
                    drawerLayout.closeDrawer(Gravity.RIGHT);
                    return;
                case 3://OUR NETWORK
//                    if (FeedHolderFragment.tabPoition != 1) {
//                        AppBrodcastReciver.sendBroadcast(getActivity(), AppConstants.ACTION.OUR_NETWORK);
//                    }
                    drawerLayout.closeDrawer(Gravity.RIGHT);
                    return;
                case 4://OUR CURRENT_ISSUE
//                    if (FeedHolderFragment.tabPoition != 2) {
//                        AppBrodcastReciver.sendBroadcast(getActivity(), AppConstants.ACTION.CURRENT_ISSUE);
//                    }
                    drawerLayout.closeDrawer(Gravity.RIGHT);
                    return;
                case 9://GALLERY
//                    if (FeedHolderFragment.tabPoition != 3) {
//                        AppBrodcastReciver.sendBroadcast(getActivity(), AppConstants.ACTION.GALLERY);
//                    }
                    drawerLayout.closeDrawer(Gravity.RIGHT);
                    return;
                case 10://VIDEO
//                    if (FeedHolderFragment.tabPoition != 4) {
//                        AppBrodcastReciver.sendBroadcast(getActivity(), AppConstants.ACTION.VIDEO);
//                    }
                    drawerLayout.closeDrawer(Gravity.RIGHT);
                    return;
                case 11://EVENTS
//                    if (FeedHolderFragment.tabPoition != 5) {
//                        AppBrodcastReciver.sendBroadcast(getActivity(), AppConstants.ACTION.EVENT);
//                    }
                    drawerLayout.closeDrawer(Gravity.RIGHT);
                    return;
                case 12://OUR LEGACY
//                    bundle.putString(AppConstants.BUNDLE_KEYS.TITLE,getString(R.string.our_legecy));
//                    ActivityContainer.startActivity(getActivity(), AppConstants.FRAGMENT_TYPES.OUR_LEGACY, bundle);
                    drawerLayout.closeDrawer(Gravity.RIGHT);
                    return;
                case 5://OUR OBJECTIVES
//                    bundle.putString(AppConstants.BUNDLE_KEYS.TITLE,getString(R.string.our_objectives));
//                    ActivityContainer.startActivity(getActivity(), AppConstants.FRAGMENT_TYPES.OUR_OBJECTIVES, bundle);
                    drawerLayout.closeDrawer(Gravity.RIGHT);
                    break;
                case 6://NOTIFICATION
//                    showToast(getString(R.string.coming_sonn));
                    drawerLayout.closeDrawer(Gravity.RIGHT);
                    return;
            }
        }
        drawerLayout.closeDrawers();
    }

    private void startLoginActivityForResult() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivityForResult(intent, AppConstants.REQUEST_CODES.LOGIN);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent,
                        int resourceID, LayoutInflater inflater) {
        final Holder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.row_drawer, parent, false);
            holder = new Holder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        DrawerModel drawerItem = itemsList.get(position);
        holder.tv_name.setText(drawerItem.getName());
        Picasso.with(getActivity()).load(drawerItem.getIcon()).into(holder.iv_image);
        return convertView;
    }

    @SuppressLint("WrongConstant")
    @Override
    public void addFragment(Fragment fragment, boolean b) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        if (getActivity().getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getActivity().getSupportFragmentManager().popBackStack();
        }
        if (b) {
            fragmentTransaction.addToBackStack(null);
        }
//        fragmentTransaction.replace(R.id.content_frame, fragment).commit();
        drawerLayout.closeDrawer(Gravity.START);
    }

    @SuppressLint("WrongConstant")
    @Override
    public void removeAllFragment() {
        if (getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_container) != null) {
            if (getActivity().getSupportFragmentManager().getBackStackEntryCount() > 0) {
                for (int i = 0; i < getActivity().getSupportFragmentManager().getBackStackEntryCount(); i++) {
                    getActivity().getSupportFragmentManager().popBackStack();
                }
            }
        }
        drawerLayout.closeDrawer(Gravity.START);
    }

    class Holder {
        TextView tv_name;
        ImageView iv_image;

        private Holder(View view) {
            tv_name = view.findViewById(R.id.tv_item_drawer);
            iv_image = view.findViewById(R.id.iv_item_drawer);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case AppConstants.REQUEST_CODES.REGISTER:
                if (data != null) {
                    String stringExtra = data.getStringExtra(AppConstants.BUNDLE_KEYS.TO_LOGIN);
                    if (!TextUtils.isEmpty(stringExtra)) {
                        startLoginActivityForResult();
                        return;
                    }
                }
                refreshViews();
                break;
            case AppConstants.REQUEST_CODES.LOGIN:
                refreshViews();
                break;
        }

    }

    private void refreshViews() {
        setHeaderData();
        initDrawerList();
        getActivity().invalidateOptionsMenu();
    }
}
