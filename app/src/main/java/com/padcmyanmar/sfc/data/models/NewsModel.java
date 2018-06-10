package com.padcmyanmar.sfc.data.models;

import android.content.Context;

import com.padcmyanmar.sfc.data.db.AppDataBase;
import com.padcmyanmar.sfc.data.vo.NewsVO;
import com.padcmyanmar.sfc.events.RestApiEvents;
import com.padcmyanmar.sfc.network.MMNewsDataAgent;
import com.padcmyanmar.sfc.network.MMNewsDataAgentImpl;
import com.padcmyanmar.sfc.utils.AppConstants;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aung on 12/3/17.
 */

public class NewsModel {

    private static NewsModel objInstance;

    private List<NewsVO> mNews;
    private int mmNewsPageIndex = 1;

    private AppDataBase mAppDataBase;

    private NewsModel() {
        EventBus.getDefault().register(this);
        mNews = new ArrayList<>();
    }

    public static NewsModel getInstance() {
        if(objInstance == null) {
            objInstance = new NewsModel();
        }
        return objInstance;
    }

    public void initDatabase(Context context) {
        mAppDataBase = AppDataBase.getNewsDatabase(context);
    }

    public void startLoadingMMNews() {
        MMNewsDataAgentImpl.getInstance().loadMMNews(AppConstants.ACCESS_TOKEN, mmNewsPageIndex);
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onNewsDataLoaded(RestApiEvents.NewsDataLoadedEvent event) {
        mNews.addAll(event.getLoadNews());
        mmNewsPageIndex = event.getLoadedPageIndex() + 1;

        List<NewsVO> newsList = event.getLoadNews();

        for (NewsVO news : newsList){
            mAppDataBase.publicationDao().insertPublication(news.getPublication());

            mAppDataBase.newsDao().insertNews(news);

        }


    }
}
