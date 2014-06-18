package com.example.das_system_app.activities;

import java.util.HashMap;

import com.example.das_system_app.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class ChatGlobalActivity extends Activity implements OnClickListener {

	ImageButton postbtn;
	TextView chatView;
	EditText inputField;

	// HashMap<String, String> chatlog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat_global);

		// chatlog = new HashMap<String, String>();

		inputField = (EditText) findViewById(R.id.editText2);
		chatView = (TextView) findViewById(R.id.textviewChat);

		postbtn = (ImageButton) findViewById(R.id.imageButton1);
		postbtn.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {

		String inputText = inputField.getText().toString().trim();

		String username = "PeterL";

		if (!inputText.isEmpty()) {
			chatView.append("\n" + username + "> " + inputText);
			inputField.setText("");
		}

	}
}
