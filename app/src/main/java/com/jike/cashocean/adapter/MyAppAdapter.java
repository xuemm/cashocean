package com.jike.cashocean.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.SPUtils;
import com.jike.cashocean.R;
import com.jike.cashocean.component.GlideApp;
import com.jike.cashocean.model.HomeListData;
import com.jike.cashocean.net.Key;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * @author Ming
 * @Date on 2019/12/11
 * @Description
 */
public class MyAppAdapter extends RecyclerView.Adapter<MyAppAdapter.Holder> {

    Context context;
    List<HomeListData.DataBean.DatasBean.AppListBean> list;

    public MyAppAdapter(Context context, List<HomeListData.DataBean.DatasBean.AppListBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_loans_app_new, parent,
                false);
        return new MyAppAdapter.Holder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        HomeListData.DataBean.DatasBean.AppListBean listBean = list.get(position);
        GlideApp.with(context)
                .load(listBean.getLogo_img())
                .placeholder(R.mipmap.placeholed)
                .fitCenter()
                .into(holder.appLogo);
        holder.tvAppName.setText(listBean.getApp_name());
        if (listBean.getStars() == 0) {
            holder.ratingbar.setVisibility(View.GONE);
            holder.tvScore.setText("");
        } else {
            holder.tvScore.setText(String.valueOf(listBean.getStars()));
            holder.ratingbar.setVisibility(View.VISIBLE);
        }
        holder.ratingbar.setRating(listBean.getStars());
        holder.llroot_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SPUtils.getInstance().put(listBean.getApp_name(), true);
                _onClickListener.clickItme(listBean.getId());
            }
        });
        holder.tvLimetRangeCount.setText(listBean.getLoan_min_money() + "-" + listBean.getLoan_max_money());
        holder.tvFreeRateCount.setText("≥" + listBean.getLoan_min_rate());
        holder.tvDescBottom.setText(listBean.getDesc_content());

        if (listBean.getIs_hot() == 1) {
            holder.ivHot.setVisibility(View.VISIBLE);
        } else {
            holder.ivHot.setVisibility(View.GONE);
        }

        if (SPUtils.getInstance().getBoolean(listBean.getApp_name(), false)) {
            holder.iv.setVisibility(View.VISIBLE);
            holder.btn.setBackgroundResource(R.drawable.shape_white_btn_bg);
            holder.btn.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
        } else {
            holder.iv.setVisibility(View.GONE);
            holder.btn.setBackgroundResource(R.drawable.shape_green_btn_bg);
            holder.btn.setTextColor(ContextCompat.getColor(context, R.color.white));
        }
    }

    MyAppListAdapter.OnItemClickListener _onClickListener;

    public void setOnClickListener(MyAppListAdapter.OnItemClickListener onClickListener) {
        _onClickListener = onClickListener;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        @BindView(R.id.ll_root_view)
        RelativeLayout llroot_view;
        @BindView(R.id.app_logo)
        ImageView appLogo;
        @BindView(R.id.tv_app_name)
        TextView tvAppName;
        @BindView(R.id.tv_score)
        TextView tvScore;
        @BindView(R.id.ratingbar)
        MaterialRatingBar ratingbar;
        @BindView(R.id.btn)
        Button btn;
        @BindView(R.id.tv_limet_range)
        TextView tvLimetRange;
        @BindView(R.id.tv_limet_range_count)
        TextView tvLimetRangeCount;
        @BindView(R.id.tv_free_rate)
        TextView tvFreeRate;
        @BindView(R.id.tv_free_rate_count)
        TextView tvFreeRateCount;
        @BindView(R.id.tv_desc_bottom)
        TextView tvDescBottom;
        @BindView(R.id.iv_hot)
        ImageView ivHot;
        @BindView(R.id.iv)
        ImageView iv;

        public Holder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
