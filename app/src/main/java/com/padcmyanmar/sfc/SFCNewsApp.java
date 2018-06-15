package com.padcmyanmar.sfc;

import android.app.Application;

import com.google.gson.Gson;
import com.padcmyanmar.sfc.data.models.NewsModel;
import com.padcmyanmar.sfc.network.MMNewsAPI;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by aung on 11/4/17.
 */

public class SFCNewsApp extends Application {

    public static final String LOG_TAG = "SFCNewsApp";

    private MMNewsAPI theAPI;

    public MMNewsAPI getTheAPI() {
        return theAPI;
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        NewsModel.getInstance().initDatabase(getApplicationContext());
//        NewsModel.getInstance().startLoadingMMNews();

        initMMNewsApi();

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
                .client(okHttpClient)
                .build();

        theAPI = retrofit.create(MMNewsAPI.class);
    }
}
