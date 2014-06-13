/**
 * 
 */
package com.example.das_system_app.activities;

import com.example.das_system_app.R;
import com.example.das_system_app.R.id;
import com.example.das_system_app.R.layout;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

/**
 * @author marcman
 *
 */
public class RaumActivity extends Activity implements OnClickListener{
	
	private Button scanBtn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.raum);
		scanBtn = (Button)findViewById(R.id.scanBtn);
		scanBtn.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.scanBtn){
			IntentIntegrator scanIntegrator = new IntentIntegrator(this);	
			scanIntegrator.initiateScan();
		}
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
		if (scanningResult != null) {
			String scanContent = scanningResult.getContents();
			String scanFormat = scanningResult.getFormatName();
			Toast toast = Toast.makeText(getApplicationContext(), 
		            scanContent+" "+scanFormat, Toast.LENGTH_SHORT);
		    toast.show();
		}else{
		    Toast toast = Toast.makeText(getApplicationContext(), 
		            "No scan data received!", Toast.LENGTH_SHORT);
		    toast.show();
		}
		
	}

}
