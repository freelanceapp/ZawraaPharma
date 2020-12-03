package com.zawraapharma.mvp.activity_debt_disclosure_mvp;

import android.content.Context;
import com.zawraapharma.models.UserModel;
import com.zawraapharma.mvp.activity_pay_pill_mvp.PayPillActivityView;
import com.zawraapharma.preferences.Preferences;


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
