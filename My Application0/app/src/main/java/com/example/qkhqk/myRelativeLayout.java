package com.example.qkhqk;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.qkhqk.myapplication.R;

public class myRelativeLayout extends RelativeLayout {
    private TextView Caption;
    private TextView CaptionLittle;

    private Button bBack, bNext;
    private Context context;
    private ImageView imageView, imageView1;
    TypedArray myAttrs;

    private  String cContextCaption="";
    public String getcContextCaption() {
        return cContextCaption;
    }
    public void setcContextCaption(String cContextCaption) {
        this.cContextCaption = cContextCaption;
        Caption.setText(cContextCaption);
    }

    private  String cContextCaptionLittle="";
    public String getcContextCaptionLittle() {
        return cContextCaptionLittle;
    }
    public void setcContextCaptionLittle(String cContextCaptionLittle) {
        this.cContextCaptionLittle = cContextCaptionLittle;
         CaptionLittle.setText(cContextCaptionLittle);
    }

    private   int myBackImageID=-1;
    public int getMyBackImageID() {
        return myBackImageID;
    }
    public void setMyBackImageID(int myBackImageID) {
        this.myBackImageID = myBackImageID;
        Drawable drawableback = myAttrs.getDrawable(myBackImageID);// getResources().getDrawable(R.drawable.ic_launcher);//getResources().getDrawable(myAttrs.getInt(R.styleable.myRelativeLayout_myBackImageID,0));//
        drawableback.setBounds(0, 0, drawableback.getMinimumWidth(), drawableback.getMinimumHeight());
        bBack.setCompoundDrawables(drawableback, null, null, null);
    }

    private   int myNextImageID=-2;
    public int getMyNextImageID() {
        return myNextImageID;
    }
    public void setMyNextImageID(int myNextImageID) {
        this.myNextImageID = myNextImageID;
        Drawable drawablenext= myAttrs.getDrawable(myNextImageID);// getResources().getDrawable(R.drawable.ic_launcher);//getResources().getDrawable(myAttrs.getInt(R.styleable.myRelativeLayout_myBackImageID,0));//
        drawablenext.setBounds(0, 0, drawablenext.getMinimumWidth(), drawablenext.getMinimumHeight());
        bNext.setCompoundDrawables(null, null, drawablenext, null);
    }

    public myRelativeLayout(Context context) {
        super(context, null);
        this.context = context;
        // TODO Auto-generated constructor stub
    }

    public myRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        this.context = context;
        name(attrs);
        // TODO Auto-generated constructor stub
    }

    private void name(AttributeSet attrs) {

        myAttrs = context.obtainStyledAttributes(attrs, R.styleable.myRelativeLayout);
        RelativeLayout relativeLayout = new RelativeLayout(this.context);
        LayoutParams lP = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        lP.height = 120;
        relativeLayout.setLayoutParams(lP);
        relativeLayout.setBackgroundColor(getResources().getColor(R.color.aqua));
        relativeLayout.setVisibility(VISIBLE);
        float f=myAttrs.getFloat(R.styleable.myRelativeLayout_myCaptionSize,0f);
        LayoutParams lpCaption = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        Caption = new TextView(this.context);
        Caption.setBackgroundColor(getResources().getColor(R.color.aqua));


        Caption.setGravity(Gravity.CENTER);
        Caption.setId(R.id.CaptionID);
        Caption.setText(myAttrs.getString(R.styleable.myRelativeLayout_myCaption));
        Caption.setTextSize(myAttrs.getInteger(R.styleable.myRelativeLayout_myCaptionSize,20));
        Caption.setHeight(myAttrs.getInteger(R.styleable.myRelativeLayout_myCaptionheight,20));
        Caption.setLayoutParams(lpCaption);
        Caption.setVisibility(VISIBLE);

        LayoutParams lpCaptionLittle = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        lpCaptionLittle.addRule(RelativeLayout.BELOW, R.id.CaptionID);
        CaptionLittle = new TextView(this.context);
        CaptionLittle.setBackgroundColor(getResources().getColor(R.color.aqua));
        CaptionLittle.setGravity(Gravity.CENTER);
        CaptionLittle.setId(R.id.CaptionLittleID);
        CaptionLittle.setText(myAttrs.getString(R.styleable.myRelativeLayout_myCaptionLittle));
        CaptionLittle.setTextSize(myAttrs.getInteger(R.styleable.myRelativeLayout_myCaptionLittleSize,20));
        CaptionLittle.setHeight(myAttrs.getInteger(R.styleable.myRelativeLayout_myCaptionLittleheight,20));
        CaptionLittle.setLayoutParams(lpCaptionLittle);

        bBack = new Button(this.context);
        LayoutParams lpBack = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        if(myBackImageID<=0)
           myBackImageID=R.styleable.myRelativeLayout_myBackImageID;

        Drawable drawableback = myAttrs.getDrawable(myBackImageID);// getResources().getDrawable(R.drawable.ic_launcher);//getResources().getDrawable(myAttrs.getInt(R.styleable.myRelativeLayout_myBackImageID,0));//
        drawableback.setBounds(0, 0, drawableback.getMinimumWidth(), drawableback.getMinimumHeight());
        bBack.setCompoundDrawables(drawableback, null, null, null);
        lpBack.addRule(RelativeLayout.ALIGN_LEFT | RelativeLayout.ALIGN_TOP);
        lpBack.addRule(RelativeLayout.ALIGN_LEFT, R.id.CaptionID);
        bBack.setText(myAttrs.getString(R.styleable.myRelativeLayout_myBackText));
        bBack.setBackgroundColor(Color.TRANSPARENT);
        bBack.setLayoutParams(lpBack);
        bBack.setId(R.id.myBackID);
        ;

        bNext = new Button(this.context);
        LayoutParams lpNext = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        if(myNextImageID<=0)
            myNextImageID=R.styleable.myRelativeLayout_myNextImageID;

        Drawable drawableNext = myAttrs.getDrawable(myNextImageID);
        drawableNext.setBounds(0, 0, drawableNext.getMinimumWidth(), drawableNext.getMinimumHeight());
        bNext.setCompoundDrawables(null, null, drawableNext, null);
        lpNext.addRule(RelativeLayout.ALIGN_RIGHT | RelativeLayout.ALIGN_TOP);
        lpNext.addRule(RelativeLayout.ALIGN_RIGHT, R.id.CaptionID);
        bNext.setText(myAttrs.getString(R.styleable.myRelativeLayout_myNextText));
        bNext.setBackgroundColor(Color.TRANSPARENT);
        bNext.setLayoutParams(lpNext);
        bNext.setId(R.id.myNextID);


        relativeLayout.addView(Caption);
        relativeLayout.addView(CaptionLittle);
        relativeLayout.addView(bBack);
        relativeLayout.addView(bNext);


        addView(relativeLayout);

    }


}
