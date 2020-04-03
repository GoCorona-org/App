package com.gocorona.adapter;

import android.content.Context;

import com.gocorona.model.BaseAdapterModel;

import java.util.List;

import simplifii.framework.utility.AppConstants;

public class DialogListAdapter<T extends BaseAdapterModel> extends BaseRecycleAdapter<T> {

//    public static final int COUNTRY_ISD = AppConstants.VIEW_CODES.COUNTRY_ISD;
    public static final int STATE_IN = AppConstants.VIEW_CODES.STATE_IN;
    public static final int DISTRICT = AppConstants.VIEW_CODES.DISTRICT;
    public static final int CITY = AppConstants.VIEW_CODES.CITY;
    public static final int COUNTRY_CODE = AppConstants.VIEW_CODES.COUNTRY_CODE;
    public static final int Constituency = AppConstants.VIEW_CODES.Constituency;
    private int viewType;


    public DialogListAdapter(Context context, List list, int viewType) {
        super(context, list);
        this.viewType = viewType;
    }

    @Override
    public int getItemViewType(int position) {
        return viewType;
    }
}
