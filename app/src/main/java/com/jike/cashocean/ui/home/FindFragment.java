package com.jike.cashocean.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.jike.cashocean.Content.Code;
import com.jike.cashocean.R;
import com.jike.cashocean.adapter.MyFindHomeAdapter;
import com.jike.cashocean.interfaceUtils.RvClickListener;
import com.jike.cashocean.model.FindHomeBean;
import com.jike.cashocean.model.FindInfoBean;
import com.jike.cashocean.net.Key;
import com.jike.cashocean.ui.base.BaseFragment;
import com.jike.cashocean.ui.find.CardTestActivity;
import com.jike.cashocean.ui.find.PagerInfoActivity;
import com.jike.cashocean.ui.find.PhoneTestActivity;
import com.jike.cashocean.ui.find.compoment.DaggerFindComponent;
import com.jike.cashocean.ui.find.contract.FindHomeContract;
import com.jike.cashocean.ui.find.module.FindModule;
import com.jike.cashocean.ui.home.presenter.FindPresenter;
import com.jike.cashocean.ui.login.LoginActivity;
import com.jike.cashocean.view.SmoothListView.SmoothListView;

import java.util.ArrayList;

import butterknife.BindView;
import cn.bingoogolapple.bgabanner.BGABanner;

public class FindFragment extends BaseFragment<FindPresenter> implements FindHomeContract.View {

    @BindView(R.id.listView_find)
    SmoothListView listView;

    public static FindFragment newInstance() {
        Bundle args = new Bundle();
        FindFragment fragment = new FindFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getContentLayout() {
        return R.layout.fragment_find;
    }

    @Override
    public void initInjector() {
        DaggerFindComponent.builder().findModule(new FindModule("发现首页", getActivity())).build().inject(this);
    }

    LinearLayout linearLayout;
    LinearLayout lineCardTest;
    LinearLayout linePhoneTest;
    BGABanner bgaBanner;
    LinearLayout marqueeTextView;

    @Override
    public void bindView(View view, Bundle sacedInstanceState) {
        linearLayout =
                (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.item_find_head,
                        listView, false);
        bgaBanner = linearLayout.findViewById(R.id.banner_content);
        marqueeTextView = linearLayout.findViewById(R.id.line_sign);
        lineCardTest = linearLayout.findViewById(R.id.line_card_test);
        linePhoneTest = linearLayout.findViewById(R.id.line_phone_test);
        marqueeTextView.setVisibility(View.GONE);
        bgaBanner.setAutoPlayAble(false);
        listView.addHeaderView(linearLayout);
        lineCardTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(SPUtils.getInstance().getString(Key.TOKEN))) {
                    ActivityUtils.startActivity(LoginActivity.class);

                } else {

                    ActivityUtils.startActivity(CardTestActivity.class);
                }
            }
        });
        linePhoneTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(SPUtils.getInstance().getString(Key.TOKEN))) {
                    ActivityUtils.startActivity(LoginActivity.class);
                } else {
                    ActivityUtils.startActivity(PhoneTestActivity.class);
                }
            }
        });
        adapter = new MyFindHomeAdapter(getActivity(), homeList);
        listView.setAdapter(adapter);
        adapter.setOnItemClick(new RvClickListener() {
            @Override
            public void onItemChildClick(int id, int position, String content) {
                //点击跳转
                mPresenter.getInfo(id);
                showLoadingDialog();
            }
        });
        listView.setSmoothListViewListener(new SmoothListView.ISmoothListViewListener() {
            @Override
            public void onRefresh() {
                page = 1;
                mPresenter.getFindHomeList(page, perpage);

            }

            @Override
            public void onLoadMore() {
                page++;
                mPresenter.getFindHomeList(page, perpage);
            }
        });

        bgaBanner.setAdapter(new BGABanner.Adapter<ImageView, String>() {
            @Override
            public void fillBannerItem(BGABanner banner, ImageView itemView, String model,
                                       int position) {
                Glide.with(getActivity())
                        .load(model)
                        .placeholder(R.mipmap.placeholed)
                        .error(R.mipmap.placeholed)
                        .centerCrop()
                        .dontAnimate()
                        .into(itemView);
            }
        });
    }

    MyFindHomeAdapter adapter;

    int page = 1;
    int perpage = 20;

    @Override
    public void initData() {
        mPresenter.getFindHomeList(page, perpage);
        isFirstAdd = false;
    }

    @Override
    public void onReloadClickCashMall(View v) {

    }

    @Override
    public boolean needUserLoadSir() {
        return false;
    }

    boolean isFirstAdd = true;
    ArrayList<String> imageUrls = new ArrayList<>();
    ArrayList<String> tipDescs = new ArrayList<>();
    ArrayList<FindHomeBean.DataBean.DatasBean.ListBean> homeList = new ArrayList<>();

    @Override
    public void showFindHome(FindHomeBean findHomeBean) {
        hideLoadingDialog();
        listView.stopRefresh();
        listView.stopLoadMore();
        if (findHomeBean == null) {
            return;
        }
        if (findHomeBean.getData().getDatas().getBanner_list().size() == 0) {
            bgaBanner.setVisibility(View.GONE);
        } else {
            imageUrls.clear();//这是banner 图的url数据
            tipDescs.clear();
            for (int i = 0; i < findHomeBean.getData().getDatas().getBanner_list().size(); i++) {
                imageUrls.add(findHomeBean.getData().getDatas().getBanner_list().get(i).getBanner_image());
                tipDescs.add("");
            }
            bgaBanner.setData(imageUrls, tipDescs);
        }
        if (page == 1) {
            homeList.clear();
        }
        if (findHomeBean.getData().getDatas().getList().size() < 20) {
            listView.setLoadMoreEnable(false);
        }
        homeList.addAll(findHomeBean.getData().getDatas().getList());
        adapter.notifyDataSetChanged();

    }

    @Override
    public void jumpInfo(FindInfoBean findInfoBean) {
        hideLoadingDialog();
        if (findInfoBean == null) {
            return;
        }
        if (findInfoBean.getData().getCode() == Code.SUCCESS_CODE) {
            FindInfoBean.DataBean.DatasBean datas = findInfoBean.getData().getDatas();
            Intent intent = new Intent(getActivity(), PagerInfoActivity.class);
            intent.putExtra("title", datas.getTitle());
            intent.putExtra("see_num", datas.getRead_num());
            intent.putExtra("image_url", datas.getMaster_img());
            intent.putExtra("info", datas.getContent());
            intent.putExtra("time", datas.getCreated_at());
            startActivity(intent);
        } else {
            ToastUtils.showShort(findInfoBean.getData().getMsgX());
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isFirstAdd) {
            page=1;
            mPresenter.getFindHomeList(page, perpage);
        }
    }
}
