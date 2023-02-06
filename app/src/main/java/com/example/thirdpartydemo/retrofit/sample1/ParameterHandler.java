package com.example.thirdpartydemo.retrofit.sample1;

public interface ParameterHandler<T> {
    void apply(RequestBuilder requestBuilder, T value);
    //多种策略，Query,Part,QueryMap，Field
    class Query<T> implements ParameterHandler<T>{
        //保存的是参数的 key = userName
        private String key;

        public Query(String key) {
            this.key = key;
        }

        @Override
        public void apply(RequestBuilder requestBuilder, T value) {
            //添加到request中
            requestBuilder.addQueryName(key,value.toString());
        }
    }

}
