package com.jike.cashocean.util;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jike.cashocean.R;

/**
 * @author Ming
 * @Date on 2019/12/5
 * @Description
 */
public class ChooseDialog extends BaseDialogFragment {
    public interface OnClickMakeSure {
        void chooseCancel();

        void chooseSure();
    }

    private String title;

    public ChooseDialog(String title) {
        this.title = title;
    }

    public OnClickMakeSure onClickMakeSure;

    public void setOnClickMakeSure(OnClickMakeSure onClickMakeSure) {
        this.onClickMakeSure = onClickMakeSure;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_choose, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView tvCancel = view.findViewById(R.id.tv_cancel);
        TextView tvDesc = view.findViewById(R.id.tv_content_desc);
        tvDesc.setText(title);
        TextView tvConfirm = view.findViewById(R.id.tv_confirm);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickMakeSure.chooseCancel();
            }
        });
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickMakeSure.chooseSure();
            }
        });
    }
}
