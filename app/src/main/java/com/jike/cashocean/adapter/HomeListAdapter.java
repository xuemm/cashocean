package com.jike.cashocean.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.SPUtils;
import com.jike.cashocean.R;
import com.jike.cashocean.component.GlideApp;
import com.jike.cashocean.model.HomeListData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * @author Ming
 * @Date on 2020/1/8
 * @Description
 */
public class HomeListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //    https://blog.csdn.net/qq_38356174/article/details/90716344
//    https://blog.csdn.net/sinat_33150417/article/details/78793175
    private List<HomeListData.DataBean.DatasBean.AppListBean> list;
    private Context context;

    public HomeListAdapter(List<HomeListData.DataBean.DatasBean.AppListBean> list,
                           Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View inflate = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_loans_app_new, parent, false);
        HomeListHolder homeListHolder = new HomeListHolder(inflate);
        return homeListHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        HomeListHolder holder1 = (HomeListHolder) holder;
        GlideApp.with(context)
                .load(list.get(position).getLogo_img())
                .placeholder(R.mipmap.placeholed)
                .fitCenter()
                .into(holder1.appLogo);
        holder1.tvAppName.setText(list.get(position).getApp_name());
        if (list.get(position).getStars() == 0) {
            holder1.ratingbar.setVisibility(View.GONE);
            holder1.tvScore.setText("");
        } else {
            holder1.tvScore.setText(String.valueOf(list.get(position).getStars()));
            holder1.ratingbar.setVisibility(View.VISIBLE);
        }
        holder1.ratingbar.setRating(list.get(position).getStars());
        holder1.llRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SPUtils.getInstance().put(list.get(position).getApp_name(), true);
//                    _onClickListener.clickItme(listBean.getId());
            }
        });
        holder1.tvLimetRangeCount.setText(list.get(position).getLoan_min_money() + "-" + list.get(position).getLoan_max_money());
        holder1.tvFreeRateCount.setText("≥" + list.get(position).getLoan_min_rate());
        holder1.tvDescBottom.setText(list.get(position).getDesc_content());

        if (list.get(position).getIs_hot() == 1) {
            holder1.ivHot.setVisibility(View.VISIBLE);
        } else {
            holder1.ivHot.setVisibility(View.GONE);
        }

        if (SPUtils.getInstance().getBoolean(list.get(position).getApp_name(), false)) {
            holder1.iv.setVisibility(View.VISIBLE);
            holder1.btn.setBackgroundResource(R.drawable.shape_white_btn_bg);
            holder1.btn.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
        } else {
            holder1.iv.setVisibility(View.GONE);
            holder1.btn.setBackgroundResource(R.drawable.shape_green_btn_bg);
            holder1.btn.setTextColor(ContextCompat.getColor(context, R.color.white));
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class HomeListHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_hot)
        ImageView ivHot;
        @BindView(R.id.app_logo)
        ImageView appLogo;
        @BindView(R.id.card_view)
        CardView cardView;
        @BindView(R.id.tv_app_name)
        TextView tvAppName;
        @BindView(R.id.tv_score)
        TextView tvScore;
        @BindView(R.id.ratingbar)
        MaterialRatingBar ratingbar;
        @BindView(R.id.btn)
        Button btn;
        @BindView(R.id.iv)
        ImageView iv;
        @BindView(R.id.line_app)
        LinearLayout lineApp;
        @BindView(R.id.tv_limet_range)
        TextView tvLimetRange;
        @BindView(R.id.tv_limet_range_count)
        TextView tvLimetRangeCount;
        @BindView(R.id.tv_free_rate)
        TextView tvFreeRate;
        @BindView(R.id.tv_free_rate_count)
        TextView tvFreeRateCount;
        @BindView(R.id.line_money_fee)
        LinearLayout lineMoneyFee;
        @BindView(R.id.bottom_divider)
        View bottomDivider;
        @BindView(R.id.tv_desc_bottom)
        TextView tvDescBottom;
        @BindView(R.id.line1)
        LinearLayout line1;
        @BindView(R.id.ll_root_view)
        RelativeLayout llRootView;

        public HomeListHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
