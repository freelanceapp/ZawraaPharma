package com.zawraapharma.mvp.activity_pay_pill_mvp;

import android.content.Context;
import com.zawraapharma.models.UserModel;
import com.zawraapharma.preferences.Preferences;


public class ActivityPayPillPresenter {
    private Context context;
    private PayPillActivityView view;
    private Preferences preference;
    private UserModel userModel;

    public ActivityPayPillPresenter(Context context, PayPillActivityView view) {
        this.context = context;
        this.view = view;
        preference = Preferences.getInstance();
        userModel = preference.getUserData(context);

    }




    public void backPress(){
        view.onFinished();
    }
}
