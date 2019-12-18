package com.jike.cashocean.ui.find.contract;

import com.jike.cashocean.model.UserLevelBean;
import com.jike.cashocean.ui.base.BaseContract;
import com.jike.cashocean.ui.find.PhoneTestActivity;

/**
 * @author Ming
 * @Date on 2019/12/10
 * @Description
 */
public interface PhoneTestContract {
    interface View extends BaseContract.BaseView {

        void jumpInfo(UserLevelBean userLevelBean);

    }

    interface Presenter extends BaseContract.BasePresenter<PhoneTestActivity> {
        void upLoadPhoneInfo(String imei, String gps);

    }
}
