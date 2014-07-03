package com.example.das_system_app.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.das_system_app.R;
import com.example.das_system_app.activities.LoginActivity.UserLoginTask;
import com.example.das_system_app.adapters.ChatListAdapter;
import com.example.das_system_app.adapters.ExpandableListAdapter;
import com.example.das_system_app.rest.DasSystemRESTAccessor;
import com.example.das_system_app.rest.IDasSystemRESTAccessor;
import com.example.das_system_app.rest.valueobject.User;
import com.example.das_system_app.rest.valueobject.User_old;

import de.tavendo.autobahn.WebSocketConnection;
import de.tavendo.autobahn.WebSocketException;
import de.tavendo.autobahn.WebSocketHandler;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ChatActivity extends Activity implements OnClickListener {

	private static String URLS[] = {
			"ws://10.0.2.2:8080/DAS-SYSTEM-SERVER/chat/",
			"ws://192.168.178.60:8080/DAS-SYSTEM-SERVER/chat/" };

	ImageButton postbtn;
	ListView mChatListView;
	ChatListAdapter listAdapter;
	EditText inputField;
	User currentUser;
	String room;
	boolean isConnectionEnabled = false;
	private ChatConnectTask mConnectTask = null;

	List<String> labelValues = new ArrayList<String>();
	List<String> values = new ArrayList<String>();

	private static String CLS = "ChatService";
	private final WebSocketConnection mConnection = new WebSocketConnection();

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_chat);

		currentUser = (User) getIntent().getSerializableExtra("user");

		room = getIntent().getStringExtra("room");

		inputField = (EditText) findViewById(R.id.editText2);
		// chatView = (TextView) findViewById(R.id.textviewChat);

		mChatListView = (ListView) findViewById(R.id.ChatlistView);

		listAdapter = new ChatListAdapter(getBaseContext(), values, labelValues);

		mChatListView.setAdapter(listAdapter);

		postbtn = (ImageButton) findViewById(R.id.imageButton1);
		postbtn.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {

		String inputText = inputField.getText().toString().trim();

		if (!inputText.isEmpty()) {

			JSONObject chatmessage = new JSONObject();
			String sender = currentUser.getForename() + "|"
					+ currentUser.getSurname();

			try {
				chatmessage.put("message", inputText);
				chatmessage.put("sender", sender);
				chatmessage.put("received", "");
			} catch (JSONException e) {
				e.printStackTrace();
			}

			mConnection.sendTextMessage(chatmessage.toString());
			inputField.setText("");
		}

	}

	public class ChatConnectTask extends AsyncTask<Void, Void, Boolean> {

		@Override
		protected Boolean doInBackground(Void... params) {
			connect();
			return false;
		}

		@Override
		protected void onPostExecute(final Boolean success) {

			Handler handler = new Handler();
			handler.postDelayed(new Runnable() {
				public void run() {

					// check if connection is Available
					if (mConnection.isConnected()) {
						Log.i("ChatConnectTask", "connection established");
					} else {
						Log.e("ChatConnectTask", "server not available");
						startFailDialog();
						// Alert Handling and
						// get back to parent activity
					}

				}
			}, 2000);

			mConnectTask = null;

		}

		@Override
		protected void onCancelled() {
			mConnectTask = null;
			// showProgress(false);
		}
	}

	public void connect() {

		final String wsuri = URLS[1] + room;

		try {
			mConnection.connect(wsuri, new WebSocketHandler() {

				@Override
				public void onOpen() {
					Log.d(CLS, "Status: Connected to " + wsuri);
				}

				@Override
				public void onTextMessage(String payload) {
					Log.d(CLS, "Got echo: " + payload);

					try {
						JSONObject jsonObj = new JSONObject(payload);
						String sender = jsonObj.getString("sender");
						String message = jsonObj.getString("message");
						String received = jsonObj.getString("received");

						labelValues.add(sender);
						values.add(message);
						listAdapter.notifyDataSetChanged();

					} catch (JSONException e) {
						Log.e(CLS, "Got exception: " + e.getMessage());
					}
				}

				@Override
				public void onClose(int code, String reason) {
					Log.d(CLS, "Connection lost.");
				}

			});

		} catch (WebSocketException e) {
			Log.d(CLS, e.toString());
		}

	}

	@Override
	protected void onPause() {
		super.onPause();
		mConnection.disconnect();
	}

	@Override
	protected void onResume() {
		super.onResume();

		if (isConnectionEnabled) {
			labelValues.add("Willkommen im Chat");
			values.add(room);

			mConnectTask = new ChatConnectTask();
			mConnectTask.execute((Void) null);

		} else {
			// boolean isPrivate = getIntent().getBooleanExtra("isPrivate",
			// false);
			// if (isPrivate) {
			// startInitialDialog();
			// } else {
			isConnectionEnabled = true;
			onResume();
			// }
		}

	}

	// private void startInitialDialog() {
	//
	// ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter
	// .createFromResource(this, R.array.chats,
	// android.R.layout.simple_list_item_1);
	//
	// AlertDialog.Builder ad = new AlertDialog.Builder(this);
	// ad.setIcon(R.drawable.ic_launcher);
	// ad.setTitle("Wählen sie eine Gruppe");
	// ad.setView(LayoutInflater.from(this).inflate(R.layout.dialog, null));
	//
	// ad.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
	// public void onClick(DialogInterface dialog, int item) {
	// if (item == 0) {
	// room = "privat1";
	// } else {
	// room = "privat2";
	// }
	//
	// isConnectionEnabled = true;
	// onResume();
	// }
	// });
	//
	// AlertDialog alert = ad.create();
	// alert.show();
	//
	// }

	private void startFailDialog() {

		AlertDialog.Builder ad = new AlertDialog.Builder(this);
		ad.setIcon(R.drawable.ic_launcher);
		ad.setTitle("Server nicht erreichbar");
		ad.setMessage("Die Verbindung zum Server konnte nicht hergestellt werden, "
				+ "Klick ok um zurück zur Hauptansicht zu gelangen");
		ad.setCancelable(true);
		ad.setNeutralButton(android.R.string.ok,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
						finish();
					}
				});

		AlertDialog alert = ad.create();
		alert.show();

	}

}
