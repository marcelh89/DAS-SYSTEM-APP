/**
 * 
 */
package com.example.das_system_app.activities;

import java.util.ArrayList;
import java.util.List;

import com.example.das_system_app.R;
import com.example.das_system_app.rest.DasSystemRESTAccessor;
import com.example.das_system_app.rest.IDasSystemRESTAccessor;
import com.example.das_system_app.rest.valueobject.Vorlesung;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
/**
 * @author marcman
 *
 */
public class DozentActivity extends Activity implements OnClickListener{
	private Spinner listVorlesung;
	private EditText code;
	private String codeStr;
	private Button submit;
	private ProgressDialog mDialog;
	private HoleVorlesungenTask mVorTask;
	private UpdateCodeTask mUpdateTask;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dozent_code);

		listVorlesung = (Spinner) findViewById(R.id.list_vorlesung);
		code = (EditText) findViewById(R.id.code);
		submit = (Button) findViewById(R.id.btnSubmit);
		submit.setOnClickListener(this);
		
		mVorTask = new HoleVorlesungenTask(this);
		mVorTask.execute();


	}
	
	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.btnSubmit){
			checkInputAndSend();
		}
		
	}
	
	private void checkInputAndSend() {
		codeStr = code.getText().toString();
		code.setError(null);
		boolean cancel = false;
		View focusView = null;

		// Check for a valid password.
		if (TextUtils.isEmpty(codeStr)) {
			code.setError("Codefeld ist leer");
			focusView = code;
			cancel = true;
		}
		if (cancel) {
			focusView.requestFocus();
		} else {
			mUpdateTask = new UpdateCodeTask(this);
			mUpdateTask.execute();
		}
	}

	public class HoleVorlesungenTask extends AsyncTask<Void, Void, List<Vorlesung>> {
		
		public HoleVorlesungenTask(Context context) {
			mDialog = new ProgressDialog(context);
		}
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mDialog.setMessage("Lade Vorlesungen");
			mDialog.show();
		}
		
		@Override
		protected List<Vorlesung> doInBackground(Void... params) {
			SharedPreferences settings = getSharedPreferences(LoginActivity.PREFS_NAME, 0);
			int userid = settings.getInt("UserId", 0);
			IDasSystemRESTAccessor acc = new DasSystemRESTAccessor();
			List<Vorlesung> vorlesungen = acc.getVorlesungByDozent(userid);
			return vorlesungen;
		}

		@Override
		protected void onPostExecute(List<Vorlesung> resultList) {
			super.onPostExecute(resultList);
			mDialog.hide();
			mVorTask = null;
			if(resultList != null){
				ArrayAdapter<Vorlesung> dataAdapter = new ArrayAdapter<Vorlesung>(DozentActivity.this,android.R.layout.simple_spinner_item, resultList);
				dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				listVorlesung.setAdapter(dataAdapter);
				Vorlesung vorlesung = (Vorlesung)listVorlesung.getSelectedItem();
				if(vorlesung.getAnmeldecode() != null){
					code.setText(vorlesung.getAnmeldecode());					
				}
			}else{
				Toast toast = Toast.makeText(getApplicationContext(), 
						"Konnte keine Vorlesungen finden!", Toast.LENGTH_SHORT);
				toast.show();
			}
			
		}
		
	}
	public class UpdateCodeTask extends AsyncTask<Void, Void, Boolean> {
		public UpdateCodeTask(Context context) {
			mDialog = new ProgressDialog(context);
		}
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mDialog.setMessage("Update Code");
			mDialog.show();
		}
		
		@Override
		protected Boolean doInBackground(Void... params) {
			Vorlesung vorlesung = (Vorlesung)listVorlesung.getSelectedItem();
			vorlesung.setAnmeldecode(codeStr);
			IDasSystemRESTAccessor acc = new DasSystemRESTAccessor();
			return acc.updateVorlesungCode(vorlesung);
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			mUpdateTask = null;
			mDialog.hide();
			if(result){
				Toast toast = Toast.makeText(getApplicationContext(), 
						"Update erfolgreich", Toast.LENGTH_SHORT);
				toast.show();
			}else{
				Toast toast = Toast.makeText(getApplicationContext(), 
						"Update Fehlgeschlagen!", Toast.LENGTH_SHORT);
				toast.show();
			}
		}
		
	}
}
