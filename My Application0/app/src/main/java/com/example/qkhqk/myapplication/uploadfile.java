package com.example.qkhqk.myapplication;

import android.os.Environment;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

public class uploadfile {
    private static final String TAG = "uploadFile";
    private static final int TIME_OUT = 10 * 1000;   //超时时间
    private static final String CHARSET = "utf-8"; //设置编码

    public  static void aaaa()
    {

    }


    public static String aa(String cAddress)
    {

        return aa("",cAddress);
    }
    public static String aa(String cFileName, String cAddress) {
        cFileName="bar24.png";
        cFileName = Environment.getExternalStorageDirectory() + File.separator + cFileName;
        File file = new File(cFileName);
      return   uploadFile(file, " http://" + cAddress + "/myFileUpLoad.aspx");
    }
    /**
     * android上传文件到服务器
     *
     * @param file       需要上传的文件
     * @param RequestURL 请求的rul
     * @return 返回响应的内容
     */
    public static String uploadFile(File file, String RequestURL) {
        String result = null;
        String BOUNDARY = "7e32652a0b32";//UUID.randomUUID().toString();  //边界标识   随机生成
        String[] cSplit=BOUNDARY.split("-");
        BOUNDARY=cSplit[cSplit.length-1];
        String PREFIX = "---------------------------", LINE_END = "\r\n";
        String CONTENT_TYPE = "multipart/form-data";   //内容类型

        try {
            URL url = new URL(RequestURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(TIME_OUT);
            conn.setConnectTimeout(TIME_OUT);
            conn.setDoInput(true);  //允许输入流
            conn.setDoOutput(true); //允许输出流
            conn.setUseCaches(false);  //不允许使用缓存
            conn.setRequestMethod("POST");  //请求方式
            conn.setRequestProperty("Accept","text/html, application/xhtml+xml, */*");
            conn.setRequestProperty("Referer","http://192.168.0.122/myFileUpLoad.aspx");
            conn.setRequestProperty("Accept-Language","zh-CN");
            conn.setRequestProperty("User-Agent","Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; WOW64; Trident/6.0)");
            conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" +PREFIX+ BOUNDARY);

            conn.setRequestProperty("Accept-Encoding", "gzip, deflate");  //设置编码
            conn.setRequestProperty("connection", "keep-alive");
            conn.setRequestProperty("Host", "192.168.0.122");  //设置编码
            conn.setRequestProperty("DNT", "1");

            conn.setRequestProperty("connection", "keep-alive");
            conn.setRequestProperty("Cache-Control", "no-cache");

            conn.setRequestProperty("POST", "/UploadFile.ashx HTTP/1.1");
            conn.connect();

            if (file != null) {
                /**
                 * 当文件不为空，把文件包装并且上传
                 */
                DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
                StringBuffer sb = new StringBuffer();
                sb.append(PREFIX);
                sb.append(BOUNDARY);
                sb.append(LINE_END);
                /**
                 * 这里重点注意：
                 * name里面的值为服务器端需要key   只有这个key 才可以得到对应的文件
                 * filename是文件的名字，包含后缀名的   比如:abc.png
                 */

                sb.append("Content-Disposition: form-data; name=\"img\"; filename=\"" + file.getName() + "\"" + LINE_END);

                sb.append(LINE_END);
                dos.write(sb.toString().getBytes());
                InputStream is = new FileInputStream(file);
                byte[] bytes = new byte[1024];
                int len = 0;
                while ((len = is.read(bytes)) != -1) {
                    dos.write(bytes, 0, len);
                }
                is.close();
                dos.write(LINE_END.getBytes());
                byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END).getBytes();
                dos.write(end_data);
                dos.flush();
                /**
                 * 获取响应码  200=成功
                 * 当响应成功，获取响应的流
                 */
                int res = conn.getResponseCode();
                if (res == 200) {
                    InputStream input = conn.getInputStream();
                    StringBuffer sb1 = new StringBuffer();
                    int ss;
                    while ((ss = input.read()) != -1) {
                        sb1.append((char) ss);
                    }
                    result = sb1.toString();
                    System.out.println(result);
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            result=e.getMessage();
        } catch (IOException e) {
            e.printStackTrace();
            result=e.getMessage();
        }
        return result;
    }
}