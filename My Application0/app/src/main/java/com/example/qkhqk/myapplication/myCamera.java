package com.example.qkhqk.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.ImageReader;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.util.Size;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class myCamera {


    public static class Camera_Intent {
        /**
         * 文件存放路径
         */
        public static String FILE_PATH = "";

        /**
         * 文件名称
         */
        public static String File_Name = "";

        /**\
         *存放拍摄照片的ImageView
         */
        public static ImageView imageView = null;

        public static void myCamera(Context context) {

            File FilePath = new File(FILE_PATH);
            if (!FilePath.exists()) {
                FilePath.mkdirs();
            }
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            cameraIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);

            cameraIntent.addCategory(Intent.CATEGORY_DEFAULT);
            File file = new File(FILE_PATH + File.separator + File_Name);
            if (file.exists()) {
                file.delete();
            }
            // 把文件地址转换成Uri格式
            Uri uri = Uri.fromFile(file);
            // 设置系统相机拍摄照片完成后图片文件的存放地址

            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            ((Activity) context).startActivityForResult(cameraIntent, 100);

        }


        public static void onActivityResult(int requestCode, int resultCode, Intent data) {
            // TODO Auto-generated method stub

            if (resultCode == -1) {
                File file = new File(FILE_PATH + File.separator + File_Name);
                Uri uri = Uri.fromFile(file);
                imageView.refreshDrawableState();
                imageView.setImageURI(uri);

            }

        }
    }

    public static class Camera_SurfaceView {
        public static Context context;
        public static SurfaceView surfaceView;
        private static SurfaceHolder surfaceHolder;
        private static Camera cameraManager;
        private static Handler mBackgroundHandler;
        private static HandlerThread mBackgroundThread;
        private static String cFilePath = "", cFileName = "";

        public static boolean Camera_open() {
            if (cameraManager == null) {
                try {
                    cameraManager = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);// (CameraManager)context.getSystemService(Context.CAMERA_SERVICE);
                    surfaceHolder = surfaceView.getHolder();
                    surfaceHolder.addCallback(callback);
                    cameraManager.setDisplayOrientation(90);
                    cameraManager.setPreviewDisplay(surfaceHolder);
                    cameraManager.startPreview();
                    return true;
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            } else {
                cameraManager.startPreview();
                return true;
            }


        }

        public static void Camera_takePicture(String cCurFilePath, String cCurFileName) {
            cFilePath = cCurFilePath;
            cFileName = cCurFileName;
            cameraManager.takePicture(null, null, mpicture);

        }

        public static void Camera_Stop() {
            cameraManager.stopPreview();

        }

        private static SurfaceHolder.Callback callback = new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                if (cameraManager == null)
                    Camera_open();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                surfaceHolder.removeCallback(callback);
                if (cameraManager != null) {
                    cameraManager.setPreviewCallback(null);
                    cameraManager.stopPreview();
                    cameraManager.release();
                    cameraManager = null;
                }
            }
        };


        private static Camera.PictureCallback mpicture = new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                try {
                    File pictureFile = new File(cFilePath + File.separator + cFileName + ".jpg");
                    FileOutputStream fos = new FileOutputStream(pictureFile);
                    fos.write(data);
                    fos.close();
                    ;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

    }


    public static class Camera_SurfaceTexture {
        public static TextureView textureView;
        public static String cFilePath = Environment.getExternalStorageDirectory().toString();
        public static String cFileName = "";
        public static Context pcontext;
        private static Camera2 camera2;

        public Camera_SurfaceTexture(Context context) {
            this.pcontext = context;

        }

        public Camera_SurfaceTexture(Context context, TextureView textureView) {
            this.pcontext = context;
            this.textureView = textureView;

        }

        public static void Camera_open() {
            camera2 .Camera_open();
        }

        public static void Camera_open(Context context, TextureView textureView) {
            pcontext = context;
            textureView = textureView;

            camera2 = new Camera2(pcontext, textureView);
        }

        public static void Camera_teke() {
            camera2.cFilePath = Environment.getExternalStorageDirectory() + File.separator + "Photos";
            camera2.cFileName = "Pic_" + (new SimpleDateFormat("yyyyMMddHHmmss")).format(new Date()) + ".jpg";
            camera2.captureStillPicture();
        }

        public static void Camera_Close() {
            camera2.Camrea_Close();
        }
    }


}

class Camera2 {
    public TextureView textureView;
    private CameraManager cameraManager;
    private CameraDevice cameraDevice;
    private CaptureRequest.Builder previewRequestBuilder;
    private CaptureRequest pRequest;
    private CameraCaptureSession captureSession;
    private ImageReader imageReader;
    public String cFilePath = Environment.getExternalStorageDirectory().toString();
    public String cFileName = "";

