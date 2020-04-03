package com.gocorona.fragments;

import android.view.View;

import com.gocorona.R;
import com.gocorona.adapter.QuestionsViewPagerAdapter;
import com.gocorona.customViews.NonSwipeableViewPager;
import com.gocorona.model.dummy.QuestionData;
import com.gocorona.model.dummy.QuestionsDataResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import simplifii.framework.utility.JsonUtil;
import simplifii.framework.widgets.CustomFontTextView;

public class QuestionsMainFargment extends AppBaseFragment {
    private final int MIN_VIEW_PAGER_COUNT = 0;
    private NonSwipeableViewPager mVpFragContainer;
    private List<QuestionsViewPagerAdapter.FragmentModelHolder> mListFragmentHolder = new ArrayList<>();
    private List<QuestionData> questionsList = new ArrayList<>();
    private QuestionData mUserData = new QuestionData();
    private int mViewPagerCurrentItemIndex = MIN_VIEW_PAGER_COUNT;

    @Override
    public void initViews() {
        mVpFragContainer = (NonSwipeableViewPager) findView(R.id.vp_questionsFragContainer);
        setNavTextsDrawables();
        initQuestionsData();
        initViewPagerFragments();
        initViewPager();
        setOnClickListener(R.id.vw_next, R.id.vw_back);
    }

    private void initQuestionsData() {
        try {
            String s = readFromAssets(getActivity(), "questions.json");
            QuestionsDataResponse questionsDataResponse = (QuestionsDataResponse) JsonUtil.parseJson(s, QuestionsDataResponse.class);
            if (questionsDataResponse != null && questionsDataResponse.getData() != null) {
                questionsList.clear();
                questionsList.addAll((Collection<? extends QuestionData>) questionsDataResponse.getData().getQstionList());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void setNavTextsDrawables() {
        CustomFontTextView back, next;
        back = (CustomFontTextView) findView(R.id.vw_back);
        next = (CustomFontTextView)  findView(R.id.vw_next);
        if (back != null)
            addVectorDrawableOnTextView(back, R.drawable.ic_backward_arrow, 0, 0, 0);
        if (next != null)
            addVectorDrawableOnTextView(next, 0, 0, R.drawable.ic_forward_arrow, 0);
    }

    //Add fragments to list...
    private void initViewPagerFragments() {
        mListFragmentHolder.add(new QuestionsViewPagerAdapter.FragmentModelHolder(IntroOneFragment.newInstance(), ""));
        mListFragmentHolder.add(new QuestionsViewPagerAdapter.FragmentModelHolder(IntroTwoFragment.newInstance(), ""));
        mListFragmentHolder.add(new QuestionsViewPagerAdapter.FragmentModelHolder(QuestionFragment.newInstance(), ""));
    }

    //Set fragments to view pager....
    private void initViewPager() {
        QuestionsViewPagerAdapter pagerAdapter = new QuestionsViewPagerAdapter(getChildFragmentManager());
        for (QuestionsViewPagerAdapter.FragmentModelHolder holder : mListFragmentHolder) {
            holder.getFragment().setClickListenerCallback(this);
            pagerAdapter.addFragments(holder);
        }
        mVpFragContainer.setAdapter(pagerAdapter);
        mVpFragContainer.setOffscreenPageLimit(4);
    }

    private void swipeViewPager(boolean toNext) {
        if (toNext && mViewPagerCurrentItemIndex < mListFragmentHolder.size() - 1)
            mVpFragContainer.setCurrentItem(++mViewPagerCurrentItemIndex);
        else if (mViewPagerCurrentItemIndex > MIN_VIEW_PAGER_COUNT)
            mVpFragContainer.setCurrentItem(--mViewPagerCurrentItemIndex);
        //Show-hide change buttons...
        findView(R.id.vw_next).setVisibility(mViewPagerCurrentItemIndex == mListFragmentHolder.size() - 1 ? View.INVISIBLE : View.VISIBLE);
        findView(R.id.vw_back).setVisibility(mViewPagerCurrentItemIndex == 0 ? View.INVISIBLE : View.VISIBLE);
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.vw_next:
                onNextClicked();
                break;
            case R.id.vw_back:
                onBackClicked();
                break;
            case R.id.btn_submit:
                onSubmitButtonClicked();
                break;
            /*case R.id.tv_privacy_and_security:
            case R.id.tv_terms_and_conditions:
                ActivityContainer.startActivity(this,
                        AppConstants.FRAGMENT_TYPE.TERMS_AND_CONDITIONS, null);
                break;*/
        }
    }

    private void onNextClicked() {
        if (mListFragmentHolder.get(mViewPagerCurrentItemIndex).getFragment().isValid()) {
            mListFragmentHolder.get(mViewPagerCurrentItemIndex).getFragment().apply(mUserData);
            swipeViewPager(true);
        } else {
            mListFragmentHolder.get(mViewPagerCurrentItemIndex).getFragment().showInvalidFieldError();
        }
    }

    private void onBackClicked() {
        swipeViewPager(false);
    }

    private void onSubmitButtonClicked() {
        //Registration Fragment callback will get here....
        if (mListFragmentHolder.get(mViewPagerCurrentItemIndex).getFragment().isValid()) {
            mListFragmentHolder.get(mViewPagerCurrentItemIndex).getFragment().apply(mUserData);
//            onPostUserRegistration(mUserData);
        } else {
            mListFragmentHolder.get(mViewPagerCurrentItemIndex).getFragment().showInvalidFieldError();
        }
    }

    @Override
    public int getViewID() {
        return R.layout.fragment_questions_main;
    }
}
