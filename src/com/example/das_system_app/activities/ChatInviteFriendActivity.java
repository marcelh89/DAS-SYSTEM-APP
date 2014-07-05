package com.example.das_system_app.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.das_system_app.R;
import com.example.das_system_app.model.Gruppe;
import com.example.das_system_app.rest.valueobject.User;
import com.example.das_system_app.util.DataWrapper;

import android.widget.AdapterView.*;

public class ChatInviteFriendActivity extends Activity implements
		OnClickListener {

	public static final String GROUP = "group";
	public static final String USER = "user";

	public static final int RESULT_INVITE_FRIEND = 1;

	Spinner mUsers, mGroups;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chat_invitefriend);

		DataWrapper<Gruppe> dw = (DataWrapper) getIntent()
				.getSerializableExtra("grouplist");
		ArrayList<Gruppe> grouplist = dw.getList();
		grouplist.remove(0); // delete global group

		User currentUser = (User) getIntent().getSerializableExtra("user");

		DataWrapper<User> dw2 = (DataWrapper) getIntent().getSerializableExtra(
				"userlist");
		List<User> userlist = dw2.getList();

		ArrayList<Gruppe> grouplistCopy = (ArrayList<Gruppe>) grouplist.clone();

		// delete groups im not creator of
		for (int i = 0; i < grouplistCopy.size(); i++) {

			User user = grouplistCopy.get(i).getCreator();

			if (!user.getUid().equals(currentUser.getUid())) {
				grouplist.remove(0);
			}

		}

		// check if grouplist empty after deleting non creator groups
		if (grouplist.isEmpty()) {
			startFailDialog();
		}

		mGroups = (Spinner) findViewById(R.id.GroupSpinner);
		ArrayAdapter<Gruppe> dataAdapter = new ArrayAdapter<Gruppe>(this,
				android.R.layout.simple_spinner_item, grouplist);
		mGroups.setAdapter(dataAdapter);

		mUsers = (Spinner) findViewById(R.id.UserSpinner);
		ArrayAdapter<User> adapter = new ArrayAdapter<User>(this,
				android.R.layout.simple_spinner_item, userlist);
		mUsers.setAdapter(adapter);

		Button mAccept = (Button) findViewById(R.id.AccButton);
		mAccept.setOnClickListener(this);

	}

	private int startFailDialog() {

		AlertDialog.Builder ad = new AlertDialog.Builder(this);
		ad.setIcon(R.drawable.ic_launcher);
		ad.setTitle("Fehler beim Finden von Gruppen");
		ad.setMessage("Sie haben bisher keine EIGENE Gruppe angelegt, bitte tuen Sie dies!");
		ad.setCancelable(true);
		ad.setNegativeButton(android.R.string.ok,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
						finish();
					}
				});

		AlertDialog alert = ad.create();
		alert.show();

		return 0;
	}

	@Override
	public void onClick(View v) {

		Intent returnIntent = new Intent();

		// TODO hier is n denkfehler!
		User selectedUser = (User) mUsers.getSelectedItem();
		Gruppe selectedGroup = (Gruppe) mGroups.getSelectedItem();

		if (selectedGroup != null && selectedUser != null) {
			returnIntent.putExtra(GROUP, selectedGroup);
			returnIntent.putExtra(USER, selectedUser);
			setResult(RESULT_INVITE_FRIEND, returnIntent);
			finish();
		} else {

		}

	}
}
