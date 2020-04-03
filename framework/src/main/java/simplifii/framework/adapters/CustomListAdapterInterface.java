package simplifii.framework.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public interface CustomListAdapterInterface {
    public View getView(int position, View convertView, ViewGroup parent, int resourceID,LayoutInflater inflater);

}
