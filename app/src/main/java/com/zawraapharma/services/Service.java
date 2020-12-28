package com.zawraapharma.services;


import com.zawraapharma.models.AppointmentDataModel;
import com.zawraapharma.models.BillResponse;
import com.zawraapharma.models.CartModel;
import com.zawraapharma.models.CompanyDataModel;
import com.zawraapharma.models.CompanyProductDataModel;
import com.zawraapharma.models.DebtsDataModel;
import com.zawraapharma.models.InvoiceDataModel;
import com.zawraapharma.models.LogoutModel;
import com.zawraapharma.models.PharmacyDataModel;
import com.zawraapharma.models.ResponseData;
import com.zawraapharma.models.RetrieveModel;
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

    @GET("api/search-pharmacy-or-bill-code")
    Call<PharmacyDataModel> search_bill_number(@Header("Authorization") String token,
                                               @Query("search_key") String query

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
    Call<BillResponse> sendData(@Header("Authorization") String token,
                                @Body CartModel cartModel);

    @GET("api/company-item")
    Call<CompanyProductDataModel> getCompanyProduct(@Header("Authorization") String token,
                                                    @Query("company_id") int company_id

    );

    @GET("api/companies")
    Call<CompanyDataModel> getCompanies(@Header("Authorization") String token
    );

    @POST("api/back-bills")
    Call<LogoutModel> retrieveBill(@Header("Authorization") String token,
                                   @Body RetrieveModel retrieveModel);

    @GET("api/get-dept-bills")
    Call<DebtsDataModel> getDebts(@Header("Authorization") String token,
                                  @Query("search_key") String search_key
    );

}