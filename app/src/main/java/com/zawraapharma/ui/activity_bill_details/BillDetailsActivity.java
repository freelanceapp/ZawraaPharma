package com.zawraapharma.ui.activity_bill_details;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.facebook.react.bridge.Callback;
import com.zawraapharma.R;
import com.zawraapharma.adapters.InvoiceAdapter2;
import com.zawraapharma.databinding.ActivityBillDetailsBinding;
import com.zawraapharma.language.Language;
import com.zawraapharma.models.BillResponse;
import com.zawraapharma.models.LoginModel;
import com.zawraapharma.models.PrinterModel;
import com.zawraapharma.mvp.activity_login_presenter.ActivityLoginPresenter;
import com.zawraapharma.preferences.Preferences;
import com.zawraapharma.printer.Constants;
import com.zawraapharma.printer.PrinterManager;
import com.zawraapharma.ui.activity_printer_devices.PrinterDevicesActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.paperdb.Paper;

public class BillDetailsActivity extends AppCompatActivity {
    private ActivityBillDetailsBinding binding;
    private BillResponse.Data model;
    private String lang = "";
    private InvoiceAdapter2 adapter;
    private List<BillResponse.Data.Bill> billList;
    public static String EXTRA_DEVICE_ADDRESS = "device_address";
    private PrinterManager printerManager;
    private final String write_permission = Manifest.permission.WRITE_EXTERNAL_STORAGE;


    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_bill_details);
        getDataFromIntent();
        initView();

    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        model = (BillResponse.Data) intent.getSerializableExtra("data");
    }

    private void initView() {
        printerManager = new PrinterManager();

        billList = new ArrayList<>();
        Paper.init(this);
        lang = Paper.book().read("lang", "ar");
        binding.setLang(lang);
        binding.setModel(model);

        binding.recView.setLayoutManager(new LinearLayoutManager(this));
        billList.addAll(model.getBills());
        adapter = new InvoiceAdapter2(billList, this);
        binding.recView.setAdapter(adapter);
        binding.btnShow.setOnClickListener(view -> {
            /*Intent intent = new Intent(this, PrinterDevicesActivity.class);
            startActivityForResult(intent, 100);*/
        });

        binding.llBack.setOnClickListener(view -> finish());


    }

    private void checkWritePermission(){
        if (ContextCompat.checkSelfPermission(this,write_permission)== PackageManager.PERMISSION_GRANTED){
            Bitmap bitmap = getBitmapFromView(binding.llBill);
            /*printerManager.printImage(this, bitmap, args -> {

            });*/
        }else {
            String [] permission = {write_permission};
            ActivityCompat.requestPermissions(this,permission,200);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            PrinterModel printerModel = (PrinterModel) data.getSerializableExtra("data");
            try {

                switch (requestCode) {
                    case PrinterManager.REQUEST_ENABLE_BT:
                        if (resultCode == RESULT_OK) {
                            Toast.makeText(this, Constants.bt_on, Toast.LENGTH_LONG).show();
                        }
                        break;
                    case PrinterManager.REQUEST_CONNECT_DEVICE:
                        if (resultCode == RESULT_OK) {
                            String address = printerModel.getAddress();
                            printerManager.connectDevice(address);
                            checkWritePermission();

                        }
                        break;
                }
            } catch (Exception ex) {
                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }

        } else {
            try {

                switch (requestCode) {
                    case PrinterManager.REQUEST_ENABLE_BT:
                        if (resultCode == RESULT_OK) {
                            Toast.makeText(this, Constants.bt_on, Toast.LENGTH_LONG).show();
                        }
                        break;
                    case PrinterManager.REQUEST_CONNECT_DEVICE:
                        if (resultCode == RESULT_OK) {
                            String address = data.getExtras().getString(EXTRA_DEVICE_ADDRESS);
                            printerManager.connectDevice(address);
                        }
                        break;
                }
            } catch (Exception ex) {
                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==200&&grantResults.length>0){
            if (grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Bitmap bitmap = getBitmapFromView(binding.llBill);
                printerManager.printImage(this, bitmap, new Callback() {
                    @Override
                    public void invoke(Object... args) {

                    }
                });

            }else {
                Toast.makeText(this, "Access storage in your device denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static Bitmap getBitmapFromView(View view) {
        long now = System.currentTimeMillis();
        Bitmap returnBitmap = null;

        try {

            String mPath = Environment.getExternalStorageDirectory().toString() + "/" + now + ".jpg";

            view.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
            view.setDrawingCacheEnabled(false);

            File imageFile = new File(mPath);

            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();
            if (imageFile.exists()) {
                returnBitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
            }

        } catch (Throwable e) {
            e.printStackTrace();
        }
        return returnBitmap;
    }

}