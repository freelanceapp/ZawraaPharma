package com.zawraapharma.services;


import com.zawraapharma.models.LogoutModel;
import com.zawraapharma.models.UserModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface Service {

    @FormUrlEncoded
    @POST("api/login")
    Call<UserModel> login(@Field("access_code") String access_code);


    @FormUrlEncoded
    @POST("api/login")
    Call<UserModel> updateLocation(@Header ("Authorization") String user_token,
                                   @Field("user_id") String user_id,
                                   @Field("latitude") double latitude,
                                   @Field("longitude") double longitude

    );

    @FormUrlEncoded
    @POST("api/update-firebase")
    Call<LogoutModel> updateFirebaseToken(@Header("Authorization") String token,
                                          @Field("user_id") int user_id,
                                          @Field("phone_token") String phone_token,
                                          @Field("software_type") String software_type

    );

    @FormUrlEncoded
    @POST("api/logout")
    Call<LogoutModel> logout(@Header("Authorization") String token,
                             @Field("user_id") int user_id,
                             @Field("phone_token") String phone_token,
                             @Field("software_type") String software_type

    );

}