package com.zawraapharma.models;

import android.content.Context;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;


import com.zawraapharma.BR;
import com.zawraapharma.R;

import java.io.Serializable;

public class LoginModel extends BaseObservable implements Serializable {
    private String secret_code;
    public ObservableField<String> error_secret_code = new ObservableField<>();

    public LoginModel() {
        secret_code ="";
    }

    public boolean isDataValid(Context context){
        if (!secret_code.isEmpty()){
            error_secret_code.set(null);
            return true;
        }else {
            error_secret_code.set(context.getString(R.string.field_req));
            return false;
        }
    }

    @Bindable
    public String getSecret_code() {
        return secret_code;
    }

    public void setSecret_code(String secret_code) {
        this.secret_code = secret_code;
        notifyPropertyChanged(BR.secret_code);

    }
}
