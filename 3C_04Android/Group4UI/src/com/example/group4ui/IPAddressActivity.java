package com.example.group4ui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class IPAddressActivity extends Activity{
		private Button btn_IP_OK;
		private EditText edit_txt_IP;
		
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	        setContentView(R.layout.activity_ip);
	        Log.d("fuck", "IP Address Activity");
	        
	        btn_IP_OK = (Button)findViewById(R.id.btn_IP_OK);
	        edit_txt_IP = (EditText)findViewById(R.id.edit_txt_IP);
	        
	        btn_IP_OK.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Pub.IP_Address = edit_txt_IP.getText().toString();
					Log.d("fuck", Pub.IP_Address);
					finish();
				}
			});
	    }


	    @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        // Inflate the menu; this adds items to the action bar if it is present.
//	        getMenuInflater().inflate(R.menu.main, menu);
	        return true;
	    }

	    @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        // Handle action bar item clicks here. The action bar will
	        // automatically handle clicks on the Home/Up button, so long
	        // as you specify a parent activity in AndroidManifest.xml.
//	        int id = item.getItemId();
//	        if (id == R.id.action_settings) {
//	            return true;
//	        }
	        return super.onOptionsItemSelected(item);
	    }
}
