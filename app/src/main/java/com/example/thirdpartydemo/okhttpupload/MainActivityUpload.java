package com.example.thirdpartydemo.okhttpupload;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.permissionx.guolindev.PermissionX;
import com.permissionx.guolindev.callback.RequestCallback;

import java.io.File;
import java.io.IOException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class MainActivityUpload extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        PermissionX.init(MainActivityUpload.this)
                .permissions(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .request(new RequestCallback() {
                    @Override
                    public void onResult(boolean allGranted, @NonNull List<String> grantedList, @NonNull List<String> deniedList) {
                        if(allGranted){
                            Toast.makeText(MainActivityUpload.this, "All permissions are granted", Toast.LENGTH_SHORT).show();
                            uploadFile();
                        }
                    }
                });
    }

    public void uploadFile(){
        //okhttp 上传文件的用法
        String url = "http://api.yesapi.cn/api/App/CDN/UploadImg";
        File file = new File("/sdcard/two.jpg");
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
        builder.addFormDataPart("platform","android");
        builder.addFormDataPart("file",file.getName(),
                RequestBody.create(MediaType.parse(guessMimeType(file.getAbsolutePath())),file));
        //构建一个请求

        ExMultipartBody exMultipartBody = new ExMultipartBody(builder.build(), new UploadProgressListener() {
            @Override
            public void onProgress(long total, long current) {
                Log.e("TAG",total+ " : "+current);
            }
        });

        final Request request = new Request.Builder()
                .url(url)
                .post(exMultipartBody)
                .build();
        // new RealCall发起请求
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e("TAG","onFailure");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Log.e("TAG","onResponse");
            }
        });
    }

    private String guessMimeType(String filePath){
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String mimType = fileNameMap.getContentTypeFor(filePath);
        if(TextUtils.isEmpty(mimType)){
            return "application/octet-stream";
        }
        return mimType;
    }
}
