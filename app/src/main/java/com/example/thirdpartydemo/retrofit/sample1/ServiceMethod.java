package com.example.thirdpartydemo.retrofit.sample1;


import android.util.Log;

import com.google.gson.Gson;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;

public class ServiceMethod {

    final Retrofit retrofit;
    final Method method;
    Annotation[] methodAnnotations;
    String httpMethod;
    String relativeUrl;
    final ParameterHandler<?>[] parameterHandler;

    public ServiceMethod(Builder builder) {
        this.retrofit = builder.retrofit;
        this.method = builder.method;
        this.methodAnnotations = builder.methodAnnotations;
        this.httpMethod = builder.httpMethod;
        this.relativeUrl = builder.relativeUrl;
        this.parameterHandler = builder.parameterHandler;
    }

    public okhttp3.Call createNewCall(Object[] args) {
        //还需要一个对象
        RequestBuilder requestBuilder = new RequestBuilder(retrofit.baseUrl,relativeUrl,httpMethod,parameterHandler,args);
        //添加参数


        return retrofit.callFactory.newCall(requestBuilder.build());
    }

    public <T> T parseBody(ResponseBody responseBody) {
        //获取解析类型 T 获取方法返回值的类型
        Type returnType = method.getGenericReturnType();//返回值对象的所有泛型
        Class<T> dataClass = (Class<T>) ((ParameterizedType) returnType).getActualTypeArguments()[0];
        Gson gson = new Gson();
        T body = null;
        try {
            body = gson.fromJson(responseBody.string(), dataClass);
        }catch (Exception e){
            e.printStackTrace();
        }
        return body;
    }

    public static class Builder {
        final Retrofit retrofit;
        final Method method;
        Annotation[] methodAnnotations;
        String httpMethod;
        String relativeUrl;
        Annotation[][] parameterAnnotations;
        final ParameterHandler<?>[] parameterHandler;

        public Builder(Retrofit retrofit, Method method) {
            this.retrofit = retrofit;
            this.method = method;
            methodAnnotations = method.getAnnotations();
            parameterAnnotations = method.getParameterAnnotations();
            parameterHandler = new ParameterHandler[parameterAnnotations.length];
        }

        public ServiceMethod build() {
            //解析，okhttp请求的时候 url = baseUrl+relativeUrl,method
            for (Annotation methodAnnotation : methodAnnotations) {
                //解析 POST GET
                parseAnnotationMethod(methodAnnotation);
            }
            //解析参数注解
            int count = parameterAnnotations.length;
            for (int i = 0; i < count; i++) {
                Annotation parameter = parameterAnnotations[i][0];
                //Query 等等
                Log.e("TAG", "parameter = " + parameter.annotationType().getName());
                //这里涉及到模板和策略
                if (parameter instanceof Query) {
                    parameterHandler[i] = new ParameterHandler.Query<>(((Query)parameter).value());
                }
            }
            return new ServiceMethod(this);
        }

        private void parseAnnotationMethod(Annotation methodAnnotation) {
            //value 请求方法
            if (methodAnnotation instanceof GET) {
                parseMethodAndPath("GET", ((GET) methodAnnotation).value());
            } else if (methodAnnotation instanceof POST) {
                parseMethodAndPath("POST", ((POST) methodAnnotation).value());
            }
            //还有其他各种注解
        }

        private void parseMethodAndPath(String method, String value) {
            this.httpMethod = method;
            this.relativeUrl = value;
        }
    }
}
