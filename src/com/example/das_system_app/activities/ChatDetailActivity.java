package com.example.das_system_app.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Switch;

import com.example.das_system_app.R;
import com.example.das_system_app.rest.valueobject.User;

/**
 * 
 */

/**
 * @author marcman
 * 
 */
public class ChatDetailActivity extends Activity {

	User currentUser;
	String room;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chat_detail);

		currentUser = (User) getIntent().getSerializableExtra("user");
		room = getIntent().getStringExtra("room");

		EditText mGroupName = (EditText) findViewById(R.id.GroupName);
		mGroupName.setEnabled(false);
		mGroupName.setText(room);

		Switch mPrivate = (Switch) findViewById(R.id.switch1);

		if (room.equals("global")) {
			mPrivate.setChecked(false);
		} else {
			mPrivate.setChecked(true);

		}

		mPrivate.setClickable(false);

	}
}
