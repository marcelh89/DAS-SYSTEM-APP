/**
 * 
 */
package com.example.das_system_app.activities;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.das_system_app.R;
import com.example.das_system_app.rest.DasSystemRESTAccessor;
import com.example.das_system_app.rest.IDasSystemRESTAccessor;
import com.example.das_system_app.rest.valueobject.KursAnmeldenIn;
import com.example.das_system_app.rest.valueobject.Rauminformation;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author marcman & patman
 *
 */
public class KursAnmeldenActivity extends Activity implements OnClickListener{
	
	private Button scanBtn;
	private AnmeldeTask mAnmeldeTask;
	private TextView vorlesung,inhalt;
	private CheckBox angemeldet;
	private Rauminformation rauminfo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.kursanmelden);
		scanBtn = (Button)findViewById(R.id.scanBtn);
		scanBtn.setOnClickListener(this);
		
		vorlesung = (TextView) findViewById(R.id.vorlesungText);
		inhalt = (TextView) findViewById(R.id.inhaltText);
		angemeldet = (CheckBox) findViewById(R.id.angemeldet);
		
//		mDialog = new ProgressDialog(this);
//		mDialog.setMessage("Rufe Informationen ab...");
		

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
//			String scanFormat = scanningResult.getFormatName();
			mAnmeldeTask = new AnmeldeTask(KursAnmeldenActivity.this);
			mAnmeldeTask.execute(scanContent);
		}else{
		    Toast toast = Toast.makeText(getApplicationContext(), 
		            "No scan data received!", Toast.LENGTH_SHORT);
		    toast.show();
		}
	}
	
	public class AnmeldeTask extends AsyncTask<String, Void, Boolean> {
		String anmeldeCode;
//		private ProgressDialog mDialog;
		public AnmeldeTask(Context context) {
//			mDialog = new ProgressDialog(context);
//			mDialog.setMessage("Rufe Informationen ab...");
		}
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
//			mDialog.show();
		}
		@SuppressLint("SimpleDateFormat")
		@Override
		protected Boolean doInBackground(String... params) {
			anmeldeCode = params[0];
			System.out.println(anmeldeCode);
			SimpleDateFormat sf = new SimpleDateFormat("dd.MM.yyyy");
			SharedPreferences settings = getSharedPreferences(LoginActivity.PREFS_NAME, 0);
			int userid = settings.getInt("UserId", 0);
			KursAnmeldenIn kIn = new KursAnmeldenIn();
			kIn.setAnmeldecode(anmeldeCode);
			kIn.setUserid(userid);
			kIn.setDatum(sf.format(new Date()));
			IDasSystemRESTAccessor acc = new DasSystemRESTAccessor();
			rauminfo = acc.anKursAnmelden(kIn);
			if(rauminfo != null){
				return true;	
			}else{
				return false;
			}
			
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			mAnmeldeTask = null;
//			mDialog.hide();
			if(result){
				angemeldet.setChecked(true);
				vorlesung.setText(rauminfo.getName()); 
				inhalt.setText(rauminfo.getInhalt());
			}else{
				Toast toast = Toast.makeText(getApplicationContext(), 
						"Konnte Veranstalltung nicht finden!", Toast.LENGTH_SHORT);
				toast.show();
			}
		}

		@Override
		protected void onCancelled() {
			super.onCancelled();
			mAnmeldeTask = null;
//			mDialog.hide();
		}
		
	}

}
