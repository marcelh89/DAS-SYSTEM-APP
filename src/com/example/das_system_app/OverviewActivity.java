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

		Button buttons[] = { (Button) findViewById(R.id.button1),
				(Button) findViewById(R.id.button2),
				(Button) findViewById(R.id.button3),
				(Button) findViewById(R.id.button4),
				(Button) findViewById(R.id.button5) };

		for (Button button : buttons) {
			button.setOnClickListener(this);
		}

	}

	@Override
	public void onClick(View view) {

		switch (view.getId()) {
		case R.id.button1:
			startActivity(new Intent(this, NavigationActivity.class));
			break;
		case R.id.button2:
			startActivity(new Intent(this, RegisterActivity.class));
			break;
		case R.id.button3:
			startActivity(new Intent(this, ProfilActivity.class));
			break;
		case R.id.button4:
			startActivity(new Intent(this, RegisterActivity.class));
			break;
		case R.id.button5:
			startActivity(new Intent(this, RegisterActivity.class));
			break;
		default:

		}

	}

}
