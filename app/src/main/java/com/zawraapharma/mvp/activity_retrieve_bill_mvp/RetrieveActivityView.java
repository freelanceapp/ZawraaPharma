package com.zawraapharma.mvp.activity_retrieve_bill_mvp;

import com.zawraapharma.models.CompanyModel;
import com.zawraapharma.models.CompanyProductModel;
import com.zawraapharma.models.PharmacyModel;

import java.util.List;

public interface RetrieveActivityView {
    void onCompanySuccess(List<CompanyModel> data);
    void onProductSuccess(List<CompanyProductModel> data);
    void onRetrieveSuccess();
    void onFailed(String msg);
    void onProgressShow();
    void onProgressHide();

}
