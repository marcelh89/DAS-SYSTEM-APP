package com.example.das_system_app.activities;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.das_system_app.R;

import de.tavendo.autobahn.WebSocketConnection;
import de.tavendo.autobahn.WebSocketException;
import de.tavendo.autobahn.WebSocketHandler;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SlidingDrawer;
import android.widget.TextView;

public class ChatGlobalActivity extends Activity implements OnClickListener {

	ImageButton postbtn;
	TextView chatView;
	EditText inputField;

	private static String CLS = "ChatService";
	private final WebSocketConnection mConnection = new WebSocketConnection();

	// HashMap<String, String> chatlog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat_global);

		// chatlog = new HashMap<String, String>();

		inputField = (EditText) findViewById(R.id.editText2);
		chatView = (TextView) findViewById(R.id.textviewChat);

		postbtn = (ImageButton) findViewById(R.id.imageButton1);
		postbtn.setOnClickListener(this);

		connect();

	}

	@Override
	public void onClick(View v) {

		String inputText = inputField.getText().toString().trim();

		// String username = "PeterL";

		if (!inputText.isEmpty()) {
			// chatView.append("\n" + username + "> " + inputText);
			mConnection.sendTextMessage(inputText);
			inputField.setText("");
		}

	}

	public void connect() {

		final String wsuri = "ws://192.168.178.60:8080/DAS-SYSTEM-SERVER/chat";

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
						String nickname = jsonObj.getString("nickname");
						String msg = jsonObj.getString("message");
						chatView.append(nickname + "> " + msg + "\n");
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
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
	protected void onResume() {
		// get back foreign connection (closes when screen gets dark)
		super.onResume();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		mConnection.disconnect();
		super.onStop();
	}
}
