package com.jike.cashocean.ui.bill.contract;

import com.jike.cashocean.model.NormalBean;
import com.jike.cashocean.model.SystemAppBean;
import com.jike.cashocean.ui.base.BaseContract;
import com.jike.cashocean.ui.bill.AddBillActivity;

import java.util.Map;

public interface BillContract {
    interface View extends BaseContract.BaseView {
        void getSystemApplist(SystemAppBean systemAppBean);

        void addBillResult(NormalBean normalBean);
    }

    interface Presenter extends BaseContract.BasePresenter<AddBillActivity> {

        void addBill(String appName, String repay_money, String repay_time);

        void getSystemApp();
    }
}
