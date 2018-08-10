package com.interview.ammaryali.pheramor_android_developer_position_assessment.services;

import com.interview.ammaryali.pheramor_android_developer_position_assessment.model.UserVO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserApi {

    String BASE_URL = "https://external.dev.pheramor.com/";

    @POST("post")
    Call<POST> createUser(@Body UserVO user);
}
