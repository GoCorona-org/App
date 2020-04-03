package simplifii.framework.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;


public class CustomListAdapter<T> extends ArrayAdapter<T> {
    List<T> list;
    int rowlayoutID;
    LayoutInflater inflater;
    CustomListAdapterInterface ref;

    public CustomListAdapter(Context context, int resource, List<T> objects, CustomListAdapterInterface ref) {
        super(context, resource, objects);
        inflater = LayoutInflater.from(context);
        this.list = objects;
        this.ref = ref;
        this.rowlayoutID = resource;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        return ref.getView(position, convertView, parent, rowlayoutID,inflater);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return ref.getView(position, convertView, parent, rowlayoutID,inflater);
    }
}
