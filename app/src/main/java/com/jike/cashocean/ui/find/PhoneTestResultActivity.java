package com.jike.cashocean.ui.find;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jike.cashocean.Content.Code;
import com.jike.cashocean.R;
import com.jike.cashocean.adapter.MyAppAdapter;
import com.jike.cashocean.adapter.MyAppListAdapter;
import com.jike.cashocean.model.ClickAppListEntity;
import com.jike.cashocean.model.HomeListData;
import com.jike.cashocean.net.Key;
import com.jike.cashocean.ui.MainNewActivity;
import com.jike.cashocean.ui.authentication.AuthenticationActivity;
import com.jike.cashocean.ui.base.BaseActivity;
import com.jike.cashocean.ui.find.compoment.DaggerFindComponent;
import com.jike.cashocean.ui.find.contract.PhoneTestResultContract;
import com.jike.cashocean.ui.find.module.FindModule;
import com.jike.cashocean.ui.find.presenter.PhoneTestResultPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.jike.cashocean.Content.KeyValue.HOME_CLICK_URL;
import static com.jike.cashocean.Content.KeyValue.HOME_PACKAGE_NAME;

public class PhoneTestResultActivity extends BaseActivity<PhoneTestResultPresenter> implements PhoneTestResultContract.View {

    @BindView(R.id.ibn_back)
    ImageButton ibnBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_level)
    ImageView ivLevel; //用户等级图标
    @BindView(R.id.tv_level_hint)
    TextView tvLevelHint;  //用户等级描述
    @BindView(R.id.tv_title_right)
    TextView tvTitleRight;
    @BindView(R.id.listView)
    RecyclerView rl;
    @BindView(R.id.tv_to_home)
    TextView tvToHome;

    @Override
    public int getContentLayout() {
        return R.layout.activity_phone_test_result;
    }

    @Override
    public void initInjector() {
        DaggerFindComponent.builder().findModule(new FindModule("证件测评结果", this)).build().inject(this);
    }

    @Override
    public void bindView(View view, Bundle sacedInstanceState) {
        tvTitle.setText(R.string.phone_test_page);
        int level = getIntent().getIntExtra("level", 0);
        ibnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainNewActivity.changePage(0);
                finish();
            }
        });

        switch (level) {
            case 1:
                ivLevel.setImageResource(R.mipmap.icon_a);
                tvLevelHint.setText(getString(R.string.result_hint_a));
                break;
            case 2:
                ivLevel.setImageResource(R.mipmap.icon_b);
                tvLevelHint.setText(getString(R.string.result_hint_a));
                break;
            case 3:
                ivLevel.setImageResource(R.mipmap.icon_c);
                tvLevelHint.setText(getString(R.string.result_hint_c));
                break;
            default:
                break;
        }
        myAppAdapter = new MyAppAdapter(this, list);
        myAppAdapter.setOnClickListener(new MyAppListAdapter.OnItemClickListener() {
            @Override
            public void clickItme(String id) {
                showLoadingDialog();
                mPresenter.clickItem(id);
            }
        });
        rl.setAdapter(myAppAdapter);
    }

    @Override
    public void initData() {
        showLoadingDialog();
        mPresenter.getCommendApp();
    }

    @Override
    public void onReloadClick(View v) {

    }

    @Override
    public boolean needUserLoadSir() {
        return false;
    }

    MyAppAdapter myAppAdapter;
    List<HomeListData.DataBean.DatasBean.AppListBean> list = new ArrayList<>();

    @Override
    public void loadCommendApp(HomeListData homeListData) {
        hideLoadingDialog();
        if (homeListData == null) {
            return;
        }
        list.clear();
        list.addAll(homeListData.getData().getDatas().getApp_list());
        myAppAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadClick(ClickAppListEntity clickAppListEntity) {
        hideLoadingDialog();
        if (clickAppListEntity != null) {
            if (clickAppListEntity.getRet() == 200) {
                if (clickAppListEntity.getData().getCode() == 100) {
                    String isAuth = SPUtils.getInstance().getString(Key.IS_AUTHENTICAITON, "1");
                    if ("0".equals(isAuth)) {
//                        ActivityUtils.startActivity(AuthenticationActivity.class);
                        Intent intent = new Intent(this, AuthenticationActivity.class);
                        intent.putExtra(HOME_CLICK_URL,
                                clickAppListEntity.getData().getDatas().getClick_url());
                        intent.putExtra(HOME_PACKAGE_NAME,
                                clickAppListEntity.getData().getDatas().getPackage_name());
                        this.startActivityForResult(intent, Code.APPLY_APP_CLICK);
                    } else if ("1".equals(isAuth)) {

                        int jump_type = clickAppListEntity.getData().getDatas().getJump_type();
                        if (jump_type == 1) {
                            //继续请求下一个接口
                            //请求服务器返回的链接,成功后打开Google play
                            showLoadingDialog();
                            mPresenter.requestAf(clickAppListEntity.getData().getDatas().getClick_url(),
                                    clickAppListEntity.getData().getDatas().getPackage_name());
                        } else if (jump_type == 2) {
                            //关闭loading显示
                            Intent intent = new Intent();
                            intent.setData(Uri.parse(clickAppListEntity.getData().getDatas().getClick_url()));
                            intent.setAction(Intent.ACTION_VIEW);
                            startActivity(intent);
                        }
                    }
                } else {
                    ToastUtils.showLong(clickAppListEntity.getData().getMsg());
                }
            } else {
                ToastUtils.showLong(clickAppListEntity.getMsg());
            }
        }
    }

    @Override
    public void openGoogle(String packageName) {
        hideLoadingDialog();
        try {
            Uri uri = Uri.parse("market://details?id=" + packageName);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setPackage("com.android.vending");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivityForResult(intent, 0);
        } catch (Throwable t) {
            Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=" + packageName);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivityForResult(intent, 0);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.getCommendApp();
    }
}
