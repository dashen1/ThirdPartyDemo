package com.example.thirdpartydemo.download.db;

import android.database.sqlite.SQLiteDatabase;

public class DaoSupportFactory {

    private static DaoSupportFactory mFactory;

    //持有外部数据库的引用
    private SQLiteDatabase mSqLiteDatabase;

    public DaoSupportFactory() {
    }
}
