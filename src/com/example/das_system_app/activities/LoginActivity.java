package com.example.das_system_app.activities;

import com.example.das_system_app.R;
import com.example.das_system_app.rest.DasSystemRESTAccessor;
import com.example.das_system_app.rest.IDasSystemRESTAccessor;
import com.example.das_system_app.rest.valueobject.User;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends Activity implements OnClickListener {
	
	private boolean isOnline;
	private ProgressDialog mDialog;
	private EditText mEmailView;
	private EditText mPasswordView;
	private UserLoginTask mAuthTask = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		mEmailView = (EditText) findViewById(R.id.LoginEmail);
		mPasswordView = (EditText) findViewById(R.id.LoginPassword);
		
		Button loginButton = (Button) findViewById(R.id.LoginButton);
		loginButton.setOnClickListener(this);
		
		TextView registerView = (TextView) findViewById(R.id.LoginRegistrieren);
		registerView.setOnClickListener(this);
		
		TextView passwordVergessenView = (TextView) findViewById(R.id.LoginPasswortVergessen);
		passwordVergessenView.setOnClickListener(this);
		
//		isServerReachable();

	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.LoginRegistrieren:
			startActivity(new Intent(this, RegisterActivity.class));
			break;
		case R.id.LoginPasswortVergessen:
			startActivity(new Intent(this, RegisterActivity.class));
			break;
		case R.id.LoginButton:
			mAuthTask = new UserLoginTask();
			mAuthTask.execute((Void) null);
//			startActivity(new Intent(this, OverviewActivity.class));
			break;
		default:
		}

	}
	
	
	public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
		private boolean mailFalsch = false;
		@Override
		protected Boolean doInBackground(Void... params) {
//			if(isOnline){
			IDasSystemRESTAccessor acc = new DasSystemRESTAccessor();
			System.out.println(mEmailView.getText().toString()+","+ mPasswordView.getText().toString());
			
			User u = acc.login(new User(mEmailView.getText().toString(), mPasswordView.getText().toString()));
			System.out.println("user:"+u);
//			System.out.println(acc.halloWelt());
				
//			}else{
//				for (String credential : DUMMY_CREDENTIALS) {
//					String[] pieces = credential.split(":");
//					if (pieces[0].equals(mEmail)) {
//						// Account exists, return true if the password matches.
//						return pieces[1].equals(mPassword);
//						}
//					mailFalsch = true;
//				}
			return false;
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			mAuthTask = null;
//			showProgress(false);

			if (success) {
				Intent intent = new Intent(LoginActivity.this, OverviewActivity.class);
//				intent.putExtra(IS_ONLINE, isOnline);
				startActivity(intent);
				finish();
			} 
//			else {
//				if(mailFalsch){
//					mEmailView.setError(getString(R.string.error_invalid_email));
//				}else{
//					mPasswordView.setError(getString(R.string.error_incorrect_password));
//					mPasswordView.requestFocus();
//				}
		}

		@Override
		protected void onCancelled() {
			mAuthTask = null;
//			showProgress(false);
		}
	}
}
