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
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends Activity implements OnClickListener {
	private String mEmail;
	private String mPassword;
	// private boolean isOnline;
	private ProgressDialog mDialog;
	private EditText mEmailView;
	private EditText mPasswordView;
	// private View mLoginFormView;
	// private View mLoginStatusView;
	private Button mButton;
	// private TextView mLoginStatusMessageView;
	private UserLoginTask mAuthTask = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		mEmailView = (EditText) findViewById(R.id.LoginEmail);
		mPasswordView = (EditText) findViewById(R.id.LoginPassword);

		mEmailView.setText("marcelh89@googlemail.com");
		mPasswordView.setText("123");

		mButton = (Button) findViewById(R.id.LoginButton);
		mButton.setOnClickListener(this);
		// Button loginButton = (Button) findViewById(R.id.LoginButton);
		// loginButton.setOnClickListener(this);

		TextView registerView = (TextView) findViewById(R.id.LoginRegistrieren);
		registerView.setOnClickListener(this);

		TextView passwordVergessenView = (TextView) findViewById(R.id.LoginPasswortVergessen);
		passwordVergessenView.setOnClickListener(this);

		// mLoginFormView = findViewById(R.id.login_form);
		// mLoginStatusView = findViewById(R.id.login_status);
		// mLoginStatusMessageView = (TextView)
		// findViewById(R.id.login_status_message);

		// isServerReachable();

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
			attemptLogin();
			break;
		default:
		}

	}

	public void attemptLogin() {
		if (mAuthTask != null) {
			return;
		}

		// Reset errors.
		mEmailView.setError(null);
		mPasswordView.setError(null);

		// Store values at the time of the login attempt.
		mEmail = mEmailView.getText().toString();
		mPassword = mPasswordView.getText().toString();

		boolean cancel = false;
		View focusView = null;

		// Check for a valid password.
		if (TextUtils.isEmpty(mPassword)) {
			mPasswordView.setError("Passwortfeld ist leer");
			focusView = mPasswordView;
			cancel = true;
		} else if (mPassword.length() < 1) {
			mPasswordView.setError("Passwort zu kurz");
			focusView = mPasswordView;
			cancel = true;
		}

		// Check for a valid email address.
		if (TextUtils.isEmpty(mEmail)) {
			mEmailView.setError("Emailfeld ist leer");
			focusView = mEmailView;
			cancel = true;
		} else if (!mEmail.contains("@")) {
			mEmailView.setError("Email nicht korrekt");
			focusView = mEmailView;
			cancel = true;
		}

		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
			// mLoginStatusMessageView.setText(R.string.login_progress_signing_in);
			// showProgress(true);
			mAuthTask = new UserLoginTask(this);
			mAuthTask.execute((Void) null);
		}
	}

	public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
		Context context;
		User u;

		public UserLoginTask(Context context) {
			this.context = context;
		}

		protected void onPreExecute() {
			super.onPreExecute();
			mDialog = new ProgressDialog(context);
			mDialog.setMessage("Logge ein...");
			mDialog.show();
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			boolean userExists = false;
			IDasSystemRESTAccessor acc = new DasSystemRESTAccessor();
			System.out.println(mEmailView.getText().toString() + ","
					+ mPasswordView.getText().toString());
			u = acc.login2(new User(mEmailView.getText().toString(),
					mPasswordView.getText().toString()));
			// User_old u = acc.login(new
			// User_old(mEmailView.getText().toString(),
			// mPasswordView.getText().toString()));
			System.out.println("user:" + u);
			if (u != null) {
				userExists = true;
			}
			return userExists;

		}

		@Override
		protected void onPostExecute(Boolean success) {
			mAuthTask = null;
			// showProgress(false);
			mDialog.hide();

			if (success) {
				Intent intent = new Intent(LoginActivity.this,
						OverviewActivity.class);
				intent.putExtra("user", u);
				startActivity(intent);
			} else {
				mEmailView.setError("Email oder Passwort falsch");
				mPasswordView.setError("Email oder Passwort falsch");
				mPasswordView.requestFocus();
			}
		}

		@Override
		protected void onCancelled() {
			mAuthTask = null;
			// mDialog.hide();
			// showProgress(false);
		}
	}
}
