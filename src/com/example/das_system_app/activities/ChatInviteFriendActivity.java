package com.example.das_system_app.activities;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.das_system_app.R;
import com.example.das_system_app.rest.valueobject.User;

public class ChatInviteFriendActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chat_invitefriend);
		
		
		List<String> chatlist = getIntent().getStringArrayListExtra("chatlist");
		chatlist.remove(0); // only the private ones
		// seperate only those chats which i am creator of
		

		Spinner mGroups = (Spinner) findViewById(R.id.GroupSpinner);
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, chatlist);
		mGroups.setAdapter(dataAdapter);

		
		User currentUser = (User) getIntent().getSerializableExtra("user");
		List<String> userlist = getIntent().getStringArrayListExtra("userlist");
		userlist.remove(currentUser);		//remove creator of group
		
		Spinner mUsers = (Spinner) findViewById(R.id.UserSpinner);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, userlist);
		mUsers.setAdapter(adapter);
	}
}
