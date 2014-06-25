package com.example.das_system_app.activities;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.das_system_app.R;

import de.tavendo.autobahn.WebSocketConnection;
import de.tavendo.autobahn.WebSocketException;
import de.tavendo.autobahn.WebSocketHandler;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class ChatGlobalActivity extends Activity implements OnClickListener {

	ImageButton postbtn;
	TextView chatView;
	EditText inputField;
	String currentUserName = "android"; // set dynamically
	String room = "global";

	private static String CLS = "ChatService";
	private final WebSocketConnection mConnection = new WebSocketConnection();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Log.i("ChatGlobalActivity", "oncreate triggered");

		setContentView(R.layout.activity_chat);

		room = getIntent().getStringExtra("room");

		inputField = (EditText) findViewById(R.id.editText2);
		chatView = (TextView) findViewById(R.id.textviewChat);
		chatView.setText("Willkommen im Chat " + room + "\n");

		postbtn = (ImageButton) findViewById(R.id.imageButton1);
		postbtn.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {

		Log.i("ChatGlobalActivity", "onclick triggered");

		String inputText = inputField.getText().toString().trim();

		if (!inputText.isEmpty()) {

			JSONObject chatmessage = new JSONObject();

			try {
				chatmessage.put("message", inputText);
				chatmessage.put("sender", currentUserName);
				chatmessage.put("received", "");
			} catch (JSONException e) {
				e.printStackTrace();
			}

			mConnection.sendTextMessage(chatmessage.toString());
			inputField.setText("");
		}

	}

	public void connect() {

		Log.i("ChatGlobalActivity", "connect method");

		final String wsuri = "ws://192.168.178.60:8080/DAS-SYSTEM-SERVER/chat/"
				+ room;

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

						chatView.append(sender + "> " + message + "\n");

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

		Log.i("ChatGlobalActivity", "onPause triggered");

		// save state of chatlogs
		mConnection.disconnect();
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.i("ChatGlobalActivity", "onResume triggered");

		connect();
	}

	@Override
	protected void onStop() {
		Log.i("ChatGlobalActivity", "onStop triggered");

		// mConnection.disconnect();
		super.onStop();
	}

}
