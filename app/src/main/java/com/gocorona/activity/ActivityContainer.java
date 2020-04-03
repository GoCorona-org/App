package com.gocorona.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.gocorona.R;
import com.gocorona.fragments.AppBaseFragment;

import simplifii.framework.utility.AppConstants;

public class ActivityContainer extends AppBaseActivity {
    int fragmentType;
    private Fragment f;

    public static void startActivity(Context context, int fragmentType, Bundle extraBundle) {
        Intent i = new Intent(context, ActivityContainer.class);
        if (extraBundle != null) {
            i.putExtras(extraBundle);
        }
        i.putExtra(AppConstants.BUNDLE_KEYS.FRAGMENT_TYPE, fragmentType);
        context.startActivity(i);
    }

    public static void startActivityForResult(AppBaseFragment ctx, int fragmentType, Bundle extraBundle) {
        Intent i = new Intent(ctx.getActivity(), ActivityContainer.class);
        if (extraBundle != null) {
            i.putExtras(extraBundle);
        }
        i.putExtra(AppConstants.BUNDLE_KEYS.FRAGMENT_TYPE, fragmentType);
        ctx.startActivityForResult(i, fragmentType);
    }

    @Override
    protected int getHomeIcon() {
        return R.drawable.ic_back_brown;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        fragmentType = getIntent().getExtras().getInt(AppConstants.BUNDLE_KEYS.FRAGMENT_TYPE);
        String title = getIntent().hasExtra(AppConstants.BUNDLE_KEYS.TITLE) ? getIntent().getStringExtra(AppConstants.BUNDLE_KEYS.TITLE) : "";
        this.f = getFragment(fragmentType);
        if (f != null)
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_fragmentContainer, f).commit();
        initAcitvityContainerToolbar(title);
    }

    public void initAcitvityContainerToolbar(String title) {
        initToolBar(title);
    }

    private Fragment getFragment(int fragmentType) {
        Fragment fragment = null;
        switch (fragmentType) {
            case AppConstants.FRAGMENT_TYPES.VIDEO_DETAIL:
//                fragment = VideoFeedDetailFragment.newInstance();
                break;
            case AppConstants.FRAGMENT_TYPES.YOUTUBE_DETAIL:
//                fragment = YoutubeFeedDetailFragment.newInstance();
                break;
            case AppConstants.FRAGMENT_TYPES.IMAGE_DETAIL:
//                fragment = ImageFeedDetailFragment.newInstance();
                break;
            case AppConstants.FRAGMENT_TYPES.ABOUT_US:
//                fragment = AboutUsFragment.newInstance();
                break;
            case AppConstants.FRAGMENT_TYPES.OUR_LEGACY:
//                fragment = OurLegacyFragment.newInstance();
                break;
            case AppConstants.FRAGMENT_TYPES.OUR_OBJECTIVES:
//                fragment = OurObjectivesFragmentFragment.newInstance();
                break;
        }
        if (fragment != null) {
            fragment.setArguments(getIntent().getExtras());
        }
        return fragment;
    }

    public static void startActivityForResult(Context context, int fragmentType, Bundle extraBundle, int requestCode, Fragment fragment) {
        Intent intent = new Intent(context, ActivityContainer.class);
        if (extraBundle != null) {
            intent.putExtra(AppConstants.BUNDLE_KEYS.EXTRA_BUNDLE, extraBundle);
        }
        intent.putExtra(AppConstants.BUNDLE_KEYS.FRAGMENT_TYPE, fragmentType);
        fragment.startActivityForResult(intent, requestCode);
    }

    public static void startActivityForResult(Activity context, int fragmentType, Bundle extraBundle, int requestCode) {
        Intent i = new Intent(context, ActivityContainer.class);
        if (extraBundle != null) {
            i.putExtras(extraBundle);
        }
        i.putExtra(AppConstants.BUNDLE_KEYS.FRAGMENT_TYPE, fragmentType);
        context.startActivityForResult(i, requestCode);
    }

    public static void startActivityForResult(Fragment fragment, int fragmentType, Bundle extraBundle, int requestCode) {
        Intent i = new Intent(fragment.getActivity(), ActivityContainer.class);
        if (extraBundle != null) {
            i.putExtras(extraBundle);
        }
        i.putExtra(AppConstants.BUNDLE_KEYS.FRAGMENT_TYPE, fragmentType);
        fragment.startActivityForResult(i, requestCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (this.f != null) {
            this.f.onActivityResult(requestCode, resultCode, data);
        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
