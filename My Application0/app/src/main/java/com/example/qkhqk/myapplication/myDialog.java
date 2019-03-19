package com.example.qkhqk.myapplication;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

public class myDialog {

  public static class myCustomerDialog extends Dialog {

        TextView tv_dialog_confirm, tv_dialog_caption, tv_dialog_cancel, tv_dialog_context;

        public void setCaption(String cCaption) {

            tv_dialog_caption.setText(cCaption);
            ;
        }

        public void setContext(String cContext) {
            tv_dialog_context.setText(cContext);
            ;
        }

        public void setCancel(String cCancel) {
            tv_dialog_cancel.setText(cCancel);
            ;
        }

        public void setConfirm(String cConfirm) {
            tv_dialog_confirm.setText(cConfirm);
            ;
        }


        public myCustomerDialog(Context context, int width, int height, View layout, int style) {

            super(context, style);

            setContentView(layout);

            Window window = getWindow();

            WindowManager.LayoutParams params = window.getAttributes();

            params.gravity = Gravity.CENTER;

            window.setAttributes(params);

        }

        private void alpha(float falpha) {
            Window window = getWindow();

            WindowManager.LayoutParams params = window.getAttributes();

            params.gravity = Gravity.CENTER;
            params.alpha = falpha;
            window.setAttributes(params);
        }

        public myCustomerDialog(Context context, int width, int height, int layoutid, int style, final mydialog_clickevent mydialogClickevent) {

            super(context, style);
            View view = getLayoutInflater().inflate(R.layout.dialog_layout, null);
            setContentView(view);

            tv_dialog_caption = view.findViewById(R.id.tv_dialog_caption);
            tv_dialog_context = view.findViewById(R.id.tv_dialog_context);

            alpha(1f);
            tv_dialog_confirm = view.findViewById(R.id.tv_dialog_confirm);
            tv_dialog_confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mydialogClickevent.confirm_click();
                    alpha(1f);
                    dismiss();
                }
            });

            tv_dialog_cancel = view.findViewById(R.id.tv_dialog_cancel);
            tv_dialog_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mydialogClickevent.cancle_click();
                    alpha(1f);
                    dismiss();
                    ;
                }
            });
            setCanceledOnTouchOutside(false);
        }

        public myCustomerDialog(Context context, int width, int height, View layout, int style, mydialog_clickevent mydialogClickevent) {

            super(context, style);

            setContentView(layout);

            Window window = getWindow();

            WindowManager.LayoutParams params = window.getAttributes();

            params.gravity = Gravity.CENTER;

            window.setAttributes(params);
        }

        public interface mydialog_clickevent {
            public void cancle_click();

            public void confirm_click();
        }

    }

  class myCustomerPopupWindow extends PopupWindow {
        PopupWindow popupWindow;

        public myCustomerPopupWindow(final Context context, int ContentViewLayoutID, View ParentView) {
            View contentview;
            contentview = LayoutInflater.from(context).inflate(ContentViewLayoutID, null);
            final PopupWindow popupWindow = new PopupWindow(contentview, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT, true);
            popupWindow.showAtLocation(ParentView, Gravity.CENTER, 0, 0);
            WindowManager.LayoutParams lp = ((Activity) context).getWindow().getAttributes();
            lp.alpha = 0.4f;

            ((Activity) context).getWindow().setAttributes(lp);

            Button button = contentview.findViewById(R.id.bt1);

            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    WindowManager.LayoutParams lp = ((Activity) context).getWindow().getAttributes();
                    lp.alpha = 1f;
                    ((Activity) context).getWindow().setAttributes(lp);
                }
            });

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();
                }
            });
        }

        /**
         * 弹出自动义PopupWindow
         *
         * @param context             调用者的上、下文信息
         * @param ContentViewLayoutID 待显示的弹出窗体内部布局ID
         * @param ParentView          要显示的于父对象的位置
         * @param x                   弹出窗体的X轴坐标 或 偏移量
         * @param y                   弹出窗体的Y轴坐标  或 偏移量
         * @param iGravity            对象方式 如：Gravity.CENTER
         * @param ishowType           显示方式：1 showAtLocation(ParentView, iGravity, x, y) ，2  showAsDropDown(ParentView,x,y,iGravity)，3 showAsDropDown(ParentView);
         */
        public myCustomerPopupWindow(final Context context, int ContentViewLayoutID, View ParentView, int x, int y, int iGravity, int ishowType) {
            View contentview = LayoutInflater.from(context).inflate(ContentViewLayoutID, null);
            popupWindow = new PopupWindow(contentview, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT, true);
            switch (ishowType) {
                case 1:
                    popupWindow.showAtLocation(ParentView, iGravity, x, y);
                    break;
                case 2:
                    popupWindow.showAsDropDown(ParentView, x, y, iGravity);
                    break;
                case 3:
                    popupWindow.showAsDropDown(ParentView);
                    break;
            }
            WindowManager.LayoutParams lp = ((Activity) context).getWindow().getAttributes();
            lp.alpha = 0.4f;

            ((Activity) context).getWindow().setAttributes(lp);

            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    WindowManager.LayoutParams lp = ((Activity) context).getWindow().getAttributes();
                    lp.alpha = 1f;
                    ((Activity) context).getWindow().setAttributes(lp);
                }
            });


        }

        public myCustomerPopupWindow(final Context context, View ContentView, View ParentView, int x, int y, int iGravity, int ishowType) {
            // View   contentview = LayoutInflater.from(context).inflate(ContentViewLayoutID, null);
            popupWindow = new PopupWindow(ContentView, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT, true);
            switch (ishowType) {
                case 1:
                    popupWindow.showAtLocation(ParentView, iGravity, x, y);
                    break;
                case 2:
                    popupWindow.showAsDropDown(ParentView, x, y, iGravity);
                    break;
                case 3:
                    popupWindow.showAsDropDown(ParentView);
                    break;
            }
            WindowManager.LayoutParams lp = ((Activity) context).getWindow().getAttributes();
            lp.alpha = 0.4f;

            ((Activity) context).getWindow().setAttributes(lp);

            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    WindowManager.LayoutParams lp = ((Activity) context).getWindow().getAttributes();
                    lp.alpha = 1f;
                    ((Activity) context).getWindow().setAttributes(lp);
                }
            });


        }

        public void dismiss() {
            popupWindow.dismiss();
        }

    }


}
