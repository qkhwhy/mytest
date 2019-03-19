package com.example.qkhqk.myapplication;


        import java.io.DataOutputStream;
        import java.io.File;
        import java.io.FileInputStream;
        import java.io.IOException;
        import java.io.OutputStream;
        import java.net.HttpURLConnection;
        import java.net.InetAddress;
        import java.net.MalformedURLException;
        import java.net.Socket;
        import java.net.URL;

        import javax.net.ssl.HttpsURLConnection;

//        import org.apache.http.client.HttpClient;
//        import org.apache.http.client.methods.HttpPost;
//        import org.apache.http.impl.client.DefaultHttpClient;

        import android.R.integer;
        import android.app.Activity;
        import android.content.Context;
        import android.os.AsyncTask;
        import android.os.Environment;
        import android.os.Handler;
        import android.os.Message;
        import android.widget.Toast;

public class myfileupload {
    public static class myhttp
    {
        /**
         * 上传文件，在调用事件写入Handler的定义，What=1 obj=1成功，=0失败。
         * final Handler handler = new Handler() {
         *  public void handleMessage(Message msg) {
         *  super.handleMessage(msg);
         *  if (msg.what == 1) {
         *  if ((int) msg.obj == 1) {
         *  Toast.makeText( mycontext, "上传成功！", 0).show();
         *  } else {
         *  Toast.makeText( mycontext, "上传失败！", 0).show();
         *  }
         * @param context  调用上传文件的上下文对象
         * @param cLinkAddress   上传文件的链接（.net *.ASHX）
         * @param cFileName   待上传的文件名称
         * @param handler    传入的handler
         */
        public static void uploadHandle(Context context, final String cLinkAddress, final String cFileName, final Handler handler) {
            final Context mycontext = context;
            new Thread(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    String cReturnString =uploadFileForHandle(mycontext,cLinkAddress,cFileName);
                    Message message = handler.obtainMessage(1);
                    if (cReturnString.equals("OK!")) {
                        message.obj = 1;
                    } else {
                        message.obj = 0;
                    }
                    handler.sendMessage(message);
                }

            }

            ).start();

        }

        private static     String  uploadFileForHandle(Context context,  String cLinkAddress,   String cFileName) {
            //https://www.cnblogs.com/freexiaoyu/p/5177751.html
            String end ="\r\n";
            String cShortHyphens ="--";
            String cShortLenHyphens ="---------------------------";
            String cLengthHyphenS="-----------------------------";
            String boundary ="7e339318103ee";

            try {
                File file=new File( Environment.getExternalStorageDirectory() + File.separator +cFileName);
                String[]   sboundary=java.util.UUID.randomUUID().toString().split("-");
                boundary=sboundary[sboundary.length-1];
                /*计算文件属性中长度*/
                StringBuffer sb=new StringBuffer();
                sb.append(cLengthHyphenS+boundary+end);
                sb.append("Content-Disposition: form-data; name=\"uploadfile\"; filename=\""+cFileName+"\""+end);
                sb.append("Content-Type: image/png"+end);
                sb.append(end);

                StringBuffer	sb_end=new StringBuffer();
                sb_end.append(end+cLengthHyphenS+boundary+cShortHyphens+end);

                long iTotal=sb.length()+sb_end.length()+file.length();


                URL url=new URL(cLinkAddress);
                HttpURLConnection connection=null;
                try {
                    connection=(HttpURLConnection)url.openConnection();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    return e.getMessage();
                }


                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setUseCaches(false);


                connection.setRequestMethod("POST");
                connection.setRequestProperty("Charsert",  "UTF-8");
                connection.setRequestProperty("Connection", "Keep-Alive");
                connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=---------------------------"+boundary );

                connection.setConnectTimeout(2000);
                DataOutputStream dataOutputStream=new DataOutputStream(connection.getOutputStream());
                dataOutputStream.writeBytes(sb.toString());

                ////此昝需要添加读取到的文件内容信息

                FileInputStream fileInputStream=new FileInputStream(file);

                byte[] bread=new byte[1024];
                int len = 0;
                while((len=fileInputStream.read(bread))!=-1){
                    dataOutputStream.write(bread, 0, len);
                }
                fileInputStream.close();

                dataOutputStream.writeBytes(sb_end.toString());


                dataOutputStream.flush();
                int iReturn=connection.getResponseCode();

                return "OK!";
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                return "MalformedURLException"+e.getMessage();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                return "IOException"+e.getMessage();
            }

        }

        //		public    class Upload extends AsyncTask<String,Void,String> {

