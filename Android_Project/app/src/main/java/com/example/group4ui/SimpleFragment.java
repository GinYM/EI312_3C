package com.example.group4ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class SimpleFragment extends Fragment{
	private Button btn_mode_button;
	private Button btn_mode_gravity;
	private Button btn_mode_voice;
	
	private ModeButtonFragment mode_button_frag;
	private ModeGravityFragment mode_gravity_frag;
	private ModeVoiceFragment mode_voice_frag;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState){
		Log.d("fuck","SimpleFragment");
		View view = inflater.inflate(R.layout.simplefragment, container, false);
		btn_mode_button = (Button)view.findViewById(R.id.btn_mode_button);
		btn_mode_gravity = (Button)view.findViewById(R.id.btn_mode_gravity);
		btn_mode_voice = (Button)view.findViewById(R.id.btn_mode_voice);
		
		setDefaultFragment();
		btn_mode_button.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v) {
				btn_mode_button.setTextColor(getResources().getColor(R.color.subtab));
				btn_mode_button.setBackgroundColor(getResources().getColor(R.color.background));
				btn_mode_gravity.setTextColor(getResources().getColor(R.color.white));
				btn_mode_gravity.setBackgroundColor(getResources().getColor(R.color.subtab));
				btn_mode_voice.setTextColor(getResources().getColor(R.color.white));
				btn_mode_voice.setBackgroundColor(getResources().getColor(R.color.subtab));
				FragmentManager fm = getFragmentManager();
				FragmentTransaction transaction = fm.beginTransaction();
				mode_button_frag = new ModeButtonFragment();
				transaction.replace(R.id.id_simple_content, mode_button_frag);
				transaction.commit();
			}
		});
		
		btn_mode_gravity.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v) {
				btn_mode_button.setTextColor(getResources().getColor(R.color.white));
				btn_mode_button.setBackgroundColor(getResources().getColor(R.color.subtab));
				btn_mode_gravity.setTextColor(getResources().getColor(R.color.subtab));
				btn_mode_gravity.setBackgroundColor(getResources().getColor(R.color.background));
				btn_mode_voice.setTextColor(getResources().getColor(R.color.white));
				btn_mode_voice.setBackgroundColor(getResources().getColor(R.color.subtab));
				FragmentManager fm = getFragmentManager();
				FragmentTransaction transaction = fm.beginTransaction();
				mode_gravity_frag = new ModeGravityFragment();
				transaction.replace(R.id.id_simple_content, mode_gravity_frag);
				transaction.commit();
			}
		});
		
		btn_mode_voice.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v) {
				btn_mode_button.setTextColor(getResources().getColor(R.color.white));
				btn_mode_button.setBackgroundColor(getResources().getColor(R.color.subtab));
				btn_mode_gravity.setTextColor(getResources().getColor(R.color.white));
				btn_mode_gravity.setBackgroundColor(getResources().getColor(R.color.subtab));
				btn_mode_voice.setTextColor(getResources().getColor(R.color.subtab));
				btn_mode_voice.setBackgroundColor(getResources().getColor(R.color.background));
				FragmentManager fm = getFragmentManager();
				FragmentTransaction transaction = fm.beginTransaction();
				mode_voice_frag = new ModeVoiceFragment();
				transaction.replace(R.id.id_simple_content, mode_voice_frag);
				transaction.commit();
			}
		});
		return view;
	}
	
	private void setDefaultFragment(){
		FragmentManager fm = getFragmentManager();
		FragmentTransaction transaction = fm.beginTransaction();
		mode_button_frag = new ModeButtonFragment();
		transaction.replace(R.id.id_simple_content, mode_button_frag);
		transaction.commit();
	}
}
