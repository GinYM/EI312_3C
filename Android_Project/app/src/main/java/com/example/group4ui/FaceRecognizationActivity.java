package com.example.group4ui;

import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Face;
import android.hardware.Camera.FaceDetectionListener;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;


public class FaceRecognizationActivity extends Activity {
	private String TAG = "fuck";
	private Camera mCamera;
    private CameraPreview mPreview;
    private int count = 0;
    private int y = 0, h = 0;
    private int delay;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_face_recognization);
        // Create an instance of Camera
        mCamera = getCameraInstance();
        mCamera.setFaceDetectionListener(faceDetectionListener);
        // Create our Preview view and set it as the content of our activity.
        mPreview = new CameraPreview(this, mCamera);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(mPreview);
    }

    FaceDetectionListener faceDetectionListener = new FaceDetectionListener(){
    	@Override
    	public void onFaceDetection(Face[] faces, Camera camera){
    		if(faces.length == 0){
    			Log.d("fuck", "No Face Detected!");
    		}else{
    			++count;
    			if(count==20){
    				y = faces[0].rect.centerY();
    				h = faces[0].rect.height();
    				Log.d("fuck", "face: X: "+ faces[0].rect.centerX() + " Y: " + y+" height: " +h);
    				
    				if(Math.abs(y)>250){
    					delay = Math.abs(y) / 3;
    					if(y<0) Pub.sendMessage("l");
    					else Pub.sendMessage("r");
    				}
    				else if(h>850){
    					delay = (h-700) * 2;
    					Pub.sendMessage("d");
    				}
    				else if(h<550){
    					delay = (700-h) * 2;;
    					Pub.sendMessage("u");
    				}
    				new Thread(new Runnable(){
    					public void run(){
    						try{
    							Thread.sleep(delay);
    						} catch(InterruptedException e){
    							e.printStackTrace();
    						}
    						Pub.sendMessage("s");
    					}
    				}).start();
    				count = 0;
    			}
    		}
    	}
    };
    /** A safe way to get an instance of the Camera object. */
    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open(CameraInfo.CAMERA_FACING_FRONT);
            c.autoFocus(null);// attempt to get a Camera instance
        }
        catch (Exception e){
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }
    
    /** A basic Camera preview class */
    public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
        private SurfaceHolder mHolder;
        private Camera mCamera;
        @SuppressWarnings("deprecation")
		public CameraPreview(Context context, Camera camera) {
            super(context);
            mCamera = camera;
            // Install a SurfaceHolder.Callback so we get notified when the
            // underlying surface is created and destroyed.
            mHolder = getHolder();
            mHolder.addCallback(this);
            // deprecated setting, but required on Android versions prior to 3.0
            mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        public void surfaceCreated(SurfaceHolder holder) {
            // The Surface has been created, now tell the camera where to draw the preview.
            try {
                mCamera.setPreviewDisplay(holder);
                mCamera.startPreview();
            } catch (IOException e) {
                Log.d(TAG, "Error setting camera preview: " + e.getMessage());
            }
        }
        public void surfaceDestroyed(SurfaceHolder holder) {
            // empty. Take care of releasing the Camera preview in your activity.
        	mCamera.stopFaceDetection();
        	mCamera.stopPreview();
        	mCamera.release();
        }
        public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
            // If your preview can change or rotate, take care of those events here.
            // Make sure to stop the preview before resizing or reformatting it.
            if (mHolder.getSurface() == null){
              // preview surface does not exist
              return;
            }
            // stop preview before making changes
            try {
            	mCamera.stopFaceDetection();
                mCamera.stopPreview();
            } catch (Exception e){
              // ignore: tried to stop a non-existent preview
            }
            // set preview size and make any resize, rotate or
            // reformatting changes here
            // start preview with new settings
            try {
                mCamera.setPreviewDisplay(mHolder);
                mCamera.startPreview();
                mCamera.startFaceDetection();
            } catch (Exception e){
                Log.d(TAG, "Error starting camera preview: " + e.getMessage());
            }
        }
    }
}