//	        File file;
//	        Context context;
//	        String cLinkAddress="",  cFileName="";
//	        public   Upload(Context context,  String cLinkAddress,String cFileName){
//	            this.cLinkAddress = cLinkAddress;
//	            this.cFileName=cFileName;
//	            this.context=context;
//	        }
//	        @Override
//	        protected String doInBackground(String... strings) {
//	        	uploadfile(context,cLinkAddress,cFileName);
//	        	return "";
//	        }
//
//	        @Override
//	        protected void onPostExecute(String s) {
//	            super.onPostExecute(s);
//	            if(s != null){
//	                Toast.makeText(context,"上传成功",Toast.LENGTH_SHORT).show(a);
//	            }else{
//	                Toast.makeText(context,"上传失败",Toast.LENGTH_SHORT).show();
//	            }
//	        }
//	    }
        public static long name() {
            File file=new File( Environment.getExternalStorageDirectory() + File.separator +"bar24.png");
            if(!file.exists())
                return 0;

            String end ="\r\n";
            String cShortHyphens ="--";
            String cShortLenHyphens ="---------------------------";
            String cLengthHyphenS="-----------------------------";
            String boundary ="7e339318103ee";
            /*计算文件属性中长度*/
            StringBuffer sb=new StringBuffer();

            sb.append(cLengthHyphenS+boundary+end);
            sb.append("Content-Disposition: form-data; name=\"__VIEWSTATE\""+end);
            sb.append(end);
            sb.append("/wEPDwUJMTA2MjMyNzk0ZGTcObUCW0JKWqzgHePNmhWAdrpKY0PR1Wm/WLpxIRrp8Q=="+end);

            sb.append(cLengthHyphenS+boundary+end);
            sb.append("Content-Disposition: form-data; name=\"__VIEWSTATEGENERATOR\""+end);
            sb.append(end);
            sb.append("EA5D7481"+end);


            sb.append(cLengthHyphenS+boundary+end);
            sb.append("Content-Disposition: form-data; name=\"FileUpload1\"; filename=\"bar24.png\""+end);
            sb.append("Content-Type: image/png"+end);
            sb.append(end);
            long headlen=sb.length();

            long fileleng=	file.length();

            sb=new StringBuffer();
            sb.append(end+cLengthHyphenS+boundary+cShortHyphens+end);
            long endlen=sb.length();
            return headlen+fileleng+endlen;
        }



        public static     String  uploadfile0(Context context,  String cLinkAddress,   String cFileName) {
            //https://www.cnblogs.com/freexiaoyu/p/5177751.html
            String end ="\r\n";
            String cShortHyphens ="--";
            String cShortLenHyphens ="---------------------------";
            String cLengthHyphenS="-----------------------------";
            String boundary ="7e339318103ee";

            try {
                File file=new File( Environment.getExternalStorageDirectory() + File.separator +cFileName);
                String[]   sboundary=java.util.UUID.randomUUID().toString().split("-");
                boundary=sboundary[sboundary.length-1];
                /*计算文件属性中长度*/
                StringBuffer sb=new StringBuffer();
                sb.append(cLengthHyphenS+boundary+end);
                sb.append("Content-Disposition: form-data; name=\"uploadfile\"; filename=\""+cFileName+"\""+end);
                sb.append("Content-Type: image/png"+end);
                sb.append(end);

                StringBuffer	sb_end=new StringBuffer();
                sb_end.append(end+cLengthHyphenS+boundary+cShortHyphens+end);

                long iTotal=sb.length()+sb_end.length()+file.length();


                URL url=new URL(cLinkAddress);
                HttpURLConnection connection=null;
                try {
                    connection=(HttpURLConnection)url.openConnection();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    return e.getMessage();
                }


                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setUseCaches(false);


                connection.setRequestMethod("POST");
                connection.setRequestProperty("Charsert",  "UTF-8");
                connection.setRequestProperty("Connection", "Keep-Alive");
                connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=---------------------------"+boundary );

                connection.setConnectTimeout(2000);
                DataOutputStream dataOutputStream=new DataOutputStream(connection.getOutputStream());
                dataOutputStream.writeBytes(sb.toString());

                ////此昝需要添加读取到的文件内容信息

                FileInputStream fileInputStream=new FileInputStream(file);

                byte[] bread=new byte[1024];
                int len = 0;
                while((len=fileInputStream.read(bread))!=-1){
                    dataOutputStream.write(bread, 0, len);
                }
                fileInputStream.close();

                dataOutputStream.writeBytes(sb_end.toString());


                dataOutputStream.flush();
                int iReturn=connection.getResponseCode();

                return "OK!";
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                return "MalformedURLException"+e.getMessage();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                return "IOException"+e.getMessage();
            }

        }

        public static     String  uploadfile(Context context,  String cLinkAddress,String cFileName) {
            String end ="\r\n";
            String cShortHyphens ="--";
            String cShortLenHyphens ="---------------------------";
            String cLengthHyphenS="-----------------------------";
            String boundary ="7e339318103ee";

            try {
                File file=new File( Environment.getExternalStorageDirectory() + File.separator +cFileName);
                String[]   sboundary=java.util.UUID.randomUUID().toString().split("-");
                boundary=sboundary[sboundary.length-1];
                /*计算文件属性中长度*/
                StringBuffer sb=new StringBuffer();

                sb.append(cLengthHyphenS+boundary+end);
                sb.append("Content-Disposition: form-data; name=\"__VIEWSTATE\""+end);
                sb.append(end);
                sb.append("/wEPDwUJMTA2MjMyNzk0ZGTcObUCW0JKWqzgHePNmhWAdrpKY0PR1Wm/WLpxIRrp8Q=="+end);

                sb.append(cLengthHyphenS+boundary+end);
                sb.append("Content-Disposition: form-data; name=\"__VIEWSTATEGENERATOR\""+end);
                sb.append(end);
                sb.append("EA5D7481"+end);


                sb.append(cLengthHyphenS+boundary+end);
                sb.append("Content-Disposition: form-data; name=\"FileUpload1\"; filename=\"bar24.png\""+end);
                sb.append("Content-Type: image/png"+end);
                sb.append(end);

                StringBuffer	sb_end=new StringBuffer();
                sb_end.append(end+cLengthHyphenS+boundary+cShortHyphens+end);

                long iTotal=sb.length()+sb_end.length()+file.length();


                URL url=new URL(cLinkAddress);
                HttpURLConnection connection=null;
                try {
                    connection=(HttpURLConnection)url.openConnection();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    return e.getMessage();
                }


                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setUseCaches(false);


                connection.setRequestMethod("POST");

//				connection.setRequestProperty("active", "POST /UploadFile.ashx HTTP/1.1");
//				connection.setRequestProperty("Accept", "text/html, application/xhtml+xml, */*");
//				connection.setRequestProperty("Referer", "http://192.168.1.102/myFileUpLoad.aspx");
//				connection.setRequestProperty("Accept-Language", "zh-CN");
//				connection.setRequestProperty("User-Agent", "Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; WOW64; Trident/6.0)");
                connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=---------------------------"+boundary );
//				connection.setRequestProperty("Accept-Encoding", "gzip, deflate");
//				connection.setRequestProperty("Host", "192.168.1.102");
//				connection.setRequestProperty("Content-Length", iTotal+"" );
//				connection.setRequestProperty("DNT", "1");
                connection.setRequestProperty("Charsert",  "UTF-8");
                connection.setRequestProperty("Connection", "Keep-Alive");
//				connection.setRequestProperty("Cache-Control", "no-cache");

                connection.setConnectTimeout(20000);
                DataOutputStream dataOutputStream=new DataOutputStream(connection.getOutputStream());
                dataOutputStream.writeBytes(sb.toString());

                ////此昝需要添加读取到的文件内容信息

                FileInputStream fileInputStream=new FileInputStream(file);

                byte[] bread=new byte[1024];
                int len = 0;
                while((len=fileInputStream.read(bread))!=-1){
                    dataOutputStream.write(bread, 0, len);
                }
                fileInputStream.close();

                dataOutputStream.writeBytes(sb_end.toString());


                dataOutputStream.flush();
                int iReturn=connection.getResponseCode();

                return "OK!";
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                return "MalformedURLException"+e.getMessage();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                return "IOException"+e.getMessage();
            }

        }

        public static     String  uploadfileSocket(Context context,  String cLinkAddress,String cFileName) {
            String end ="\r\n";
            String cShortHyphens ="--";
            String cShortLenHyphens ="---------------------------";
            String cLengthHyphenS="-----------------------------";
            String boundary ="";

            try {
                File file=new File( Environment.getExternalStorageDirectory() + File.separator +cFileName);
                String[]   sboundary=java.util.UUID.randomUUID().toString().split("-");
                boundary=sboundary[sboundary.length-1];
                /*计算文件属性中长度*/
                StringBuffer sb=new StringBuffer();

                sb.append(cLengthHyphenS+boundary+end);
                sb.append("Content-Disposition: form-data; name=\"__VIEWSTATE\""+end);
                sb.append(end);
                sb.append("/wEPDwUJMTA2MjMyNzk0ZGTcObUCW0JKWqzgHePNmhWAdrpKY0PR1Wm/WLpxIRrp8Q=="+end);

                sb.append(cLengthHyphenS+boundary+end);
                sb.append("Content-Disposition: form-data; name=\"__VIEWSTATEGENERATOR\""+end);
                sb.append(end);
                sb.append("EA5D7481"+end);


                sb.append(cLengthHyphenS+boundary+end);
                sb.append("Content-Disposition: form-data; name=\"FileUpload1\"; filename=\"bar24.png\""+end);
                sb.append("Content-Type: image/png"+end);
                sb.append(end);

                StringBuffer	sb_end=new StringBuffer();
                sb_end.append(end+cLengthHyphenS+boundary+cShortHyphens+end);

                long iTotal=sb.length()+sb_end.length()+file.length();




                StringBuffer cHead=new StringBuffer();
                cHead.append( "POST /UploadFile.ashx HTTP/1.1\r\n");
                cHead.append( "Accept:text/html, application/xhtml+xml, */*\r\n");


                cHead.append("Referer: http://192.168.1.102/myFileUpLoad.aspx\r\n");
                cHead.append("Accept-Language: zh-CN\r\n");
                cHead.append("User-Agent: Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; WOW64; Trident/6.0)\r\n");
                cHead.append("Content-Type:  multipart/form-data; boundary=---------------------------"+boundary+"\r\n");
                cHead.append("Accept-Encoding: gzip, deflate\r\n");
                cHead.append("Host: 192.168.1.102\r\n");
                cHead.append("Content-Length: "+ iTotal+"\r\n" );
                cHead.append("DNT: 1\r\n");
                cHead.append("Connection: Keep-Alive\r\n");
                cHead.append("Cache-Control: no-cache\r\n");
                cHead.append("\r\n");

                URL url=new URL(cLinkAddress);
                Socket socket=null;
                Toast.makeText(context,cLinkAddress, 1).show();
                try {
                    socket=new Socket(InetAddress.getByName(url.getHost()),80);
                } catch (Exception e) {
                    // TODO: handle exception
                    String cMessage=e.getMessage();
                    Toast.makeText(context, cMessage, 1).show();
                }


                OutputStream outputStream=socket.getOutputStream();
                outputStream.write(cHead.toString().getBytes());



                ////此昝需要添加读取到的文件内容信息

                FileInputStream fileInputStream=new FileInputStream(file);

                byte[] bread=new byte[1024];
                int len = 0;
                while((len=fileInputStream.read(bread))!=-1){
                    outputStream.write(bread, 0, len);
                }
                fileInputStream.close();

                outputStream.write(sb_end.toString().getBytes());


                outputStream.flush();
                outputStream.close();
                socket.close();

                return "OK!";
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                return "MalformedURLException"+e.getMessage();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                return "IOException"+e.getMessage();
            }

        }

    }

    public class mysock
    {

    }

    public  class   myHttpClient{
        public void name(String cUrl,String cFileName) {
//			File file=new File(Environment.getExternalStorageDirectory()+File.separator+cFileName);
//			HttpClient client=new DefaultHttpClient();
//			HttpPost post=new HttpPost(cUrl);
//		   FileBody filebody=new FileBody(file,"image/png");
//		   MultipartEntity entity=new MultipartEntity();



        }

    }

}
