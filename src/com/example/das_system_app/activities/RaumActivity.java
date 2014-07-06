/**
 * 
 */
package com.example.das_system_app.activities;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.example.das_system_app.R;
import com.example.das_system_app.rest.DasSystemRESTAccessor;
import com.example.das_system_app.rest.IDasSystemRESTAccessor;
import com.example.das_system_app.rest.valueobject.RauminfoIn;
import com.example.das_system_app.rest.valueobject.Rauminformation;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author marcman & patman
 *
 */
public class RaumActivity extends Activity implements OnClickListener{
	
	private Button scanBtn;
	private RaumInformation mRaumTask;
	private TextView raumNr,vorlesung,begin,ende,inhalt;
	private Rauminformation rauminfo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.raum);
		scanBtn = (Button)findViewById(R.id.scanBtn);
		scanBtn.setOnClickListener(this);
		
		raumNr = (TextView) findViewById(R.id.raumNrText);
		vorlesung = (TextView) findViewById(R.id.vorlesungText);
		begin = (TextView) findViewById(R.id.beginText);
		ende = (TextView) findViewById(R.id.endeText);
		inhalt = (TextView) findViewById(R.id.inhaltText);
		
//		mDialog = new ProgressDialog(this);
//		mDialog.setMessage("Rufe Informationen ab...");
		

	}

	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.scanBtn){
			IntentIntegrator scanIntegrator = new IntentIntegrator(this);	
			scanIntegrator.initiateScan();
//			mRaumTask = new RaumInformation(this);
//			mRaumTask.execute();
		}
	}
	
	
	
	@Override
	protected void onResume() {
		super.onResume();
		if(rauminfo != null){
			raumNr.setText(rauminfo.getRaumNr());
			vorlesung.setText(rauminfo.getName()); 
			begin.setText(rauminfo.getBegin());
			ende.setText(rauminfo.getEnde());
			inhalt.setText(rauminfo.getInhalt());	
		}
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
		if (scanningResult != null) {
			String scanContent = scanningResult.getContents();
//			String scanFormat = scanningResult.getFormatName();
			mRaumTask = new RaumInformation(RaumActivity.this);
			mRaumTask.execute(scanContent);
//			Toast toast = Toast.makeText(getApplicationContext(), 
//		            scanContent+" "+scanFormat, Toast.LENGTH_SHORT);
//		    toast.show();
		}else{
		    Toast toast = Toast.makeText(getApplicationContext(), 
		            "No scan data received!", Toast.LENGTH_SHORT);
		    toast.show();
		}
	}
	
	public class RaumInformation extends AsyncTask<String, Void, Boolean> {
		String raumNrStr;
//		private ProgressDialog mDialog;
		public RaumInformation(Context context) {
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
			raumNrStr = params[0];
			System.out.println(raumNrStr);
			IDasSystemRESTAccessor acc = new DasSystemRESTAccessor();
			RauminfoIn rIn = new RauminfoIn();
			SimpleDateFormat sf = new SimpleDateFormat("dd.MM.yyyy-HH:mm");
			//TODO Testzwecke
			Calendar monTest = new GregorianCalendar(2014,5,30,9,15);
			//
			rIn.setDatum(sf.format(monTest.getTime()));
			rIn.setRaumNr(raumNrStr);
			rauminfo= acc.getRauminformation(rIn);
			System.out.println(rauminfo);
			if(rauminfo != null){
				return true;	
			}else{
				return false;
			}
			
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			mRaumTask = null;
//			mDialog.hide();
			if(result){
//				raumNr = (TextView) findViewById(R.id.raumNrText);
//				vorlesung = (TextView) findViewById(R.id.vorlesungText);
//				begin = (TextView) findViewById(R.id.beginText);
//				ende = (TextView) findViewById(R.id.endeText);
//				inhalt = (TextView) findViewById(R.id.inhaltText);
//				System.out.println(""+raumNr+vorlesung+begin+ende+inhalt);
				raumNr.setText(raumNrStr);
				vorlesung.setText(rauminfo.getName()); 
				begin.setText(rauminfo.getBegin());
				ende.setText(rauminfo.getEnde());
				inhalt.setText(rauminfo.getInhalt());
			}else{
				raumNr.setText(raumNrStr);
				Toast toast = Toast.makeText(getApplicationContext(), 
						"Keine aktuelle Veranstalltung!", Toast.LENGTH_SHORT);
				toast.show();
			}
		}

		@Override
		protected void onCancelled() {
			super.onCancelled();
			mRaumTask = null;
//			mDialog.hide();
		}
		
	}

}
