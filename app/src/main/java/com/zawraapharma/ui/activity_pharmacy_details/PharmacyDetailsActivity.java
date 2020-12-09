package com.zawraapharma.ui.activity_pharmacy_details;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.zawraapharma.R;
import com.zawraapharma.databinding.ActivityDebtDisclosureBinding;
import com.zawraapharma.databinding.ActivityPharmacyDetailsBinding;
import com.zawraapharma.language.Language;
import com.zawraapharma.mvp.activity_calender.ActivityCalenderPresenter;
import com.zawraapharma.ui.activity_pay_bill.PayBillActivity;
import com.zawraapharma.ui.activity_payment_date.PaymentDateActivity;
import com.zawraapharma.ui.activity_retrieve_bill.RetrieveBillActivity;

import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;

public class PharmacyDetailsActivity extends AppCompatActivity implements OnMapReadyCallback {
    private ActivityPharmacyDetailsBinding binding;
    private String lang;
    private GoogleMap mMap;


    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase,Paper.book().read("lang","ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pharmacy_details);
        initView();
    }



    private void initView() {
        Paper.init(this);
        lang = Paper.book().read("lang","ar");
        binding.setLang(lang);

        binding.cardPayBill.setOnClickListener(view -> {
            Intent intent = new Intent(this, PayBillActivity.class);
            startActivity(intent);
        });

        binding.cardRetrieveInvoice.setOnClickListener(view -> {
            Intent intent = new Intent(this, RetrieveBillActivity.class);
            startActivity(intent);
        });

        binding.cardPaymentDate.setOnClickListener(view -> {
            Intent intent = new Intent(this, PaymentDateActivity.class);
            startActivity(intent);
        });

        binding.imageBack.setOnClickListener(view -> finish());
        updateUI();

    }


    private void updateUI() {

        SupportMapFragment fragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        fragment.getMapAsync(this);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        if (googleMap != null) {
            mMap = googleMap;
            mMap.setTrafficEnabled(false);
            mMap.setBuildingsEnabled(false);
            mMap.setIndoorEnabled(true);
            addMarker(new LatLng(0.0,0.0));

        }
    }

    private void addMarker(LatLng latLng) {
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)).position(latLng));

    }

}