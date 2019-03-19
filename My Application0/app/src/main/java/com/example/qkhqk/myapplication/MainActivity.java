package com.example.qkhqk.myapplication;

import android.Manifest;
import android.app.ActionBar;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import android.hardware.Camera;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.View;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qkhqk.Camera2;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;
import scut.carson_ho.diy_view.SuperEditText;

import com.honeywell.aidc.AidcManager;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private PopupWindow popupWindow;

    myDialog.myCustomerDialog myDialog;
    AidcManager aidcManager = null;
    Context context;
    TextView t1;
    ImageView imageView;
    Camera camera;
    SurfaceView surfaceView;
    TextureView t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        this.context = this;
        t1 = this.findViewById(R.id.t1);
        imageView = this.findViewById(R.id.imageView);
        surfaceView = this.findViewById(R.id.surfaceView);
        myCamera.Camera_SurfaceView.surfaceView =surfaceView;


//        myDataBase   dbHelper = new myDataBase(context, "BookStore.db", null, 1);
//        String cDBPath=getApplicationContext().getDatabasePath("BookStore.db").getAbsolutePath();
//
//        File file=new File(cDBPath);
//        if(file.exists()) {
//            Toast.makeText(getBaseContext(), "数据库已存在", 1).show();
//
//        }
//        else
//        {
//            Toast.makeText(getBaseContext(), "数据库不存在", 1).show();
//        }
//        dbHelper.getWritableDatabase();
//        Toast.makeText(getBaseContext(),cDBPath+"\r\n"+getApplicationContext().getFilesDir().getParent()+File.separator+"database",1).show();
//       file=new File(cDBPath);
//        if(file.exists()) {
//            Toast.makeText(getBaseContext(), "数据库已存在", 1).show();
//            file.delete(); ;
//        }
//        else
//        {
//            Toast.makeText(getBaseContext(), "数据库不存在", 1).show();
//        }
        aa(this);

        this.t=this.findViewById(R.id.textureView);
        myCamera.Camera_SurfaceTexture.textureView=t;
        myCamera.Camera_SurfaceTexture.pcontext=this;

        myCamera.Camera_SurfaceTexture.Camera_open(this.context,this.t);




        new RxPermissions(this).requestEach(Manifest.permission.READ_EXTERNAL_STORAGE
                , Manifest.permission.WRITE_EXTERNAL_STORAGE
                , Manifest.permission.INTERNET
                , Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS
                , Manifest.permission.CAMERA
        )
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {
                            String filePath = "/sdcard/xxx.xlsx";

                            Toast.makeText(getBaseContext(), "OK", 0).show();
                        }
                    }
                });


    }

    public  void barcode(View v)
    {
        Intent iscan=new Intent(MainActivity.this,  com.dtr.zxing.activity.CaptureActivity.class);
        startActivityForResult(iscan,0);
    }


    public void popc(View view) {

    }
    public   String  getVersionname(Context context) {
        PackageManager manager=context.getPackageManager();
        try {
            PackageInfo info=manager.getPackageInfo(context.getPackageName().toString(), 0);

            return  info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block

            e.printStackTrace();
            return "";
        }
    }

    public   String  getVersionCode(Context context) {

        PackageManager manager=context.getPackageManager();
        try {
            PackageInfo info=manager.getPackageInfo(context.getPackageName().toString(), 0);

            return   info.versionCode+""+Build.VERSION.SDK_INT+Build.VERSION_CODES.P;
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block

            e.printStackTrace();
            return "";
        }
    }
    public void mycamerasurface(View view1) {
       myCamera.Camera_SurfaceView.Camera_open();

    }

    public void mycamera2(View view1)
    {

//        this.t=this.findViewById(R.id.textureView);
         myCamera.Camera_SurfaceTexture.Camera_open( );
       //  Toast.makeText(this,"sfsfsdfds",Toast.LENGTH_SHORT).show();;



//        myCamera.Camera2_SurfaceTexture.textureView=t;
//        myCamera.Camera2_SurfaceTexture.context=context;
//        myCamera.Camera2_SurfaceTexture.Camera_open();
    }
    public void take(View view1) {
        myCamera.Camera_SurfaceTexture.cFilePath=Environment.getExternalStorageDirectory()+File.separator+"Photos";
        myCamera.Camera_SurfaceTexture.cFileName="Pic_"+(new SimpleDateFormat("yyyyMMddHHmmss")).format(new Date())+".jpg";
        myCamera.Camera_SurfaceTexture.Camera_teke( ); ;
    }

    public void mycamerastop(View view1)
    {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
        Date date=new Date();
        myCamera.Camera_SurfaceView.Camera_takePicture(Environment.getExternalStorageDirectory()+File.separator+"Pictures",sdf.format(date).toString());
    }

    public void mycamera(View view) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();

        myCamera.Camera_Intent.File_Name = "AAA" + sdf.format(date).toString() + "" + ".PNG";
        myCamera.Camera_Intent.FILE_PATH = Environment.getExternalStorageDirectory().toString() + File.separator + "photos";
        myCamera.Camera_Intent.imageView = imageView;
        myCamera.Camera_Intent.myCamera(context)
        ;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (resultCode == 0) {
                Bundle bundle = data.getExtras();
                String scanResult = bundle.getString("result")+bundle.getString("Format");
                t1.setText(scanResult);
            }
            else {
                myCamera.Camera_Intent.onActivityResult(requestCode, resultCode, data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pops(View view) {

        SuperEditText set = this.findViewById(R.id.t11);
        set.setText("我是介大本营");
        View contentview;
        contentview = LayoutInflater.from(this).inflate(R.layout.popwindowlayout, null);
        popupWindow = new PopupWindow(contentview, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT, true);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.4f;
        getWindow().setAttributes(lp);
//        popupWindow.showAsDropDown(view);
        Button button = contentview.findViewById(R.id.bt1);

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

    }

    public void dialog(View viewp) {

        myDialog = new myDialog.myCustomerDialog(this, 0, 0, R.layout.dialog_layout, R.style.DialogTheme, new myDialog.myCustomerDialog.mydialog_clickevent() {
            @Override
            public void cancle_click() {
                Toast.makeText(context, "取消", 0).show();
            }

            @Override
            public void confirm_click() {
                Toast.makeText(context, "确定", 0).show();
            }
        });
        myDialog.setCancel("取消息");
        myDialog.setContext("背景色彩一直没法去掉！");
        myDialog.setCaption("友情提醒");
        myDialog.show();


    }


    public void refreshFile(View viewi) {
        try {
            String filePath = Environment.getExternalStorageDirectory() + File.separator + "盘点数据3" + File.separator;
            File file = new File(filePath);
            if (!file.exists()) file.mkdirs();


            Intent scanIntent = new Intent(
                    "android.intent.action.MEDIA_SCANNER_SCAN_FILE");
            scanIntent.setData(Uri.fromFile(new File(filePath))); // DIR
            // 替换为你想要刷新的目录
            context.sendBroadcast(scanIntent);

            MediaScannerConnection.scanFile(this, new String[]{file.getAbsolutePath()}, null, null);


            filePath = filePath + File.separator + "aaa2.xls";
            file = new File(filePath);
            if (!file.exists()) file.createNewFile();
            Uri data = Uri.parse(filePath);
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                    data));

            MediaScannerConnection.scanFile(this, new String[]{file.getAbsolutePath()}, null, null);

            MimeTypeMap mtm = MimeTypeMap.getSingleton();
            MediaScannerConnection.scanFile(context,
                    new String[]{file.toString()},
                    new String[]{mtm.getMimeTypeFromExtension(file.toString().substring(file.toString().lastIndexOf(".") + 1))},
                    new MediaScannerConnection.OnScanCompletedListener() {
                        @Override
                        public void onScanCompleted(final String path, final Uri uri) {
                            Toast.makeText(context, "刷新完毕", 0).show();
                        }
                    });
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), 0).show();
        } finally {
        }
    }


    public void click(View view) {
        int i = 0;
        if (i == 0) {
            myfileupload.myhttp.uploadfileSocket(context, "http://192.168.0.122/UploadFile.ashx", "close24.png");
            return;
        } else {
            final Handler handler = new Handler() {

                @Override
                public void handleMessage(Message msg) {
                    // TODO Auto-generated method stub
                    super.handleMessage(msg);
                    if (msg.what == 1) {
                        if ((int) msg.obj == 1) {
                            Toast.makeText(context, "上传成功！", 0).show();
                        } else {
                            Toast.makeText(context, "上传失败！", 0).show();

                        }
                    }
                }
            };

            myfileupload.myhttp.uploadHandle(context,
                    "http://192.168.0.122/UploadFile.ashx", "close24.png",
                    handler);


            Intent intent = new Intent();
            intent.setClass(MainActivity.this, myapptest.class);
            startActivity(intent);
        }
    }

    void aa(Context context) {
        StringBuilder sb = new StringBuilder();

        sb.append("CREATE TABLE t_ScanDetails ( id  INTEGER   PRIMARY KEY AUTOINCREMENT, cDeviceName TEXT (20),cBarCode  TEXT (30),cUserCode   TEXT (20), cUserName   TEXT (20), cDateTime   TEXT (20)  );\r\n");

        sb.append("CREATE TABLE t_order (  item  VARCHAR (30), cOrder VARCHAR (30),  cCode TEXT,  cDeep TEXT, cColor TEXT,   cStyle TEXT,  cUnit TEXT,  iQty  INTEGER (10), iArea DECIMAL (18, 3), iAreaTotal DECIMAL (18, 3), iScanQty   INTEGER (10)  ); \r\n");
        sb.append("CREATE TABLE t_ordertemp ( item  VARCHAR (30),  cOrder VARCHAR (30), cCode TEXT, cDeep TEXT, cColor TEXT, cStyle TEXT, cUnit TEXT, iQty  INTEGER (10), iArea DECIMAL (18, 3),  iAreaTotal DECIMAL (18, 3) );\r\n ");
        sb.append("CREATE TABLE t_scan ( cOrder   VARCHAR (30), item VARCHAR (30), cCode    TEXT, cBarCode TEXT, cSN TEXT, cDate    TEXT,  iQty INTEGER (10) ); \r\n");

        sb.append("CREATE TABLE t_user (cUserName TEXT, cUserCode TEXT, cPerForm TEXT, bUse TEXT,cPassWord TEXT); \r\n");
        sb.append("insert into t_user (cUserName, cUserCode,cPerForm,bUse,cPassWord)  values(\"管理员\",\"admin\",\"M\",\"1\",\"13063806084\"); \r\n");


        sb.append("CREATE TABLE t_Device (cDeviceName TEXT ); \r\n");
        // sb.append("insert into t_Device (cDeviceName )  values(\"PDA-0001\"  ); \r\n");
        StringBuffer sba = new StringBuffer();
        sba.append(sb.toString());
        myDBHelper.cDataBaseName = "asdf331.db";
        myDBHelper.cTables = sba;
        myDBHelper.context = context;
//        myDBHelper dbHelper=new myDBHelper(context, myDBHelper.cDataBaseName,null,1);
//        dbHelper.getWritableDatabase();
        myDBHelper.myDBOpen(true);
        ContentValues cv = new ContentValues();
        cv.put("cDeviceName", "sdfsdfsdf");
        myDBHelper.sqlitedb.insert("t_Device", null, cv);
        Toast.makeText(context, myDBHelper.myGetSingle("select  cDeviceName from  t_Device", ""), 1).show();
        ;

    }

    public  void click1(View view)
    {
        this.setContentView(R.layout.culayout);
         com.example.qkhqk.myRelativeLayout myrelativeLayout=(com.example.qkhqk.myRelativeLayout)this.findViewById(R.id.m1);
         Button b=   (Button) myrelativeLayout.findViewById(R.id.myBackID)   ;
         b.setText("第一个");

        com.example.qkhqk.myRelativeLayout myrelativeLayout2=(com.example.qkhqk.myRelativeLayout)this.findViewById(R.id.m2);
        Button b2=   (Button) myrelativeLayout2.findViewById(R.id.myNextID)   ;
        b2.setText("第二个");

        myrelativeLayout.setcContextCaption("苏州码诚电子有限公司");
        try {
            myrelativeLayout.setMyBackImageID(  R.drawable.delete);
            myrelativeLayout.setMyNextImageID(  R.drawable.barcode_example_icon);
        } catch (Exception e) {
            e.printStackTrace();
            String cError=e.getMessage();
            Toast.makeText(this,cError,1).show();
        }

    }
    public void click01(View view) {
        //myCustomerPopupWindow mypop=new myCustomerPopupWindow(context,R.layout.popwindowlayout,view);
        // myCustomerPopupWindow mypop = new myCustomerPopupWindow(context, R.layout.dialog_layout, view, 0, 0, Gravity.CENTER, 2);

        //http://192.168.0.122/webservice1.asmx?op=getSystemDate
        new Thread(new Runnable() {
            @Override
            public void run() {

                final String SERVER_URL = "http://192.168.0.122/webservice1.asmx/getSystemDate";
                HttpPost request = new HttpPost(SERVER_URL); // 根据内容来源地址创建一个Http请求
                request.setHeader("Accept", "text/html, application/xhtml+xml, */*");
                request.addHeader("Content-Type", "application/x-www-form-urlencoded");//必须要添加该Http头才能调用WebMethod时返回JSON数据
                request.addHeader("Connection", "Keep-Alive");
                try {
                    HttpResponse httpResponse = new DefaultHttpClient().execute(request); // 发送请求并获取反馈
                    int iReturnCode = httpResponse.getStatusLine().getStatusCode();
                    if (iReturnCode == 200) {
                        String result = EntityUtils.toString(httpResponse.getEntity());
                        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                        XmlPullParser parser = factory.newPullParser();
                        parser.setInput(new StringReader(result));
                        int eventType = parser.getEventType();
                        while (eventType != XmlPullParser.END_DOCUMENT) {
                            // String ccc=    XmlPullParser.T;
                            String Name = parser.getName();
                            if (Name != null && Name.equals("string")) {
                                String cc = parser.toString();
                                String bb = cc;
                            }
                            eventType = parser.next();

                        }
                    } else if (iReturnCode == 500) {
                        //  t1.setText(iReturnCode+"");
                    } else   //StatusCode为200表示与服务端连接成功，404为连接不成功
                    {

                        //  t1.setText(iReturnCode+"");

                    }

                } catch (IOException e) {

                    String cErr1 = e.getMessage();
                } catch (XmlPullParserException e) {
                    String cErr = e.getMessage();
                }
            }
        }).start();
    }

    public void mycameraclose(View view) {
        myCamera.Camera_SurfaceTexture.Camera_Close();
    }
}

