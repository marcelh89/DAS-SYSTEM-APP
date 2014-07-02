/**
 * 
 */
package com.example.das_system_app.activities;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.example.das_system_app.R;
import com.example.das_system_app.R.id;
import com.example.das_system_app.R.layout;
import com.example.das_system_app.rest.DasSystemRESTAccessor;
import com.example.das_system_app.rest.IDasSystemRESTAccessor;
import com.example.das_system_app.rest.valueobject.RauminfoIn;
import com.example.das_system_app.rest.valueobject.Rauminformation;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
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
	private ProgressDialog mDialog;
	private RaumInformation mRaumTask;
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
//			IntentIntegrator scanIntegrator = new IntentIntegrator(this);	
//			scanIntegrator.initiateScan();
			mRaumTask = new RaumInformation(this);
			mRaumTask.execute();
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
	
	public class RaumInformation extends AsyncTask<Void, Void, Boolean> {
		Context context;
		public RaumInformation(Context context) {
			this.context = context; 
		}
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mDialog = new ProgressDialog(context);
			mDialog.setMessage("Rufe Informationen ab...");
			mDialog.show();
		}
		@SuppressLint("SimpleDateFormat")
		@Override
		protected Boolean doInBackground(Void... params) {
			IDasSystemRESTAccessor acc = new DasSystemRESTAccessor();
			RauminfoIn rIn = new RauminfoIn();
			SimpleDateFormat sf = new SimpleDateFormat("dd.MM.yyyy-HH:mm");
			//TODO Testzwecke
			Calendar monTest = new GregorianCalendar(2014,5,30,9,15);
			//
			rIn.setDatum(sf.format(monTest.getTime()));
			rIn.setRaumNr("A34");
			Rauminformation raumInfo= acc.getRauminformation(rIn);
			if(raumInfo != null){
				return true;	
			}else{
				return false;
			}
			
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			mDialog.hide();
			if(result){
				Toast toast = Toast.makeText(getApplicationContext(), 
						"DA isser", Toast.LENGTH_SHORT);
				toast.show();
			}else{
				Toast toast = Toast.makeText(getApplicationContext(), 
						"Keine aktuelle Veranstalltung!", Toast.LENGTH_SHORT);
				toast.show();
			}
		}

		@Override
		protected void onCancelled() {
			super.onCancelled();
			mDialog.hide();
		}
		
	}

}
