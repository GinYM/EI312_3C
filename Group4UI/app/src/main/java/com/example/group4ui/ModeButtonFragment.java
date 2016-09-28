package com.example.group4ui;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class ModeButtonFragment extends Fragment{
	private Button btn_up_arrow;
	private Button btn_left_arrow;
	private Button btn_right_arrow;
	private Button btn_down_arrow;
	private Button btn_center;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState){
		Log.d("fuck","ModeButtonFragment");
		View view = inflater.inflate(R.layout.modebuttonfragment, container, false);
		btn_up_arrow = (Button)view.findViewById(R.id.btn_up_arrow);
		btn_left_arrow = (Button)view.findViewById(R.id.btn_left_arrow);
		
		btn_right_arrow = (Button)view.findViewById(R.id.btn_right_arrow);
		btn_down_arrow = (Button)view.findViewById(R.id.btn_down_arrow);
		btn_center = (Button)view.findViewById(R.id.btn_center);
		
		btn_up_arrow.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v) {
				btn_up_arrow.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.uparrowpurple), null, null);
				btn_left_arrow.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.leftarrowpink), null, null);
				btn_right_arrow.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.rightarrowpink), null, null);
				btn_down_arrow.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.downarrowpink), null, null);
				btn_center.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.centerpink), null, null);
				Pub.sendMessage("u");
			}
		});
		btn_left_arrow.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v) {
				btn_up_arrow.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.uparrowpink), null, null);
				btn_left_arrow.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.leftarrowpurple), null, null);
				btn_right_arrow.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.rightarrowpink), null, null);
				btn_down_arrow.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.downarrowpink), null, null);
				btn_center.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.centerpink), null, null);
				Pub.sendMessage("l");
			}
		});
		btn_right_arrow.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v) {
				btn_up_arrow.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.uparrowpink), null, null);
				btn_left_arrow.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.leftarrowpink), null, null);
				btn_right_arrow.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.rightarrowpurple), null, null);
				btn_down_arrow.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.downarrowpink), null, null);
				btn_center.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.centerpink), null, null);
				Pub.sendMessage("r"); 
			}
		});
		btn_down_arrow.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v) {
				btn_up_arrow.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.uparrowpink), null, null);
				btn_left_arrow.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.leftarrowpink), null, null);
				btn_right_arrow.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.rightarrowpink), null, null);
				btn_down_arrow.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.downarrowpurple), null, null);
				btn_center.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.centerpink), null, null);
				Pub.sendMessage("d");
			}
		});
		btn_center.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v) {
				btn_up_arrow.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.uparrowpink), null, null);
				btn_left_arrow.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.leftarrowpink), null, null);
				btn_right_arrow.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.rightarrowpink), null, null);
				btn_down_arrow.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.downarrowpink), null, null);
				btn_center.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.centerpurple), null, null);
				Pub.sendMessage("s");
			}
		});
		return view;
	}
}
