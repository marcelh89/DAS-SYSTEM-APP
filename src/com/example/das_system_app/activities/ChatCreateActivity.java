package com.example.das_system_app.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.example.das_system_app.R;
import com.example.das_system_app.model.Gruppe;

public class ChatCreateActivity extends Activity implements OnClickListener {

	public static final int RESULT_CREATE_GROUP = 0;

	public static final String GROUP_NAME = "gName";
	public static final String GROUP_PASSWORD = "gPassword";

	Button mCreateGroup;
	EditText mGroupName, mGroupPassword;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.chat_creategroup);

		mGroupName = (EditText) findViewById(R.id.GroupName);
		mGroupPassword = (EditText) findViewById(R.id.GroupPassword);
		mCreateGroup = (Button) findViewById(R.id.CreateGroupButton);

		mCreateGroup.setOnClickListener(this);

	}

	@Override
	public void onClick(View view) {

		Intent returnIntent = new Intent();

		String name = mGroupName.getText().toString().replaceAll("\\s+", "")
				.trim();
		String password = mGroupPassword.getText().toString()
				.replaceAll("\\s+", "").trim();

		if (name != "" && password != "") {
			returnIntent.putExtra(GROUP_NAME, name);
			returnIntent.putExtra(GROUP_PASSWORD, password);
			setResult(RESULT_CREATE_GROUP, returnIntent);
			finish();
		} else {
			// alert

		}

	}
}
