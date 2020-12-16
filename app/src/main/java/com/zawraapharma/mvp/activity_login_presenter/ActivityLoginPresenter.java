package com.zawraapharma.mvp.activity_login_presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.preference.Preference;
import android.util.Log;

import com.zawraapharma.R;
import com.zawraapharma.models.LoginModel;
import com.zawraapharma.models.UserModel;
import com.zawraapharma.preferences.Preferences;
import com.zawraapharma.remote.Api;
import com.zawraapharma.share.Common;
import com.zawraapharma.tags.Tags;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ActivityLoginPresenter {
    private Context context;
    private ActivityLoginView view;
    private LoginModel model;
    private Preferences preference;

    public ActivityLoginPresenter(Context context, ActivityLoginView view) {
        this.context = context;
        this.view = view;
        preference = Preferences.getInstance();

    }

    public void checkData(LoginModel loginModel) {
        this.model = loginModel;
        if (model.isDataValid(context)) {
            login();
        }
    }

    private void login() {

        ProgressDialog dialog = Common.createProgressDialog(context, context.getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        Api.getService(Tags.base_url)
                .login(model.getAccess_code())
                .enqueue(new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                        dialog.dismiss();
                        if (response.isSuccessful() &&response.body()!=null&& response.body().getStatus()==200) {
                            preference.create_update_userdata(context,response.body());
                            view.onUserFound(response.body());
                        } else if (response.isSuccessful() &&response.body()!=null&&response.body().getStatus()==404) {
                            view.onUserNoFound();
                        } else {
                            dialog.dismiss();
                            try {
                                Log.e("error", response.code()+response.errorBody().string());
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
                    public void onFailure(Call<UserModel> call, Throwable t) {
                        try {
                            dialog.dismiss();
                            if (t.getMessage() != null) {
                                Log.e("msg_category_error", t.getMessage() + "__");

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
