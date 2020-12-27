package com.zawraapharma.ui.activity_printer_devices;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import com.zawraapharma.R;
import com.zawraapharma.adapters.DebtsAdapter;
import com.zawraapharma.adapters.PrinterDevicesAdapter;
import com.zawraapharma.databinding.ActivityPrinterDevicesBinding;
import com.zawraapharma.language.Language;
import com.zawraapharma.models.DebtsModel;
import com.zawraapharma.models.PrinterModel;
import com.zawraapharma.mvp.activity_debt_disclosure_mvp.ActivityDebtDisclosurePresenter;
import com.zawraapharma.mvp.activity_printers_mvp.ActivityPrinterPresenter;
import com.zawraapharma.mvp.activity_printers_mvp.PrinterActivityView;
import com.zawraapharma.share.Common;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class PrinterDevicesActivity extends AppCompatActivity implements PrinterActivityView {

    private ActivityPrinterDevicesBinding binding;
    private String lang;
    private ActivityPrinterPresenter presenter;
    private PrinterDevicesAdapter adapter;
    private List<PrinterModel> printerModelList;

    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_printer_devices);
        initView();
    }


    private void initView() {
        printerModelList = new ArrayList<>();
        Paper.init(this);
        lang = Paper.book().read("lang", "ar");
        binding.setLang(lang);
        binding.recView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PrinterDevicesAdapter(printerModelList,this);
        binding.recView.setAdapter(adapter);
        presenter = new ActivityPrinterPresenter(this,this);
        presenter.checkBluetoothPermission();
        binding.llBack.setOnClickListener(view -> {finish();});
        binding.btnShow.setOnClickListener(view -> presenter.checkBluetoothPermission());

    }


    @Override
    public void onDevicesSuccess(List<PrinterModel> data) {
        if (data.size()>0){
            printerModelList.addAll(data);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onFailed(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProgressShow() {
        binding.progBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onProgressHide() {
        binding.progBar.setVisibility(View.GONE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==100&&grantResults.length>0){

            if (grantResults[0]== PackageManager.PERMISSION_GRANTED&&grantResults[1]==PackageManager.PERMISSION_GRANTED){
                presenter.checkBluetoothPermission();
            }else {
                Toast.makeText(this, "Access bluetooth denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void setItemData(PrinterModel model) {
        Intent intent = getIntent();
        intent.putExtra("data",model);
        setResult(RESULT_OK,intent);
        finish();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.unRegisterBroadcast();
    }
}