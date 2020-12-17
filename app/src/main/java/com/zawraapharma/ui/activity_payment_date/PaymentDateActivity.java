package com.zawraapharma.ui.activity_payment_date;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.zawraapharma.R;
import com.zawraapharma.databinding.ActivityPaymentDateBinding;
import com.zawraapharma.language.Language;
import com.zawraapharma.models.PharmacyModel;
import com.zawraapharma.mvp.activity_date_mvp.ActivityDatePresenter;
import com.zawraapharma.mvp.activity_date_mvp.ActivityDateView;

import io.paperdb.Paper;

public class PaymentDateActivity extends AppCompatActivity implements ActivityDateView {
    private ActivityPaymentDateBinding binding;
    private ActivityDatePresenter presenter;
    private String lang;
    private PharmacyModel pharmacyModel;
    private String date="";


    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase,Paper.book().read("lang","ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_payment_date);
        getDataFromIntent();
        initView();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        pharmacyModel = (PharmacyModel) intent.getSerializableExtra("data");
    }


    private void initView() {
        Paper.init(this);
        lang = Paper.book().read("lang","ar");
        binding.setLang(lang);
        presenter = new ActivityDatePresenter(this,this);
        binding.cardViewDate.setOnClickListener(view -> presenter.showDateDialog(getFragmentManager()));
        binding.imageBack.setOnClickListener(view -> finish());
        binding.btnSave.setOnClickListener(view -> {
            presenter.createAppointment(date, String.valueOf(pharmacyModel.getId()));
        });
        binding.btnBack.setOnClickListener(view -> finish());

    }

    @Override
    public void onDateSelected(String date) {
        try {
            this.date = date;
            binding.llActions.setVisibility(View.VISIBLE);
            binding.tvDate.setText(date);
        }catch (Exception e){}

    }

    @Override
    public void onSuccess() {
        Toast.makeText(this, getString(R.string.suc), Toast.LENGTH_SHORT).show();
        finish();

    }

    @Override
    public void onFailed(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}