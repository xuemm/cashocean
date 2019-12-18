package com.jike.cashocean.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jike.cashocean.Content.Code;
import com.jike.cashocean.R;
import com.jike.cashocean.adapter.MyBillAdapter;
import com.jike.cashocean.interfaceUtils.RvClickEventListener;
import com.jike.cashocean.interfaceUtils.RvClickListener;
import com.jike.cashocean.model.BillAppBean;
import com.jike.cashocean.model.NormalBean;
import com.jike.cashocean.model.ServerDataCode;
import com.jike.cashocean.model.SystemAppBean;
import com.jike.cashocean.model.UserBillInfoBean;
import com.jike.cashocean.net.Key;
import com.jike.cashocean.net.exception.SERVER_CODE;
import com.jike.cashocean.ui.base.BaseFragment;
import com.jike.cashocean.ui.base.IStateView;
import com.jike.cashocean.ui.bill.AddBillActivity;
import com.jike.cashocean.ui.bill.AllBillActivity;
import com.jike.cashocean.ui.bill.BillInfoActivity;
import com.jike.cashocean.ui.bill.compoment.DaggerBillComponent;
import com.jike.cashocean.ui.bill.contract.BillHomeContract;
import com.jike.cashocean.ui.bill.module.BillModule;
import com.jike.cashocean.ui.home.presenter.BookkeepPresenter;
import com.jike.cashocean.ui.login.LoginActivity;
import com.jike.cashocean.util.ChooseDialog;
import com.jike.cashocean.util.MoneyUtils;
import com.jike.cashocean.view.SmoothListView.SmoothListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

public class BookkeeperFragment extends BaseFragment<BookkeepPresenter> implements BillHomeContract.View, IStateView {

