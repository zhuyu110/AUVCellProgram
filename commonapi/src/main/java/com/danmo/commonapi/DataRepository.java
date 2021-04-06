package com.danmo.commonapi;

/**
 * 数据仓库的基类
 */
public class DataRepository {

    private static DataRepository sInstance;

    public AppDatabase mDatabase;//数据库数据

    public CommonApi commonApi;//网络接口


    public DataRepository(AppDatabase database, CommonApi mcommonApi ) {
        mDatabase = database;
        commonApi=mcommonApi;
    }



    public static DataRepository getInstance(AppDatabase database, CommonApi mcommonApi ) {
        if (sInstance == null) {
            synchronized (DataRepository.class) {
                if (sInstance == null) {
                    sInstance = new DataRepository(database,mcommonApi);
                }
            }
        }

        return sInstance;
    }

}
