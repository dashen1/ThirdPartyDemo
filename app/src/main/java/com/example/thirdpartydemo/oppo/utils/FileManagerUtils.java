package com.example.thirdpartydemo.oppo.utils;

public class FileManagerUtils {

    public static String getFileName(String path){
        int index = path.lastIndexOf("/") + 1;
        return path.substring(index);
    }

    public static int computerSizeOfApk(long apkSize){
        return Math.round((apkSize/1024f)/1024);
    }
}