    @BindView(R.id.ibn_back)
    ImageButton ibnBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_title_right)
    TextView tvTitleRight;
    @BindView(R.id.listView)
    SmoothListView listView;
    @BindView(R.id.ll_no_record)
    LinearLayout llNoRecord;
    @BindView(R.id.tv_add_to_bill)
    TextView tvAddToBill;

    public static BookkeeperFragment newInstance() {
        Bundle args = new Bundle();
        BookkeeperFragment fragment = new BookkeeperFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getContentLayout() {
        return R.layout.fragment_bookkeep_new;
    }

    @Override
    public void initInjector() {
        DaggerBillComponent.builder().billModule(new BillModule("记账首页", getActivity())).build().inject(this);
    }

    private MyBillAdapter billAdapter;
    TextView tvAddBill;
    TextView tvAllMoney; //总金额
    TextView tvNum; //总数量
    List<BillAppBean.DataBean.DatasBean.ListBean> fd;

    @Override
    public void bindView(View view, Bundle sacedInstanceState) {
        ibnBack.setVisibility(View.GONE);

        tvTitle.setText(getString(R.string.title_bookkeep));
        tvTitleRight.setText(getString(R.string.bookkeep_see_more));
        LinearLayout inflate =
                (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.bookkeep_item,
                        listView, false);
        tvAddBill = inflate.findViewById(R.id.tv_add_bill);
        tvAddBill.setOnClickListener(view1 -> {
            if (!TextUtils.isEmpty(SPUtils.getInstance().getString(Key.TOKEN))) {
                startAddBillActivity();
            } else {
                startLoginActivity();
            }
        });
        tvAllMoney = inflate.findViewById(R.id.tv_all_money);
        tvNum = inflate.findViewById(R.id.tv_num);
        listView.addHeaderView(inflate);
        fd = new ArrayList<>();
        tvTitleRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.startActivity(AllBillActivity.class);
            }
        });

        tvAddToBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(SPUtils.getInstance().getString(Key.TOKEN))) {
                    startLoginActivity();
                } else {
                    startAddBillActivity();
                }
            }
        });
        billAdapter = new MyBillAdapter(getActivity(), fd);
        billAdapter.setOnItemEventListener(new RvClickEventListener() {
            @Override
            public void onItemEventClick(int id, int position, String content) {
                switch (id) {
                    case 1:
                        //设置为已还
                        chooseDialog =
                                new ChooseDialog(getActivity().getString(R.string.change_bill_status_repay));
                        chooseDialog.show(getFragmentManager(), "show_return");
                        chooseDialog.setOnClickMakeSure(new ChooseDialog.OnClickMakeSure() {
                            @Override
                            public void chooseCancel() {
                                chooseDialog.dismiss();
                            }

                            @Override
                            public void chooseSure() {
                                chooseDialog.dismiss();
                                showLoadingDialog();
                                mPresenter.changeBillStatus(fd.get(position).getId(), "2");
                            }
                        });
                        break;
                    case 2:
                        //设置删除
                        chooseDialog =
                                new ChooseDialog(getActivity().getString(R.string.change_bill_status_delete));
                        chooseDialog.show(getFragmentManager(), "show_delete");
                        chooseDialog.setOnClickMakeSure(new ChooseDialog.OnClickMakeSure() {
                            @Override
                            public void chooseCancel() {
                                chooseDialog.dismiss();
                            }

                            @Override
                            public void chooseSure() {
                                chooseDialog.dismiss();
                                showLoadingDialog();
                                mPresenter.changeBillStatus(fd.get(position).getId(), "3");
                            }
                        });
                        break;
                    default:
                        break;
                }
            }
        });
        listView.setRefreshEnable(true);
        billAdapter.setOnItemClick(new RvClickListener() {
            @Override
            public void onItemChildClick(int id, int position, String content) {
                //跳转详情界面
                Intent intent = new Intent(getActivity(), BillInfoActivity.class);
                intent.putExtra("app_name", fd.get(position).getApp_name());
                intent.putExtra("app_logo", fd.get(position).getApp_logo());
                intent.putExtra("loan_money", fd.get(position).getRepay_money());
                intent.putExtra("repayment_date", fd.get(position).getRepay_date());
                intent.putExtra("add_bill_time", fd.get(position).getCreated_at());
                startActivity(intent);
            }
        });
        listView.setLoadMoreEnable(true);

        listView.setAdapter(billAdapter);
        listView.setSmoothListViewListener(new SmoothListView.ISmoothListViewListener() {
            @Override
            public void onRefresh() {
                fdClear = true;
                page = 1;
                listView.setLoadMoreEnable(true);
                mPresenter.getUserBill();
                mPresenter.getBillHomeList(page, perpage);
            }

            @Override
            public void onLoadMore() {
                page++;
                fdClear = false;
                mPresenter.getBillHomeList(page, perpage);
            }
        });
    }

    ChooseDialog chooseDialog;

    private void startAddBillActivity() {
        Intent intent = new Intent(getActivity(), AddBillActivity.class);
        startActivityForResult(intent, 1000);
    }

    private void startLoginActivity() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivityForResult(intent, 1000);
    }

    boolean fdClear = true;

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            fdClear = true;
            showLoadingDialog();
            mPresenter.getUserBill();
            mPresenter.getSystemApp();
        }
    }

    boolean isFirstAdd = true;

    @Override
    public void initData() {
        isFirstAdd = false;
        if (TextUtils.isEmpty(SPUtils.getInstance().getString(Key.TOKEN))) {
            listView.setVisibility(View.GONE);
            tvTitleRight.setVisibility(View.GONE);
            llNoRecord.setVisibility(View.VISIBLE);
            loadService.showSuccess();
            return;
        }
        mPresenter.getUserBill();
        mPresenter.getSystemApp();
    }

    int page = 1;
    int perpage = 10;

    @Override
    public void onReloadClickCashMall(View v) {
        showLoading();
        mPresenter.getUserBill();
        mPresenter.getBillHomeList(page, perpage);
    }

    @Override
    public boolean needUserLoadSir() {
        return true;
    }

    private BillAppBean.DataBean.DatasBean.ListBean billApp;

    @Override
    public void showBillHome(BillAppBean billAppBean) {
        listView.stopRefresh();
        listView.stopLoadMore();
        loadService.showSuccess();
        hideLoadingDialog();
        if (billAppBean == null) {
            showAgain();
            return;
        }
        if (billAppBean.getData().getCode() == Code.SUCCESS_CODE) {
            if (fdClear) {
                fd.clear();
            }
            for (int i = 0; i < billAppBean.getData().getDatas().getList().size(); i++) {
                billApp = billAppBean.getData().getDatas().getList().get(i);
                billApp.setApp_logo(hashMap.get(billApp.getApp_name()));
                fd.add(billApp);
            }
//            fd.addAll(billAppBean.getData().getDatas().getList());
            LogUtils.e("记账列表" + fd.size());
            listView.setVisibility(View.VISIBLE);
            tvTitleRight.setVisibility(View.VISIBLE);
            llNoRecord.setVisibility(View.GONE);
            if (billAppBean.getData().getDatas().getList().size() == 0 && page == 1) {
                //没有记账
                listView.setVisibility(View.GONE);
                tvTitleRight.setVisibility(View.GONE);
                llNoRecord.setVisibility(View.VISIBLE);
            } else if (billAppBean.getData().getDatas().getList().size() < perpage) {
                //数量不足，加载更多隐藏
                listView.setLoadMoreEnable(false);
            }

            billAdapter.notifyDataSetChanged();
        } else {
//            ToastUtils.showShort(billAppBean.getData().getMsgX());
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && !isFirstAdd) {
            if (TextUtils.isEmpty(SPUtils.getInstance().getString(Key.TOKEN))) {
                listView.setVisibility(View.GONE);
                tvTitleRight.setVisibility(View.GONE);
                llNoRecord.setVisibility(View.VISIBLE);
                return;
            }
            showLoadingDialog();
            mPresenter.getUserBill();
            mPresenter.getSystemApp();
        }
    }

    @Override
    public void getUserBillResult(UserBillInfoBean userBillInfoBean) {

        if (userBillInfoBean == null) {
            showAgain();
        } else {
            if (userBillInfoBean.getData().getCode() == Code.SUCCESS_CODE) {
                tvNum.setText(userBillInfoBean.getData().getDatas().getCounts() + "");
                tvAllMoney.setText(MoneyUtils.fomatMoney(userBillInfoBean.getData().getDatas().getTotal_money()));
            } else {
                ToastUtils.showShort(userBillInfoBean.getData().getMsgX());
            }
        }
    }

    HashMap<String, String> hashMap = new HashMap<>();
    private List<SystemAppBean.DataBean.DatasBean.ListBean> allList = new ArrayList<>();

    @Override
    public void getSystemApplist(SystemAppBean systemAppBean) {
        if (systemAppBean==null) {
            hideLoadingDialog();
            return;
        }
        allList.clear();
        allList = systemAppBean.getData().getDatas().getList();
        for (int i = 0; i < allList.size(); i++) {
            hashMap.put(allList.get(i).getApp_name(), allList.get(i).getLogo_img());
        }
        mPresenter.getBillHomeList(page, perpage);
    }


    @Override
    public void changeBillStatusResult(NormalBean normalBean) {
        hideLoadingDialog();
        if (normalBean == null) {
            return;
        }
        if (normalBean.getData().getCode() == Code.SUCCESS_CODE) {
            page = 1;
            fdClear = true;
            mPresenter.getUserBill();
            mPresenter.getBillHomeList(page, perpage);
        } else {
            ToastUtils.showShort(normalBean.getData().getMsgX());
        }
    }

    @Override
    public void showLoading() {
        loadService.showWithConvertor(new ServerDataCode() {
            @Override
            public int getServerDataCode() {
                return SERVER_CODE.DATA_LOADING;
            }
        });
    }

    @Override
    public void showSuccess() {
        loadService.showWithConvertor(new ServerDataCode() {
            @Override
            public int getServerDataCode() {
                return SERVER_CODE.SUCCESS;
            }
        });
    }

    @Override
    public void showFaild() {
        loadService.showWithConvertor(new ServerDataCode() {
            @Override
            public int getServerDataCode() {
                return SERVER_CODE.DATA_ERROR;
            }
        });
    }

    @Override
    public void showAgain() {
        loadService.showWithConvertor(new ServerDataCode() {
            @Override
            public int getServerDataCode() {
                return SERVER_CODE.DATA_AGAIN_LOADING;
            }
        });
    }
}
