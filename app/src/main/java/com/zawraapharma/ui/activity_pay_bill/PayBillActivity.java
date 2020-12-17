package com.zawraapharma.ui.activity_pay_bill;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.zawraapharma.R;
import com.zawraapharma.adapters.InvoiceAdapter;
import com.zawraapharma.databinding.ActivityPayBillBinding;
import com.zawraapharma.databinding.ActivityPayPillBinding;
import com.zawraapharma.language.Language;
import com.zawraapharma.models.BillModel;
import com.zawraapharma.models.InvoiceModel;
import com.zawraapharma.models.PharmacyModel;
import com.zawraapharma.mvp.activity_pay_bill_mvp.ActivityPayBillPresenter;
import com.zawraapharma.mvp.activity_pay_bill_mvp.PayBillActivityView;
import com.zawraapharma.mvp.activity_pay_pill_mvp.ActivityPayPillPresenter;
import com.zawraapharma.share.Common;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class PayBillActivity extends AppCompatActivity implements PayBillActivityView {
    private ActivityPayBillBinding binding;
    private String lang;
    private PharmacyModel pharmacyModel;
    private List<InvoiceModel> invoiceModelList;
    private ActivityPayBillPresenter presenter;
    private InvoiceAdapter adapter;
    private List<BillModel> paidBillList;
    private double total = 0.0;
    private double discount = 0;

    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase,Paper.book().read("lang","ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pay_bill);
        getDataFromIntent();
        initView();
    }


    private void getDataFromIntent() {
        Intent intent = getIntent();
        pharmacyModel = (PharmacyModel) intent.getSerializableExtra("data");
    }

    private void initView() {
        paidBillList = new ArrayList<>();
        invoiceModelList = new ArrayList<>();
        Paper.init(this);
        lang = Paper.book().read("lang","ar");
        binding.setLang(lang);
        binding.setTitle(pharmacyModel.getTitle());
        binding.recView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new InvoiceAdapter(invoiceModelList,this);
        binding.recView.setAdapter(adapter);
        presenter = new ActivityPayBillPresenter(this,this);
        presenter.getBill(String.valueOf(pharmacyModel.getId()));

        binding.progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(this,R.color.colorPrimary), PorterDuff.Mode.SRC_IN);

        binding.imageBack.setOnClickListener(view -> finish());

        binding.edtDiscount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().isEmpty()){
                    discount =0.0;
                }else {
                    discount = Double.parseDouble(editable.toString());

                }
                calculateTotal();

            }
        });


    }


    @Override
    public void onSuccess(List<InvoiceModel> data) {
        if (data.size()>0){
            invoiceModelList.clear();
            invoiceModelList.addAll(data);
            adapter.notifyDataSetChanged();

        }
    }

    @Override
    public void onFailed(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProgressShow() {
        binding.flLoad.setVisibility(View.VISIBLE);
    }

    @Override
    public void onProgressHide() {
        binding.flLoad.setVisibility(View.GONE);

    }

    public void addUpdateItem(int adapterPosition, InvoiceModel model, String amount) {
        int pos = getBillPos(String.valueOf(model.getId()));
        if (pos==-1){
            if (!amount.equals("0")){
                BillModel billModel = new BillModel(String.valueOf(model.getId()),amount);
                paidBillList.add(billModel);
            }
        }else {
            if (!amount.equals("0")){
                BillModel billModel = paidBillList.get(pos);
                billModel.setPaid_amount(amount);
                paidBillList.set(pos,billModel);
            }else {
                paidBillList.remove(pos);
            }
        }

        calculateTotal();
    }

    private void calculateTotal() {
        double totalBill = 0.0;
        for (BillModel billModel:paidBillList){
            totalBill +=Double.parseDouble(billModel.getPaid_amount());

        }
        binding.tvTotal.setText(String.valueOf(totalBill));
        double totalAfterDiscount = totalBill-(totalBill*(discount/100));
        binding.tvFinalTotal.setText(String.valueOf(totalAfterDiscount));


    }

    private int getBillPos(String bill_id){
        int pos = -1;
        for (int index=0;index<paidBillList.size();index++){
            BillModel billModel = paidBillList.get(index);
            if (billModel.getBill_id().equals(bill_id)){
                pos = index;
                return pos;
            }
        }

        return pos;
    }

    public void createAlert() {
        Common.CreateDialogAlert(this,getString(R.string.paid_amount_grater));
    }
}