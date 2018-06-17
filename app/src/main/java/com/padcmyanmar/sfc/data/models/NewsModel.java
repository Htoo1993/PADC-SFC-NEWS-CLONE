package com.padcmyanmar.sfc.data.models;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.padcmyanmar.sfc.SFCNewsApp;
import com.padcmyanmar.sfc.data.db.AppDataBase;
import com.padcmyanmar.sfc.data.vo.NewsVO;
import com.padcmyanmar.sfc.events.RestApiEvents;
import com.padcmyanmar.sfc.network.MMNewsAPI;
import com.padcmyanmar.sfc.network.MMNewsDataAgent;
import com.padcmyanmar.sfc.network.MMNewsDataAgentImpl;
import com.padcmyanmar.sfc.network.reponses.GetNewsResponse;
import com.padcmyanmar.sfc.utils.AppConstants;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by aung on 12/3/17.
 */

public class NewsModel {

    private static NewsModel objInstance;

    private List<NewsVO> mNews;
    private int mmNewsPageIndex = 1;

    private AppDataBase mAppDataBase;

    private MMNewsAPI theAPI;

    public NewsModel() {
//        EventBus.getDefault().register(this);
        mNews = new ArrayList<>();
        initMMNewsApi();
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

    public MMNewsAPI getTheAPI() {
        return theAPI;
    }

    private void initMMNewsApi() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://padcmyanmar.com/padc-3/mm-news/apis/")
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();

        theAPI = retrofit.create(MMNewsAPI.class);
    }

    public void startLoadingMMNews(final PublishSubject<List<NewsVO>> newsList) {
//        MMNewsDataAgentImpl.getInstance().loadMMNews(AppConstants.ACCESS_TOKEN, mmNewsPageIndex);
        Observable<GetNewsResponse> newsListResponseObservable = theAPI.getNewsList(mmNewsPageIndex, AppConstants.ACCESS_TOKEN);
        newsListResponseObservable
                .subscribeOn(Schedulers.io()) //run value creation code on a specific thread (non-UI thread)
                .map(new Function<GetNewsResponse, List<NewsVO>>() {
                    @Override
                    public List<NewsVO> apply(GetNewsResponse getNewsResponse) throws Exception {
                        return getNewsResponse.getNewsList();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread()) //observe the emitted value of the Observable on an appropriate thread
                .subscribe(new Observer<List<NewsVO>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<NewsVO> news) {
                        newsList.onNext(news);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

   /* @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onNewsDataLoaded(RestApiEvents.NewsDataLoadedEvent event) {
        mNews.addAll(event.getLoadNews());
        mmNewsPageIndex = event.getLoadedPageIndex() + 1;

        List<NewsVO> newsList = event.getLoadNews();

        for (NewsVO news : newsList){
            mAppDataBase.publicationDao().insertPublication(news.getPublication());

            mAppDataBase.newsDao().insertNews(news);

        }

    }*/

    private Observable<GetNewsResponse> getNewsListResponseObservable() {
        return theAPI.getNewsList(mmNewsPageIndex, AppConstants.ACCESS_TOKEN);
    }
}
