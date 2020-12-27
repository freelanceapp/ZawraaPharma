package com.zawraapharma.ui.activity_dept_disclosure;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.zawraapharma.R;
import com.zawraapharma.adapters.DebtsAdapter;
import com.zawraapharma.databinding.ActivityDebtDisclosureBinding;
import com.zawraapharma.language.Language;
import com.zawraapharma.models.AppointmentModel;
import com.zawraapharma.models.DebtsModel;
import com.zawraapharma.models.PharmacyModel;
import com.zawraapharma.mvp.activity_calender_mvp.ActivityCalenderPresenter;
import com.zawraapharma.mvp.activity_calender_mvp.CalenderActivityView;
import com.zawraapharma.mvp.activity_debt_disclosure_mvp.ActivityDebtDisclosurePresenter;
import com.zawraapharma.mvp.activity_debt_disclosure_mvp.DebtDisclosureActivityView;
import com.zawraapharma.share.Common;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class DebtDisclosureActivity extends AppCompatActivity implements DebtDisclosureActivityView {
    private ActivityDebtDisclosureBinding binding;
    private ActivityDebtDisclosurePresenter presenter;
    private String lang;
    private DebtsAdapter adapter;
    private List<DebtsModel> debtsModelList;

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
        debtsModelList = new ArrayList<>();
        Paper.init(this);
        lang = Paper.book().read("lang", "ar");
        presenter = new ActivityDebtDisclosurePresenter(this, this);
        binding.setLang(lang);
        binding.recView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DebtsAdapter(debtsModelList,this);
        binding.recView.setAdapter(adapter);
        binding.llBack.setOnClickListener(view -> presenter.backPress());


        binding.edtSearch.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_SEARCH) {
                String query = binding.edtSearch.getText().toString();
                if (!query.isEmpty()) {
                    Common.CloseKeyBoard(this,binding.edtSearch);
                    presenter.getDebts(query);
                }
            }
            return false;
        });
        binding.imgSearch.setOnClickListener(view -> {
            String query = binding.edtSearch.getText().toString();
            if (!query.isEmpty()) {
                Common.CloseKeyBoard(this,binding.edtSearch);
                presenter.getDebts(query);
            }
        });


    }


    @Override
    public void onBackPressed() {
        presenter.backPress();
    }


    @Override
    public void onSuccess(List<DebtsModel> data) {
        if (data.size()>0){
            debtsModelList.addAll(data);
            adapter.notifyDataSetChanged();
            binding.tvNoData.setVisibility(View.GONE);
        }else {
            binding.tvNoData.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public void onFailed(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProgressShow() {
        binding.tvNoData.setVisibility(View.GONE);
        binding.progBar.setVisibility(View.VISIBLE);
        debtsModelList.clear();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onProgressHide() {
        binding.progBar.setVisibility(View.GONE);


    }

    @Override
    public void onFinished() {
        finish();
    }


}