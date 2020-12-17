package com.zawraapharma.ui.activity_dept_disclosure;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.zawraapharma.R;
import com.zawraapharma.databinding.ActivityDebtDisclosureBinding;
import com.zawraapharma.language.Language;
import com.zawraapharma.models.AppointmentModel;
import com.zawraapharma.models.PharmacyModel;
import com.zawraapharma.mvp.activity_calender_mvp.ActivityCalenderPresenter;
import com.zawraapharma.mvp.activity_calender_mvp.CalenderActivityView;
import com.zawraapharma.mvp.activity_debt_disclosure_mvp.ActivityDebtDisclosurePresenter;
import com.zawraapharma.mvp.activity_debt_disclosure_mvp.DebtDisclosureActivityView;

import java.util.List;

import io.paperdb.Paper;

public class DebtDisclosureActivity extends AppCompatActivity implements DebtDisclosureActivityView {
    private ActivityDebtDisclosureBinding binding;
    private ActivityDebtDisclosurePresenter presenter;
    private String lang;

    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_debt_disclosure);
        initView();
    }


    private void initView() {
        Paper.init(this);
        lang = Paper.book().read("lang", "ar");
        presenter = new ActivityDebtDisclosurePresenter(this, this);
        binding.setLang(lang);
        binding.llBack.setOnClickListener(view -> finish());

    }


    @Override
    public void onBackPressed() {
        presenter.backPress();
    }


    @Override
    public void onFinished() {
        finish();
    }


}