package com.padcmyanmar.sfc.data.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.padcmyanmar.sfc.data.vo.NewsVO;
import com.padcmyanmar.sfc.data.vo.PublicationVO;

@Database(entities = {
        NewsVO.class, PublicationVO.class
},version = 1,exportSchema = false)

public abstract class AppDataBase extends RoomDatabase{

    private static final String DB_NAME= "MMNEWS-Room.DB";

    private static AppDataBase INSTANCE;

    public abstract NewsDao newsDao();

    public abstract PublicationDao publicationDao();

    public static AppDataBase getNewsDatabase(Context context) {
        if (INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDataBase.class, DB_NAME)
                    .allowMainThreadQueries() //Remove this after testing. Access to DB should always be from background thread.
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance(){
        INSTANCE = null;
    }
}
