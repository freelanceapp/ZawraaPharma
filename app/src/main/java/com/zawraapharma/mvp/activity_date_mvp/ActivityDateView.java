package com.zawraapharma.mvp.activity_date_mvp;

public interface ActivityDateView {
    void onDateSelected(String date);
    void onSuccess();
    void onFailed(String msg);
}
