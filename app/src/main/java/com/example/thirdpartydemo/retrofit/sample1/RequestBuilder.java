package com.example.thirdpartydemo.retrofit.sample1;

import okhttp3.HttpUrl;
import okhttp3.Request;

public class RequestBuilder {

    ParameterHandler<Object>[] parameterHandler;
    Object[] args;
    HttpUrl.Builder httpUrl;

    public RequestBuilder(String baseUrl, String relativeUrl, String httpMethod, ParameterHandler<?>[] parameterHandler, Object[] args) {
        this.parameterHandler = (ParameterHandler<Object>[]) parameterHandler;
        this.args = args;
        httpUrl = HttpUrl.parse(baseUrl+relativeUrl).newBuilder();
    }

    public Request build() {

        int count = args.length;
        for (int i = 0; i < count; i++) {
            //userName = coder
            parameterHandler[i].apply(this, args[i]);
        }

        Request request = new Request.Builder().url(httpUrl.build()).build();
        return request;
    }

    public void addQueryName(String key, String value) {
        //userName = coder&password = coder123
        httpUrl.addQueryParameter(key,value).build();
    }

}
