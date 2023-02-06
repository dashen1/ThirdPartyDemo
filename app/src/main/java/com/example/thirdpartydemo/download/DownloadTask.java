package com.example.thirdpartydemo.download;

import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.thirdpartydemo.download.db.DaoManagerHelper;
import com.example.thirdpartydemo.download.db.entity.DownloadFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class DownloadTask {

    public DownloadTask(String url, DownloadCallback callback) {
        this.mUrl = url;
        this.mDownloadCallback = callback;
    }

    public String getUrl() {
        return mUrl;
    }

    private String mUrl;
    private long mContentLength;
    private List<DownloadRunnable> mRunnables;

    private DownloadCallback mDownloadCallback;

    private volatile int mSuccessNumber;

    private ThreadPoolExecutor threadPoolExecutor;

    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int THREAD_SIZE = Math.max(2, Math.min(CPU_COUNT - 1, 2));

    public static int getThreadSize() {
        return THREAD_SIZE;
    }

    //使用线程池的原因
    //每次new okhttpclient的时候，都会调用DownloadDispatcher,每次都创建线程池
    public synchronized ThreadPoolExecutor threadPoolExecutor() {
        if (threadPoolExecutor == null) {
            threadPoolExecutor = new ThreadPoolExecutor(0, THREAD_SIZE, 30, TimeUnit.SECONDS,
                    new SynchronousQueue<Runnable>(), new ThreadFactory() {
                @Override
                public Thread newThread(Runnable r) {
                    Thread thread = new Thread(r, "DownloadTasks");
                    thread.setDaemon(false);
                    return thread;
                }
            });
        }
        return threadPoolExecutor;
    }

    public DownloadTask(String url, long contentLength, DownloadCallback downloadCallback) {
        this.mUrl = url;
        this.mContentLength = contentLength;
        mRunnables = new ArrayList<>();
        this.mDownloadCallback = downloadCallback;
    }

    public void init() {
        Log.e("TAG", "init()");
        List<DownloadFile> downloadFiles = DaoManagerHelper.getManager().queryAllDownloadFiles();
        Log.e("TAG", "threadSize = " + (mContentLength / THREAD_SIZE));
        if (downloadFiles != null && downloadFiles.size() != 0) {
            Log.e("TAG", "downloadFiles size() = " + downloadFiles.size());
            for (DownloadFile downloadFile : downloadFiles) {
                Log.e("TAG", "start = " + downloadFile.getStart() + " end = " + downloadFile.getEnd() + " downloadFile progress = " + downloadFile.getProcess());
            }
        }

        for (int i = 0; i < THREAD_SIZE; i++) {
            //每个线程分别要下载的大小
            long threadSize = mContentLength / THREAD_SIZE;

            //初始化的时候 要去读取数据库
            long start = i * threadSize;
            long end = (i + 1) * threadSize - 1;
            long progress = 0;
            //防止除不尽，将剩下的内容大小都分配给最后一个线程
            if (i == THREAD_SIZE - 1) {
                end = mContentLength - 1;
            }
            //第一次下载的话大小肯定为0
            //继续下载 假设保存到数据库中的文件下载进度的数量只有2个
            //暂停下载的时候 保存到数据库中的顺序不一定和此时的执行顺序相同
            if (downloadFiles != null && downloadFiles.size() != 0) {
                for (DownloadFile downloadFile : downloadFiles) {
                    if (downloadFile.getThread() == i) {
                        progress = downloadFile.getProcess();
                        DownloadRunnable downloadRunnable = new DownloadRunnable(mUrl, i, start, end, progress,downloadCallback);
                        threadPoolExecutor().execute(downloadRunnable);
                        mRunnables.add(downloadRunnable);
                    }
                }
            } else {
                DownloadRunnable downloadRunnable = new DownloadRunnable(mUrl, i, start, end, progress, downloadCallback);
                threadPoolExecutor().execute(downloadRunnable);
                mRunnables.add(downloadRunnable);
            }

        }
    }

    private final DownloadCallback downloadCallback = new DownloadCallback() {
        @Override
        public void onFailure(IOException e) {
            //一个apk下载里面有一个线程异常了，处理异常，其它线程停止
            mDownloadCallback.onFailure(e);
        }

        @Override
        public void onSucceed(File file) {
            //线程同步一下
            synchronized (DownloadTask.this) {
                mSuccessNumber += 1;

                if (mSuccessNumber == THREAD_SIZE) {
                    mDownloadCallback.onSucceed(file);
                    //下载完了记得要回收
                    DownloadDispatcher.getDispatcher().recycleTask(DownloadTask.this);
                    //清楚数据库这个文件下载存储
                    DaoManagerHelper.getManager().deleteDownloadFiles();
                }
            }
        }
    };

    public void startDownload(){
        Call call = OkHttpManager.getInstance().asyncCall(getUrl());
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                //获取文件大小
                long contentLength = response.body().contentLength();
                if (contentLength <= -1) {
                    //没有获取到文件大小 和后台协商
                    //这种情况只能采用单线程下载
                    return;
                }
                MainActivityDownload.mProgressBar.setMax((int) contentLength);
                Message msg = Message.obtain();
                msg.obj = (int) contentLength;
                msg.what = MainActivityDownload.MAX_PROGRESS;
                MainActivityDownload.handler.sendMessage(msg);
                //计算每个线程负责下载多少
                init();
            }
        });
    }

    public void stop() {
        for (DownloadRunnable mRunnable : mRunnables) {
            mRunnable.stop();
        }
    }

    public long getProgress() {
        long progress = 0;
        for (DownloadRunnable mRunnable : mRunnables) {
            progress += mRunnable.getProgress();
        }
        return progress;
    }

    public long getContentLength() {
        return mContentLength;
    }
}
