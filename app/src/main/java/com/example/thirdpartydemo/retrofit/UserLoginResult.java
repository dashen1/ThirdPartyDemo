package com.example.thirdpartydemo.retrofit;

public class UserLoginResult {

    public UserInfo data;

    public class UserInfo{
        public String userName;
        public String userPwd;

        @Override
        public String toString() {
            return "UserInfo{" +
                    "userName='" + userName + '\'' +
                    ", userPwd='" + userPwd + '\'' +
                    '}';
        }
    }
}
