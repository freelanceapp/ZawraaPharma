package com.zawraapharma.mvp.activity_printers_mvp;

import com.zawraapharma.models.CompanyModel;
import com.zawraapharma.models.CompanyProductModel;
import com.zawraapharma.models.PrinterModel;

import java.util.List;

public interface PrinterActivityView {
    void onDevicesSuccess(List<PrinterModel> data);
    void onFailed(String msg);
    void onProgressShow();
    void onProgressHide();

}
