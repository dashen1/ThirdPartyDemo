package com.example.thirdpartydemo.download.db;

import android.content.Context;

import com.example.thirdpartydemo.App;
import com.example.thirdpartydemo.download.db.entity.DownloadFile;
import com.example.thirdpartydemo.download.db.greendao.DaoSession;
import com.example.thirdpartydemo.download.db.greendao.DownloadFileDao;

import org.greenrobot.greendao.query.Query;

import java.util.List;

public class DaoManagerHelper {
    private DownloadFileDao downloadFileDao;
    private Query<DownloadFile> downloadFileDaoQuery;

    private static final DaoManagerHelper sDaoManagerHelper = new DaoManagerHelper();

    public static DaoManagerHelper getManager(){
        return sDaoManagerHelper;
    }

    public void init(Context context){
        DaoSession daoSession = ((App) context.getApplicationContext()).getDaoSession();
        downloadFileDao = daoSession.getDownloadFileDao();
    }

    public List<DownloadFile> queryAllDownloadFiles(){
        downloadFileDaoQuery = downloadFileDao.queryBuilder().orderAsc(DownloadFileDao.Properties.Thread).build();
        List<DownloadFile> fileList = downloadFileDaoQuery.list();
        return fileList;
    }

    public void insertDownloadFiles(DownloadFile downloadFile){
        long insert = downloadFileDao.insert(downloadFile);
    }

    public void deleteDownloadFiles(){
        downloadFileDao.deleteAll();
    }

    public void updateDownloadFiles(DownloadFile downloadFile) {
        downloadFileDao.update(downloadFile);
    }
}
