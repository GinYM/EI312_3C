package com.example.group4ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import java.math.*;

public class FlexibleView extends View{
	private Path path;
	private Paint paint;
	private int x1, y1, x2, y2;
	private int delay = 0;
	private int v = 0;
	public FlexibleView(Context context, AttributeSet attrs){
		super(context, attrs);
//		this.setFocusable(true);
		path = new Path();
		paint = new Paint();
		paint.setColor(Color.BLUE);
		paint.setAntiAlias(true);
		paint.setStrokeWidth(4);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeCap(Paint.Cap.ROUND);
	}
	
	@Override
	protected void onDraw(Canvas canvas){
		super.onDraw(canvas);
		canvas.drawPath(path, paint);
	}
	
	
	@SuppressLint("ClickableViewAccessibility") @Override
	public boolean onTouchEvent(MotionEvent e){
		int a = 0;
		int b = 0;
		int l = 0;
		int arg = 0;
		switch(e.getAction()){
		case MotionEvent.ACTION_DOWN:
			x1 = (int)e.getX();
			y1 = (int)e.getY();
			path.reset();
			path.moveTo(e.getX(), e.getY());
			
			break;
//		case MotionEvent.ACTION_MOVE:
//			path.lineTo(e.getX(), e.getY());
//			invalidate();
//			break;
		case MotionEvent.ACTION_UP:
			x2 = (int)e.getX();
			y2 = (int)e.getY();
			path.lineTo(e.getX(), e.getY());
			Log.d("fuck", "x2 : "+ x2 + " y2 : " + y2);
			a = x1 - x2;
			b = y1 - y2;
			l = (int)(Math.sqrt(a*a + b*b));
			Log.d("fuck", "a: " + a + " b: " + b + " l: " + l);
			v = l / 2;
			if(v>255) v=255;
			arg = (int)(Math.acos(((double)b)/((double)l))/Math.PI * 180);
			Log.d("fuck", "v: " + v +" arg: "+arg);
			delay = arg * 3000 / 360;
			if(Math.abs(a)<10 && Math.abs(b)<10) Pub.sendMessage("s");
			else if(a<0) Pub.sendMessage("r");
			else if(a>0) Pub.sendMessage("l");
			else if(a==0 && b>0);
			else if(a==0 && b<0) Pub.sendMessage("r");
			new Thread(new Runnable(){
				public void run(){
					try{
						Thread.sleep(delay);
					} catch(InterruptedException e){
						e.printStackTrace();
					}
					Pub.sendMessage(""+v);
				}
			}).start();
			invalidate();
			break;
		}
		return true;
	}
}
