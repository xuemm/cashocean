package com.jike.cashocean.ui.find.contract;

import com.jike.cashocean.model.NormalBean;
import com.jike.cashocean.model.SaveIdentityInfoEntity;
import com.jike.cashocean.ui.base.BaseContract;
import com.jike.cashocean.ui.find.CardTestActivity;

import java.util.Map;

public interface CardTestContract {
    interface View extends BaseContract.BaseView {
        void loadSaveInfoSuccess(NormalBean normalBean);

        void loadSuccessFacePhoto(String faceName);

        void loadScuccessIDPhoto(String idName);


    }

    interface Presenter extends BaseContract.BasePresenter<CardTestActivity> {
        void saveInfo(Map<String, String> mapInfo);
    }
}
