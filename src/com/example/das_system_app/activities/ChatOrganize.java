package com.example.das_system_app.activities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.das_system_app.R;
import com.example.das_system_app.rest.valueobject.User;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 
 */

/**
 * @author marcman
 * 
 */
public class ChatOrganize extends Activity implements OnItemClickListener {

	public static final String GROUP_NAME = "gName";
	public static final String GROUP_PASSWORD = "gPassword";

	public static final int GROUP_CREATE = 0;
	public static final int FRIEND_INVITE = 1;

	List<String> chatlist;
	ArrayAdapter<String> dataAdapter;
	User currentUser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chat_choose);

		currentUser = (User) getIntent().getSerializableExtra("user");

		ListView listView = (ListView) findViewById(R.id.listView1);

		chatlist = new ArrayList<String>(Arrays.asList("global", "privat1",
				"privat2"));

		dataAdapter = new ArrayAdapter<String>(this, R.layout.simplerow,
				chatlist);

		listView.setAdapter(dataAdapter);

		listView.setOnItemClickListener(this);

		new Handler().postDelayed(new Runnable() {
			public void run() {
				openOptionsMenu();
			}
		}, 2000);

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		TextView c = (TextView) view.findViewById(R.id.rowTextView);
		String room = c.getText().toString();
		Log.i("OnItemClicked", room);

		Intent intent = new Intent(this, ChatActivity.class);

		// if (room != "global") {
		// intent.putExtra("isPrivate", true);
		// }

		intent.putExtra("room", room);
		intent.putExtra("user", currentUser);
		startActivity(intent);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.layout.chatmenu, menu);
		return true;
	}

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
			startActivityForResult(new Intent(this,
					ChatInviteFriendActivity.class), FRIEND_INVITE);

			break;
		default:
			val = false;

		}

		return val;

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == GROUP_CREATE) {
			String name = data.getStringExtra(GROUP_NAME);
			String password = data.getStringExtra(GROUP_PASSWORD);
			chatlist.add(name);
			dataAdapter.notifyDataSetChanged();

		} else if (requestCode == FRIEND_INVITE) {

		}
	}
}
