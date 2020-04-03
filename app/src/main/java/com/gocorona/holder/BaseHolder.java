package com.gocorona.holder;


import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.gocorona.adapter.BaseRecycleAdapter;

import java.io.Serializable;

public class BaseHolder extends RecyclerView.ViewHolder implements Serializable {

    protected Context context;
    protected BaseRecycleAdapter.RecyclerClickInterface clickListener;
    private int position;

    public BaseHolder(View itemView) {
        super(itemView);
        context = itemView.getContext();
    }

    public BaseRecycleAdapter.RecyclerClickInterface getClickListener() {
        return clickListener;
    }

    public void setClickListener(BaseRecycleAdapter.RecyclerClickInterface clickListener) {
        this.clickListener = clickListener;
    }

    protected void sendClickEvent(int actionType, Object model) {
        if (clickListener != null) {
            clickListener.onItemClick(itemView,getAdapterPosition(), model, actionType);
        }
    }

    protected View findView(int id) {
        return itemView.findViewById(id);
    }

    protected TextView findTv(int id) {
        return (TextView) findView(id);
    }

    public void onBind(int position, Object obj) {
        this.position = position;
    }

    protected void setTextOrGone(TextView tv, String text){
        if(!TextUtils.isEmpty(text)){
            tv.setVisibility(View.VISIBLE);
            tv.setText(text);
        }else{
            tv.setVisibility(View.GONE);
        }
    }

    protected void setTextOrInvisible(TextView tv, String text){
        if(!TextUtils.isEmpty(text)){
            tv.setVisibility(View.VISIBLE);
            tv.setText(text);
        }else{
            tv.setVisibility(View.INVISIBLE);
        }
    }

    public void share(){
//        BranchIOUtil.getInstance().createShareLinkForOnlyOpenApp(context);
    }

}
