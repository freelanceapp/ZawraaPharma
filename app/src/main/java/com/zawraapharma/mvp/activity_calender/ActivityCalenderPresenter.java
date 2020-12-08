package com.zawraapharma.mvp.activity_calender;

import android.content.Context;
import com.zawraapharma.models.UserModel;
import com.zawraapharma.preferences.Preferences;


public class ActivityCalenderPresenter {
    private Context context;
    private CalenderActivityView view;
    private Preferences preference;
    private UserModel userModel;

    public ActivityCalenderPresenter(Context context, CalenderActivityView view) {
        this.context = context;
        this.view = view;
        preference = Preferences.getInstance();
        userModel = preference.getUserData(context);

    }




    public void backPress(){
        view.onFinished();
    }
}
