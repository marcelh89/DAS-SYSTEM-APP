/**
 * 
 */
package com.example.das_system_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * @author marcman
 * 
 */
public class OverviewActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.overview);

		Button buttons[] = { (Button) findViewById(R.id.OverviewNavigation),
				(Button) findViewById(R.id.OverviewChat),
				(Button) findViewById(R.id.OverviewNutzerprofil),
				(Button) findViewById(R.id.OverviewRauminfo),
				(Button) findViewById(R.id.OverviewDozent) };

		for (Button button : buttons) {
			button.setOnClickListener(this);
		}

	}

	@Override
	public void onClick(View view) {

		switch (view.getId()) {
		case R.id.OverviewNavigation:
			startActivity(new Intent(this, NavigationActivity.class));
			break;
		case R.id.OverviewChat:
			startActivity(new Intent(this, RegisterActivity.class));
			break;
		case R.id.OverviewNutzerprofil:
			startActivity(new Intent(this, ProfilActivity.class));
			break;
		case R.id.OverviewRauminfo:
			startActivity(new Intent(this, RegisterActivity.class));
			break;
		case R.id.OverviewDozent:
			startActivity(new Intent(this, RegisterActivity.class));
			break;
		default:

		}

	}

}
