package com.zawraapharma.ui.activity_dept_disclosure;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.zawraapharma.R;
import com.zawraapharma.databinding.ActivityDebtDisclosureBinding;
import com.zawraapharma.databinding.ActivityPayBillBinding;
import com.zawraapharma.language.Language;
import com.zawraapharma.mvp.activity_debt_disclosure_mvp.ActivityDebtDisclosurePresenter;
import com.zawraapharma.mvp.activity_debt_disclosure_mvp.DebtDisclosureActivityView;
import com.zawraapharma.mvp.activity_pay_pill_mvp.ActivityPayPillPresenter;
import com.zawraapharma.mvp.activity_pay_pill_mvp.PayPillActivityView;

import java.util.List;

import io.paperdb.Paper;

public class DebtDisclosureActivity extends AppCompatActivity implements DebtDisclosureActivityView {
    private ActivityDebtDisclosureBinding binding;
    private FragmentManager fragmentManager;
    private ActivityDebtDisclosurePresenter presenter;

    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase,Paper.book().read("lang","ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_debt_disclosure);
        initView();
    }



    private void initView() {
        fragmentManager = getSupportFragmentManager();
        presenter = new ActivityDebtDisclosurePresenter(this, this);


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<Fragment> fragments = fragmentManager.getFragments();
        for (Fragment fragment : fragments) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        List<Fragment> fragments = fragmentManager.getFragments();
        for (Fragment fragment : fragments) {
            fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
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