package com.jike.cashocean.ui.find;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.jike.cashocean.R;
import com.jike.cashocean.ui.MainNewActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CardTestResultActivity extends AppCompatActivity {

    @BindView(R.id.ibn_back)
    ImageButton ibnBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_title_right)
    TextView tvTitleRight;
    @BindView(R.id.ll_to_loan)
    LinearLayout llToLoan;
    @BindView(R.id.tv_to_loan)
    TextView tvToLoan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_test_result);
        ButterKnife.bind(this);
        ibnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvTitle.setText("");
        tvToLoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                MainNewActivity.changePage(0);
            }
        });
        llToLoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                MainNewActivity.changePage(0);
            }
        });
    }
}
