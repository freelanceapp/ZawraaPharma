package com.zawraapharma.services;


import com.zawraapharma.models.AppointmentDataModel;
import com.zawraapharma.models.CartModel;
import com.zawraapharma.models.InvoiceDataModel;
import com.zawraapharma.models.LogoutModel;
import com.zawraapharma.models.PharmacyDataModel;
import com.zawraapharma.models.ResponseData;
import com.zawraapharma.models.UserModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Service {

    @FormUrlEncoded
    @POST("api/login")
    Call<UserModel> login(@Field("access_code") String access_code);


    @FormUrlEncoded
    @POST("api/update-location")
    Call<UserModel> updateLocation(@Header("Authorization") String user_token,
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

    @GET("api/search-pharmacy")
    Call<PharmacyDataModel> search(@Query("search_name") String query

    );

    @GET("api/my-appointments")
    Call<AppointmentDataModel> getMyAppointments(@Header("Authorization") String token,
                                                 @Query("user_id") String user_id

    );

    @GET("api/search-appointment")
    Call<PharmacyDataModel> searchAppointments(@Header("Authorization") String token,
                                               @Query("user_id") String user_id,
                                               @Query("fired_at") String fired_at


    );


    @FormUrlEncoded
    @POST("api/create-appointment")
    Call<ResponseData> createAppointment(@Header("Authorization") String token,
                                         @Field("user_id") String user_id,
                                         @Field("client_id") String client_id,
                                         @Field("fired_at") String fired_at

    );


    @GET("api/get-pharmacy-bills")
    Call<InvoiceDataModel> getPharmacyBill(@Header("Authorization") String token,
                                           @Query("user_id") int user_id,
                                           @Query("pharmacy_id") String pharmacy_id

    );

    @POST("api/pay-bills")
    Call<LogoutModel> sendData(@Header("Authorization") String token,
                               @Body CartModel cartModel);

}