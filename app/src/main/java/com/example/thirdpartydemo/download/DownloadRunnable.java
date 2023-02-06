package com.example.thirdpartydemo.download;

import android.util.Log;

import com.example.thirdpartydemo.download.db.DaoManagerHelper;
import com.example.thirdpartydemo.download.db.entity.DownloadFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.List;

import okhttp3.Response;

public class DownloadRunnable implements Runnable{

    private static final int STATUS_DOWNLOAD = 1;
    private static final int STATUS_STOP = 2;
    private String mUrl;
    private int threadId;
    private long start;
    private long end;
    private DownloadCallback mDownloadCallback;

    private int mStatus = STATUS_DOWNLOAD;
    private long mProgress;
    private long currentThreadId;
    private boolean isDownloaded;

    public DownloadRunnable(String mUrl, int threadId, long start, long end, long progress, DownloadCallback downloadCallback) {
        this.mUrl = mUrl;
        this.threadId = threadId;
        this.start = start;
        this.end = end;
        this.mProgress = progress;
        this.mDownloadCallback = downloadCallback;
    }

    @Override
    public void run() {
        //只读写自己部分的内容
        RandomAccessFile accessFile = null;
        File file;
        InputStream inputStream = null;
        try {
            Response response = OkHttpManager.getInstance().syncResponse(mUrl, start, end, mProgress);
            Log.e("TAG",this.toString());
            inputStream = response.body().byteStream();
            file = FileManager.getManager().getFile(mUrl);
            accessFile = new RandomAccessFile(file, "rwd");
            //从这里开始
            Log.e("TAG","accessFile start = "+(start+mProgress));
            accessFile.seek(start+mProgress);
            int len = 0;
            byte[] buffer = new byte[10*1024];
            while ((len = inputStream.read(buffer)) != -1){
                if(mStatus == STATUS_STOP){
                    Log.e("TAG","stop download break.");
                    break;
                }
                //保存进度
                mProgress += len;
                accessFile.write(buffer, 0,len);
                setProgressBar(len);
                MainActivityDownload.handler.sendEmptyMessage(MainActivityDownload.CURRENT_PROGRESS);
            }
            accessFile.close();
            inputStream.close();
            if (mStatus == STATUS_DOWNLOAD){
                isDownloaded = true;
                mDownloadCallback.onSucceed(file);
            }
        } catch (IOException e) {
            Log.e("TAG","DownloadRunnable run fails.");
            mDownloadCallback.onFailure(e);
        }finally {
            Utils.close(inputStream);
            Utils.close(accessFile);
            //保存到数据库 怎么存？
            Log.e("TAG","stop download and save to database.");
            if (!isDownloaded)
            saveDownloadFileProgress(threadId,start,end,mProgress);
        }
    }

    public synchronized void setProgressBar(int len){
        MainActivityDownload.pb_count+=len;
        //MainActivityDownload.mProgressBar.setProgress(MainActivityDownload.pb_count);
    }

    private void saveDownloadFileProgress(int threadId, long start, long end, long mProgress) {
        List<DownloadFile> downloadFiles = DaoManagerHelper.getManager().queryAllDownloadFiles();
        boolean isThreadIdExist = false;
        DownloadFile newDownloadFile = null;
        if (downloadFiles != null){
            for (DownloadFile downloadFile : downloadFiles) {
                if(downloadFile.getThread() == threadId){
                    isThreadIdExist = true;
                    newDownloadFile = downloadFile;
                    break;
                }
            }
        }
        if (isThreadIdExist){
            newDownloadFile.setProcess(mProgress);
            DaoManagerHelper.getManager().updateDownloadFiles(newDownloadFile);
        } else {
            newDownloadFile = new DownloadFile();
            newDownloadFile.setThread(threadId);
            newDownloadFile.setStart(start);
            newDownloadFile.setEnd(end);
            newDownloadFile.setProcess(mProgress);
            DaoManagerHelper.getManager().insertDownloadFiles(newDownloadFile);
        }

        //List<DownloadFile> downloadFiles = DaoManagerHelper.getManager().queryAllDownloadFiles();
        //假如我用四个线程去下载一个apk文件，但是停止下载的时候只保存了其中两个线程的下载进度（假设只有两个运行线程，其它两个线程还没有运行）
        //此时的数据库只保存了其中两个下载线程的下载进度，这里判断下载的数量就不合适了
    }

    @Override
    public String toString() {
        return "DownloadRunnable{" +
                "mUrl='" + mUrl + '\'' +
                ", threadId=" + threadId +
                ", start=" + start +
                ", end=" + end +
                ", progress=" + mProgress +
                '}';
    }

    public void stop() {
        mStatus = STATUS_STOP;
    }

    public long getProgress(){
        return mProgress;
    }
}
