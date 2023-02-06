package com.example.database;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.database.entity.DownloadFile;
import com.example.database.greendao.DaoSession;
import com.example.database.greendao.DownloadFileDao;

import org.greenrobot.greendao.query.Query;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DownloadFileDao downloadFileDao;
    private Query<DownloadFile> downloadFileDaoQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DaoSession daoSession = ((App) getApplication()).getDaoSession();
        downloadFileDao = daoSession.getDownloadFileDao();

        // query all downloadFile
        downloadFileDaoQuery = downloadFileDao.queryBuilder().build();

        printLog();

    }

    public void printLog(){
        List<DownloadFile> downloadFiles = downloadFileDaoQuery.list();
        for (DownloadFile downloadFile : downloadFiles) {
            Log.e("TAG","id : "
                    +downloadFile.getId() + " "
                    +"start : " +downloadFile.getStart()+" "
                    + "end : "+downloadFile.getEnd()+" "+" process : "+downloadFile.getProcess());
        }
    }

}