package simplifii.framework.listeners;

import android.os.Bundle;

public interface OnFragmentCallBack {
    void onFragmentResult(int result, Bundle data);
    void onLabelChange();
}