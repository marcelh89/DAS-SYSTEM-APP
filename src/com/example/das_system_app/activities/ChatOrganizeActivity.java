package com.example.das_system_app.activities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;

import com.example.das_system_app.R;
import com.example.das_system_app.activities.LoginActivity.UserLoginTask;
import com.example.das_system_app.model.Gruppe;
import com.example.das_system_app.rest.DasSystemRESTAccessor;
import com.example.das_system_app.rest.IDasSystemRESTAccessor;
import com.example.das_system_app.rest.valueobject.User;
import com.example.das_system_app.util.DataWrapper;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 
 */

/**
 * @author marcman
 * 
 */
public class ChatOrganizeActivity extends Activity implements
		OnItemClickListener, OnItemLongClickListener {

	public static final String GROUP_NAME = "gName";
	public static final String GROUP_PASSWORD = "gPassword";

	public static final String OPTION_DELETE_STR = "Gruppe löschen";
	public static final String OPTION_DETAIL_STR = "Details ansehen";

	public static final int GROUP_CREATE = 0;
	public static final int FRIEND_INVITE = 1;

	public static final String GROUP = "group";
	public static final String USER = "user";

	List<Gruppe> grouplist;
	List<User> userlist;

	Gruppe actGroup;
	User actUser;

	ArrayAdapter<Gruppe> dataAdapter;
	User currentUser;
	String room;
	IDasSystemRESTAccessor acc;
	private GroupLoadTask mGroupLoadTask = null;
	private GroupAddTask mGroupAddTask = null;
	private UserLoadTask mUserLoadTask = null;
	private GroupUpdateTask mGroupUpdateTask = null;
	private GroupDeleteTask mGroupDeleteTask = null;

	/**
	 * triggered once on startup of activity
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chat_choose);

		currentUser = (User) getIntent().getSerializableExtra("user");
		acc = new DasSystemRESTAccessor();

		ListView listView = (ListView) findViewById(R.id.listView1);
		grouplist = new ArrayList<Gruppe>();
		userlist = new ArrayList<User>();

		dataAdapter = new ArrayAdapter<Gruppe>(this, R.layout.simplerow,
				grouplist);

		listView.setAdapter(dataAdapter);

		listView.setOnItemClickListener(this);
		listView.setOnItemLongClickListener(this);

		registerForContextMenu(listView);

		new Handler().postDelayed(new Runnable() {
			public void run() {
				openOptionsMenu();
			}
		}, 2000);

	}

	/**
	 * listview itemClicklistener
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		TextView c = (TextView) view.findViewById(R.id.rowTextView);
		room = c.getText().toString();
		Log.i("OnItemClicked", room);

		Intent intent = new Intent(this, ChatActivity.class);

		intent.putExtra("room", room);
		intent.putExtra("user", currentUser);
		startActivity(intent);

	}

	/**
	 * listview itemlongclicklistener fuer löschen, detailansicht von chats
	 */
	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {

		TextView c = (TextView) view.findViewById(R.id.rowTextView);
		room = c.getText().toString();
		Log.i("OnItemLongClicked", room);

		startSelectionDialog(position);

		return false;
	}

	/**
	 * context menu
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.layout.chatmenu, menu);
		return true;
	}

	/**
	 * context menu actions
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		boolean val = true;

		switch (id) {
		case R.id.GroupCreate:
			startActivityForResult(new Intent(this, ChatCreateActivity.class),
					GROUP_CREATE);

			break;
		case R.id.FriendInvite:

			mUserLoadTask = new UserLoadTask(this);
			mUserLoadTask.execute((Void) null);

			break;
		default:
			val = false;

		}

		return val;

	}

	/**
	 * child activity handling
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == GROUP_CREATE) {
			String name = data.getStringExtra(GROUP_NAME);
			String password = data.getStringExtra(GROUP_PASSWORD);
			grouplist.add(new Gruppe(grouplist.size() + 1, name, false,
					currentUser));

			// add to database
			mGroupAddTask = new GroupAddTask(this);
			mGroupAddTask.execute((Void) null);

		} else if (requestCode == FRIEND_INVITE) {

			int selectedUserPosition = data.getIntExtra(USER, -1);
			int selectedGroupPosition = data.getIntExtra(GROUP, -1);

			if (selectedGroupPosition != -1 && selectedUserPosition != -1) {

				actGroup = grouplist.get(selectedGroupPosition);
				actUser = userlist.get(selectedUserPosition);

				// send PUT/POST change groups add user to group.users
				// update param group, user
				mGroupUpdateTask = new GroupUpdateTask(this);
				mGroupUpdateTask.execute((Void) null);

			}

		}
	}

	/**
	 * longclick dialog for delete and detail view
	 * 
	 * @param position
	 * @return
	 */
	private int startSelectionDialog(final int position) {

		AlertDialog.Builder ad = new AlertDialog.Builder(this);
		ad.setIcon(R.drawable.ic_launcher);
		ad.setTitle("Was wollen Sie tun?");
		ad.setCancelable(true);
		ad.setNegativeButton(OPTION_DELETE_STR,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();

						Gruppe group = grouplist.get(position);
						Integer creatorId = group.getCreator().getUid();

						// set global group for access in async task
						actGroup = group;

						boolean isSystem = creatorId.equals(2);
						boolean isCreator = currentUser.getUid().equals(
								creatorId);

						if (isSystem || !isCreator) {
							startFailDialog();
						} else {
							grouplist.remove(position);
							dataAdapter.notifyDataSetChanged();

							// send changements to db
							mGroupDeleteTask = new GroupDeleteTask(
									getBaseContext());
							mGroupDeleteTask.execute((Void) null);
						}

					}
				});
		ad.setNeutralButton(OPTION_DETAIL_STR,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
						Intent intent = new Intent(getBaseContext(),
								ChatDetailActivity.class);

						intent.putExtra("room", room);
						intent.putExtra("user", currentUser);
						startActivity(intent);

					}
				});

		AlertDialog alert = ad.create();
		alert.show();

		return 0;

	}

	/**
	 * failure dialog
	 * 
	 * @return
	 */
	private int startFailDialog() {

		AlertDialog.Builder ad = new AlertDialog.Builder(this);
		ad.setIcon(R.drawable.ic_launcher);
		ad.setTitle("Fehler beim Löschen");
		ad.setMessage("Die globale Gruppe kann nicht gelöscht werden, da sie nicht der Ersteller sind!");
		ad.setCancelable(true);
		ad.setNegativeButton(android.R.string.ok,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});

		AlertDialog alert = ad.create();
		alert.show();

		return 0;

	}

	/**
	 * onresume triggered after oncreate and when it comes up after onstop
	 */
	@Override
	protected void onResume() {
		super.onResume();
		mGroupLoadTask = new GroupLoadTask(this);
		mGroupLoadTask.execute((Void) null);
	};

	/**
	 * load groups task
	 * 
	 * @author marcman
	 * 
	 */
	public class GroupLoadTask extends AsyncTask<Void, Void, Boolean> {
		Context context;
		List<Gruppe> gruppen;

		public GroupLoadTask(Context context) {
			this.context = context;
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			IDasSystemRESTAccessor acc = new DasSystemRESTAccessor();
			gruppen = new ArrayList<Gruppe>();
			gruppen.addAll(acc.getGroups(currentUser));
			return false;

		}

		@Override
		protected void onPostExecute(Boolean success) {

			for (Gruppe group : gruppen) {
				if (!grouplist.contains(group)) {
					grouplist.add(group);
				}
			}

			dataAdapter.notifyDataSetChanged();
		}

		@Override
		protected void onCancelled() {
			mGroupLoadTask = null;
		}
	}

	/**
	 * add Groups task
	 * 
	 * @author marcman
	 * 
	 */
	public class GroupAddTask extends AsyncTask<Void, Void, Boolean> {
		Context context;

		public GroupAddTask(Context context) {
			this.context = context;
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			IDasSystemRESTAccessor acc = new DasSystemRESTAccessor();
			Gruppe gruppe = grouplist.get(grouplist.size() - 1);
			return acc.addGroup(gruppe);
		}

		@Override
		protected void onCancelled() {
			mGroupLoadTask = null;
		}
	}

	/**
	 * add Groups task
	 * 
	 * @author marcman
	 * 
	 */
	public class GroupDeleteTask extends AsyncTask<Void, Void, Boolean> {
		Context context;

		public GroupDeleteTask(Context context) {
			this.context = context;
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			IDasSystemRESTAccessor acc = new DasSystemRESTAccessor();
			return acc.deleteGroup(actGroup);
		}

		@Override
		protected void onCancelled() {
			mGroupLoadTask = null;
		}
	}

	/**
	 * update Groups task
	 * 
	 * @author marcman
	 * 
	 */
	public class GroupUpdateTask extends AsyncTask<Void, Void, Boolean> {
		Context context;

		public GroupUpdateTask(Context context) {
			this.context = context;
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			IDasSystemRESTAccessor acc = new DasSystemRESTAccessor();
			boolean check = acc.updateGroup(actGroup, actUser);
			return check;
		}

		@Override
		protected void onCancelled() {
			mGroupLoadTask = null;
		}
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
		protected Boolean doInBackground(Void... params) {
			IDasSystemRESTAccessor acc = new DasSystemRESTAccessor();
			users = acc.getUser();
			return false;
		}

		@Override
		protected void onPostExecute(Boolean success) {

			for (User user : users) {
				if (!userlist.contains(user)) {

					// exclude currentuser from invitelist
					if (user.getEmail().equals(currentUser.getEmail())) {
						System.out.println();
					} else {
						userlist.add(user);
					}

				}
			}

			// redirect to next intent
			Intent intent = new Intent(ChatOrganizeActivity.this,
					ChatInviteFriendActivity.class);
			intent.putExtra("grouplist", new DataWrapper<Gruppe>(grouplist));
			intent.putExtra("userlist", new DataWrapper<User>(userlist));
			intent.putExtra("user", currentUser);
			startActivityForResult(intent, FRIEND_INVITE);

		}

		@Override
		protected void onCancelled() {
			mGroupLoadTask = null;
		}
	}

}
