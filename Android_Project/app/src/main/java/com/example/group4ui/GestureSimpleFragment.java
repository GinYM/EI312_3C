package com.example.group4ui;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class GestureSimpleFragment extends Fragment{
	private int x1;
	private int x2;
	private int y1;
	private int y2;
	
	
	private TextView txt_gesture_show;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState){
		Log.d("fuck","GestureSimpleFragment");
		View view = inflater.inflate(R.layout.gesturesimplefragment, container, false);
		txt_gesture_show = (TextView)view.findViewById(R.id.txt_gesture_show);
		txt_gesture_show.setOnTouchListener(new OnTouchListener() {
			
			@SuppressLint("ClickableViewAccessibility") 
			@Override
			public boolean onTouch(View v, MotionEvent e) {
				switch(e.getAction()){
				case MotionEvent.ACTION_DOWN:
					x1 = (int)e.getX();
					y1 = (int)e.getY();
					break;
				case MotionEvent.ACTION_UP:
					x2 = (int)e.getX();
					y2 = (int)e.getY();
					if(y1 - y2 > 150) {
						txt_gesture_show.setText("UP");
						Pub.sendMessage("u");
					} else if(y2 - y1 > 150) {
						txt_gesture_show.setText("DOWN");
						Pub.sendMessage("d");
					} else if(x1 - x2 > 150) {
						txt_gesture_show.setText("LEFT");
						Pub.sendMessage("l");
					} else if(x2 - x1 > 150) {
						txt_gesture_show.setText("RIGHT");
						Pub.sendMessage("r");
					} else if(((x1-x2)<10||(x2-x1)<10) && ((y1-y2)<10||(y2-y1)<10)){
						txt_gesture_show.setText("STOP");
						Pub.sendMessage("s");
					}
					break;
				}
				return true;
			}
		});
		return view;
	}
}
