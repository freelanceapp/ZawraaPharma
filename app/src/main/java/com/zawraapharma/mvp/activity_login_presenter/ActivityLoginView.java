package com.zawraapharma.mvp.activity_login_presenter;

import com.zawraapharma.models.UserModel;

public interface ActivityLoginView {
    void onLoad();
    void onUserFound(UserModel userModel);
    void onUserNoFound();
    void onFailed();
    void onServer();
    void onFinishload();
    void onnotconnect(String msg);


}
