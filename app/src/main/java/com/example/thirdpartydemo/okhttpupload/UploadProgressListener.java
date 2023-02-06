package com.example.thirdpartydemo.okhttpupload;

public interface UploadProgressListener {

    void onProgress(long total, long current);
}
