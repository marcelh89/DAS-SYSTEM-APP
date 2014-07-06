package com.example.das_system_app.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.das_system_app.R;
import com.example.das_system_app.model.Gruppe;
import com.example.das_system_app.rest.DasSystemRESTAccessor;
import com.example.das_system_app.rest.IDasSystemRESTAccessor;
import com.example.das_system_app.rest.valueobject.User;
import com.example.das_system_app.util.DataWrapper;

public class ChatInviteFriendActivity extends Activity implements
		OnClickListener, OnItemSelectedListener {

	private static final String MSG_NOFRIEND = "Sie haben keine weiteren Freunde die sie "
			+ "hinzufügen könnten bzw. es alle Freunde bereits hinzugefügt worden.";
	private static final String MSG_NOGROUP = "Sie haben bisher keine EIGENE Gruppe "
			+ "angelegt, bitte tuen Sie dies!";

	public static final String GROUP = "group";
	public static final String USER = "user";

	public static final int RESULT_INVITE_FRIEND = 1;

	List<User> userlist = new ArrayList<User>();
	private UserLoadTask mUserLoadTask = null;
	Spinner mUsers, mGroups;
	ArrayAdapter<User> userAdapter;
	User currentUser;
	ArrayList<Gruppe> grouplist;
	Gruppe selectedGroup;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chat_invitefriend);

		// mUserLoadTask = new UserLoadTask(this);
		// mUserLoadTask.execute((Void) null);

		DataWrapper<Gruppe> dw = (DataWrapper) getIntent()
				.getSerializableExtra("grouplist");
		grouplist = dw.getList();
		// grouplist.remove(0); // delete global group

		currentUser = (User) getIntent().getSerializableExtra("user");
		ArrayList<Gruppe> grouplistCopy = (ArrayList<Gruppe>) grouplist.clone();

		// delete groups im not creator of
		for (int i = 0; i < grouplistCopy.size(); i++) {

			User user = grouplistCopy.get(i).getCreator();

			if (!user.getUid().equals(currentUser.getUid())) {
				grouplist.remove(0);
			}

		}

		// cancel if grouplist is empty
		if (grouplist.isEmpty()) {
			startFailDialog(MSG_NOGROUP);
		}

		mGroups = (Spinner) findViewById(R.id.GroupSpinner);
		ArrayAdapter<Gruppe> groupAdapter = new ArrayAdapter<Gruppe>(this,
				android.R.layout.simple_spinner_item, grouplist);
		mGroups.setAdapter(groupAdapter);
		mGroups.setOnItemSelectedListener(this);

		mUsers = (Spinner) findViewById(R.id.UserSpinner);
		userAdapter = new ArrayAdapter<User>(this,
				android.R.layout.simple_spinner_item, userlist);
		mUsers.setAdapter(userAdapter);

		Button mAccept = (Button) findViewById(R.id.AccButton);
		mAccept.setOnClickListener(this);

	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {

		Log.i("OnItemSelected", position + " -- "
				+ grouplist.get(position).toString());

		selectedGroup = grouplist.get(position);

		mUserLoadTask = new UserLoadTask(this);
		mUserLoadTask.execute((Void) null);

	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
	}

	@Override
	public void onClick(View v) {

		Intent returnIntent = new Intent();

		User selectedUser = (User) mUsers.getSelectedItem();
		Gruppe selectedGroup = (Gruppe) mGroups.getSelectedItem();

		// if (selectedGroup != null && selectedUser != null) {
		returnIntent.putExtra(GROUP, selectedGroup);
		returnIntent.putExtra(USER, selectedUser);
		setResult(RESULT_INVITE_FRIEND, returnIntent);
		finish();
		// } else {

		// }

	}

	/**
	 * load Users task
	 * 
	 * @author marcman
	 * 
	 */
	public class UserLoadTask extends AsyncTask<Void, Void, Boolean> {
		Context context;
		List<User> users;

		public UserLoadTask(Context context) {
			this.context = context;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			userlist.clear();
			userAdapter.notifyDataSetChanged();

		}

		@Override
		protected Boolean doInBackground(Void... params) {
			IDasSystemRESTAccessor acc = new DasSystemRESTAccessor();
			users = acc.getUser();
			return false;
		}

		@Override
		protected void onPostExecute(Boolean success) {

			for (User user : users) {

				// exclude currentuser from invitelist
				if (user.getUid().equals(currentUser.getUid())) {
					System.out.println();
				} else {
					userlist.add(user);
				}

			}

			// delete users that are already in selectedGroup.getUsers();
			List<User> selectedUsers = selectedGroup.getUsers();

			// create copy of userlist with copy constructor
			ArrayList<User> userlistCopy = new ArrayList<User>(userlist);

			// work with copied list
			for (User user : userlistCopy) {

				for (User selUser : selectedUsers) {
					if (selUser.getUid().equals(user.getUid())) {
						userlist.remove(user);
						Log.i("remove", "remove");
					}
				}

			}

			userAdapter.notifyDataSetChanged();

			if (userlist.isEmpty()) {
				startFailDialog(MSG_NOFRIEND);
			}

		}

		@Override
		protected void onCancelled() {
			mUserLoadTask = null;
		}
	}

	private int startFailDialog(String msg) {

		AlertDialog.Builder ad = new AlertDialog.Builder(this);
		ad.setIcon(R.drawable.ic_launcher);
		ad.setTitle("Ein Fehler ist aufgetreten");
		ad.setMessage(msg);
		ad.setCancelable(true);
		ad.setNegativeButton(android.R.string.ok,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
						Intent returnIntent = new Intent();
						setResult(RESULT_INVITE_FRIEND, returnIntent);
						finish();
					}
				});

		AlertDialog alert = ad.create();
		alert.show();

		return 0;
	}

	@Override
	public void onBackPressed() {
		Intent returnIntent = new Intent();
		setResult(RESULT_INVITE_FRIEND, returnIntent);
		finish();
	}

}
