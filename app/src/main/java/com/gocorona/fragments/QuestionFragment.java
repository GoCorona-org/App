package com.gocorona.fragments;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.gocorona.R;
import com.gocorona.model.dummy.OptionsData;
import com.gocorona.model.dummy.QuestionData;
import com.gocorona.model.dummy.QuestionProgressData;

import java.util.ArrayList;
import java.util.List;

import simplifii.framework.adapters.CustomListAdapter;
import simplifii.framework.adapters.CustomListAdapterInterface;
import simplifii.framework.utility.CollectionUtils;
import simplifii.framework.utility.ValidationHelper;
import simplifii.framework.widgets.CustomFontButton;

public class QuestionFragment extends BaseQuestionsFargment implements CustomListAdapterInterface, AdapterView.OnItemClickListener {
    private View.OnClickListener mListenerCallback;
    private QuestionData questionData;
    private int index, listSize;
    List<OptionsData> itemsList = new ArrayList<>();
    ListView listView;
    private CustomListAdapter customAdepter;


    public static QuestionFragment newInstance(QuestionData q, int position, int listSize) {
        QuestionFragment basicFragment = new QuestionFragment();
        basicFragment.questionData = q;
        basicFragment.index = position;
        basicFragment.listSize = listSize;
        return basicFragment;
    }


    @Override
    public boolean isValid() {
//        if (ValidationHelper.isEmpty((questionData.getAnswer()))){
//            return false;
//        }
        return true;
    }

    @Override
    public void showInvalidFieldError() {

    }

    @Override
    public void apply(QuestionProgressData questionProgressData) {
        questionProgressData.setProgress(index*100/listSize);
        questionProgressData.setTitle("Patient");
    }

    @Override
    public void setClickListenerCallback(View.OnClickListener listenerCallback) {
        mListenerCallback = listenerCallback;
    }

    @Override
    public void initViews() {
        setListView();
    }

    private void setListView() {
        listView = (ListView) findView(R.id.list_view);
        itemsList = questionData.getOptions();
        customAdepter = new CustomListAdapter(getActivity(), R.layout.row_drawer, itemsList, this);
        listView.setAdapter(customAdepter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public int getViewID() {
        return R.layout.layout_question;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent, int resourceID, LayoutInflater inflater) {
        final Holder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.row_option, parent, false);
            holder = new Holder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        OptionsData options = itemsList.get(position);
        if (options !=null){
            if (options.getIsSelected() == 1) {
//                holder.tv_option.setBackgroundResource(ContextCompat.getDrawable(holder.tv_option.getContext(), R.drawable.shape_rect_bkg_brown_btn_selected));
            } else {
//                holder.tv_option.setBackgroundColor(getResourceColor(R.drawable.shape_rect_bkg_brown_btn));
            }
            if (!TextUtils.isEmpty(options.getOption())){
                holder.tv_option.setText(options.getOption());
            }

        }

        return convertView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        OptionsData options = itemsList.get(position);
        options.setIsSelected(1);
        questionData.setAnswer(options.getOption());
        listView.notifyAll();
    }

    class Holder {
        CustomFontButton tv_option;

        private Holder(View view) {
            tv_option = (CustomFontButton)view.findViewById(R.id.btn_checkup);
        }
    }
}
