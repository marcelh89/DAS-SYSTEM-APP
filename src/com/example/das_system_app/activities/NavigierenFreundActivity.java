package com.example.das_system_app.activities;

import java.util.ArrayList;

import com.example.das_system_app.R;
import com.example.das_system_app.rest.valueobject.User;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class NavigierenFreundActivity extends Activity implements OnClickListener{

	private Spinner userSpin;
	private Button btnAnzeigen;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.nav_freund);
		userSpin = (Spinner) findViewById(R.id.userSpin);
		btnAnzeigen = (Button) findViewById(R.id.btnShow);
		btnAnzeigen.setOnClickListener(this);
		@SuppressWarnings("unchecked")
		ArrayList<User> userlist= (ArrayList<User>) getIntent().getExtras().get("userList");
		ArrayAdapter<User> dataAdapter = new ArrayAdapter<User>(NavigierenFreundActivity.this,android.R.layout.simple_spinner_item, userlist);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		userSpin.setAdapter(dataAdapter);
	}
	
	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.btnShow){
			User selUser = (User)userSpin.getSelectedItem();
			if(selUser.getLastLocation() != null){
				String lon,lat,tmp[];
				if(selUser.getLastLocation().contains("Raum")){
					tmp = selUser.getLastLocation().split("Raum");
					lat = tmp[0].split(",")[0];
					lon = tmp[0].split(",")[1];
				}else{
					tmp = selUser.getLastLocation().split(",");
					lat = tmp[0];
					lon = tmp[1];
				}
				Location loc = getMyLocation();
				String latFrom = ""+loc.getLatitude();
				String lonFrom = ""+loc.getLongitude();
				final Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("http://maps.google.com/maps?" + "saddr="+ latFrom + "," + lonFrom + "&daddr=" + lat + "," + lon));
			    intent.setClassName("com.google.android.apps.maps","com.google.android.maps.MapsActivity");
			    startActivity(intent);
				
			}else{
				Toast toast = Toast.makeText(getApplicationContext(), 
						"Kein letzter Standort bekannt!", Toast.LENGTH_SHORT);
				toast.show();	
			}
		}
	}
	
	private Location getMyLocation() {
		// Get location from GPS if it's available
		LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Location myLocation = lm
				.getLastKnownLocation(LocationManager.GPS_PROVIDER);

		// Location wasn't found, check the next most accurate place for the
		// current location
		if (myLocation == null) {
			Criteria criteria = new Criteria();
			criteria.setAccuracy(Criteria.ACCURACY_COARSE);
			// Finds a provider that matches the criteria
			String provider = lm.getBestProvider(criteria, true);
			// Use the provider to get the last known location
			myLocation = lm.getLastKnownLocation(provider);
		}

		return myLocation;
	}
}
