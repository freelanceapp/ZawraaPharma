package com.zawraapharma.mvp.activity_pay_bill_mvp;

import com.zawraapharma.models.InvoiceModel;
import com.zawraapharma.models.PharmacyModel;

import java.util.List;

public interface PayBillActivityView {
    void onSuccess(List<InvoiceModel> data);
    void onFailed(String msg);
    void onProgressShow();
    void onProgressHide();

}
