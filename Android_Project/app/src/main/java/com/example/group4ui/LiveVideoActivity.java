package com.example.group4ui;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;


public class LiveVideoActivity extends Activity {
	
	private float x_tmp1;
	private float y_tmp1;
	private float x_tmp2;
	private float y_tmp2;
	
	private SurfaceView video1;
	private SurfaceHolder videoholder;
	private Camera camera;
	private readThread read=null;
	
	
	int tag, tag1=0, tag2=0, piclen = 0, len = 0, i = 0, p_len = 0;
	int pplen , judge = 0;
	byte [] picture_length , pictures;
	Size size;
	
	//Bitmap bmp;
	
	Context mContext;
	private String s_ip;
	private wifi_client w_client = null;
	static Socket socket = null, socket2 = null;
	private Handler h = new Handler();
	
	private Runnable myRunnable1 = new Runnable(){
		public void run() {
			Log.d("fuck", "myRunnable1");
			//Toast.makeText(mContext, "wifi connect".toString(), Toast.LENGTH_SHORT).show();
		}
	};
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_live_video);
		mContext = this;
		s_ip = Pub.IP_Address;
		Log.d("fuck", "onCreate");
		connect_wifi();
		Log.d("fuck", "setContentView1");
		
		Log.d("fuck", "setContentView2");
		video1 = (SurfaceView)findViewById(R.id.sv_live_video);
		videoholder = video1.getHolder();
		videoholder.addCallback(surfaceCallback);
		Log.d("fuck", "onCreate2");
		//if (control.socket != null){
		read = new readThread();
		read.start();
	}
	
	private void connect_wifi(){
		Log.d("fuck", "connect_wifi");
		w_client = new wifi_client();
		w_client.start();
	}
	
	private class wifi_client extends Thread{
		public void run(){
			try{
				socket = new Socket(s_ip, 9400);
				socket2 = new Socket(s_ip, 9500);
				Log.d("fuck", "WIFI CONNECT SUCCESSFUL");
				h.post(myRunnable1);
			} catch(IOException e){
				e.printStackTrace();
			}
		}
	}
	
	static void send_wifi_message(byte[] msg){
		if(socket == null){
			return;
		}
		try{
			OutputStream wos =socket.getOutputStream();
			wos.write(msg);
			wos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	static void send_wifi_message2(byte[] msg){
		if(socket == null){
			return;
		}
		try {
			OutputStream wos = socket2.getOutputStream();
			wos.write(msg);
			wos.flush();
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		
		float x = event.getX();  
        float y = event.getY(); 
        switch (event.getAction()){  
        case MotionEvent.ACTION_DOWN:  
            x_tmp1 = x;  
            y_tmp1 = y;  
            Log.i("WOJEFOIWEJFO","enter the DOWN");
            break;  
        case MotionEvent.ACTION_UP:  
            x_tmp2 = x;  
            y_tmp2 = y; 
            Log.i("WOJEFOIWEJFO","enter the UP");
            if(true)
            {
            	if (x_tmp1 == x_tmp2 && y_tmp1 == y_tmp2){
            		Pub.sendMessage("s");
            	}
            	if(Math.abs(x_tmp1-x_tmp2)>Math.abs(y_tmp1-y_tmp2))
            	{
            		if(Math.abs(x_tmp1-x_tmp2)>8)
    	            {
    	            	if(x_tmp1-x_tmp2>8){
    	            		Log.i("gesture","锟斤拷锟襟滑讹拷"); 
    	            		Pub.sendMessage("l");
    	            	}
    	            	
    	            	else {
    	            		Log.i("gesture","锟斤拷锟揭伙拷锟斤拷");
    	            		Pub.sendMessage("r");
    	            	}
    	            }
            	}
            	else
            	{
            		if(Math.abs(y_tmp1-y_tmp2)>8)
    	            {
    	            	if(y_tmp1-y_tmp2>8){
    	            		Log.i("gesture","锟斤拷锟较伙拷锟斤拷"); 
    	            		Pub.sendMessage("u");
    	            	}
    	            	else{
    	            		Log.i("gesture","锟斤拷锟铰伙拷锟斤拷");
    	            		Pub.sendMessage("d");
    	            	}
    	            }
            	}
	            
            }
            break;  
    }  
		//return super.onTouchEvent(event);
		return true;
	}
	
	/*private class drawpicture extends Thread{
		public void run(){
            Canvas canvas = videoholder.lockCanvas();
            canvas.drawBitmap(bmp, null, new Rect(0,0,video1.getWidth(),video1.getHeight()),null);
            videoholder.unlockCanvasAndPost(canvas);
            if (bmp != null){
            	bmp.recycle();
            }
            Log.i("WIFI","DRAW SUCCESSFUL");
		}
	}*/
	
	//锟斤拷取锟斤拷锟斤拷
 /*   private class readThread extends Thread { 
        public void run() {
        	//Toast.makeText(mContext,"run",Toast.LENGTH_LONG).show();
            byte[] buffer = new byte[1024];
            int bytes;
            InputStream mmInStream = null;
            if (control.socket != null) Log.i("WIFI", "BEGIN TO READ");
            
			try {
				mmInStream = control.socket.getInputStream();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}	
            while (true) {
                try {
                    // Read from the InputStream
                    if( (bytes = mmInStream.read(buffer)) > 0 )
                    {
                    	Log.i("WIFI","RECEIVE THE DATA");
	                    byte[] buf_data = new byte[bytes];
				    	for(int i=0; i<bytes; i++)
				    	{
				    		buf_data[i] = buffer[i];
				    	}
				    	
				    	if (judge == 0){
				    		judge = 1;
				    		Log.i("INT", new String(buf_data));
				    		pplen=Integer.valueOf(new String(buf_data)).intValue();
				    		picture_length = new byte[pplen];
				    		Log.i("INT",Integer.toString(pplen));

				    		control.send_wifi_message("photo".getBytes());
				    	}
				    	else{
				    	
                    	//Log.i("WIFI","GET DATA");
                    	int length = 0;
                    	if (len < pplen){
                    		while (len < pplen && length < bytes){
                        		picture_length[len++] = buf_data[length++];
                        	}
                    		if (len == pplen){
                    			//Log.i("WIFI",new String(picture_length));
                    			piclen = Integer.valueOf(new String(picture_length)).intValue();
                    			p_len = 0;
                            	pictures = new byte[piclen];
                    		}                    		
                    	}
                    	//Log.i("WIFI", "GET DATA1");
                    	
                    	while(piclen > p_len && length < bytes){
                    		pictures[p_len++] = buf_data[length++];
                    	}
                    	//Log.i("WIFI", "GET DATA2");
                    	if (piclen == p_len){ 
                    			
                            	Bitmap bmp = BitmapFactory.decodeByteArray(pictures, 0, piclen);
                            	if (bmp != null){
                            		Matrix matrix = new Matrix();  
                                    matrix.preRotate(90);  
                                    bmp = Bitmap.createBitmap(bmp ,0,0, bmp.getWidth(), bmp.getHeight(),matrix,true);
                                	 Canvas canvas = videoholder.lockCanvas();
                                     canvas.drawBitmap(bmp, null, new Rect(0,0,video1.getWidth(),video1.getHeight()),null);
                                     videoholder.unlockCanvasAndPost(canvas);
                    				bmp.recycle();
                    			}
                            	
                           
                    	}
                    	//Log.i("WIFI", "GET DATA3");
                    	if (length < bytes){
                    		len = 0;
                    		while (len < pplen && length < bytes){
                        		picture_length[len++] = buf_data[length++];
                        	}
                    		if (len == pplen){
                    			piclen = Integer.valueOf(new String(picture_length)).intValue();
                    			p_len = 0;
                            	pictures = new byte[piclen];
                            	while(piclen > p_len && length < bytes){
                            		pictures[p_len++] = buf_data[length++];
                            	}
                    		}
                    	}
                    	//Log.i("save data",Integer.toString(piclen));
				    	//Log.i("SAVE DATA",Integer.toString(p_len));
				    	}
                    }
                } catch (IOException e) {
                	try {
						mmInStream.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                    break;
                } 
            }
        }
    }
    */
	
	private class readThread extends Thread{
		 public void run(){
			 byte[] buffer = new byte[200], buffer2 = new byte[1024];
	         int bytes,bytes2;
	         InputStream mmInStream = null, mmInStream2 = null;
	         Log.d("fuck", "00");
	         try{
	        	 sleep(3000);
	         }catch(InterruptedException e){
	        	 
	         }
	         try {
	        	 Log.d("fuck", "001");
					mmInStream = socket.getInputStream();
					Log.d("fuck", "002");
					mmInStream2 = socket2.getInputStream();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					Log.d("fuck", "003");
					e1.printStackTrace();
				}
	         
//	         send_wifi_message("begin".getBytes());
	         
	         Log.d("fuck", "11");
	         while(true){
	        	 if (judge == 0){
	        		 judge = 1;
	        		 send_wifi_message("begin".getBytes());
	        		 Log.d("fuck", "22");
	        	 }
	        	 try {
	                    // Read from the InputStream
	        		 Log.d("fuck", "33");
	                    if( (bytes = mmInStream.read(buffer)) > 0 )
	                    {
	                    	Log.d("fuck", "33");
	                    	Log.i("WIFI","RECEIVE THE DATA");
		                    byte[] buf_data = new byte[bytes];
					    	for(int i=0; i<bytes; i++)
					    	{
					    		buf_data[i] = buffer[i];
					    	}
					    	
					    	piclen = Integer.valueOf(new String(buf_data)).intValue();
					    	pictures = new byte[piclen];
					    	
					    	//control.send_wifi_message2("begin".getBytes());
					    	Log.d("fuck", "44");
					    	while(true){
					    		try{
					    			if( (bytes2 = mmInStream2.read(buffer2)) > 0 )
					    			{
					    				//byte[] buf_data2 = new byte[bytes2];
					    				int i = 0;
								    	for( i=0; i<bytes2; i++)
								    	{
								    		//buf_data2[i] = buffer2[i];
								    		pictures[p_len++] = buffer2[i];
								    		if (piclen == p_len) break;
								    	}
								    	if (piclen == p_len){
								    		Bitmap bmp = BitmapFactory.decodeByteArray(pictures, 0, piclen);
			                            	if (bmp != null){
			                            		Matrix matrix = new Matrix();  
			                                    matrix.preRotate(90);  
			                                    bmp = Bitmap.createBitmap(bmp ,0,0, bmp.getWidth(), bmp.getHeight(),matrix,true);
			                                	 Canvas canvas = videoholder.lockCanvas();
			                                     canvas.drawBitmap(bmp, null, new Rect(0,0,video1.getWidth(),video1.getHeight()),null);
			                                     videoholder.unlockCanvasAndPost(canvas);
			                    				bmp.recycle();
			                    				//control.send_wifi_message2("".getBytes());
			                    			}
			                            	p_len = 0;
			                            	send_wifi_message("begin".getBytes());
								    		break;
								    	}
								    	
					    			}
					    		}catch(IOException e2){
					    			try {
										mmInStream2.close();
									} catch (IOException e3) {
										// TODO Auto-generated catch block
										e3.printStackTrace();
									}
				                    break;
					    		}
					    	}
					    	
	                    }
	                } catch (IOException e) {
	                	try {
							mmInStream.close();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
	                    break;
	                } 
	         }
		 }
	}
	
	
	
    SurfaceHolder.Callback surfaceCallback = new SurfaceHolder.Callback() {  
    	  
        public void surfaceCreated(SurfaceHolder holder) {  
            Log.i("good", "surfaceCallback====");              
            camera = Camera.open(); // Turn on the camera 
    		size = camera.getParameters().getPreviewSize();  
           /* //setDisplayOrientation();
            try {  
                camera.setPreviewDisplay(holder); // Set Preview  
            } catch (IOException e) {  
                camera.release();// release camera  
                camera = null;  
            }  */
        }  
  
        public void surfaceChanged(SurfaceHolder holder, int format, int width,  
                int height) {  
            Log.i("good","====surfaceChanged");  
           /* Camera.Parameters parameters = camera.getParameters(); // Camera parameters to obtain  
            parameters.setPictureFormat(PixelFormat.JPEG);// Setting Picture Format  
            camera.setParameters(parameters); // Setting camera parameters  
            camera.startPreview(); // Start Preview  */
            //h.post(ownRunnable);
        }  
  
        public void surfaceDestroyed(SurfaceHolder holder) {  
            Log.i("good","====surfaceDestroyed");  
           // camera.stopPreview();// stop preview  
            camera.release(); // Release camera resources  
            camera = null;  
        }  
    }; 
    
    protected void onPause(){
    	super.onPause();
    	send_wifi_message("end".getBytes());
    	send_wifi_message2("end".getBytes());
    	/*new Thread(){
        	public void run(){
        		if (read != null){
        			read.interrupt();
        			read = null;
        		}
        	}
        }.start();*/
    }
    
    protected void onResume(){
    	super.onResume();
    	judge = 0;
    	/*read = new readThread();
		read.start();*/
		//if (judge != 0)
		//	control.send_wifi_message("photo".getBytes());
    }
    
    protected void onDestroy() {
        super.onDestroy();
        
        new Thread(){
        	public void run(){
        		if (read != null){
        			read.interrupt();
        			read = null;
        		}
        		if(w_client != null)
        		{
        			w_client.interrupt();
        			w_client = null;
        		}
                try {					
        			if(socket != null)
        			{
        				socket.close();
        				socket = null;
        			}
        			if(socket2 != null)
        			{
        				socket2.close();
        				socket2 = null;
        			}
        		} catch (IOException e) {
        			Log.e("server", "mserverSocket.close()", e);
        		}
        	}
        }.start();
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		return super.onOptionsItemSelected(item);
	}
}

/*public class LiveVideoActivity extends Activity{
	private SurfaceView sv_live_video;
	private SurfaceHolder sv_live_video_holder;
	private Camera camera;
	
	private wifi_client w_client = null;
	private Socket socket;
	private readThread read = null;
	Size size;
	
	int tag, tag1 = 0, tag2 = 0, piclen = 0, len = 0, i = 0, p_len = 0;
	int pplen, judge = 0;
	byte [] picture_length, pictures;
	Bitmap bmp;
	
	Context mContext;
	
	private Handler h = new Handler();
	
	private Runnable myRunnable1 = new Runnable(){
		public void run(){
			Toast.makeText(mContext, "Wifi connect succeed!", Toast.LENGTH_SHORT).show();
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_live_video);
		
		connect_wifi();
		sv_live_video = (SurfaceView)findViewById(R.id.sv_live_video);
		sv_live_video_holder = sv_live_video.getHolder();
		sv_live_video_holder.addCallback(surfaceCallback);
		
		read = new readThread();
		read.start();
	}
	
	@Override
	protected void onDestroy(){
		super.onDestroy();
		Log.d("fuck", "live video destroy");
		if(w_client != null){
			w_client.interrupt();
			w_client = null;
		}
		try{
			if(socket != null){
				socket.close();
				socket = null;
			}
		} catch(IOException e){
			Log.e("fuck", "mserverSocket.close()", e);
		}
		
		new Thread(){
			public void run(){
				if(read != null){
					read.interrupt();
					read = null;
				}
			}
		}.start();
	}
	
	private void connect_wifi(){
		w_client = new wifi_client();
		w_client.start();
	}
	
	private void send_wifi_message(byte[] msg){
		if(socket == null) return;
		try{
			OutputStream wos = socket.getOutputStream();
			wos.write(msg);
			wos.flush();
		} catch(IOException e){
			e.printStackTrace();
		}
	}
	
	private class wifi_client extends Thread{
		public void run(){
			try{
				Log.d("fuck", "Pub.IP_Address " + Pub.IP_Address);
				socket = new Socket(Pub.IP_Address, 9400);
				Log.d("fuck", "wifi connect succeed");
//				h.post(myRunnable1);
			} catch(IOException e){
				e.printStackTrace();
				Log.d("fuck", "Socket failed ");
			}
			
		}
	}
	SurfaceHolder.Callback surfaceCallback = new SurfaceHolder.Callback() {
		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {
			Log.d("fuck", "surface destroyed");
			camera.release();
			camera = null;
		}
		
		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			Log.d("fuck", "surface created");
			camera = Camera.open();
			size = camera.getParameters().getPreviewSize();
		}
		
		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) {
			
		}
	};
	
	private class readThread extends Thread {
		public void run(){
			byte[] buffer = new byte[1024];
			int bytes;
			InputStream mmInStream = null;
			Log.d("fuck", "while start!");
			while(socket == null);
			Log.d("fuck", "while finish!");
			if(socket != null) Log.d("fuck", "begin");
			try{
				mmInStream = socket.getInputStream();
			} catch(IOException e1){
				e1.printStackTrace();
			}
			while(true){
				try {
					if((bytes = mmInStream.read(buffer)) > 0){
						byte[] buf_data = new byte[bytes];
						for(int i=0; i<bytes; ++i){
							buf_data[i] = buffer[i];
						}
						if(judge == 0){
							judge = 1;
							pplen = Integer.valueOf(new String(buf_data)).intValue();
							picture_length = new byte[pplen];
							
							send_wifi_message("photo".getBytes());
						} else{
							int length = 0;
							if(len < pplen){
								while(len < pplen && length < bytes){
									picture_length[len++] = buf_data[length++];
								}
								if(len == pplen){
									piclen = Integer.valueOf(new String(picture_length)).intValue();
									p_len = 0;
									pictures = new byte[piclen];
								}
							}
							
							while(piclen > p_len && length < bytes){
								pictures[p_len++] = buf_data[length++];
							}
							
							if(piclen == p_len){
								if(bmp != null) bmp.recycle();
								bmp = BitmapFactory.decodeByteArray(pictures, 0, piclen);
								Matrix matrix = new Matrix();
								matrix.preRotate(90);
								bmp = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
								Canvas canvas = sv_live_video_holder.lockCanvas();
								canvas.drawBitmap(bmp, null, new Rect(0, 0, sv_live_video.getWidth(), sv_live_video.getHeight()), null);
								sv_live_video_holder.unlockCanvasAndPost(canvas);
							}
							
							if(length < bytes){
								len = 0;
								while(len < pplen && length < bytes){
									picture_length[len++] = buf_data[length++];
								}
								if(len == pplen){
									piclen = Integer.valueOf(new String(picture_length)).intValue();
									p_len = 0;
									pictures = new byte[piclen];
									while(piclen > p_len && length < bytes){
										pictures[p_len++] = buf_data[length++];
									}
								}
							}
						}
					}
				} catch (IOException e){
					try{
						mmInStream.close();
					} catch(IOException e1){
						e1.printStackTrace();
					}
					break;
				}
			}
		}
	}
}*/
