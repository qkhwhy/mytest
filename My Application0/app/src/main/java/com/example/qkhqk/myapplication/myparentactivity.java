package com.example.qkhqk.myapplication;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class myparentactivity extends Activity {
	Button myapp_title_btn_config = null;
	Button myapp_title_btn_back = null;
	TextView myapp_title_tv_caption = null;
	ImageView myapp_title_img_back=null;

	/**
	 * 显示自定义标题，可对返回按钮 myapp_title_btn_back,设置按钮 myapp_title_btn_config 进行clickevent监听
	 * @param activity  传入待添加此方法的Activity
	 * @param layoutid  传入待添加此方法的Layoutid
	 * @param cBackCaption  传入返回按钮上的文字信息
	 * @param cConfigCaption  传入设置按钮上的文字信息
	 * @param cTitleCaption  传入设置标题上的文字信息
	 */
	public    void displayCustomerCaption(Activity  activity, int layoutid, String cBackCaption, String cConfigCaption,String cTitleCaption) {
		//Activity activity=(Activity) context;

		activity.requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		activity.setContentView(layoutid);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.myapp_title);
		myapp_title_btn_config = (Button) this
				.findViewById(R.id.myapp_title_btn_config);
		myapp_title_btn_back = (Button) this
				.findViewById(R.id.myapp_title_btn_back);
		myapp_title_tv_caption = (TextView) this
				.findViewById(R.id.myapp_title_tv_caption);
		myapp_title_img_back =this
				.findViewById(R.id.myapp_title_img_back);

		myapp_title_tv_caption.setText(cTitleCaption);

		WindowManager wManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		Point p = new Point();
		wManager.getDefaultDisplay().getSize(p);
		int w = View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED);
		int h = View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED);
		myapp_title_btn_config.measure(w, h);
        myapp_title_btn_back.setText(cBackCaption);
		myapp_title_btn_config.setText(cConfigCaption);
		int iWidth = p.x - myapp_title_btn_config.getMeasuredWidth();
		myapp_title_btn_back.measure(w, h);
		iWidth = iWidth - myapp_title_btn_back.getMeasuredWidth();
		myapp_title_img_back.measure(w, h);
		iWidth = iWidth - myapp_title_img_back.getMeasuredWidth();
		myapp_title_tv_caption.setWidth(iWidth);

		myapp_title_btn_back.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

}
