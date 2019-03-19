package com.example.qkhqk.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.TextView;

@SuppressLint("AppCompatCustomView")
public class myEditText extends  EditText implements TextWatcher,OnFocusChangeListener {

    private Drawable mClearDrawable;
    private TextView tv;
    /**
     * 控件是否有焦点
     */
    private boolean hasFoucs;

    private boolean isShow = false;
    public myEditText(Context context) {
        super(context);
        init();
    }

    public myEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
       TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.myEditText, 0, 0);
        int i=typedArray.getColor(R.styleable.myEditText_mytextcolor,0);
        String c=typedArray.getString(R.styleable.myEditText_mytext);
         typedArray.recycle();
        this.setBackgroundColor(i);
        this.setText(c);


//       this.setCompoundDrawables(getResources().getDrawable(R.drawable.delete),null,getResources().getDrawable(R.drawable.delete),null);
//       this.setPadding(this.getPaddingLeft(),this.getPaddingTop(),this.getPaddingRight(),this.getPaddingBottom());

        init();
    }

    public myEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public myEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if(hasFoucs){
            setClearIconVisible(s.length() > 0);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        this.hasFoucs = hasFocus;
        if (hasFocus) {
            setClearIconVisible(getText().length() > 0);
        } else {
            setClearIconVisible(false);
        }
    }

    private void init() {
        //获取EditText的DrawableRight,假如没有设置我们就使用默认的图片,getCompoundDrawables()获取Drawable的四个位置的数组

          mClearDrawable = getCompoundDrawables()[2];
        if (mClearDrawable == null) {
            mClearDrawable = getResources().getDrawable(R.drawable.delete);
//            throw new NullPointerException("You can add drawableRight attribute in XML");
        }
        //设置图标的位置以及大小,getIntrinsicWidth()获取显示出来的大小而不是原图片的带小
        mClearDrawable.setBounds(0, 0, 64, 64);
        //默认设置隐藏图标
        setClearIconVisible(false);
        //设置焦点改变的监听
        setOnFocusChangeListener(this);
        //设置输入框里面内容发生改变的监听
        addTextChangedListener(this);
    }

    protected void setClearIconVisible(boolean visible) {
        Drawable right = visible ? mClearDrawable : null;
        setCompoundDrawables(getCompoundDrawables()[0],
                getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (getCompoundDrawables()[2] != null) {
                 boolean touchable = event.getX() > (getWidth() - getTotalPaddingRight())
                        && (event.getX() < ((getWidth() - getPaddingRight())));

                if (touchable) {
                    this.setText("");
                }
            }

        }

        return super.onTouchEvent(event);
    }
}
