package com.example.thirdpartydemo.download;

import android.text.TextUtils;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils {

    public static String md5Url(String url) {

        if (TextUtils.isEmpty(url)) {
            return url;
        }

        StringBuffer sb = new StringBuffer();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("md5");
            messageDigest.update(url.getBytes());
            byte[] cypher = messageDigest.digest();

            for (byte b : cypher) {
                //拿到加密后的数据还要转为16进制，不足补0
                String hexString = Integer.toHexString(b & 0xff);
                sb.append(hexString.length() == 1 ? "0" + hexString : hexString);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static void close(Closeable closeable) {
        if (closeable != null){
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
