package com.example.thirdpartydemo.okttpdownload;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.example.thirdpartydemo.R;
import com.permissionx.guolindev.PermissionX;
import com.permissionx.guolindev.callback.RequestCallback;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivityOkHttpDownload extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_download);
//        File file = new File("/sdcard/内涵段子.apk");
//        downloadApk();
//        return;

            PermissionX.init(MainActivityOkHttpDownload.this)
                    .permissions(
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .request(new RequestCallback() {
                        @Override
                        public void onResult(boolean allGranted, @NonNull List<String> grantedList, @NonNull List<String> deniedList) {
                            if(allGranted){
                                Toast.makeText(MainActivityOkHttpDownload.this, "All permissions are granted.", Toast.LENGTH_SHORT).show();
                                downloadApk();
                            }
                        }
                    });

        //什么叫断点续传？逻辑是什么？
        //如果下载中断（网络中断、程序退出），下次可以接着上次的地方下载
        //多线程下载的逻辑是什么？多个线程读后台文件每个线程只读取单独的内容

        //文件更新，专门下载apk
        //文件更新，1、可以直接跳到浏览器更新 2、直接下载不断点（非多线程） 3、多线程 4、多线程+断点
        //断电续传需要服务器配合，思路跟断点下载类似
    }

    private void downloadApk(){
        OkHttpManager okHttpManager = new OkHttpManager();
        Call call = okHttpManager.asyncCall("http://a.dxiazaicc.com/apk/neihanduanzi_downcc.apk");
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e("TAG","onFailure");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Log.e("TAG","onResponse success");
                Log.e("TAG","contentLength:"+response.body().contentLength());
                int CPU_COUNT = Runtime.getRuntime().availableProcessors();
                int THREAD_SIZE = Math.max(2, Math.min(CPU_COUNT - 1, 4));
                Log.e("TAG","CPU_COUNT:"+CPU_COUNT);
                Log.e("TAG","THREAD_SIZE:"+THREAD_SIZE);
                Log.e("TAG","content size :"+response.body().contentLength() / THREAD_SIZE);
                InputStream is = response.body().byteStream();
                File dir = new File(getExternalCacheDir(),"download/apk");
                if (!dir.exists()){
                    dir.mkdir();
                }
                File apkFile = new File(dir,"内涵段子.apk");
                FileOutputStream fos = new FileOutputStream(apkFile);
                int len = 0;
                byte[] buffer = new byte[1024*10];
                while ((len = is.read(buffer)) !=-1){
                    fos.write(buffer,0,len);
                }
                is.close();
                fos.close();
                installFile(apkFile);
            }
        });
    }

    private void installFile(File apkFile) {
        Log.e("TAG","installFile: "+apkFile.getAbsolutePath());
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(this, this.getPackageName() + ".provider", apkFile);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
            Log.e("TAG","Uri1: "+contentUri);
        }else {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = Uri.fromFile(apkFile);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
            Log.e("TAG","Uri2: "+contentUri);
        }
        this.startActivity(intent);
    }

}
