package com.example.group4ui;

import android.app.Fragment;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class ModeGravityFragment extends Fragment{
	// Gravity Definitions
	private SensorManager sensor_manager;
	private Sensor gravity_sensor;
	private SensorEventListener gravity_listener;
	
	private TextView txt_gravity_y;
	
	private SeekBar seekbar;
	public int seek_val = 3;
	@Override
    public void onPause(){
		super.onPause();
    	sensor_manager.unregisterListener(gravity_listener);
    }
//	    @Override
//	    protected void onCreate(Bundle savedInstanceState) {
//	    	
//	        super.onCreate(savedInstanceState);
//	        setContentView(R.layout.gravitylayout);
//	        Log.d("Gravity", "in");
//	        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
//	        // Get TextView
//	        
//	        gravity_show = (TextView)findViewById(R.id.gravity_show);
//	        gravity_show.setText("Gravity Mode");
//	        
//	        // Init gravitySensor
//	        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
//	        gravitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
//	        gravityListener = new SensorEventListener() {
//				
//				@Override
//				public void onSensorChanged(SensorEvent event) {
//					// TODO Auto-generated method stub
//					float x = event.values[SensorManager.DATA_X];
//					float y = event.values[SensorManager.DATA_Y];
//					float z = event.values[SensorManager.DATA_Z];
//					
//					gravity_show.setText("X = "+(int)x+"\nY = "+(int)y+"\nZ = "+(int)z);
//				}
//				
//				@Override
//				public void onAccuracyChanged(Sensor sensor, int accuracy) {
//					// TODO Auto-generated method stub
//					
//				}
//			};
//	        // Init Buttons
//	        btnStartGravity = (Button)findViewById(R.id.gravity_start);
//	        btnEndGravity = (Button)findViewById(R.id.gravity_stop);
//	        btnEndGravity.setClickable(false);
//	        
//	        btnStartGravity.setOnClickListener(new OnClickListener(){
//	        	public void onClick(View v){
//	        		sensorManager.registerListener(gravityListener, gravitySensor, SensorManager.SENSOR_DELAY_GAME);
//	        		btnStartGravity.setClickable(false);
//	        		btnEndGravity.setClickable(true);
//	        	}
//	        });
//	        
//	        btnEndGravity.setOnClickListener(new OnClickListener() {
//				
//				@Override
//				public void onClick(View v) {
//					// TODO Auto-generated method stub
//					sensorManager.unregisterListener(gravityListener);
//					btnStartGravity.setClickable(true);
//					btnEndGravity.setClickable(false);
//					gravity_show.setText("Gravity Mode");
//				}
//			});
//	        
//	    }
	public String msg;
	int CLOCK = 0;
	@Deprecated
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState){
		Log.d("fuck","ModeGravityFragment");
		View view = inflater.inflate(R.layout.modegravityfragment, container, false);
		txt_gravity_y = (TextView)view.findViewById(R.id.txt_gravity_y);
		sensor_manager = (SensorManager)getActivity().getSystemService(Context.SENSOR_SERVICE);
        gravity_sensor = sensor_manager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        msg = "";
        seekbar = (SeekBar)view.findViewById(R.id.seekbar);
        seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				seek_val = progress;
			}
		});
        
        
        
        gravity_listener = new SensorEventListener() {
			@Override
			public void onSensorChanged(SensorEvent event) {
				// TODO Auto-generated method stub
				int y = (int)(event.values[SensorManager.DATA_Y]);
				++CLOCK;
				if(CLOCK==20){
					Log.d("fuck", "CLOCK");
					Log.d("fuck", ""+seek_val);
					msg = "";
					if(seek_val==3) { Pub.sendMessage("s"); CLOCK = 0; return;}
//					if(seek_val<3) msg += "gu";
//					else if(seek_val>3) msg += "gd";
//					msg += ('0'+Math.abs(seek_val-3));
//					if(Math.abs(y)<20) msg += '0';
//					else if(Math.abs(y)<45) msg += '1';
//					else msg += '2';
//					if(Math.abs(y)<20) msg += 'n';
//					else if(y<0) msg += 'l';
//					else msg += 'r';
					if(seek_val<3) msg += "gu";
					else msg+= "gd";
					if(seek_val==0 || seek_val==6) msg+='3';
					else if(seek_val==1 || seek_val==5) msg+='2';
					else msg+='1';
					if(Math.abs(y)<30) msg += 'n';
					else if(y<0) msg += 'l';
					else msg += 'r';
					Pub.sendMessage(msg);
					CLOCK = 0;
				}
				txt_gravity_y.setText("Y = "+y);
			}
			
			@Override
			public void onAccuracyChanged(Sensor sensor, int accuracy) {
				// TODO Auto-generated method stub				
			}
		};
		sensor_manager.registerListener(gravity_listener, gravity_sensor, SensorManager.SENSOR_DELAY_GAME);
		
		return view;
	}
}
