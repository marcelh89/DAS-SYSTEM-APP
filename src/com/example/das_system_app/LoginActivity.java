package com.example.das_system_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		Button loginButton = (Button) findViewById(R.id.LoginButton);
		loginButton.setOnClickListener(this);
		
		TextView registerView = (TextView) findViewById(R.id.LoginRegistrieren);
		registerView.setOnClickListener(this);
		
		TextView passwordVergessenView = (TextView) findViewById(R.id.LoginPasswortVergessen);
		passwordVergessenView.setOnClickListener(this);
		
		

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
			startActivity(new Intent(this, OverviewActivity.class));
			break;
		default:
		}

	}
}
