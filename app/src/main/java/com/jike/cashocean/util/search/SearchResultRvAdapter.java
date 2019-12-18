package com.jike.cashocean.util.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jike.cashocean.R;
import com.jike.cashocean.model.SystemAppBean;

import java.util.List;


public class SearchResultRvAdapter extends RecyclerView.Adapter<SearchResultRvAdapter.ViewHolder> {

    Context mContext;
    List<SystemAppBean.DataBean.DatasBean.ListBean> list;
    PopupRvClickListener popupRvClickListener;

    public SearchResultRvAdapter(Context context,
                                 List<SystemAppBean.DataBean.DatasBean.ListBean> data) {
        mContext = context;
        list = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_search_result_layout,
                parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.content.setText(list.get(position).getApp_name());
        holder.parentLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupRvClickListener.onItemChildClick(holder.parentLl.getId(), position,
                        list.get(position).getApp_name());
            }
        });
//        holder.iconRight.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                popupRvClickListener.onItemChildClick(holder.iconRight.getId(), position,
//                        list.get(position).getApp_name());
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView content;
        private ImageView iconRight;
        private LinearLayout parentLl;

        public ViewHolder(View itemView) {
            super(itemView);
            content = itemView.findViewById(R.id.item_search_result_content);
            iconRight = itemView.findViewById(R.id.item_search_result_icon_right);
            parentLl = itemView.findViewById(R.id.item_search_result_parent_ll);
        }
    }

    public PopupRvClickListener getPopupRvClickListener() {
        return popupRvClickListener;
    }

    public void setPopupRvClickListener(PopupRvClickListener popupRvClickListener) {
        this.popupRvClickListener = popupRvClickListener;
    }
}
