package com.example.qkhqk.myapplication;

import android.R.integer;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class myapp_title extends Activity {
     Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		context=this;
		displaycaptionlocation(this,R.layout.myapp_title_test);

	}

	public    void displaycaptionlocation( Activity  activity, int layoutid) {
		//Activity activity=(Activity) context;
		Button myapp_title_btn_config = null;
		Button myapp_title_btn_back = null;
		TextView myapp_title_tv_caption = null;

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

		WindowManager wManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		Point p = new Point();
		wManager.getDefaultDisplay().getSize(p);
		int w = View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED);
		int h = View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED);
		myapp_title_btn_config.measure(w, h);

		int iWidth = p.x - myapp_title_btn_config.getMeasuredWidth();
		myapp_title_btn_back.measure(w, h);
		iWidth = iWidth - myapp_title_btn_back.getMeasuredWidth();
		myapp_title_tv_caption.setWidth(iWidth);

		myapp_title_btn_config.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

}