    private Size mPreviewSize;
    private String CameraID = Integer.toString(CameraCharacteristics.LENS_FACING_FRONT);
    private Surface surface;

    private Context context;

    public Camera2(Context context) {
        this.context = context;
        textureView.setSurfaceTextureListener(SurfaceTextureListener);
    }

    public Camera2(Context context, TextureView textureView) {
        this.context = context;
        this.textureView = textureView;
        textureView.setSurfaceTextureListener(SurfaceTextureListener);
    }

    public void Camera_open() {
        try {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            cameraManager.openCamera(CameraID, stateCallback, null); // ①
    } catch (CameraAccessException e) {
        e.printStackTrace();
    }
}
    private static final SparseIntArray ORIENTATIONS = new SparseIntArray();

    static {
        ORIENTATIONS.append(Surface.ROTATION_0, 90);
        ORIENTATIONS.append(Surface.ROTATION_90, 0);
        ORIENTATIONS.append(Surface.ROTATION_180, 270);
        ORIENTATIONS.append(Surface.ROTATION_270, 180);
    }


    public   void captureStillPicture() {
        try {
            if (cameraDevice == null) {
                return;
            }
            // 创建作为拍照的CaptureRequest.Builder
            previewRequestBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);
            // 将imageReader的surface作为CaptureRequest.Builder的目标
            previewRequestBuilder.addTarget(imageReader.getSurface());
            // 设置自动对焦模式
            previewRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE,
                    CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
            // 设置自动曝光模式
            previewRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH);
            // 获取设备方向
            int rotation = ((Activity) context).getWindowManager().getDefaultDisplay().getRotation();
            // 根据设备方向计算设置照片的方向
            previewRequestBuilder.set(CaptureRequest.JPEG_ORIENTATION, ORIENTATIONS.get(rotation));
            // 停止连续取景
            captureSession.stopRepeating();
            // 捕获静态图像
            captureSession.capture(previewRequestBuilder.build(), new CameraCaptureSession.CaptureCallback() // ⑤
            {
                // 拍照完成时激发该方法
                @Override
                public void onCaptureCompleted(CameraCaptureSession session, CaptureRequest request, TotalCaptureResult result) {
                    try {
                        // 重设自动对焦模式
                        previewRequestBuilder.set(CaptureRequest.CONTROL_AF_TRIGGER, CameraMetadata.CONTROL_AF_TRIGGER_CANCEL);
                        // 设置自动曝光模式
                        previewRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH);
                        // 打开连续取景模式
                        captureSession.setRepeatingRequest(pRequest, null, null);
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                }
            }, null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    public void openCamera(int width, int height) {
        setUpCameraOutputs(width, height);
        CameraManager manager = (CameraManager) context.getSystemService(context.CAMERA_SERVICE);
        try {
            // 打开摄像头
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            cameraManager.openCamera(CameraID, stateCallback, null); // ①
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    public void setUpCameraOutputs(int width, int height) { // Camera_open
        cameraManager = (CameraManager) context .getSystemService(context.CAMERA_SERVICE);
        try {
            for (String cameraid1 : cameraManager.getCameraIdList()) {
                CameraCharacteristics characteristics = cameraManager
                        .getCameraCharacteristics(cameraid1);
                if (characteristics.get(CameraCharacteristics.LENS_FACING) == CameraCharacteristics.LENS_FACING_FRONT) {
                    StreamConfigurationMap map = characteristics
                            .get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);

                    Size largest = Collections
                            .max(Arrays.asList(map
                                            .getOutputSizes(ImageFormat.JPEG)),
                                    new CompareSizesByArea());
                    mPreviewSize=largest;
                    imageReader = ImageReader.newInstance(largest.getWidth(),
                            largest.getHeight(), ImageFormat.JPEG, 2);
                    imageReader.setOnImageAvailableListener( new ImageReader.OnImageAvailableListener()
                    {
                        // 当照片数据可用时激发该方法
                        @Override
                        public void onImageAvailable(ImageReader reader)
                        {
                            // 获取捕获的照片数据
                            Image image = reader.acquireNextImage();
                            ByteBuffer buffer = image.getPlanes()[0].getBuffer();
                            byte[] bytes = new byte[buffer.remaining()];
                            // 使用IO流将照片写入指定文件
                            File file = new File(cFilePath,cFileName);
                            buffer.get(bytes);
                            try (FileOutputStream output = new FileOutputStream(file))
                            {
                                output.write(bytes);
                                Toast.makeText(context, "保存: " + file, Toast.LENGTH_SHORT).show();
                            }
                            catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                            finally
                            {
                                image.close();
                            }
                        }
                    }, null);

                }

            }

        } catch (CameraAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // StreamConfigurationMap map=

    }

    private static Size chooseOptimalSize(Size[] choices, int width,  int height, Size aspectRatio) {

        // 收集摄像头支持的大过预览Surface的分辨率
        List<Size> bigEnough = new ArrayList<>();
        int w = aspectRatio.getWidth();
        int h = aspectRatio.getHeight();
        for (Size option : choices)
        {
            if (option.getHeight() == option.getWidth() * h / w
                    && option.getWidth() >= width
                    && option.getHeight() >= height)
            {
                bigEnough.add(option);
            }
        }
        // 如果找到多个预览尺寸，获取其中面积最小的
        if (bigEnough.size() > 0)
        {
            return Collections.min(bigEnough, new CompareSizesByArea());
        }
        else
        {
            System.out.println("找不到合适的预览尺寸！！！");
            return choices[0];
        }

    }

    // 为Size定义一个比较器Comparator

    static class CompareSizesByArea implements Comparator<Size>

    {

        @Override
        public int compare(Size lhs, Size rhs)

        {

            // 强转为long保证不会发生溢出

            return Long.signum((long) lhs.getWidth() * lhs.getHeight() -

                    (long) rhs.getWidth() * rhs.getHeight());

        }

    }
    private void createCameraPreviewSession()

    {

        try

        {

            SurfaceTexture texture = textureView.getSurfaceTexture();

            texture.setDefaultBufferSize(mPreviewSize.getWidth(), mPreviewSize.getHeight());

            // 创建作为预览的CaptureRequest.Builder

            previewRequestBuilder = cameraDevice

                    .createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);

            // 将textureView的surface作为CaptureRequest.Builder的目标

            Surface surface = new Surface(texture);
            previewRequestBuilder.addTarget(surface);

            // 创建CameraCaptureSession，该对象负责管理处理预览请求和拍照请求

            cameraDevice.createCaptureSession(Arrays.asList(surface

                    , imageReader.getSurface()), new CameraCaptureSession.StateCallback() // ③

                    {

                        @Override

                        public void onConfigured(CameraCaptureSession cameraCaptureSession)

                        {

                            // 如果摄像头为null，直接结束方法

                            if (null == cameraDevice)

                            {

                                return;

                            }

                            // 当摄像头已经准备好时，开始显示预览

                            captureSession = cameraCaptureSession;

                            try

                            {

                                // 设置自动对焦模式

                                previewRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE,

                                        CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);

                                // 设置自动曝光模式

                                previewRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE,

                                        CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH);

                                // 开始显示相机预览

                                pRequest = previewRequestBuilder.build();

                                // 设置预览时连续捕获图像数据

                                captureSession.setRepeatingRequest(pRequest,

                                        null, null);  // ④

                            }

                            catch (CameraAccessException e)

                            {

                                e.printStackTrace();

                            }

                        }

                        @Override

                        public void onConfigureFailed(CameraCaptureSession cameraCaptureSession)

                        {

                            Toast.makeText((Activity)context, "配置失败！"   , Toast.LENGTH_SHORT).show();

                        }

                    }, null

            );

        }

        catch (CameraAccessException e)

        {

            e.printStackTrace();

        }

    }
    private TextureView.SurfaceTextureListener SurfaceTextureListener = new TextureView.SurfaceTextureListener() {

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture arg0, int arg1,
                                                int arg2) {
            // TODO Auto-generated method stub

        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture arg0) {
            // TODO Auto-generated method stub
            return true;
        }

        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture arg0, int arg1,
                                              int arg2) {
            // TODO Auto-generated method stub
            openCamera(arg1, arg2);
        }
    };

    private CameraDevice.StateCallback stateCallback = new CameraDevice.StateCallback() {

        @Override
        public void onOpened(CameraDevice arg0) {
            // TODO Auto-generated method stub
            cameraDevice = arg0;
            createCameraPreviewSession();  // ②
        }

        @Override
        public void onError(CameraDevice arg0, int arg1) {
            // TODO Auto-generated method stub
            cameraDevice.close();

            cameraDevice = null;
        }

        @Override
        public void onDisconnected(CameraDevice arg0) {
            // TODO Auto-generated method stub
            cameraDevice.close();

            cameraDevice = null;
        }
    };
     public    void Camrea_Close()
     {
         cameraDevice.close();
     }
}
