package com.example.group4ui;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class GesturePathFragment extends Fragment{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState){
		Log.d("fuck","GesturePathFragment");
		return inflater.inflate(R.layout.gesturepathfragment, container, false);
	}
}
