/**
 * 
 */
package com.example.das_system_app;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.example.das_system_app.adapters.ExpandableListAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.Toast;

public class OverviewActivity extends Activity {

	List<String> groupList;
	List<String> childList;
	Map<String, List<String>> laptopCollection;
	ExpandableListView expListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.overview);

		createGroupList();

		createCollection();

		expListView = (ExpandableListView) findViewById(R.id.laptop_list);
		final ExpandableListAdapter expListAdapter = new ExpandableListAdapter(
				this, groupList, laptopCollection);
		expListView.setAdapter(expListAdapter);

		// setGroupIndicatorToRight();

		expListView.setOnChildClickListener(new OnChildClickListener() {

			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				final String selected = (String) expListAdapter.getChild(
						groupPosition, childPosition);
				Toast.makeText(getBaseContext(), selected, Toast.LENGTH_LONG)
						.show();
				
				/*
				 * switch-case für neue Intents
				 * startActivity(new Intent(this, RegisterActivity.class));
				 */

				return true;
			}
		});
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
		String[] chatOptions = { "globaler Chat", "Gruppenchat" };
		String[] profilOptions = { "Profil einsehen" };
		String[] raumOptions = { "Raum identifizieren",
				"Für einen Kurs anmelden" };
		String[] dozentOptions = { "Anwesenheit prüfen",
				"QR-Code für LV bereitstellen" };

		laptopCollection = new LinkedHashMap<String, List<String>>();

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

			laptopCollection.put(laptop, childList);
		}
	}

	private void loadChild(String[] laptopModels) {
		childList = new ArrayList<String>();
		for (String model : laptopModels)
			childList.add(model);
	}

	// Convert pixel to dip
	public int getDipsFromPixel(float pixels) {
		// Get the screen's density scale
		final float scale = getResources().getDisplayMetrics().density;
		// Convert the dps to pixels, based on density scale
		return (int) (pixels * scale + 0.5f);
	}

}