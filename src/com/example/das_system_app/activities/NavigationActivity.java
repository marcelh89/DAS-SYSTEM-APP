/**
 * 
 */
package com.example.das_system_app.activities;

import com.example.das_system_app.R;
import com.example.das_system_app.R.layout;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

/**
 * @author marcman
 * 
 */
// TODO Implementation
// https://developers.google.com/maps/documentation/android/v1/hello-mapview
public class NavigationActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.navigation);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}
}
