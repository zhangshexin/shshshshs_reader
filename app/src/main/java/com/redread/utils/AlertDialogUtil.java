package com.redread.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.redread.R;
import com.redread.databinding.LayoutDialogGeneralBinding;
import com.redread.databinding.LayoutDialogProgressBinding;

/**
 * Created by zhangshexin on 2018/9/18.
 * 弹出框管理工具
 */

public class AlertDialogUtil {

    private Context context;
    private OnLeftBtnClickListener onLeftBtnClickListener;
    private OnRightBtnClickListener onRightBtnClickListener;

    public OnLeftBtnClickListener getOnLeftBtnClickListener() {
        return onLeftBtnClickListener;
    }

    public AlertDialogUtil setOnLeftBtnClickListener(OnLeftBtnClickListener onLeftBtnClickListener) {
        this.onLeftBtnClickListener = onLeftBtnClickListener;
        return this;
    }

    public OnRightBtnClickListener getOnRightBtnClickListener() {
        return onRightBtnClickListener;
    }

    public AlertDialogUtil setOnRightBtnClickListener(OnRightBtnClickListener onRightBtnClickListener) {
        this.onRightBtnClickListener = onRightBtnClickListener;
        return this;
    }
    private  LayoutInflater inflater;

    public AlertDialogUtil(Context context){
        this.context=context;
        inflater=LayoutInflater.from(context);
    }

    private AlertDialog dialog;

    /**
     * 显示一个普通的二次确认框
     * @param title
     * @param msg
     */
    public void showReentryDialog(int title,int msg){
        showReentryDialog(context.getString(title),context.getString(msg));
    }
    /**
     * 显示一个普通的二次确认框
     * @param title
     * @param msg
     */
    public AlertDialogUtil showReentryDialog(String title,String msg){

        dialog=new AlertDialog.Builder(context).create();
        LayoutDialogGeneralBinding binding= DataBindingUtil.inflate(inflater, R.layout.layout_dialog_general,null,false);
        View view=binding.getRoot();
        if(!TextUtils.isEmpty(title))
            binding.dialogTitle.setText(title);
        if(!TextUtils.isEmpty(msg))
            binding.dialogMsg.setText(msg);
        binding.dialogLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onLeftBtnClickListener!=null)
                    onLeftBtnClickListener.leftClickListener(v);
            }
        });
        binding.dialogRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onRightBtnClickListener!=null)
                    onRightBtnClickListener.rightClickListener(v,DIALOG_GENERAL);
            }
        });
        dialog.show();
        dialog.getWindow().setContentView(view);
        return this;
    }
    private LayoutDialogProgressBinding layoutDialogProgressBinding;
    /**
     * 展示一个带进度的提示，如果没有
     * @return
     */
    public AlertDialogUtil showProgressDialog(int max){
        layoutDialogProgressBinding=DataBindingUtil.inflate(inflater,R.layout.layout_dialog_progress,null,false);
        View view=layoutDialogProgressBinding.getRoot();
        layoutDialogProgressBinding.dialogAccurateCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onRightBtnClickListener!=null)
                    onRightBtnClickListener.rightClickListener(v,DIALOG_PROGRESS);
            }
        });
        layoutDialogProgressBinding.dialogProgressAccurate.setMax(max);
        dialog.getWindow().setContentView(view);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        return this;
    }

    public LayoutDialogProgressBinding getLayoutDialogProgressBinding(){
        return layoutDialogProgressBinding;
    }
    public void dismiss(){
        dialog.dismiss();
    }

    public static final int DIALOG_PROGRESS=0;
    public static final int DIALOG_GENERAL=1;
    public interface OnLeftBtnClickListener{
        void leftClickListener(View v);
    }
    public interface OnRightBtnClickListener{
        void rightClickListener(View v,int statue);
    }
}
