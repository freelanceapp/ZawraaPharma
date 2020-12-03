package com.zawraapharma.mvp.activity_Find_pharmacy_mvp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.MenuItem;

import androidx.fragment.app.FragmentManager;

import com.zawraapharma.R;
import com.zawraapharma.models.UserModel;
import com.zawraapharma.mvp.activity_home_mvp.HomeActivityView;
import com.zawraapharma.preferences.Preferences;


public class ActivityFindPharmacyPresenter {
    private Context context;
    private FragmentManager fragmentManager;
    private FindPharmacyActivityView view;
    private Preferences preference;
    private UserModel userModel;

    public ActivityFindPharmacyPresenter(Context context, FindPharmacyActivityView view) {
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.view = view;
        preference = Preferences.getInstance();
        userModel = preference.getUserData(context);

    }




    public void backPress(){

                view.onFinished();


    }
}
