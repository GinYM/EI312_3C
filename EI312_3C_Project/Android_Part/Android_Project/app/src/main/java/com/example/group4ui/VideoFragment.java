package com.example.group4ui;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class VideoFragment extends Fragment{
	private Button btn_live_video;
	private Button btn_face_recognization;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState){
		Log.d("fuck","VideoFragment");
		View view = inflater.inflate(R.layout.videofragment, container, false);
		
		btn_live_video = (Button)view.findViewById(R.id.btn_live_video);
		btn_face_recognization = (Button)view.findViewById(R.id.btn_face_recognization);
		
		btn_live_video.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getActivity(), vMainActivity.class));
			}
		});
		
		btn_face_recognization.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//				btn_face_recognization.setBackgroundColor(getResources().getColor(R.color.theme));
				startActivity(new Intent(getActivity(), FaceRecognizationActivity.class));
			}
		});
		return view;
	}
}
