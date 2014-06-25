/**
 * 
 */
package com.example.das_system_app.activities;

import com.example.das_system_app.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;

public class ChatChooseActivity extends Activity implements OnItemClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chat_choose);

		ListView mainListView = (ListView) this.findViewById(R.id.listView1);

		ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter
				.createFromResource(this, R.array.chats,
						android.R.layout.simple_list_item_1);
		mainListView.setAdapter(arrayAdapter);

		mainListView.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		Log.i(view.toString(), position + "");

		Intent intent = new Intent(this, ChatGlobalActivity.class);

		// set Extra Attribute for choosing room in ChatGlobalActivity activity
		if (position == 0) {
			intent.putExtra("room", "privat1");
		} else {
			intent.putExtra("room", "privat2");
		}

		startActivity(intent);

	}
}
