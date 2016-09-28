package com.example.group4ui;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.GrammarListener;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.sunflower.FlowerCollector;

public class ModeVoiceFragment extends Fragment implements OnClickListener{
	private static final String KEY_GRAMMAR_ABNF_ID = "grammar_abnf_id";
	private static final String GRAMMAR_TYPE_ABNF = "abnf";
	private static final String GRAMMAR_TYPE_BNF = "bnf";
	private SharedPreferences mSharedPreferences;
	private SpeechRecognizer mAsr;
	private String mCloudGrammar = null;
	private String mLocalGrammar = null;
	private View view;
	
	private String mEngineType = SpeechConstant.TYPE_LOCAL;
	private Toast mToast;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState){
		Log.d("fuck","ModeVoiceFragment");
		view = inflater.inflate(R.layout.modevoicefragment, container, false);
		
//		mEngineType = SpeechConstant.TYPE_CLOUD;
		mEngineType = SpeechConstant.TYPE_LOCAL;
		
		
		mAsr = SpeechRecognizer.createRecognizer(getActivity().getApplicationContext(), mInitListener);
		
		mCloudGrammar = FucUtil.readFile(getActivity().getApplicationContext(),"grammar_sample.abnf","utf-8");
		mLocalGrammar = FucUtil.readFile(getActivity().getApplicationContext(), "call.bnf", "utf-8");
//getActivity();
		//				"#ABNF 1.0 UTF-8;" +
//				"language zh-CN;" +
//				"mode voice;\n root $main;" +
//				"$main = 前进|后退|左转|右转|停止;";
		mSharedPreferences = getActivity().getSharedPreferences(getActivity().getPackageName(), Context.MODE_PRIVATE);
		mToast = Toast.makeText(getActivity().getApplicationContext(), "", Toast.LENGTH_SHORT);
		
		initLayout();
		return view;
	}
	String mContent;
	int ret = 0;
	private void initLayout(){
		view.findViewById(R.id.txt_voice).setOnClickListener(this);
//		view.findViewById(R.id.isr_grammar).setOnClickListener(this);
//		view.findViewById(R.id.isr_cancel).setOnClickListener(this);
//		view.findViewById(R.id.isr_stop).setOnClickListener(this);
		
		Log.d("fuck", "grammar");

		showTip("上传预设语法文件");
		mContent = new String(mLocalGrammar);
		mAsr.setParameter(SpeechConstant.TEXT_ENCODING, "utf-8");
		mAsr.setParameter(SpeechConstant.ENGINE_TYPE, mEngineType);
		ret = mAsr.buildGrammar(GRAMMAR_TYPE_BNF, mContent, grammarListener);
		if(ret != ErrorCode.SUCCESS){
			showTip("语法构建失败，错误码： "+ret);
		}
//		mContent = new String(mCloudGrammar);
//		Log.d("fuck", mContent);
//		mAsr.setParameter(SpeechConstant.ENGINE_TYPE, mEngineType);
//		mAsr.setParameter(SpeechConstant.TEXT_ENCODING,"utf-8");
//		ret = mAsr.buildGrammar(GRAMMAR_TYPE_ABNF, mContent, grammarListener);
//		Log.d("fuck", "" + ret);
//		if(ret != ErrorCode.SUCCESS)
//			showTip("语法构建失败，错误码："+ret);
		mEngineType = SpeechConstant.TYPE_LOCAL;
		if(!SpeechUtility.getUtility().checkServiceInstalled()){
			Log.d("fuck", "installer");
		}
	}
	
	
	@Override
	public void onClick(View v) {
		switch(v.getId()){
/*		case R.id.isr_grammar:
			Log.d("fuck", "grammar");
			showTip("上传预设语法文件");
			mContent = new String(mCloudGrammar);
			Log.d("fuck", mContent);
			mAsr.setParameter(SpeechConstant.ENGINE_TYPE, mEngineType);
			mAsr.setParameter(SpeechConstant.TEXT_ENCODING,"utf-8");
			ret = mAsr.buildGrammar(GRAMMAR_TYPE_ABNF, mContent, grammarListener);
			Log.d("fuck", "" + ret);
			if(ret != ErrorCode.SUCCESS)
				showTip("语法构建失败，错误码："+ret);
			break;*/
		case R.id.txt_voice:
			Log.d("fuck", "recognize");
			if(!setParam()){
				
				showTip("请先构建语法。");
				return;
			}
			ret = mAsr.startListening(mRecognizerListener);
			if(ret != ErrorCode.SUCCESS){
				showTip("识别失败，错误码："+ret);
			}
			break;
/*		case R.id.isr_stop:
			mAsr.stopListening();
			showTip("停止识别");
			break;
		case R.id.isr_cancel:
//			startActivity(new Intent(getActivity(), AsrDemo.class));
//			break;
			mAsr.cancel();
			showTip("取消识别");
			break;*/
		}
	}
	private InitListener mInitListener = new InitListener() {
		public void onInit(int code){
			Log.d("fuck", "SpeechRecogizer init() code = " + code);
			if(code != ErrorCode.SUCCESS){
				showTip("初始化失败，错误码："+code);
			}
		}
	};
	
