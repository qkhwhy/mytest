package com.example.qkhqk.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class myapptest extends myparentactivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        displayCustomerCaption(this,R.layout.myapptest,"返回","设置","这是什么啊");
        myapp_title_btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
        myapp_title_img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
       myapp_title_btn_config.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

           String  cReturn=    uploadfile.aa("192.168.0.122");
               Toast.makeText(myapptest.this,cReturn,0).show();;

           }
       });

    }

    void back() {
        finish();;
    }




}
