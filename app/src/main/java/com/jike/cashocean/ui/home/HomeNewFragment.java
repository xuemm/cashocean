package com.jike.cashocean.ui.home;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.blankj.utilcode.util.ToastUtils;
import com.jike.cashocean.R;
import com.jike.cashocean.adapter.HomeListAdapter;
import com.jike.cashocean.interfaceUtils.EndlessRecyclerOnScrollListener;
import com.jike.cashocean.model.ClickAppListEntity;
import com.jike.cashocean.model.FilterData;
import com.jike.cashocean.model.FilterEntity;
import com.jike.cashocean.model.HomeBannerEntity;
import com.jike.cashocean.model.HomeListData;
import com.jike.cashocean.model.MessageBean;
import com.jike.cashocean.ui.base.BaseFragment;
import com.jike.cashocean.ui.home.compoment.DaggerHomeComponent;
import com.jike.cashocean.ui.home.contract.HomeContract;
import com.jike.cashocean.ui.home.module.HomeModule;
import com.jike.cashocean.ui.home.presenter.HomeNewPresenter;
import com.jike.cashocean.view.FilterView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author Ming
 * @Date on 2020/1/8
 * @Description
 */
public class HomeNewFragment extends BaseFragment<HomeNewPresenter> implements HomeContract.View {
    @BindView(R.id.main)
    TextView main;
    @BindView(R.id.filterView)
    FilterView filterView;
    @BindView(R.id.real_filterView)
    FilterView real_filterView;
    @BindView(R.id.rv_home_list)
    RecyclerView rv_home_list;
    @BindView(R.id.scroll_v)
    NestedScrollView scrollV;
    @BindView(R.id.pb_foot)
    ProgressBar pbFoot;
    @BindView(R.id.tv_foot)
    TextView tvFoot;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;

    @Override
    public int getContentLayout() {
        return R.layout.fragment_homenew;
    }

    @Override
    public void initInjector() {
        DaggerHomeComponent.builder().homeModule(new HomeModule("首页", getActivity())).build().inject(this);
    }

    public static HomeNewFragment newInstance() {
        Bundle args = new Bundle();
        HomeNewFragment fragment = new HomeNewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void bindView(View view, Bundle sacedInstanceState) {
        scrollV.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY,
                                       int oldScrollX, int oldScrollY) {
                if (scrollY > main.getHeight()) {
                    filterView.setVisibility(View.VISIBLE);
                } else {
                    filterView.setVisibility(View.GONE);
                }

                if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                    loadMore();
                    page++;
                    mPresenter.getHomeListData("", page + "", pageLength + "");
                }
            }
        });

        real_filterView.setFilterData(getActivity(), filterData);
        filterView.setFilterData(getActivity(), filterData);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                list.clear();
                mPresenter.getHomeListData("", page + "", pageLength + "");
            }
        });
    }

    public void loadMore() {
        pbFoot.setVisibility(View.VISIBLE);
        tvFoot.setVisibility(View.GONE);
        tvFoot.setText(R.string.no_more);
    }

    public void loadMoreFinish() {
        pbFoot.setVisibility(View.VISIBLE);
        tvFoot.setVisibility(View.GONE);
    }

    public void norMore() {
        pbFoot.setVisibility(View.GONE);
        tvFoot.setVisibility(View.VISIBLE);
    }

    String sort = "";
    int page = 1;
    int pageLength = 20;
    List<HomeListData.DataBean.DatasBean.AppListBean> list;
    HomeListAdapter homeListAdapter;
    FilterData filterData;

    @Override
    public void initData() {
        filterData = new FilterData();
        List<FilterEntity> listf = new ArrayList<>();
        listf.add(new FilterEntity(getResources().getString(R.string.comprehensive_sort), "1"));
        listf.add(new FilterEntity(getResources().getString(R.string.sort_high_to_low), "2"));
        listf.add(new FilterEntity(getResources().getString(R.string.sort_low_to_high), "3"));
        filterData.setSorts(listf);

        list = new ArrayList<>();
        homeListAdapter = new HomeListAdapter(list, getActivity());
        rv_home_list.setAdapter(homeListAdapter);
        mPresenter.getHomeListData(sort, String.valueOf(page), String.valueOf(pageLength));
    }

    @Override
    public void onReloadClickCashMall(View v) {

    }

    @Override
    public boolean needUserLoadSir() {
        return false;
    }

    @Override
    public void loadBannerUrl(HomeBannerEntity homeBannerEntity) {

    }

    @Override
    public void loadHomeListData(HomeListData homeListEntity) {
        swipe.setRefreshing(false);
        if (homeListEntity.getData().getDatas().getApp_list().size() > 0) {
            loadMoreFinish();
        } else {
            norMore();
        }
        list.addAll(homeListEntity.getData().getDatas().getApp_list());
        homeListAdapter.notifyDataSetChanged();

    }

    @Override
    public void loadMoreSuccess(HomeListData homeListData) {

    }

    @Override
    public void refreshSuccess(HomeListData homeListData) {

    }

    @Override
    public void loadClick(ClickAppListEntity clickAppListEntity) {

    }

    @Override
    public void loadOrder(HomeListData homeListData) {

    }

    @Override
    public void openGoogle(String packageName) {

    }

    @Override
    public void loanMessages(MessageBean messageBean) {

    }
}
