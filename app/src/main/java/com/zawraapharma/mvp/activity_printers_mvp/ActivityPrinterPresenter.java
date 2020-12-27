package com.zawraapharma.mvp.activity_printers_mvp;

import android.Manifest;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.ArrayAdapter;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.zawraapharma.R;
import com.zawraapharma.models.CompanyDataModel;
import com.zawraapharma.models.CompanyProductDataModel;
import com.zawraapharma.models.LogoutModel;
import com.zawraapharma.models.PrinterModel;
import com.zawraapharma.models.Product_Bill_Model;
import com.zawraapharma.models.RetrieveModel;
import com.zawraapharma.models.UserModel;
import com.zawraapharma.mvp.activity_retrieve_bill_mvp.RetrieveActivityView;
import com.zawraapharma.preferences.Preferences;
import com.zawraapharma.printer.BluetoothService;
import com.zawraapharma.remote.Api;
import com.zawraapharma.share.Common;
import com.zawraapharma.tags.Tags;
import com.zawraapharma.ui.activity_printer_devices.PrinterDevicesActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ActivityPrinterPresenter {
    private Context context;
    private PrinterActivityView view;
    private Preferences preference;
    private UserModel userModel;
    private final String bluetooth = Manifest.permission.BLUETOOTH;
    private final String bluetoothAdmin = Manifest.permission.BLUETOOTH_ADMIN;
    private PrinterDevicesActivity activity;
    public static String EXTRA_DEVICE_ADDRESS = "device_address";
    private BluetoothService mService = null;
    private List<PrinterModel> printerModelList;
    public ActivityPrinterPresenter(Context context, PrinterActivityView view) {
        this.context = context;
        this.view = view;
        printerModelList = new ArrayList<>();
        preference = Preferences.getInstance();
        userModel = preference.getUserData(context);
        activity = (PrinterDevicesActivity) context;

    }

    public void checkBluetoothPermission(){
        if (ContextCompat.checkSelfPermission(context,bluetooth)== PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(context,bluetoothAdmin)== PackageManager.PERMISSION_GRANTED
        ){
            getDevices();

        }else {
            String [] permissions = {bluetoothAdmin,bluetooth};
            ActivityCompat.requestPermissions(activity,permissions,100);
        }
    }
    private void getDevices(){
        printerModelList.clear();
        view.onDevicesSuccess(printerModelList);

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        activity.registerReceiver(mReceiver, filter);
        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        activity.registerReceiver(mReceiver, filter);

        mService = new BluetoothService(context, null);
        Set<BluetoothDevice> pairedDevices = mService.getPairedDev();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {

                PrinterModel printerModel = new PrinterModel(device.getName(),device.getAddress(),"");
                printerModelList.add(printerModel);
            }
        }

        if (mService.isDiscovering()) {
            mService.cancelDiscovery();
        }
        mService.startDiscovery();
    }



    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                    PrinterModel printerModel = new PrinterModel(device.getName(),device.getAddress(),"");
                    printerModelList.add(printerModel);
                }
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                view.onProgressHide();
                view.onDevicesSuccess(printerModelList);

            }
        }
    };


    public void unRegisterBroadcast(){
        if (mService != null) {
            mService.cancelDiscovery();
        }
        mService = null;
        activity.unregisterReceiver(mReceiver);
    }
}
