/**
 * 
 */
package com.example.das_system_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * @author marcman
 * 
 */
public class RegisterActivity extends Activity implements
		android.view.View.OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);

		Button registerButton = (Button) findViewById(R.id.RegisterButton);
		registerButton.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		startActivity(new Intent(this, LoginActivity.class));
	}

}
