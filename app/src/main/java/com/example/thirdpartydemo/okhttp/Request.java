package com.example.thirdpartydemo.okhttp;


public class Request {
    public static class Builder {
        String url;

        public Builder() {

        }

        public Builder Builder(String url) {
            this.url = url;
            return this;
        }

        public Builder build() {
            return null;
        }

        public Builder post(RequestBody body){
            return null;
        }
    }
}
