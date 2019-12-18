package com.jike.cashocean.ui.find;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.LogUtils;
import com.jike.cashocean.R;
import com.jike.cashocean.component.GlideApp;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PagerInfoActivity extends AppCompatActivity {

    @BindView(R.id.ibn_back)
    ImageButton ibnBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_title_right)
    TextView tvTitleRight;
    @BindView(R.id.tv_info_title)
    TextView tvInfoTitle;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_see_num)
    TextView tvSeeNum;
    @BindView(R.id.iv_info)
    ImageView ivInfo;
    @BindView(R.id.tv_info)
    TextView tvInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager_info);
        ButterKnife.bind(this);

        ibnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String time = intent.getStringExtra("time");
        int seeNum = intent.getIntExtra("see_num", 0);
        String imageUrl = intent.getStringExtra("image_url");
        String textInfo = intent.getStringExtra("info");

        tvInfo.setText(Html.fromHtml(textInfo));
        tvInfoTitle.setText(title);
        tvTime.setText(getString(R.string.release_time) + ":" + time);
        tvSeeNum.setText(seeNum + "");

        GlideApp.with(this)
                .load(imageUrl)
                .placeholder(R.mipmap.placeholed)
                .fitCenter()
                .into(ivInfo);
    }
}
