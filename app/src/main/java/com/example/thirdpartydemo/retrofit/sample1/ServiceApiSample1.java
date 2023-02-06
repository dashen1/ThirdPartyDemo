package com.example.thirdpartydemo.retrofit.sample1;

public interface ServiceApiSample1 {

    @POST("login")
    Call<UserLoginResult> userLogin(
            @Query("username") String userName,
            @Query("password") String userPwd
    );
}
