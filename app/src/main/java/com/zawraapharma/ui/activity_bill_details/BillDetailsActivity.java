package com.zawraapharma.ui.activity_bill_details;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;

import com.zawraapharma.R;
import com.zawraapharma.databinding.ActivityBillDetailsBinding;
import com.zawraapharma.language.Language;
import com.zawraapharma.models.BillResponse;
import com.zawraapharma.models.LoginModel;
import com.zawraapharma.mvp.activity_login_presenter.ActivityLoginPresenter;
import com.zawraapharma.preferences.Preferences;

import io.paperdb.Paper;

public class BillDetailsActivity extends AppCompatActivity {
    private ActivityBillDetailsBinding binding;
    private BillResponse.Data model;
    private String lang="";

    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase,Paper.book().read("lang","ar")));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_bill_details);
        getDataFromIntent();
        initView();

    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        model = (BillResponse.Data) intent.getSerializableExtra("data");
    }

    private void initView() {
        Paper.init(this);
        lang = Paper.book().read("lang","ar");
        binding.setLang(lang);
        binding.setModel(model);

    }

}