package com.example.thirdpartydemo.retrofit.sample1;

import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.OkHttpClient;

public class Retrofit {
    String baseUrl;
    okhttp3.Call.Factory callFactory;
    private Map<Method, ServiceMethod> serviceMethodMap = new ConcurrentHashMap<>();

    public Retrofit(Builder builder) {
        this.baseUrl = builder.baseUrl;
        this.callFactory = builder.callFactory;
    }

    public <T> T create(Class<T> service){

        return (T)Proxy.newProxyInstance(service.getClassLoader(), new Class[]{service}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //每执行一个方法都会来到这里
                Log.e("TAG","methodName:"+method.getName());

                //判断是不是Object的方法
                if (method.getDeclaringClass() == Object.class){
                    return method.invoke(this, args);
                }
                // 1、解析参数注解
                ServiceMethod serviceMethod = loadServiceMethod(method);
                // 2、封装okhttp
                OkHttpCall okHttpCall = new OkHttpCall(serviceMethod, args);
                Log.e("TAG","methodName end");
                return okHttpCall;
            }

            private ServiceMethod loadServiceMethod(Method method) {
                //享元
                ServiceMethod serviceMethod = serviceMethodMap.get(method);
                if(serviceMethod == null){
                    serviceMethod = new ServiceMethod.Builder(Retrofit.this, method).build();
                    serviceMethodMap.put(method, serviceMethod);
                }
                return serviceMethod;
            }
        });
    }

    public static class Builder{
        String baseUrl;
        okhttp3.Call.Factory callFactory;

        public Builder baseUrl(String baseUrl){
            this.baseUrl = baseUrl;
            return this;
        }

        public Builder client(okhttp3.Call.Factory callFactory){
            this.callFactory = callFactory;
            return this;
        }

        public Retrofit build() {
            if(callFactory == null){
                callFactory = (okhttp3.Call.Factory) new OkHttpClient();
            }
            return new Retrofit(this);
        }
    }
}
