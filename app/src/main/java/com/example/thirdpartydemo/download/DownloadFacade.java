package com.example.thirdpartydemo.download;

import android.content.Context;

import com.example.thirdpartydemo.download.db.DaoManagerHelper;

public class DownloadFacade {

    private static final DownloadFacade sFacade = new DownloadFacade();

    public DownloadFacade() {

    }

    public static DownloadFacade getFacade(){
        return sFacade;
    }

    public static void addDownloadTasks(String url, DownloadCallback callback){
        DownloadDispatcher.getDispatcher().addDownloadTasks(url,callback);
    }

    public void init(Context context){
        FileManager.getManager().init(context);
        DaoManagerHelper.getManager().init(context);
    }

    public void startDownload(String url, DownloadCallback downloadCallback){
        DownloadDispatcher.getDispatcher().startDownload();
    }

    public long getProgress(String url){
        return DownloadDispatcher.getDispatcher().getProgress(url);
    }

    public long getContentLength(){
        return DownloadDispatcher.getDispatcher().getContentLength();
    }
}
