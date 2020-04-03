package simplifii.framework.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import simplifii.framework.utility.AppConstants;

/**
 * Created by nitin on 30/10/15.
 */
public class FragmentContainer extends BaseActivity {

    public static void startActivity(Context ctx, int fragmentType, Bundle extraBundle) {
        Intent i = new Intent(ctx, FragmentContainer.class);
        if (extraBundle != null) {
            i.putExtra(AppConstants.BUNDLE_KEYS.EXTRA_BUNDLE, extraBundle);
        }
        i.putExtra(AppConstants.BUNDLE_KEYS.FRAGMENT_TYPE, fragmentType);
        ctx.startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.fragmenta_container);
//        getSupportFragmentManager().beginTransaction().replace(R.id.frame, getFragment(getIntent().getExtras().getInt(AppConstants.BUNDLE_KEYS.FRAGMENT_TYPE))).commit();
    }

    private Fragment getFragment(int fragmentType) {
        switch (fragmentType) {
        }
        return null;
    }
}
