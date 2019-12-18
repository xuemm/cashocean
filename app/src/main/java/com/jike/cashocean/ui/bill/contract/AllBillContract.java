package com.jike.cashocean.ui.bill.contract;

import com.jike.cashocean.model.BillAppBean;
import com.jike.cashocean.model.NormalBean;
import com.jike.cashocean.model.SystemAppBean;
import com.jike.cashocean.model.UserBillInfoBean;
import com.jike.cashocean.ui.base.BaseContract;
import com.jike.cashocean.ui.bill.AllBillActivity;
import com.jike.cashocean.ui.home.BookkeeperFragment;

/**
 * @author Ming
 * @Date on 2019/12/9
 * @Description
 */
public interface AllBillContract {
    interface View extends BaseContract.BaseView {
        void showBillHome(BillAppBean billAppBean);

        void getSystemApplist(SystemAppBean systemAppBean);

        void changeBillStatusResult(NormalBean normalBean);
    }

    interface Presenter extends BaseContract.BasePresenter<AllBillActivity> {
        void getBillHomeList(int page, int perpage);

        void getSystemApp();

        void changeBillStatus(int id, String status);
    }
}
