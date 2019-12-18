package com.jike.cashocean.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jike.cashocean.R;
import com.jike.cashocean.component.GlideApp;
import com.jike.cashocean.interfaceUtils.RvClickListener;
import com.jike.cashocean.model.FindHomeBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyFindHomeAdapter extends BaseListAdapter<FindHomeBean.DataBean.DatasBean.ListBean> {
    Context context;

    public MyFindHomeAdapter(Context context, List<FindHomeBean.DataBean.DatasBean.ListBean> list) {
        super(context, list);
        this.context = context;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        final FindHomeHolder holder;
        if (view != null) {
            holder = (FindHomeHolder) view.getTag();
        } else {
            view = mInflater.inflate(R.layout.item_find_home, viewGroup, false);
            holder = new FindHomeHolder(view);
            view.setTag(holder);
        }
        FindHomeBean.DataBean.DatasBean.ListBean item = getItem(i);
        holder.tvSeeNum.setText(item.getRead_num() + "");
        holder.tvTitle.setText(item.getTitle());
        GlideApp.with(context)
                .load(item.getLogo_img())
                .placeholder(R.mipmap.placeholed)
                .fitCenter()
                .into(holder.iviv);
        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rvClickListener.onItemChildClick(item.getId(), i, item.getTitle());
            }
        });

        return view;
    }

    private RvClickListener rvClickListener;


    public void setOnItemClick(RvClickListener rvClickListener) {
        this.rvClickListener = rvClickListener;
    }


    class FindHomeHolder {
        @BindView(R.id.item_tv_title)
        TextView tvTitle;
        @BindView(R.id.item_tv_see_num)
        TextView tvSeeNum;
        @BindView(R.id.item_iv_iv)
        ImageView iviv;
        @BindView(R.id.item_ll)
        LinearLayout ll;

        public FindHomeHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
