/**
 * 
 */
package com.example.das_system_app.activities;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.example.das_system_app.R;
import com.example.das_system_app.R.id;
import com.example.das_system_app.R.layout;
import com.example.das_system_app.adapters.ExpandableListAdapter;
import com.example.das_system_app.rest.valueobject.User;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.Toast;

public class OverviewActivity extends Activity {

	List<String> groupList;
	List<String> childList;
	Map<String, List<String>> optionsCollection;
	ExpandableListView expListView;
	User currentUser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.overview);

		currentUser = (User) getIntent().getSerializableExtra("user");

		createGroupList();

		createCollection();

		expListView = (ExpandableListView) findViewById(R.id.laptop_list);
		final ExpandableListAdapter expListAdapter = new ExpandableListAdapter(
				this, groupList, optionsCollection);
		expListView.setAdapter(expListAdapter);

		expListView.setOnChildClickListener(new OnChildClickListener() {

			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {

				createDependingIntent(groupPosition, childPosition);

				return true;
			}

		});
	}

	private void createDependingIntent(int groupPosition, int childPosition) {

		Intent intent = null;

		switch (groupPosition) {
		case 0:
			// Freunde im Umkreis or Weg zum Freund
			intent = (childPosition == 0) ? new Intent(this,
					LoadUserListActivity.class) : new Intent(this,
					NavigierenFreundActivity.class);
			break;
		case 1:

			switch (childPosition) {
			case 0:
				intent = new Intent(this, ChatOrganizeActivity.class);

				break;
			case 1:
				intent = new Intent(this, ChatActivity.class);
				intent.putExtra("room", "global");

			default:
			}

			intent.putExtra("user", currentUser);
			break;
		case 2:
			intent = new Intent(this, ProfilActivity.class);
			intent.putExtra("user", currentUser);
			break;
		case 3:
			if(childPosition == 0){
				intent = new Intent(this, RaumActivity.class);	
			}
			if(childPosition == 1){
				intent = new Intent(this, KursAnmeldenActivity.class);
			}
			break;
		case 4:
			if(childPosition == 0){
				intent = new Intent(this, DozentAnwesenheitActivity.class);	
			}
			if(childPosition == 1){
				intent = new Intent(this, DozentActivity.class);
			}
			break;
		default:
			intent = new Intent(this, DozentActivity.class);

		}

		startActivity(intent);

	}

	private void createGroupList() {
		groupList = new ArrayList<String>();
		groupList.add("Navigation");
		groupList.add("Chat");
		groupList.add("Nutzerprofil");
		groupList.add("Rauminfo");
		groupList.add("Dozent");
	}

	private void createCollection() {
		// preparing laptops collection(child)
		String[] navigationOptions = { "Freunde im Umkreis",
				"Weg zum Freund finden" };
		String[] chatOptions = { "Chats verwalten", "Direkt zum globalen Chat" };
		String[] profilOptions = { "Profil einsehen" };
		String[] raumOptions = { "Raum identifizieren",
				"Für einen Kurs anmelden" };
		String[] dozentOptions = { "Anwesenheit prüfen",
				"QR-Code für LV bereitstellen" };

		optionsCollection = new LinkedHashMap<String, List<String>>();

		for (String laptop : groupList) {
			if (laptop.equals("Navigation")) {
				loadChild(navigationOptions);
			} else if (laptop.equals("Chat"))
				loadChild(chatOptions);
			else if (laptop.equals("Nutzerprofil"))
				loadChild(profilOptions);
			else if (laptop.equals("Rauminfo"))
				loadChild(raumOptions);
			else
				// (laptop.equals("Dozent"))
				loadChild(dozentOptions);

			optionsCollection.put(laptop, childList);
		}
	}

	private void loadChild(String[] laptopModels) {
		childList = new ArrayList<String>();
		for (String model : laptopModels)
			childList.add(model);
	}

}
