package com.gocorona.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.recyclerview.widget.RecyclerView;

import com.gocorona.holder.BaseHolder;
import com.gocorona.model.BaseAdapterModel;

import java.util.ArrayList;
import java.util.List;

import simplifii.framework.utility.AppConstants;
import simplifii.framework.utility.CollectionUtils;


/**
 * Created by nbansal2211 on 25/08/16.
 */
public class BaseRecycleAdapter<T extends BaseAdapterModel> extends RecyclerView.Adapter<BaseHolder> {
    protected List<T> list;
    protected Context context;
    protected LayoutInflater inflater;
    private RecyclerClickInterface clickInterface;

    public RecyclerClickInterface getClickInterface() {
        return clickInterface;
    }

    public void setClickInterface(RecyclerClickInterface clickInterface) {
        this.clickInterface = clickInterface;
    }

    public BaseRecycleAdapter(Context context, List<T> list) {
        if (this.list == null || CollectionUtils.isEmpty(this.list )){
            this.list = new ArrayList<>();
        }
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        switch (viewType) {
//            case AppConstants.VIEW_CODES.HOME_FEED_VIDEO:
//                itemView = inflater.inflate(R.layout.row_feed_video, parent, false);
//                HomeVideoFeedHolder videoFeedHolder = new HomeVideoFeedHolder(itemView);
//                videoFeedHolder.setClickListener(clickInterface);
//                return videoFeedHolder;
//
//            case AppConstants.VIEW_CODES.HOME_FEED_IMAGE:
//                itemView = inflater.inflate(R.layout.row_feed_image, parent, false);
//                HomeImageFeedHolder imageHolder = new HomeImageFeedHolder(itemView);
//                imageHolder.setClickListener(clickInterface);
//                return imageHolder;
//
//            case AppConstants.VIEW_CODES.HOME_FEED_YOUTUBE:
//                itemView = inflater.inflate(R.layout.row_feed_youtube, parent, false);
//                HomeYoutubeFeedHolder youtubeVideoFeedHolder = new HomeYoutubeFeedHolder(itemView);
//                youtubeVideoFeedHolder.setClickListener(clickInterface);
//                return youtubeVideoFeedHolder;
//
//            case AppConstants.VIEW_CODES.ISSUES_FEED_VIDEO:
//                itemView = inflater.inflate(R.layout.row_feed_image, parent, false);
//                IssuesYoutubeVideoFeedHolder issuesYoutubeVideoFeedHolder = new IssuesYoutubeVideoFeedHolder(itemView);
//                issuesYoutubeVideoFeedHolder.setClickListener(clickInterface);
//                return issuesYoutubeVideoFeedHolder;
//
//            case AppConstants.VIEW_CODES.ISSUES_FEED_COLLAGE_IMAGE:
//                itemView = inflater.inflate(R.layout.row_collage_view, parent, false);
//                IssuesCollageImageFeedHolder issuesCollageImageFeedHolder = new IssuesCollageImageFeedHolder(itemView);
//                issuesCollageImageFeedHolder.setClickListener(clickInterface);
//                return issuesCollageImageFeedHolder;
//
//            case AppConstants.VIEW_CODES.ISSUES_FEED_IMAGE:
//                itemView = inflater.inflate(R.layout.row_issues_feed_image, parent, false);
//                IssuesImageFeedHolder issuesImageFeedHolder = new IssuesImageFeedHolder(itemView);
//                issuesImageFeedHolder.setClickListener(clickInterface);
//                return issuesImageFeedHolder;
//
//            case AppConstants.VIEW_CODES.ISSUES_FEED_YOUTUBE:
//                itemView = inflater.inflate(R.layout.row_feed_youtube, parent, false);
//                IssuesYoutubeVideoFeedHolder issuesYoutubeFeedHolder = new IssuesYoutubeVideoFeedHolder(itemView);
//                issuesYoutubeFeedHolder.setClickListener(clickInterface);
//                return issuesYoutubeFeedHolder;
//
//            case AppConstants.VIEW_CODES.GALLERY_FEED_IMAGE:
//                itemView = inflater.inflate(R.layout.row_feed_gallery, parent, false);
//                GalleryFeedHolder galleryFeedHolder = new GalleryFeedHolder(itemView);
//                galleryFeedHolder.setClickListener(clickInterface);
//                return galleryFeedHolder;
//
//            case AppConstants.VIEW_CODES.VIDEOS:
//                itemView = inflater.inflate(R.layout.row_feed_video_light, parent, false);
//                VideoFeedHolder videoFeedHolderNormal = new VideoFeedHolder(itemView);
//                videoFeedHolderNormal.setClickListener(clickInterface);
//                return videoFeedHolderNormal;
//
//            case AppConstants.VIEW_CODES.EVENT_FEED:
//                itemView = inflater.inflate(R.layout.row_feed_event, parent, false);
//                EventFeedHolder eventFeedHolder = new EventFeedHolder(itemView);
//                eventFeedHolder.setClickListener(clickInterface);
//                return eventFeedHolder;
//
//            case AppConstants.VIEW_CODES.LANGUAGE_DATA:
//                itemView = inflater.inflate(R.layout.row_reg_languages, parent, false);
//                LanguagesDataHolder languagesDataHolder = new LanguagesDataHolder(itemView);
//                languagesDataHolder.setClickListener(clickInterface);
//                return languagesDataHolder;
//
//            case AppConstants.VIEW_CODES.STATE_IN:
//            case AppConstants.VIEW_CODES.DISTRICT:
//            case AppConstants.VIEW_CODES.CITY:
//            case AppConstants.VIEW_CODES.Constituency:
//                itemView = inflater.inflate(R.layout.row_common, parent, false);
//                CommonNameHolder countryIsdHolder3 = new CommonNameHolder(itemView);
//                countryIsdHolder3.setClickListener(clickInterface);
//                return countryIsdHolder3;
//
//            case AppConstants.VIEW_CODES.COUNTRY_CODE:
//                itemView = inflater.inflate(R.layout.row_common, parent, false);
//                CountryCodeHolder countryCodeHolder = new CountryCodeHolder(itemView);
//                countryCodeHolder.setClickListener(clickInterface);
//                return countryCodeHolder;
        }
        throw new IllegalArgumentException("Invalid View Type");
    }

    @Override
    public void onBindViewHolder(BaseHolder holder, int position) {
        holder.onBind(position, this.list.get(position));
    }


    @Override
    public int getItemViewType(int position) {
        return list.get(position).getViewType();
    }

    @Override
    public int getItemCount() {
        if (CollectionUtils.isNotEmpty(list)){
            return list.size();
        }
        return 0;
    }

    public interface RecyclerClickInterface {
        void onItemClick(View itemView, int position, Object obj, int actionType);
    }

}