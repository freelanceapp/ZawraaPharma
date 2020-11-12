package com.zawraapharma.mvp.activity_home_mvp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.MenuItem;

import androidx.fragment.app.FragmentManager;

import com.zawraapharma.R;
import com.zawraapharma.models.UserModel;
import com.zawraapharma.preferences.Preferences;


public class ActivityHomePresenter {
    private Context context;
    private FragmentManager fragmentManager;
    private HomeActivityView view;

    private Preferences preference;
    private UserModel userModel;

    public ActivityHomePresenter(Context context, HomeActivityView view) {
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.view = view;
        preference = Preferences.getInstance();
        userModel = preference.getUserData(context);

    }




    public void backPress(){
            if (userModel==null){
                view.onNavigateToLoginActivity();
            }else {
                view.onFinished();
            }

    }
}
