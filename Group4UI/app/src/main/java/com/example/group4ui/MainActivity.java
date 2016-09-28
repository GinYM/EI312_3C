package com.example.group4ui;

import java.io.IOException;
import java.util.UUID;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener{
	private BluetoothAdapter mBluetoothAdapter = null;
	private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
	private static final String macAddr = "98:D3:31:40:0B:A0";
	private BluetoothDevice mDevice;
	
	private Button btn_simple;
	private Button btn_gesture;
	private Button btn_video;
	
	private SimpleFragment simpleFrag;
	private GesturesFragment gestureFrag;
	private VideoFragment videoFrag;
	
	private TextView txt_title;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        
        txt_title = (TextView)findViewById(R.id.txt_title);
        btn_simple = (Button)findViewById(R.id.btn_simple);
        btn_gesture = (Button)findViewById(R.id.btn_gesture);
        btn_video = (Button)findViewById(R.id.btn_video);
        btn_simple.setOnClickListener(this);
        btn_gesture.setOnClickListener(this);
        btn_video.setOnClickListener(this);
        
        Log.d("fuck","good");
        setDefaultFragment();
    }
    
    private void setDefaultFragment(){
    	FragmentManager fm = getFragmentManager();
    	FragmentTransaction transaction = fm.beginTransaction();
    	simpleFrag = new SimpleFragment();
    	transaction.replace(R.id.id_content, simpleFrag);
    	transaction.commit();
    }
    
    protected void onDestroy(){
		super.onDestroy();
		Log.d("fuck", "Destroy");
		Pub.outStream = null;
		if(Pub.mSocket!=null){
			try{
				Pub.mSocket.close();
			} catch(IOException e){
				e.printStackTrace();
			}
			Pub.mSocket = null;
		}
		Pub.mSocket = null;
	}
    @Override
    public void onClick(View v){
    	FragmentManager fm = getFragmentManager();
    	FragmentTransaction transaction = fm.beginTransaction();
    	
    	switch (v.getId()){
    	case R.id.btn_simple:
//    		if(simpleFrag == null){
    			simpleFrag = new SimpleFragment();
//    		}
    		btn_simple.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.simplewhite), null, null);
    		btn_simple.setTextColor(getResources().getColor(R.color.white));
    		btn_gesture.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.gesturegray), null, null);
    		btn_gesture.setTextColor(getResources().getColor(R.color.gray));
    		btn_video.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.videogray), null, null);
    		btn_video.setTextColor(getResources().getColor(R.color.gray));
    		txt_title.setText("Simple Mode");
    		transaction.replace(R.id.id_content, simpleFrag);
    		break;
    	case R.id.btn_gesture:
//    		if(gestureFrag == null){
    			gestureFrag = new GesturesFragment();
//    		}
    		btn_gesture.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.gesturewhite), null, null);
    		btn_gesture.setTextColor(getResources().getColor(R.color.white));
    		btn_simple.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.simplegray), null, null);
    		btn_simple.setTextColor(getResources().getColor(R.color.gray));
    		btn_video.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.videogray), null, null);
    		btn_video.setTextColor(getResources().getColor(R.color.gray));
    		txt_title.setText("Gesture Mode");
    		transaction.replace(R.id.id_content, gestureFrag);
    		break;
    	case R.id.btn_video:
//    		if(videoFrag == null){
    			videoFrag = new VideoFragment();
//    		}
    		btn_video.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.videowhite), null, null);
    		btn_video.setTextColor(getResources().getColor(R.color.white));
    		btn_gesture.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.gesturegray), null, null);
    		btn_gesture.setTextColor(getResources().getColor(R.color.gray));
    		btn_simple.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.simplegray), null, null);
    		btn_simple.setTextColor(getResources().getColor(R.color.gray));
    		txt_title.setText("Video Mode");
    		transaction.replace(R.id.id_content, videoFrag);
    		break;
    	}
    	transaction.commit();
    	
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
        if (id == R.id.bluetooth_connect) {
        	Log.d("fuck", "start connect");
        	mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        	if(mBluetoothAdapter == null){
        		Toast.makeText(this, "Bluetooth is not available.", Toast.LENGTH_SHORT).show();
        		return true;
        	}
        	
        	if(!mBluetoothAdapter.isEnabled()){
        		mBluetoothAdapter.enable();
        		while(!mBluetoothAdapter.isEnabled());
        	}
        	
        	mDevice = mBluetoothAdapter.getRemoteDevice(macAddr);
        	Log.d("fuck", "get remote device");
        	try{
        		Pub.mSocket = mDevice.createRfcommSocketToServiceRecord(MY_UUID);
        	} catch(IOException e){
        		Toast.makeText(this, "Fail to create socket", Toast.LENGTH_SHORT).show();
        	}
        	mBluetoothAdapter.cancelDiscovery();
        	
        	try{
        		Pub.mSocket.connect();
        		Toast.makeText(this, "Connect success!", Toast.LENGTH_SHORT).show();
        	} catch(IOException connectException){
        		try{
        			Toast.makeText(this, "Connect fail", Toast.LENGTH_SHORT).show();
        			Pub.mSocket.close();
        		} catch(IOException closeException){}
        	}
        	
        	try{
        		Pub.outStream = Pub.mSocket.getOutputStream();
        	} catch(IOException e){
        		Toast.makeText(this, "Fail to get outStream", Toast.LENGTH_SHORT).show();
        	}
//        	for(int i=0; i<5; ++i){
//        		Pub.sendMessage("udp");
//        	}
            return true;
        }
        else if (id == R.id.ip_address_set){
        	Intent ip_address_intent = new Intent(getBaseContext(), IPAddressActivity.class);
        	Log.d("fuck", "start get IP");
			startActivity(ip_address_intent);
			return true;
        }
        else if (id == R.id.voice_test) {
        	Intent voice_test = new Intent(getBaseContext(), AsrDemo.class);
        	startActivity(voice_test);
        	return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
