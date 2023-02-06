package com.example.thirdpartydemo.oppo.entity;

public class DownloadApp {

    private String appName;
    private String appSize;
    private int appPicture;
    private String appUrl;

    public DownloadApp(String appName, String appSize, int appPicture, String appUrl) {
        this.appName = appName;
        this.appSize = appSize;
        this.appPicture = appPicture;
        this.appUrl = appUrl;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppSize() {
        return appSize;
    }

    public void setAppSize(String appSize) {
        this.appSize = appSize;
    }

    public int getAppPicture() {
        return appPicture;
    }

    public void setAppPicture(int appPicture) {
        this.appPicture = appPicture;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    @Override
    public String toString() {
        return "DownloadApp{" +
                "appName='" + appName + '\'' +
                ", appSize='" + appSize + '\'' +
                ", appPicture=" + appPicture +
                ", appUrl='" + appUrl + '\'' +
                '}';
    }
}
