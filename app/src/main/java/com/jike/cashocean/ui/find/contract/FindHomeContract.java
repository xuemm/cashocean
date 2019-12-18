package com.jike.cashocean.ui.find.contract;

import com.jike.cashocean.model.FindHomeBean;
import com.jike.cashocean.model.FindInfoBean;
import com.jike.cashocean.ui.base.BaseContract;
import com.jike.cashocean.ui.home.FindFragment;

/**
 * @author Ming
 * @Date on 2019/12/9
 * @Description
 */
public interface FindHomeContract {
    interface View extends BaseContract.BaseView {
        void showFindHome(FindHomeBean findHomeBean);

        void jumpInfo(FindInfoBean findInfoBean);

    }

    interface Presenter extends BaseContract.BasePresenter<FindFragment> {
        void getFindHomeList(int page, int perpage);

        void getInfo(int id);
    }
}