//	private GrammarListener grammarListener = new GrammarListener(){
//		@Override
//		public void onBuildFinish(String grammarId, SpeechError error){
//			if(error == null){
//				grammarId = new String(grammarId);
//				Editor editor = mSharedPreferences.edit();
//				if(!TextUtils.isEmpty(grammarId))
//					editor.putString(KEY_GRAMMAR_ABNF_ID, grammarId);
//				editor.commit();
//				showTip("构建语法成功："+grammarId);
//			}else {
//				showTip("构建语法失败，错误码："+error.getErrorCode());
//			}
//		}
//	};
	
	private GrammarListener grammarListener = new GrammarListener() {
		@Override
		public void onBuildFinish(String grammarId, SpeechError error) {
			if(error == null){
//				String grammarID = new String(grammarId);
//				Editor editor = mSharedPreferences.edit();
//				if(!TextUtils.isEmpty(grammarId))
//					editor.putString(KEY_GRAMMAR_ABNF_ID, grammarID);
//				editor.commit();
				showTip("语法构建成功：" + grammarId);
			}else{
				showTip("语法构建失败,错误码：" + error.getErrorCode());
			}	

		}
	};
	
	private void showTip(final String str){
		mToast.setText(str);
		mToast.show();
	}
	
	public boolean setParam(){
		boolean result = false;
		mAsr.setParameter(SpeechConstant.ENGINE_TYPE, mEngineType);
		mAsr.setParameter(SpeechConstant.RESULT_TYPE, "json");
//		String grammarId = mSharedPreferences.getString(KEY_GRAMMAR_ABNF_ID, null);
//		Log.d("fuck", "here");
//		if(TextUtils.isEmpty(grammarId))
//		{
//			Log.d("fuck", "is empty");
//			result =  false;
//		}else {
////			//设置云端返回结果为xml格式，后续会支持json格式
////			mAsr.setParameter(SpeechConstant.RESULT_TYPE, "json");
//			//设置云端识别使用的语法id
//			mAsr.setParameter(SpeechConstant.CLOUD_GRAMMAR, grammarId);
//			result =  true;
//		}
		mAsr.setParameter(SpeechConstant.LOCAL_GRAMMAR, "call");
		mAsr.setParameter(SpeechConstant.MIXED_THRESHOLD, "30");
		result = true;
		return result;
	}
	
private RecognizerListener mRecognizerListener = new RecognizerListener() {
        
        @Override
        public void onVolumeChanged(int volume) {
        	showTip("当前正在说话，音量大小：" + volume);
        }
        
        @Override
        public void onResult(final RecognizerResult result, boolean isLast) {
        	getActivity().runOnUiThread(new Runnable() {

				@Override
				public void run() {
					Log.d("fuck", "onresult");
					if (null != result) {
						Log.d("fuck", "recognizer result：" + result.getResultString());
						String text = "";
						text = JsonParser.parseGrammarResult(result.getResultString());
						if(text.equals("前进")) { ((TextView)view.findViewById(R.id.txt_voice)).setText("UP"); Pub.sendMessage("u");}
						else if(text.equals("左转")) { ((TextView)view.findViewById(R.id.txt_voice)).setText("LEFT"); Pub.sendMessage("l");}
						else if(text.equals("右转")) { ((TextView)view.findViewById(R.id.txt_voice)).setText("RIGHT"); Pub.sendMessage("r");}
						else if(text.equals("后退")) { ((TextView)view.findViewById(R.id.txt_voice)).setText("DOWN"); Pub.sendMessage("d");}
						else if(text.equals("停止")) { ((TextView)view.findViewById(R.id.txt_voice)).setText("STOP"); Pub.sendMessage("s");}
		            } else {
		                Log.d("fuck", "recognizer result : null");
		            }	
				}
			});
            
        }
        
        @Override
        public void onEndOfSpeech() {
        	showTip("结束说话");
        }
        
        @Override
        public void onBeginOfSpeech() {
        	showTip("开始说话");
        }

		@Override
		public void onError(SpeechError error) {
			showTip("onError Code："	+ error.getErrorCode());
		}

		@Override
		public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
			// TODO Auto-generated method stub
			
		}

    };
    
    @Override
	public void onDestroy() {
		super.onDestroy();
		// 退出时释放连接
		mAsr.cancel();
		mAsr.destroy();
	}
	
	@Override
	public void onResume() {
		//移动数据统计分析
		FlowerCollector.onResume(getActivity().getApplicationContext());
		FlowerCollector.onPageStart("AsrDemo");
		super.onResume();
	}
	@Override
	public void onPause() {
		//移动数据统计分析
		FlowerCollector.onPageEnd("AsrDemo");
		FlowerCollector.onPause(getActivity().getApplicationContext());
		super.onPause();
	}

	
}
