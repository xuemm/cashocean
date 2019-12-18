package com.jike.cashocean.ui.bill;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.jike.cashocean.Content.Code;
import com.jike.cashocean.R;
import com.jike.cashocean.adapter.MyAllBillAdapter;
import com.jike.cashocean.interfaceUtils.RvClickEventListener;
import com.jike.cashocean.interfaceUtils.RvClickListener;
import com.jike.cashocean.model.BillAppBean;
import com.jike.cashocean.model.NormalBean;
import com.jike.cashocean.model.SystemAppBean;
import com.jike.cashocean.ui.base.BaseActivity;
import com.jike.cashocean.ui.bill.compoment.DaggerBillComponent;
import com.jike.cashocean.ui.bill.contract.AllBillContract;
import com.jike.cashocean.ui.bill.module.BillModule;
import com.jike.cashocean.ui.bill.preseneter.AllBillPresenter;
import com.jike.cashocean.util.ChooseDialog;
import com.jike.cashocean.view.SmoothListView.SmoothListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AllBillActivity extends BaseActivity<AllBillPresenter> implements AllBillContract.View {


    @BindView(R.id.ibn_back)
    ImageButton ibnBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_title_right)
    TextView tvTitleRight;
    @BindView(R.id.listView)
    SmoothListView listView;
    private List<SystemAppBean.DataBean.DatasBean.ListBean> allList;

    @Override
    public int getContentLayout() {
        return R.layout.activity_all_bill;
    }

    @Override
    public void initInjector() {
        DaggerBillComponent.builder().billModule(new BillModule("全部账单", this)).build().inject(this);
    }

    ArrayList<BillAppBean.DataBean.DatasBean.ListBean> billList;
    MyAllBillAdapter myBillAdapter;
    int page = 1;
    int perpage = 10;


    @Override
    public void bindView(View view, Bundle sacedInstanceState) {
        ibnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvTitle.setText(getString(R.string.all_bill));
        tvTitleRight.setVisibility(View.GONE);
        billList = new ArrayList<>();
        myBillAdapter = new MyAllBillAdapter(this, billList);
        listView.setAdapter(myBillAdapter);

        myBillAdapter.setOnItemEventListener(new RvClickEventListener() {
            @Override
            public void onItemEventClick(int id, int position, String content) {
                switch (id) {
                    case 1:
                        //设置为已还/未还
                        if (billList.get(position).getStatus() == 2) {
                            chooseDialog =
                                    new ChooseDialog(getString(R.string.change_bill_status_no_repay));
                        } else {
                            chooseDialog =
                                    new ChooseDialog(getString(R.string.change_bill_status_repay));
                        }
                        chooseDialog.show(getSupportFragmentManager(), "show_return");
                        chooseDialog.setOnClickMakeSure(new ChooseDialog.OnClickMakeSure() {
                            @Override
                            public void chooseCancel() {
                                chooseDialog.dismiss();
                            }

                            @Override
                            public void chooseSure() {
                                chooseDialog.dismiss();
                                showLoadingDialog();
                                if (billList.get(position).getStatus() == 2) {
                                    //设置未还
                                    showLoadingDialog();
                                    mPresenter.changeBillStatus(billList.get(position).getId(),
                                            "1");
                                } else {
                                    //设置已还
                                    showLoadingDialog();
                                    mPresenter.changeBillStatus(billList.get(position).getId(),
                                            "2");
                                }
                            }
                        });
                        break;
                    case 2:
                        //设置删除
                        chooseDialog =
                                new ChooseDialog(getString(R.string.change_bill_status_delete));
                        chooseDialog.show(getSupportFragmentManager(), "show_delete");
                        chooseDialog.setOnClickMakeSure(new ChooseDialog.OnClickMakeSure() {
                            @Override
                            public void chooseCancel() {
                                chooseDialog.dismiss();
                            }

                            @Override
                            public void chooseSure() {
                                chooseDialog.dismiss();
                                showLoadingDialog();
                                mPresenter.changeBillStatus(billList.get(position).getId(), "3");
                            }
                        });
                        break;
                    default:
                        break;
                }
            }
        });
        listView.setRefreshEnable(true);
        myBillAdapter.setOnItemClick(new RvClickListener() {
            @Override
            public void onItemChildClick(int id, int position, String content) {
                //跳转详情界面

                Intent intent = new Intent(AllBillActivity.this, BillInfoActivity.class);
                intent.putExtra("app_name", billList.get(position).getApp_name());
                intent.putExtra("app_logo", billList.get(position).getApp_logo());
                intent.putExtra("loan_money", billList.get(position).getRepay_money());
                intent.putExtra("repayment_date", billList.get(position).getRepay_date());
                intent.putExtra("add_bill_time", billList.get(position).getCreated_at());
                startActivity(intent);
            }
        });
        listView.setLoadMoreEnable(true);
        listView.setSmoothListViewListener(new SmoothListView.ISmoothListViewListener() {
            @Override
            public void onRefresh() {
                fdClear = true;
                page = 1;
                listView.setLoadMoreEnable(true);
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

    boolean fdClear = true;
    private ChooseDialog chooseDialog;

    @Override
    public void initData() {
        allList = new ArrayList<>();
        showLoadingDialog();
        mPresenter.getSystemApp();
    }

    @Override
    public void onReloadClick(View v) {

    }

    private BillAppBean.DataBean.DatasBean.ListBean billApp;

    @Override
    public boolean needUserLoadSir() {
        return false;
    }

    @Override
    public void showBillHome(BillAppBean billAppBean) {
        hideLoadingDialog();
        listView.stopRefresh();
        listView.stopLoadMore();
        if (fdClear) {
            billList.clear();
        }
        if (billAppBean.getData().getCode() == Code.SUCCESS_CODE) {
            for (int i = 0; i < billAppBean.getData().getDatas().getList().size(); i++) {
                billApp = billAppBean.getData().getDatas().getList().get(i);
                billApp.setApp_logo(hashMap.get(billApp.getApp_name()));
                billList.add(billApp);
            }
//            billList.addAll(billAppBean.getData().getDatas().getList());
            myBillAdapter.notifyDataSetChanged();
            if (billAppBean.getData().getDatas().getList().size() < perpage) {
                //数量不足，加载更多隐藏
                listView.setLoadMoreEnable(false);
            }
        } else {
            ToastUtils.showShort(billAppBean.getData().getMsgX());
        }
    }

    HashMap<String, String> hashMap = new HashMap<>();

    @Override
    public void getSystemApplist(SystemAppBean systemAppBean) {
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
            showLoadingDialog();
            mPresenter.getBillHomeList(page, perpage);
        } else {
            ToastUtils.showShort(normalBean.getData().getMsgX());
        }
    }
}
