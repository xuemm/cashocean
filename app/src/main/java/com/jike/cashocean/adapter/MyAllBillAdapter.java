package com.jike.cashocean.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.jike.cashocean.R;
import com.jike.cashocean.component.GlideApp;
import com.jike.cashocean.interfaceUtils.RvClickEventListener;
import com.jike.cashocean.interfaceUtils.RvClickListener;
import com.jike.cashocean.model.BillAppBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyAllBillAdapter extends BaseListAdapter<BillAppBean.DataBean.DatasBean.ListBean> {

    Context context;


    public MyAllBillAdapter(Context context, List<BillAppBean.DataBean.DatasBean.ListBean> list) {
        super(context, list);
        this.context = context;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        final BillHolder holder;
        if (view != null) {
            holder = (BillHolder) view.getTag();
        } else {
            view = mInflater.inflate(R.layout.item_bill_list, viewGroup, false);
            holder = new BillHolder(view);
            view.setTag(holder);
        }
        BillAppBean.DataBean.DatasBean.ListBean item = getItem(i);
        holder.tvBillingDate.setText(item.getCreated_at());
        if (item.getDiff_time() > 1) {
            holder.tvDayHint.setText(context.getString(R.string.days));
        } else {
            holder.tvDayHint.setText(context.getString(R.string.day));
        }

        if (item.getStatus() == 2) {
            //已还清
            holder.tvDay.setVisibility(View.GONE);
            holder.tvDayHint.setVisibility(View.GONE);
            holder.tvDayStatus.setText(context.getString(R.string.have_repay));
            holder.tvDayStatus.setVisibility(View.VISIBLE);
            holder.tvDayStatus.setTextColor(ContextCompat.getColor(context, R.color.color_black));
            holder.tvReturn.setBackgroundResource(R.drawable.shape_gray_btn_bg_repay);
        } else {
            holder.tvDay.setVisibility(View.VISIBLE);
            holder.tvDayHint.setVisibility(View.VISIBLE);
            holder.tvDayStatus.setText(context.getString(R.string.overdue));
            holder.tvDayStatus.setTextColor(ContextCompat.getColor(context, R.color.red));
            holder.tvReturn.setBackgroundResource(R.drawable.shape_green_btn_bg);

            holder.tvDay.setText(item.getDiff_time() + "");
            if (item.getRepay_time() > System.currentTimeMillis() / 1000) {
                //没有逾期
                holder.tvDayStatus.setVisibility(View.GONE);
            } else {
                //逾期
                holder.tvDayStatus.setVisibility(View.VISIBLE);
            }
        }


        if (TextUtils.isEmpty(item.getApp_logo())) {
            holder.cardView.setVisibility(View.GONE);
            holder.rlNoLogo.setVisibility(View.VISIBLE);
            appLogoChange(false, item.getApp_name(), "", holder.tvApp, holder.appLogoNo);
        } else {
            holder.cardView.setVisibility(View.VISIBLE);
            holder.rlNoLogo.setVisibility(View.GONE);
            appLogoChange(true, item.getApp_name(), item.getApp_logo(), holder.tvApp,
                    holder.appLogo);
        }


        holder.tvAppName.setText(item.getApp_name());
        holder.tvReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rvClickEventListener.onItemEventClick(1, i,
                        holder.tvReturn.getText().toString());
            }
        });
        holder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rvClickEventListener.onItemEventClick(2, i,
                        holder.tvDelete.getText().toString());
            }
        });

        holder.llItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rvClickListener.onItemChildClick(1, i, holder.tvReturn.getText().toString());
            }
        });
        return view;
    }

    private RvClickListener rvClickListener;
    private RvClickEventListener rvClickEventListener;

    public void setOnItemClick(RvClickListener rvClickListener) {
        this.rvClickListener = rvClickListener;
    }

    public void setOnItemEventListener(RvClickEventListener rvClickEventListener) {
        this.rvClickEventListener = rvClickEventListener;
    }

    class BillHolder {
        @BindView(R.id.app_logo)
        ImageView appLogo;
        @BindView(R.id.tv_day_status)
        TextView tvDayStatus;
        @BindView(R.id.tv_billing_date)
        TextView tvBillingDate;
        @BindView(R.id.tv_app_name)
        TextView tvAppName;
        @BindView(R.id.tv_set_return)
        TextView tvReturn;
        @BindView(R.id.tv_set_delete)
        TextView tvDelete;
        @BindView(R.id.tv_day)
        TextView tvDay;
        @BindView(R.id.tv_app)
        TextView tvApp;
        @BindView(R.id.tv_day_hint)
        TextView tvDayHint;
        @BindView(R.id.ll_item)
        LinearLayout llItem;
        @BindView(R.id.card_view)
        CardView cardView;
        @BindView(R.id.rl_no_logo)
        RelativeLayout rlNoLogo;
        @BindView(R.id.app_logo_no)
        ImageView appLogoNo;

        public BillHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public void appLogoChange(boolean isHave, String appName, String appLogo, TextView tvAppName,
                              ImageView ivIcon) {
        if (isHave) {
            tvAppName.setVisibility(View.GONE);
            GlideApp.with(context)
                    .load(appLogo)
                    .placeholder(R.drawable.bg_app_name)
                    .fitCenter()
                    .into(ivIcon);
        } else {
            String name = appName.toUpperCase();
            tvAppName.setText(name.substring(0, 1));
            ivIcon.setImageResource(R.drawable.bg_app_name);
            ivIcon.setVisibility(View.VISIBLE);
            tvAppName.setVisibility(View.VISIBLE);
        }
    }
}
