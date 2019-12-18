package com.jike.cashocean.ui.bill;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jike.cashocean.Content.Code;
import com.jike.cashocean.R;
import com.jike.cashocean.component.GlideApp;
import com.jike.cashocean.model.NormalBean;
import com.jike.cashocean.model.SystemAppBean;
import com.jike.cashocean.ui.base.BaseActivity;
import com.jike.cashocean.ui.bill.compoment.DaggerBillComponent;
import com.jike.cashocean.ui.bill.contract.BillContract;
import com.jike.cashocean.ui.bill.module.BillModule;
import com.jike.cashocean.ui.bill.preseneter.AddBillPresenter;
import com.jike.cashocean.util.DateUtils;
import com.jike.cashocean.util.search.PopupRvClickListener;
import com.jike.cashocean.util.search.SearchResultPopuWindow;
import com.jike.cashocean.util.search.SearchResultRvAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;

public class AddBillActivity extends BaseActivity<AddBillPresenter> implements BillContract.View {


    @BindView(R.id.ibn_back)
    ImageButton ibnBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_title_right)
    TextView tvTitleRight;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.ll_search)
    LinearLayout llSearch;
    @BindView(R.id.iv_icon)
    ImageView ivIcon;
    @BindView(R.id.tv_app_name)
    TextView tvAppName;
    @BindView(R.id.tv_fill_money)
    EditText tvFillMoney;
    @BindView(R.id.tv_select_time)
    TextView tvSelectTime;
    @BindView(R.id.tv_query)
    TextView tvQuery;
    @BindView(R.id.tv_add_bill)
    TextView tvAddBill;

    @Override
    public int getContentLayout() {
        return R.layout.activity_add_bill;
    }

    @Override
    public void initInjector() {
        DaggerBillComponent.builder().billModule(new BillModule("添加账单", this)).build().inject(this);
    }

    private CharSequence cc;

    private void showChooseData() {
        KeyboardUtils.hideSoftInput(this);//隐藏软键盘
        Calendar startDate = Calendar.getInstance();
        Calendar currDate = Calendar.getInstance();
        startDate.set(currDate.get(Calendar.YEAR) - 50, 0, 1);
        Calendar endDate = Calendar.getInstance();
        endDate.set(currDate.get(Calendar.YEAR) + 100, 11, 31);
        TimePickerBuilder timePickerBuilder = new TimePickerBuilder(this,
                new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy");
                        String format = simpleDateFormat.format(date);
                        tvSelectTime.setText(format);
                    }
                })
                .setType(new boolean[]{true, true, true, false, false, false})
                .setCancelText(getString(R.string.cancel))
                .setDate(currDate)
                .setSubmitText(getString(R.string.confirm))
                .setOutSideCancelable(false)
                .setRangDate(startDate, endDate);
        timePickerBuilder.build().show();
    }

    @Override
    public void bindView(View view, Bundle sacedInstanceState) {
        ibnBack.setOnClickListener(view1 -> finish());
        tvTitle.setText(getString(R.string.add_bill));
        tvTitleRight.setVisibility(View.GONE);
        tvQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(etSearch.getText().toString())) {
                    ToastUtils.showShort(getString(R.string.add_bill_please_input_appname));
                } else {
                    if (popuWindow != null) {
                        popuWindow.dismiss();
                    }
                    appLogoChange(false, etSearch.getText().toString(), "");
                }
            }
        });
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                LogUtils.e("before:" + charSequence + ":" + i + ":" + i1 + ":" + i2);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                LogUtils.e("on:" + charSequence + ":" + i + ":" + i1 + ":" + i2);
//                ToastUtils.showShort(charSequence);
                cc = charSequence;
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (cc.length() > 0) {
                    if (isNeedSerach) {
                        searchStart(cc.toString());
                    } else {
                        isNeedSerach = true;
                    }
                }


            }
        });
        tvSelectTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChooseData();
            }
        });
        tvFillMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count != before) {
                    String sss = "";
                    String string = s.toString().replace(",", "");
                    int b = string.length() / 3;
                    if (string.length() >= 3) {
                        int yushu = string.length() % 3;
                        if (yushu == 0) {
                            b = string.length() / 3 - 1;
                            yushu = 3;
                        }
                        for (int i = 0; i < b; i++) {
                            sss = sss + string.substring(0, yushu) + "," + string.substring
                                    (yushu, 3);
                            string = string.substring(3, string.length());
                        }
                        sss = sss + string;
                        tvFillMoney.setText(sss);
                    }
                }
                tvFillMoney.setSelection(tvFillMoney.getText().length());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tvAddBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etSearch.getText().length() == 0) {
                    ToastUtils.showShort(R.string.add_bill_please_input_appname);
                    return;
                }
                if (TextUtils.isEmpty(tvFillMoney.getText().toString())) {
                    ToastUtils.showShort(R.string.add_bill_please_input_repaymoney);
                    return;
                }
                if (TextUtils.isEmpty(tvSelectTime.getText().toString())) {
                    ToastUtils.showShort(R.string.add_bill_please_select_repaytime);
                    return;
                }
                tvSelectTime.getText();
                showLoadingDialog();
                mPresenter.addBill(etSearch.getText().toString().trim(),
                        tvFillMoney.getText().toString().replace(",", ""),
                        DateUtils.date2TimeStamp(tvSelectTime.getText().toString() + " 23:59:59"));
            }
        });
    }

    @Override
    public void initData() {
        searchResultList = new ArrayList<>();
        allList = new ArrayList<>();
        mPresenter.getSystemApp();
    }

    @Override
    public void onReloadClick(View v) {

    }

    @Override
    public boolean needUserLoadSir() {
        return false;
    }

    @Override
    public void getSystemApplist(SystemAppBean systemAppBean) {
        allList.clear();
        allList.addAll(systemAppBean.getData().getDatas().getList());
    }

    @Override
    public void addBillResult(NormalBean normalBean) {
        hideLoadingDialog();
        if (normalBean == null) {
            return;
        }
        if (normalBean.getData().getCode() == Code.SUCCESS_CODE) {

            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish();
        } else {
            ToastUtils.showShort(normalBean.getData().getMsgX());
        }

    }

    /**
     * 搜索runnable,500ms不输入新字符，触发搜索，可自己修改
     */
    class SearchRunnable implements Runnable {
        String keyWord;
        Handler handler = new Handler();

        public void pushKeyWord(String keyWord) {
            this.keyWord = keyWord;
            handler.removeCallbacks(this);
            handler.postDelayed(this, 100);
        }

        @Override
        public void run() {
            //此处发起Http请求
            wordSearching = searchKeyWord;
            if (!TextUtils.isEmpty(searchKeyWord)) {
                getCompileList(searchKeyWord, allList);
            }
        }

    }

    private List<SystemAppBean.DataBean.DatasBean.ListBean> searchResultList;
    private List<SystemAppBean.DataBean.DatasBean.ListBean> allList;

    SearchRunnable searchRunnable = new SearchRunnable();
    String searchKeyWord;//用于在500ms内缓存用户输入的关键字
    String wordSearching;//正在搜索的关键字
    private SearchResultPopuWindow popuWindow;

    /**
     * 开始执行搜索，如果多次调用接口操作，需要手动结束上次请求，可在此方法内执行
     *
     * @param keyword
     */
    public void searchStart(final String keyword) {
        Log.i("MartinFilter", "getAllSearchTextFilter keyword=>" + keyword);
        searchKeyWord = keyword;
        searchRunnable.pushKeyWord(keyword);
    }

    boolean isNeedSerach = true;

    /**
     * 模拟模糊查询
     * <p>
     * 高级的：
     * https://www.jianshu.com/p/f4bc6655ec18
     */
    private void getCompileList(String keyWord,
                                List<SystemAppBean.DataBean.DatasBean.ListBean> list) {
        searchResultList.clear();
        Pattern pattern = Pattern.compile(keyWord.toLowerCase());
        for (int i = 0; i < list.size(); i++) {
            String s = list.get(i).getApp_name().toLowerCase();
            if (keyWord.length() <= s.length()) {
                Matcher matcher = pattern.matcher(s.substring(0, keyWord.length()));
                if (matcher.find()) {
                    searchResultList.add(list.get(i));
                }
            }
        }

        //adapter在最外层new出来，由builder传入window，方便点击事件的监听
        SearchResultRvAdapter adapter = new SearchResultRvAdapter(this, searchResultList);
        //new window
        popuWindow = new SearchResultPopuWindow.Builder(this)
                .setSearchResultRvAdapter(adapter)
                .build();
        //show方法传入editText，用来完成展示位置，和宽度计算
        popuWindow.show(llSearch);
        //recycleView各个部分的点击监听
        adapter.setPopupRvClickListener(new PopupRvClickListener() {
            @Override
            public void onItemChildClick(int id,
                                         int position,
                                         String content) {
//                Toast.makeText(AddBillActivity.this, position + content,
//                        Toast.LENGTH_SHORT).show();
                isNeedSerach = false;
                popuWindow.dismiss();
                etSearch.setText(content);
                etSearch.setSelection(etSearch.getText().length());
                appLogoChange(true,
                        searchResultList.get(position).getApp_name(),
                        searchResultList.get(position).getLogo_img());

            }
        });
    }

    public void appLogoChange(boolean isHave, String appName, String appLogo) {
        if (isHave) {
            tvAppName.setVisibility(View.GONE);
            ivIcon.setVisibility(View.VISIBLE);
            GlideApp.with(this)
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
