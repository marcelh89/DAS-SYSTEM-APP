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
	String currentUserName = "dummy";
	boolean onCreateExecuted = false; // Problem with onResume triggered twice
										// when onCreate...

	private static String CLS = "ChatService";
	private final WebSocketConnection mConnection = new WebSocketConnection();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat_global);

		inputField = (EditText) findViewById(R.id.editText2);
		chatView = (TextView) findViewById(R.id.textviewChat);

		postbtn = (ImageButton) findViewById(R.id.imageButton1);
		postbtn.setOnClickListener(this);

		connectChain();

		new Handler().postDelayed(new Runnable() {
			public void run() {
				onCreateExecuted = true;
			}
		}, 2000);

	}

	@Override
	public void onClick(View v) {

		String inputText = inputField.getText().toString().trim();

		if (!inputText.isEmpty()) {

			JSONObject chatmessage = new JSONObject();

			try {
				chatmessage.put("message", inputText);
				chatmessage.put("sender", currentUserName);
				chatmessage.put("received", new Date());
			} catch (JSONException e) {
				e.printStackTrace();
			}

			mConnection.sendTextMessage(chatmessage.toString());
			inputField.setText("");
		}

	}

	public void connect() {

		String room = getIntent().getStringExtra("room");

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
		// save state of chatlogs
		mConnection.disconnect();
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();

		// get back foreign connection (closes when screen gets dark)
		// if (onCreateExecuted) {
		// connectChain();
		// }
	}

	@Override
	protected void onStop() {
		mConnection.disconnect();
		super.onStop();
	}

	private void connectChain() {
		connect();

		// wait until mConnection established
		new Handler().postDelayed(new Runnable() {
			public void run() {
				// dynamical set username or get from session context

				JSONObject chatmessage = new JSONObject();

				try {
					chatmessage.put("message", "CONNECT");
					chatmessage.put("sender", currentUserName);
					chatmessage.put("received", new Date());
				} catch (JSONException e) {
					e.printStackTrace();
				}

				mConnection.sendTextMessage(chatmessage.toString());

			}
		}, 2000);
	}
}
