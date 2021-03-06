package com.zawraapharma.mvp.activity_find_pharmacy_mvp;

import android.content.Context;
import android.util.Log;

import androidx.fragment.app.FragmentManager;

import com.zawraapharma.R;
import com.zawraapharma.models.PharmacyDataModel;
import com.zawraapharma.models.UserModel;
import com.zawraapharma.preferences.Preferences;
import com.zawraapharma.remote.Api;
import com.zawraapharma.tags.Tags;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ActivityFindPharmacyPresenter {
    private Context context;
    private FindPharmacyActivityView view;
    private Preferences preference;
    private UserModel userModel;

    public ActivityFindPharmacyPresenter(Context context, FindPharmacyActivityView view) {
        this.context = context;
        this.view = view;
        preference = Preferences.getInstance();
        userModel = preference.getUserData(context);

    }

    public void search(String query)
    {
        view.onProgressShow();

        Api.getService(Tags.base_url)
                .search(query)
                .enqueue(new Callback<PharmacyDataModel>() {
                    @Override
                    public void onResponse(Call<PharmacyDataModel> call, Response<PharmacyDataModel> response) {
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
                    public void onFailure(Call<PharmacyDataModel> call, Throwable t) {
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
