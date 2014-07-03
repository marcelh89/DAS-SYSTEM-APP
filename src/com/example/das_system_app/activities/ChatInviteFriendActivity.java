package com.example.das_system_app.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.das_system_app.R;
import com.example.das_system_app.model.Gruppe;
import com.example.das_system_app.rest.valueobject.User;
import com.example.das_system_app.util.DataWrapper;

public class ChatInviteFriendActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chat_invitefriend);

		DataWrapper dw = (DataWrapper) getIntent().getSerializableExtra(
				"grouplist");
		ArrayList<Gruppe> grouplist = dw.getGroups();
		grouplist.remove(0); // delete global group

		User currentUser = (User) getIntent().getSerializableExtra("user");
		List<String> userlist = getIntent().getStringArrayListExtra("userlist");

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

		Spinner mGroups = (Spinner) findViewById(R.id.GroupSpinner);
		ArrayAdapter<Gruppe> dataAdapter = new ArrayAdapter<Gruppe>(this,
				android.R.layout.simple_spinner_item, grouplist);
		mGroups.setAdapter(dataAdapter);

		Spinner mUsers = (Spinner) findViewById(R.id.UserSpinner);
		// ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
		// android.R.layout.simple_spinner_item, userlist);
		// mUsers.setAdapter(adapter);
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

}
