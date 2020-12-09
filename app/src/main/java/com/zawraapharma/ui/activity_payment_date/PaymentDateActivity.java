package com.zawraapharma.ui.activity_payment_date;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.os.Bundle;

import com.zawraapharma.R;
import com.zawraapharma.databinding.ActivityPaymentDateBinding;
import com.zawraapharma.language.Language;
import com.zawraapharma.mvp.activity_date_mvp.ActivityDatePresenter;
import com.zawraapharma.mvp.activity_date_mvp.ActivityDateView;

import io.paperdb.Paper;

public class PaymentDateActivity extends AppCompatActivity implements ActivityDateView {
    private ActivityPaymentDateBinding binding;
    private ActivityDatePresenter presenter;
    private String lang;

    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase,Paper.book().read("lang","ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_payment_date);
        initView();
    }



    private void initView() {
        Paper.init(this);
        lang = Paper.book().read("lang","ar");
        binding.setLang(lang);
        presenter = new ActivityDatePresenter(this,this);
        binding.cardViewDate.setOnClickListener(view -> presenter.showDateDialog(getFragmentManager()));
        binding.imageBack.setOnClickListener(view -> finish());

    }

    @Override
    public void onDateSelected(String date) {
        binding.tvDate.setText(date);
    }
}