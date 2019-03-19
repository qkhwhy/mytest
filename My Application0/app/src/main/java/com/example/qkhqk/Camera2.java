package com.example.qkhqk;

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
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Camera2 {
	public     TextureView textureView;
	CameraManager cameraManager;
	CameraDevice cameraDevice;
	private CaptureRequest.Builder previewRequestBuilder;
	CaptureRequest pRequest;
	CameraCaptureSession captureSession;
	ImageReader imageReader;
  public 	String cFilePath=Environment.getExternalStorageDirectory().toString();
  public	String cFileName ="";

	Size mPreviewSize;
	String CameraID = Integer.toString(CameraCharacteristics.LENS_FACING_FRONT);
	Surface surface;

	Context context;
	
	public Camera2(Context context) {
		this.context = context;
		  textureView.setSurfaceTextureListener(SurfaceTextureListener);
	}

	public Camera2(Context context,TextureView textureView) {
		this.context = context;
		this.textureView=textureView;
	   textureView.setSurfaceTextureListener(SurfaceTextureListener);
	}
	
	private static final SparseIntArray ORIENTATIONS = new SparseIntArray();
	static
	{
		ORIENTATIONS.append(Surface.ROTATION_0, 90);
		ORIENTATIONS.append(Surface.ROTATION_90, 0);
		ORIENTATIONS.append(Surface.ROTATION_180, 270);
		ORIENTATIONS.append(Surface.ROTATION_270, 180);
	}

	
	public    void captureStillPicture() {
		try
		{
			if (cameraDevice == null)
			{
				return;
			}
			// ������Ϊ���յ�CaptureRequest.Builder
			previewRequestBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);
			// ��imageReader��surface��ΪCaptureRequest.Builder��Ŀ��
			previewRequestBuilder.addTarget(imageReader.getSurface());
			// �����Զ��Խ�ģʽ
			previewRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE,
			CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
			// �����Զ��ع�ģʽ
			previewRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH);
			// ��ȡ�豸����
			int rotation = ((Activity) context).getWindowManager() .getDefaultDisplay().getRotation();
			// �����豸�������������Ƭ�ķ���
			previewRequestBuilder.set(CaptureRequest.JPEG_ORIENTATION, ORIENTATIONS.get(rotation));
			// ֹͣ����ȡ��
			captureSession.stopRepeating();
			// ����̬ͼ��
			captureSession.capture(previewRequestBuilder.build() , new CameraCaptureSession.CaptureCallback() // ��
					{
						// �������ʱ�����÷���
						@Override
						public void onCaptureCompleted( 	CameraCaptureSession session , CaptureRequest request, 	TotalCaptureResult result)
						{
							try
							{
								// �����Զ��Խ�ģʽ
								previewRequestBuilder.set(CaptureRequest.CONTROL_AF_TRIGGER,CameraMetadata.CONTROL_AF_TRIGGER_CANCEL);
								// �����Զ��ع�ģʽ
								previewRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH);
								// ������ȡ��ģʽ
								captureSession.setRepeatingRequest(pRequest, null, null);
							}
							catch (CameraAccessException e)
							{
								e.printStackTrace();
							}
						}
					}, null);
		}
		catch (CameraAccessException e)
		{
			e.printStackTrace();
		}
	}

	public void openCamera(int width, int height)
	{
		setUpCameraOutputs(width, height);
		CameraManager manager = (CameraManager) context.getSystemService(context.CAMERA_SERVICE);
		try {
			// ������ͷ
			cameraManager.openCamera(CameraID, stateCallback, null); // ��
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
						// ����Ƭ���ݿ���ʱ�����÷���
						@Override
						public void onImageAvailable(ImageReader reader)
						{
							// ��ȡ�������Ƭ����
							Image image = reader.acquireNextImage();
							ByteBuffer buffer = image.getPlanes()[0].getBuffer();
							byte[] bytes = new byte[buffer.remaining()];
							// ʹ��IO������Ƭд��ָ���ļ�
							File file = new File(cFilePath,cFileName);
							buffer.get(bytes);
							try (FileOutputStream output = new FileOutputStream(file))
							{
								output.write(bytes);
								Toast.makeText(context, "����: " + file, Toast.LENGTH_SHORT).show();
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

	private static Size chooseOptimalSize(Size[] choices, int width,
			int height, Size aspectRatio) {

		// �ռ�����ͷ֧�ֵĴ��Ԥ��Surface�ķֱ���

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

		// ����ҵ����Ԥ���ߴ磬��ȡ���������С��

		if (bigEnough.size() > 0)

		{

			return Collections.min(bigEnough, new CompareSizesByArea());

		}

		else

		{

			System.out.println("�Ҳ������ʵ�Ԥ���ߴ磡����");

			return choices[0];

		}

	}

	// ΪSize����һ���Ƚ���Comparator

	static class CompareSizesByArea implements Comparator<Size>

	{

		@Override
		public int compare(Size lhs, Size rhs)

		{

			// ǿתΪlong��֤���ᷢ�����

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

	            // ������ΪԤ����CaptureRequest.Builder

	            previewRequestBuilder = cameraDevice

	                .createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);

	            // ��textureView��surface��ΪCaptureRequest.Builder��Ŀ��

	            Surface surface = new Surface(texture);
	            previewRequestBuilder.addTarget(surface);

	            // ����CameraCaptureSession���ö����������Ԥ���������������

	            cameraDevice.createCaptureSession(Arrays.asList(surface

	                , imageReader.getSurface()), new CameraCaptureSession.StateCallback() // ��

	                {

	                    @Override

	                    public void onConfigured(CameraCaptureSession cameraCaptureSession)

	                    {

	                        // �������ͷΪnull��ֱ�ӽ�������

	                        if (null == cameraDevice)

	                        {

	                            return;

	                        }

	                        // ������ͷ�Ѿ�׼����ʱ����ʼ��ʾԤ��

	                        captureSession = cameraCaptureSession;

	                        try

	                        {

	                            // �����Զ��Խ�ģʽ

	                            previewRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE,

	                                CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);

	                            // �����Զ��ع�ģʽ

	                            previewRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE,

	                                CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH);

	                            // ��ʼ��ʾ���Ԥ��

	                            pRequest = previewRequestBuilder.build();

	                            // ����Ԥ��ʱ��������ͼ������

	                            captureSession.setRepeatingRequest(pRequest,

	                                    null, null);  // ��

	                        }

	                        catch (CameraAccessException e)

	                        {

	                            e.printStackTrace();

	                        }

	                    }

	                    @Override

	                    public void onConfigureFailed(CameraCaptureSession cameraCaptureSession)

	                    {

	                        Toast.makeText((Activity)context, "����ʧ�ܣ�"   , Toast.LENGTH_SHORT).show();

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
			  createCameraPreviewSession();  // ��
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

}
