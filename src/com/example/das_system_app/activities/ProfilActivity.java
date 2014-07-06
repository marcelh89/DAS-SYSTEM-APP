/**
 * 
 */
package com.example.das_system_app.activities;

import com.example.das_system_app.R;
import com.example.das_system_app.rest.valueobject.User;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

/**
 * @author marcman
 * 
 */
public class ProfilActivity extends Activity {
	User currentUser;
	EditText mForename;
	EditText mSurname;
	EditText mEmail;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profil);

		currentUser = (User) getIntent().getSerializableExtra("user");

		mForename = (EditText) findViewById(R.id.editText1);
		mForename.setEnabled(false);
		mForename.setText(currentUser.getForename());

		mSurname = (EditText) findViewById(R.id.editText2);
		mSurname.setEnabled(false);
		mSurname.setText(currentUser.getSurname());

		mEmail = (EditText) findViewById(R.id.editText3);
		mEmail.setEnabled(false);
		mEmail.setText(currentUser.getEmail());
	}
}