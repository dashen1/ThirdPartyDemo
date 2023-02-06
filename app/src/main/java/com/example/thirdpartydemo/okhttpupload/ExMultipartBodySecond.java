package com.example.thirdpartydemo.okhttpupload;


import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;

public class ExMultipartBodySecond extends RequestBody {

    private RequestBody mRequestBody;

    public ExMultipartBodySecond(RequestBody mRequestBody) {
        this.mRequestBody = mRequestBody;
    }


    @Nullable
    @Override
    public MediaType contentType() {
        return mRequestBody.contentType();
    }

    @Override
    public long contentLength() throws IOException {
        return mRequestBody.contentLength();
    }

    @Override
    public void writeTo(@NonNull BufferedSink bufferedSink) throws IOException {
        Log.e("TAG","监听");
        mRequestBody.writeTo(bufferedSink);
    }
}
