package com.example.thirdpartydemo.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ServiceApi {

    @GET("LoginServlet")
    Call<UserLoginResult> userLogin(
            @Query("userName") String UserName,
            @Query("password") String userPwd);
}
