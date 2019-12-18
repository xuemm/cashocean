package com.jike.cashocean.ui.find.contract;

import com.jike.cashocean.model.ClickAppListEntity;
import com.jike.cashocean.model.HomeListData;
import com.jike.cashocean.ui.base.BaseContract;
import com.jike.cashocean.ui.find.PhoneTestResultActivity;

/**
 * @author Ming
 * @Date on 2019/12/11
 * @Description
 */
public interface PhoneTestResultContract {
    interface View extends BaseContract.BaseView {
        void loadCommendApp(HomeListData homeListData);

        void loadClick(ClickAppListEntity clickAppListEntity);

        void openGoogle(String packageName);
    }

    interface Presenter extends BaseContract.BasePresenter<PhoneTestResultActivity> {
        void getCommendApp();

        void clickItem(String id);
    }
}
