package com.zawraapharma.ui.activity_home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.zawraapharma.R;
import com.zawraapharma.databinding.ActivityHomeBinding;
import com.zawraapharma.language.Language;
import com.zawraapharma.mvp.activity_home_mvp.ActivityHomePresenter;
import com.zawraapharma.mvp.activity_home_mvp.HomeActivityView;
import com.zawraapharma.ui.activity_calender.CalenderActivity;
import com.zawraapharma.ui.activity_dept_disclosure.DebtDisclosureActivity;
import com.zawraapharma.ui.activity_find_pharmacy.FindPharmacyActivity;
import com.zawraapharma.ui.activity_login.LoginActivity;
import com.zawraapharma.ui.activity_pay_pill.PayPillActivity;

import java.util.List;

import io.paperdb.Paper;

public class HomeActivity extends AppCompatActivity implements HomeActivityView {
    private ActivityHomeBinding binding;
    private FragmentManager fragmentManager;
    private ActivityHomePresenter presenter;

    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase,Paper.book().read("lang","ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        initView();
    }



    private void initView() {
        fragmentManager = getSupportFragmentManager();
        presenter = new ActivityHomePresenter(this, this);
        binding.llBillPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.pillPay();
            }
        });
        binding.llFindPharmacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.findPharmacy();
            }
        });
        binding.llDebtDisclosure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.debtDisclosure();
            }
        });
        binding.llCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.calender();
            }
        });

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
    public void onNavigateToLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();

    }

    @Override
    public void onNavigateToPillPayActivity() {
        Intent intent = new Intent(this, PayPillActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onNavigateToFindPharmacyActivity() {
        Intent intent = new Intent(this, FindPharmacyActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onNavigateToCalenderActivity() {
        Intent intent = new Intent(this, CalenderActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onNavigateToDebtDisclosureActivity() {
        Intent intent = new Intent(this, DebtDisclosureActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onFinished() {
        finish();
    }
}