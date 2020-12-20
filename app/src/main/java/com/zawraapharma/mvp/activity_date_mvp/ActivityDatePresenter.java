package com.zawraapharma.mvp.activity_date_mvp;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.zawraapharma.R;
import com.zawraapharma.models.ResponseData;
import com.zawraapharma.models.UserModel;
import com.zawraapharma.preferences.Preferences;
import com.zawraapharma.remote.Api;
import com.zawraapharma.share.Common;
import com.zawraapharma.tags.Tags;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityDatePresenter implements DatePickerDialog.OnDateSetListener {
    private ActivityDateView view;
    private Context context;
    private DatePickerDialog datePickerDialog;
    private Preferences preferences;
    private UserModel userModel;

    public ActivityDatePresenter(ActivityDateView view, Context context) {
        this.view = view;
        this.context = context;
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(context);
        createDateDialog();
    }

    private void createDateDialog() {
        try {
            Calendar calendar = Calendar.getInstance(Locale.ENGLISH);

            datePickerDialog = DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.setOkText(context.getString(R.string.select));
            datePickerDialog.setCancelText(context.getString(R.string.cancel));
            datePickerDialog.setAccentColor(ContextCompat.getColor(context, R.color.colorPrimary));
            datePickerDialog.setOkColor(ContextCompat.getColor(context, R.color.colorPrimary));
            datePickerDialog.setCancelColor(ContextCompat.getColor(context, R.color.gray4));
            datePickerDialog.setLocale(Locale.ENGLISH);
            datePickerDialog.setVersion(DatePickerDialog.Version.VERSION_1);

        }catch (Exception e){}

    }

    public void showDateDialog(FragmentManager fragmentManager){
        try {
            datePickerDialog.show(fragmentManager,"");
        }catch (Exception e){}
    }
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            calendar.set(Calendar.MONTH, monthOfYear);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            String date = dateFormat.format(new Date(calendar.getTimeInMillis()));
            ActivityDatePresenter.this.view.onDateSelected(date);
        }catch (Exception e){}

    }


    public void createAppointment(String date,String client_id)
    {


        if (userModel==null){
            return;
        }
        ProgressDialog dialog = Common.createProgressDialog(context,context.getString(R.string.wait));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
        Api.getService(Tags.base_url)
                .createAppointment(userModel.getData().getToken(), String.valueOf(userModel.getData().getId()),client_id,date)
                .enqueue(new Callback<ResponseData>() {
                    @Override
                    public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                        dialog.dismiss();
                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().getStatus() == 200) {
                                view.onSuccess();

                            }else {
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
                    public void onFailure(Call<ResponseData> call, Throwable t) {
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
