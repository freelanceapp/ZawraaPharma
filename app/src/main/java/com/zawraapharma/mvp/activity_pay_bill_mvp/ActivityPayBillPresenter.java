package com.zawraapharma.mvp.activity_pay_bill_mvp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.zawraapharma.R;
import com.zawraapharma.location_service.LocationService;
import com.zawraapharma.models.BillModel;
import com.zawraapharma.models.BillResponse;
import com.zawraapharma.models.CartModel;
import com.zawraapharma.models.InvoiceDataModel;
import com.zawraapharma.models.LocationModel;
import com.zawraapharma.models.LogoutModel;
import com.zawraapharma.models.PharmacyDataModel;
import com.zawraapharma.models.ResponseData;
import com.zawraapharma.models.UserModel;
import com.zawraapharma.mvp.activity_pay_pill_mvp.PayPillActivityView;
import com.zawraapharma.preferences.Preferences;
import com.zawraapharma.remote.Api;
import com.zawraapharma.share.Common;
import com.zawraapharma.tags.Tags;
import com.zawraapharma.ui.activity_pay_bill.PayBillActivity;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ActivityPayBillPresenter implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;

    private Context context;
    private PayBillActivityView view;
    private Preferences preference;
    private UserModel userModel;
    private PayBillActivity activity;
    private final String gps_perm = Manifest.permission.ACCESS_FINE_LOCATION;
    private final int loc_req = 1255;

    public ActivityPayBillPresenter(Context context, PayBillActivityView view) {
        this.context = context;
        this.view = view;
        activity = (PayBillActivity) context;
        preference = Preferences.getInstance();
        userModel = preference.getUserData(context);
        checkPermission();
    }

    public void getBill(String pharmacy_id) {
        if (userModel == null) {
            return;
        }
        view.onProgressShow();
        Api.getService(Tags.base_url)
                .getPharmacyBill(userModel.getData().getToken(), userModel.getData().getId(), pharmacy_id)
                .enqueue(new Callback<InvoiceDataModel>() {
                    @Override
                    public void onResponse(Call<InvoiceDataModel> call, Response<InvoiceDataModel> response) {
                        view.onProgressHide();
                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().getStatus() == 200 && response.body().getData() != null) {
                                view.onSuccess(response.body().getData());

                            }


                        } else {
                            //view.onProgressHide();
                            try {
                                Log.e("errorNotCode", response.code() + "__" + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            if (response.code() == 500) {
                                view.onFailed("Server Error");

                            } else {
                                view.onFailed(context.getString(R.string.failed));

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<InvoiceDataModel> call, Throwable t) {
                        try {
                            //view.onProgressHide();


                            if (t.getMessage() != null) {
                                Log.e("error_not_code", t.getMessage() + "__");

                                if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                    view.onFailed(context.getString(R.string.something));
                                } else {
                                    view.onFailed(context.getString(R.string.failed));

                                }
                            }
                        } catch (Exception e) {
                            Log.e("Error", e.getMessage() + "__");
                        }
                    }
                });
    }


    private void checkPermission() {
        if (ActivityCompat.checkSelfPermission(context, gps_perm) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{gps_perm}, loc_req);
        } else {
            initGoogleApiClient();
        }
    }

    public void initGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        googleApiClient.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        initLocationRequest();
    }

    private void initLocationRequest() {
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(60000);
        locationRequest.setFastestInterval(60000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest)
                .setAlwaysShow(false);
        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(result1 -> {

            Status status = result1.getStatus();
            switch (status.getStatusCode()) {
                case LocationSettingsStatusCodes.SUCCESS:
                    startLocationUpdate();
                    break;
                case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                    Log.e("not available", "not available");
                    break;
                case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                    try {
                        status.startResolutionForResult(activity, 1255);
                    } catch (Exception e) {
                    }
                    break;
            }
        });

    }

    @Override
    public void onConnectionSuspended(int i) {
        if (googleApiClient != null) {
            googleApiClient.connect();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @SuppressLint("MissingPermission")
    public void startLocationUpdate() {
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                onLocationChanged(locationResult.getLastLocation());
            }
        };
        LocationServices.getFusedLocationProviderClient(context)
                .requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
    }

    @Override
    public void onLocationChanged(Location location) {

        LocationModel locationModel = new LocationModel(location.getLatitude(), location.getLongitude());
        view.onLocationSuccess(locationModel);

    }


    public void stopLocationUpdate() {
        if (googleApiClient != null && locationCallback != null) {
            LocationServices.getFusedLocationProviderClient(context).removeLocationUpdates(locationCallback);
            googleApiClient.disconnect();
            googleApiClient = null;

        }
    }

    public void setUpData(String note, List<BillModel> paidBillList, String pharmacy_id, String total, String discount, String total_after_discount, LocationModel locationModel) {

        List<CartModel.BillData> billDataList = new ArrayList<>();
        for (BillModel billModel : paidBillList) {
            CartModel.BillData billData = new CartModel.BillData(billModel.getBill_id(), billModel.getPaid_amount());
            billDataList.add(billData);
        }
        CartModel cartModel = new CartModel(String.valueOf(userModel.getData().getId()), pharmacy_id, total, discount, total_after_discount, note, String.valueOf(locationModel.getLat()), String.valueOf(locationModel.getLng()), billDataList);

        sendCartData(cartModel);
    }

    private void sendCartData(CartModel cartModel) {

        if (userModel == null) {
            return;
        }
        ProgressDialog dialog = Common.createProgressDialog(context, context.getString(R.string.wait));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
        Api.getService(Tags.base_url)
                .sendData(userModel.getData().getToken(), cartModel)
                .enqueue(new Callback<BillResponse>() {
                    @Override
                    public void onResponse(Call<BillResponse> call, Response<BillResponse> response) {
                        dialog.dismiss();
                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().getStatus() == 200) {
                                view.onCartSendSuccess(response.body().getData());

                            } else {
                                view.onFailed(context.getString(R.string.failed));
                            }


                        } else {
                            dialog.dismiss();
                            try {
                                Log.e("errorNotCode", response.code() + "__" + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            if (response.code() == 500) {
                                view.onFailed("Server Error");

                            } else {
                                view.onFailed(context.getString(R.string.failed));

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<BillResponse> call, Throwable t) {
                        try {
                            dialog.dismiss();


                            if (t.getMessage() != null) {
                                Log.e("error_not_code", t.getMessage() + "__");

                                if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                    view.onFailed(context.getString(R.string.something));
                                } else {
                                    view.onFailed(context.getString(R.string.failed));

                                }
                            }
                        } catch (Exception e) {
                            Log.e("Error", e.getMessage() + "__");
                        }
                    }
                });

    }
}
