package com.example.thirdpartydemo.download;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.example.thirdpartydemo.R;
import com.example.thirdpartydemo.download.db.DaoManagerHelper;
import com.example.thirdpartydemo.oppo.customview.DownloadProgressView;
import com.permissionx.guolindev.PermissionX;
import com.permissionx.guolindev.callback.RequestCallback;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class MainActivityDownload extends AppCompatActivity {

    public static ProgressBar mProgressBar;

    public static DownloadProgressView mDownload_progress;

    private static final int STATUS_DOWNLOAD = 1;
    private static final int STATUS_STOP = 2;

    private TextView tv_progress;

    public static int pb_count = 0;
    public static Handler handler;
    public static int pb_num = 0;
    public static long size = 0;
    public static final int CURRENT_PROGRESS = 1;
    public static final int MAX_PROGRESS = 2;
    private static final String mUrl = "http://a.dxiazaicc.com/apk/neihanduanzi_downcc.apk";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_download_greendao);
        mProgressBar = findViewById(R.id.progressbar);
        tv_progress = findViewById(R.id.tv_progress);

        mDownload_progress = findViewById(R.id.main_download_progress);
        //checkPermission();
        DownloadFacade.getFacade().init(MainActivityDownload.this);
        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case MAX_PROGRESS:
                        Log.e("TAG","MAX_PROGRESS = "+(int) msg.obj);
                        MainActivityDownload.mDownload_progress.setMaxProgress((int) msg.obj);
                        break;
                    case CURRENT_PROGRESS:
//                        Log.e("TAG", "pb_count = " + pb_count);
//                        Log.e("TAG", "size = " + size);
//                        tv_progress.setText("loading " + (long) mProgressBar.getProgress() * 100 / mProgressBar.getMax() + "%");
                        Log.e("TAG","CURRENT_PROGRESS = "+MainActivityDownload.pb_count);
                        MainActivityDownload.mDownload_progress.setProgress(MainActivityDownload.pb_count);
                        break;
                }
            }
        };
    }

    public void checkPermission() {
        PermissionX.init(MainActivityDownload.this)
                .permissions(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .request(new RequestCallback() {
                    @Override
                    public void onResult(boolean allGranted, @NonNull List<String> grantedList, @NonNull List<String> deniedList) {
                        if (allGranted) {
                            Log.e("TAG","all permissions are granted.");
                        }
                    }
                });
    }

    private void installFile(File apkFile) {
        Log.e("TAG", "installFile: " + apkFile.getAbsolutePath());
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(this, this.getPackageName() + ".provider", apkFile);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
            Log.e("TAG", "Uri1: " + contentUri);
        } else {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = Uri.fromFile(apkFile);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
            Log.e("TAG", "Uri2: " + contentUri);
        }
        this.startActivity(intent);
    }

    public void stopDownload(View view) {
        Log.e("TAG", "stopDownload");
        DownloadDispatcher.getDispatcher().stopDownload(mUrl);
    }

    public void addDownloadTask(String url){

    }

    public void startDownload(View view) {
        Log.e("TAG", "startDownload");
        DownloadFacade.getFacade().startDownload(mUrl, new DownloadCallback() {
            @Override
            public void onFailure(IOException e) {
                Log.e("TAG", "MainActivityDownload onFailure.");
                e.printStackTrace();
            }

            @Override
            public void onSucceed(File file) {
                Log.e("TAG", "MainActivityDownload onSucceed.");

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mProgressBar.setProgress(100);
                        tv_progress.setText("loaded 100%");
                        MainActivityDownload.mDownload_progress.setProgress(100);
                    }
                });
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                installFile(file);
            }
        });
    }

    public void deleteApk(View view) {
        File file = FileManager.getManager().getFile("http://a.dxiazaicc.com/apk/neihanduanzi_downcc.apk");
        if (file.exists()) {
            boolean delete = file.delete();
            Log.e("TAG", "is file delete = " + delete);
        }
        DaoManagerHelper.getManager().deleteDownloadFiles();
        MainActivityDownload.mProgressBar.setProgress(0);
        MainActivityDownload.pb_count = 0;
        tv_progress.setText("0%");
        MainActivityDownload.mDownload_progress.setProgress(0);
    }

    public void installApk(View view) {
        File file = FileManager.getManager().getFile("http://a.dxiazaicc.com/apk/neihanduanzi_downcc.apk");
        installFile(file);
    }
}