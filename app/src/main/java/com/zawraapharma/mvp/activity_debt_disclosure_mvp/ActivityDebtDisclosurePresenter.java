package com.zawraapharma.mvp.activity_debt_disclosure_mvp;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.zawraapharma.R;
import com.zawraapharma.models.CompanyProductDataModel;
import com.zawraapharma.models.DebtsDataModel;
import com.zawraapharma.models.PharmacyDataModel;
import com.zawraapharma.models.ResponseData;
import com.zawraapharma.models.UserModel;
import com.zawraapharma.preferences.Preferences;
import com.zawraapharma.remote.Api;
import com.zawraapharma.share.Common;
import com.zawraapharma.tags.Tags;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ActivityDebtDisclosurePresenter {
    private Context context;
    private DebtDisclosureActivityView view;
    private Preferences preference;
    private UserModel userModel;

    public ActivityDebtDisclosurePresenter(Context context, DebtDisclosureActivityView view) {
        this.context = context;
        this.view = view;
        preference = Preferences.getInstance();
        userModel = preference.getUserData(context);

    }


    public void getDebts(String bill_number)
    {
        if (userModel == null) {
            return;
        }
        view.onProgressShow();

        Api.getService(Tags.base_url)
                .getDebts(userModel.getData().getToken(),bill_number)
                .enqueue(new Callback<DebtsDataModel>() {
                    @Override
                    public void onResponse(Call<DebtsDataModel> call, Response<DebtsDataModel> response) {
                        view.onProgressHide();
                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().getStatus() == 200 && response.body().getData() != null) {
                                view.onSuccess(response.body().getData());

                            }


                        } else {
                            view.onProgressHide();
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
                    public void onFailure(Call<DebtsDataModel> call, Throwable t) {
                        try {
                            view.onProgressHide();


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


    public void backPress() {
        view.onFinished();
    }
}
