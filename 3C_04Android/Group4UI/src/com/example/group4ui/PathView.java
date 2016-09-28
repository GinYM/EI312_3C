package com.example.group4ui;

import java.util.ArrayList;
import java.util.List;

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

public class PathView extends View{
	private Path path;
	private Paint paint;
	private int COUNT = 0;
	private int CLOCK = 0;
	private List<Integer> args = new ArrayList<Integer>();
	private List<Integer> distances = new ArrayList<Integer>();
	private int x1 = 0, y1 = 0, x2 = 0, y2 = 0;
	private int arg = 0, a = 0, b = 0, l = 0;
	private int delay = 0;
	
	public PathView(Context context, AttributeSet attrs){
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
		switch(e.getAction()){
		case MotionEvent.ACTION_DOWN:
			args.clear();
			distances.clear();
			args.add(0);
			distances.add(0);
			path.reset();
			x1 = (int)e.getX();
			y1 = (int)e.getY();
			path.moveTo(x1, y1);
			args.add(0);
			distances.add(0);
			CLOCK = COUNT= delay = 0;
			break;
		case MotionEvent.ACTION_MOVE:
			x2 = (int)e.getX();
			y2 = (int)e.getY();
			path.lineTo(x2, y2);
			++CLOCK;
			if(CLOCK==3){
				CLOCK=0;
				a = x1 - x2;
				b = y1 - y2;
				l = (int)(Math.sqrt(a*a + b*b));
				arg = (int)(Math.acos(((double)b)/((double)l))/Math.PI * 180);
				if(a<0) arg = -arg;
				l = l * 12;
				args.add(arg);
				distances.add(l);
				Log.d("fuck", "arg: "+arg+" l: "+l);
				++COUNT;
				x1 = x2;
				y1 = y2;
			}
			invalidate();
			break;
		case MotionEvent.ACTION_UP:
			invalidate();
			new Thread(new Runnable(){
				public void run(){
					int i=1;
					for(i=1; i<=COUNT; ++i){
						arg = args.get(i) - args.get(i-1);
						if(arg>180) arg = arg - 360;
						else if(arg<-180) arg = arg + 360;
						if(arg>0) Pub.sendMessage("m");
						else if(arg<0) Pub.sendMessage("n");
						delay = Math.abs(arg) * 6500 / 360;
						try{
							Thread.sleep(delay);
						} catch(InterruptedException e){
							e.printStackTrace();
						}
						delay = distances.get(i);
						Pub.sendMessage("u");
						try{
							Thread.sleep(delay);
						} catch(InterruptedException e){
							e.printStackTrace();
						}
					}
					Pub.sendMessage("s");
					
					
				}
			}).start();
			break;
		}
		return true;
	}
}
