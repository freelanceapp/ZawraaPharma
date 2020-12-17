package com.zawraapharma.mvp.activity_debt_disclosure_mvp;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.zawraapharma.R;
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






    public void backPress(){
        view.onFinished();
    }
}
