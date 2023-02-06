package com.example.thirdpartydemo.download;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.IOException;

final class FileManager {

    private File mRootDir;
    private Context mContext;

    private static final FileManager mFILE_MANAGER = new FileManager();

    public static FileManager getManager() {
        return mFILE_MANAGER;
    }

    public void init(Context context) {
        //这样就不会有Context 内存泄露了
        this.mContext = context.getApplicationContext();
    }

    public void rootDir(File file) {
        if (!file.exists()) {
            file.mkdirs();
        }
        if (!file.exists() && file.isDirectory()) {
            mRootDir = file;
        }
    }

    public File getFile(String url) {
        String fileName = Utils.md5Url(url);
        if (mRootDir == null){
            mRootDir = mContext.getExternalCacheDir();
        }
        File dir = new File(mRootDir,"download/apk");
        if (!dir.exists()){
            Boolean flag= dir.mkdirs();
            Log.e("TAG","create dir : "+flag);
        }
        File file = new File(dir, fileName);
        return file;
    }
}
