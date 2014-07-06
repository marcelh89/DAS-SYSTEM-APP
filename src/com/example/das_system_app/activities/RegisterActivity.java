/**
 * 
 */
package com.example.das_system_app.activities;

import com.example.das_system_app.R;
import com.example.das_system_app.rest.DasSystemRESTAccessor;
import com.example.das_system_app.rest.IDasSystemRESTAccessor;
import com.example.das_system_app.rest.valueobject.User;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * @author marcman
 * 
 */
public class RegisterActivity extends Activity implements
		android.view.View.OnClickListener {
	private EditText eVorname, eNachname, eEmail, ePassword, ePassword2;
	private Button registerButton;
	private ProgressDialog mDialog;
	private UserRegisterTask uRegTask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		eVorname = (EditText) findViewById(R.id.RegisterVorname);
		eNachname = (EditText) findViewById(R.id.RegisterNachname);
		eEmail = (EditText) findViewById(R.id.RegisterEmail);
		ePassword = (EditText) findViewById(R.id.RegisterPassword);
		ePassword2 = (EditText) findViewById(R.id.RegisterCheckPassword);
		
		registerButton = (Button) findViewById(R.id.RegisterButton);
		registerButton.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.RegisterButton:
			registerUser();
//			startActivity(new Intent(this, RegisterActivity.class));
			break;
		default:
		}
	}
	
	private void registerUser() {
		if(validateInput()){
			uRegTask = new UserRegisterTask(this);
			uRegTask.execute();
		}
	}

	private boolean validateInput(){
		eVorname.setError(null);
		eNachname.setError(null);
		eEmail.setError(null);
		ePassword.setError(null);
		ePassword2.setError(null);
		
		boolean cancel = false;
		View focusView = null;
		if (TextUtils.isEmpty(eVorname.getText().toString())) {
			eVorname.setError(getString(R.string.reg_pflichtfeld));
			focusView = eVorname;
			cancel = true;
		}
		if (TextUtils.isEmpty(eNachname.getText().toString())) {
			eNachname.setError(getString(R.string.reg_pflichtfeld));
			focusView = eNachname;
			cancel = true;
		}
		if (TextUtils.isEmpty(eEmail.getText().toString())) {
			eEmail.setError(getString(R.string.reg_pflichtfeld));
			focusView = eEmail;
			cancel = true;
		}else if(!eEmail.getText().toString().contains("@")){
			eEmail.setError(getString(R.string.reg_email_ungueltig));
			focusView = eEmail;
			cancel = true;
		}
		if (TextUtils.isEmpty(ePassword.getText().toString())) {
			ePassword.setError(getString(R.string.reg_pflichtfeld));
			focusView = ePassword;
			cancel = true;
		}
		if (TextUtils.isEmpty(ePassword2.getText().toString())) {
			ePassword2.setError(getString(R.string.reg_pflichtfeld));
			focusView = ePassword2;
			cancel = true;
		} else if(!ePassword2.getText().toString().equals(ePassword.getText().toString())){
			ePassword2.setError(getString(R.string.reg_pw_ungleich));
			focusView = ePassword2;
			cancel = true;
		}

		
		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
			return false;
		} else {
			return true;
		}
	}

	public class UserRegisterTask extends AsyncTask<Void, Void, Boolean> {
		Context context;

		UserRegisterTask(Context context) {
			this.context = context;
		}
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mDialog = new ProgressDialog(context);
			mDialog.setMessage("Registriere...");
			mDialog.show();
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			User newUser = new User();
			newUser.setBirthDate(null);
			newUser.setDozent(false);
			newUser.setEmail(eEmail.getText().toString());
			newUser.setForename(eVorname.getText().toString());
			newUser.setSurname(eNachname.getText().toString());
			newUser.setPassword(ePassword.getText().toString());
			
			IDasSystemRESTAccessor acc = new DasSystemRESTAccessor();
			return acc.register(newUser);
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			mDialog.hide();
			if(result){
				Toast toast = Toast.makeText(getApplicationContext(), 
						"Erfolgreich registriert!", Toast.LENGTH_SHORT);
				toast.show();	
				Intent intent = new Intent(RegisterActivity.this, OverviewActivity.class);
				startActivity(intent);
			}else{
				Toast toast = Toast.makeText(getApplicationContext(), 
						"Registrierung fehlgeschlagen!", Toast.LENGTH_SHORT);
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
