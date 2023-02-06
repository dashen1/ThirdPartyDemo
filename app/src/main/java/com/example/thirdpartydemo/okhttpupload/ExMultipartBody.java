package com.example.thirdpartydemo.okhttpupload;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;

public class ExMultipartBody extends RequestBody {

    private RequestBody mRequestBody;
    private int mCountLength;
    private UploadProgressListener uploadProgressListener;

    public ExMultipartBody(RequestBody mRequestBody, UploadProgressListener uploadProgressListener) {
        this.mRequestBody = mRequestBody;
        this.uploadProgressListener = uploadProgressListener;
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

        Log.e("ATG","start to upload.");
        long contentLength = contentLength();

        //有一个代理 ForwardingSink
        ForwardingSink forwardingSink = new ForwardingSink(bufferedSink) {
            @Override
            public void write(@NonNull Buffer source, long byteCount) throws IOException {
                //每次都会
                mCountLength += byteCount;
                if(uploadProgressListener != null){
                    uploadProgressListener.onProgress(contentLength, mCountLength);
                }
                super.write(source, byteCount);
            }
        };

        BufferedSink buffer = Okio.buffer(forwardingSink);

        mRequestBody.writeTo(buffer);
        buffer.flush();
    }


}
