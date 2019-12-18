package com.jike.cashocean.ui.bill;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.jike.cashocean.R;
import com.jike.cashocean.component.GlideApp;
import com.jike.cashocean.util.MoneyUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BillInfoActivity extends AppCompatActivity {

    @BindView(R.id.ibn_back)
    ImageButton ibnBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_title_right)
    TextView tvTitleRight;
    @BindView(R.id.iv_icon)
    ImageView ivIcon;
    @BindView(R.id.tv_app_name)
    TextView tvAppName;
    @BindView(R.id.tv_fill_money)
    TextView tvFillMoney;
    @BindView(R.id.tv_repayment_time)
    TextView tvRepaymentTime;
    @BindView(R.id.tv_add_bill_time)
    TextView tvAddBillTime;
    @BindView(R.id.tv_app_icon)
    TextView tvAppIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_info);
        ButterKnife.bind(this);
        initDate();
        initView();
    }

    String app_name;
    String app_logo;
    String loan_money;
    String repayment_date;
    String add_bill_time;

    private void initDate() {
        Intent intent = getIntent();
        app_name = intent.getStringExtra("app_name");
        app_logo = intent.getStringExtra("app_logo");
        loan_money = intent.getStringExtra("loan_money");
        repayment_date = intent.getStringExtra("repayment_date");
        add_bill_time = intent.getStringExtra("add_bill_time");
    }

    private void initView() {

        tvTitle.setText(getString(R.string.bill_info_page));
        ibnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvAppName.setText(app_name);
        tvFillMoney.setText(MoneyUtils.fomatMoney(loan_money));
        tvRepaymentTime.setText(repayment_date);
        tvAddBillTime.setText(add_bill_time);
        if (TextUtils.isEmpty(app_logo)) {
            ivIcon.setImageResource(R.drawable.bg_app_name);
            tvAppIcon.setText(app_name.substring(0, 1).toUpperCase());
            ivIcon.setVisibility(View.VISIBLE);
            tvAppIcon.setVisibility(View.VISIBLE);
        } else {
            tvAppIcon.setVisibility(View.GONE);
            GlideApp.with(this)
                    .load(app_logo)
                    .placeholder(R.drawable.bg_app_name)
                    .fitCenter()
                    .into(ivIcon);
        }
    }
}
