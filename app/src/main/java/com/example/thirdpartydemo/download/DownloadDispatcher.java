package com.example.thirdpartydemo.download;

import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class DownloadDispatcher {

    private static final DownloadDispatcher sDispatcher = new DownloadDispatcher();

    private final Deque<DownloadTask> readyTasks = new ArrayDeque<>();

    private final Deque<DownloadTask> runningTasks = new ArrayDeque<>();

    private final Deque<DownloadTask> stoppedTasks = new ArrayDeque<>();


    public static DownloadDispatcher getDispatcher() {
        return sDispatcher;
    }

    public void startDownload() {
        if(readyTasks.size() >0){
            for (DownloadTask readyTask : readyTasks) {
                readyTask.init();
                runningTasks.add(readyTask);
            }
        }

//        Call call = OkHttpManager.getInstance().asyncCall(url);
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(@NonNull Call call, @NonNull IOException e) {
//                callback.onFailure(e);
//            }
//
//            @Override
//            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
//                //获取文件大小
//                long contentLength = response.body().contentLength();
//                if (contentLength <= -1) {
//                    //没有获取到文件大小 和后台协商
//                    //这种情况只能采用单线程下载
//                    return;
//                }
//                MainActivityDownload.mProgressBar.setMax((int) contentLength);
//                Message msg = Message.obtain();
//                msg.obj = (int) contentLength;
//                msg.what = MainActivityDownload.MAX_PROGRESS;
//                MainActivityDownload.handler.sendMessage(msg);
//                //计算每个线程负责下载多少
//                if (runningTasks.size() == 1 && downloadTask!=null){
//                    Log.e("TAG", "old DownloadTask");
//                    runningTasks.remove(downloadTask);
//                }
//                downloadTask = new DownloadTask(url, contentLength, callback);
//                downloadTask.init();
//                runningTasks.add(downloadTask);
//            }
//        });
    }

    public void recycleTask(DownloadTask downloadCallback) {
        runningTasks.remove(downloadCallback);
        //参考Okhttp Dispatcher源码
    }

    public void stopDownload(String url) {
        //这个停止的是不是正在运行的
        //假如说我准备了四个线程去下载，但正在下载的线程只有两个，那么我只能停止正在运行的两个线程即可
        if (runningTasks.size() > 0){
            for (DownloadTask runningTask : runningTasks) {
                Log.e("TAG", "downloadTask.stop()");
                if(runningTask.getUrl().endsWith(url)){
                    Log.e("TAG", "downloadTask.stop()");
                    runningTask.stop();
                }
            }
        }
    }

    public void addDownloadTasks(String url, DownloadCallback callback) {
        DownloadTask downloadTask = new DownloadTask(url, callback);
        readyTasks.add(downloadTask);
    }

    //单独开个线程去执行 所有的回调监听

    public long getProgress(String url) {
        long progress = 0;
        if (runningTasks.size()==1){
            progress = runningTasks.getFirst().getProgress();
        }
        return progress;
    }

    public long getContentLength() {
        long contentLength = 0;
        if (runningTasks.size()==1){
            contentLength = runningTasks.getFirst().getContentLength();
        }
        return contentLength;
    }


}
