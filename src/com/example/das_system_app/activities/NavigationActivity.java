/**
 * 
 */
package com.example.das_system_app.activities;

import java.util.ArrayList;

import com.example.das_system_app.R;
import com.example.das_system_app.rest.valueobject.User;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

/**
 * @author marcman
 * 
 */
// TODO Implementation
// https://developers.google.com/maps/documentation/android/v1/hello-mapview
public class NavigationActivity extends Activity {

	// Google Map
	private GoogleMap googleMap;
	double latFrom, lonFrom, latTo, lonTo;
	Location location;

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.navigation);
		ArrayList<User> userlist= (ArrayList<User>) getIntent().getExtras().get("userList");
		try {
			// Loading map
			initilizeMap();

			googleMap.setMyLocationEnabled(true);

			Location loc = getMyLocation();
			latFrom = loc.getLatitude();
			lonFrom = loc.getLongitude();

			latTo = 52.4167;
			lonTo = 12.5500;

			addMarker(latFrom, lonFrom, "Eigener Standort",
					BitmapDescriptorFactory.HUE_GREEN);
//			addMarker(latTo, lonTo, "Freund Standort",
//					BitmapDescriptorFactory.HUE_RED);
			if(userlist != null){
				String lon,lat,raum,tmp[];
				for(User u:userlist){
					if(u.getLastLocation() != null){
						if(u.getLastLocation().contains("Raum")){
							tmp = u.getLastLocation().split("Raum");
							lat = tmp[0].split(",")[0];
							lon = tmp[0].split(",")[1];
							raum = tmp[1].trim();
							addMarker(Double.parseDouble(lat), Double.parseDouble(lon), u.toString()+" "+raum, BitmapDescriptorFactory.HUE_RED);
						}else{
							tmp = u.getLastLocation().split(",");
							lat = tmp[0];
							lon = tmp[1];
							addMarker(Double.parseDouble(lat), Double.parseDouble(lon), u.toString(), BitmapDescriptorFactory.HUE_RED);
						}
						
					}
				}	
			}
			googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
					new LatLng(latFrom, lonFrom), 14.6f));
			
		} catch (Exception e) {
			e.printStackTrace();
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

	private void addMarker(double lat, double lon, String description,
			float color) {

		// initialize marker
		MarkerOptions marker = new MarkerOptions().position(
				new LatLng(lat, lon)).title(description);

		marker.icon(BitmapDescriptorFactory.defaultMarker(color));

		// add marker to map
		googleMap.addMarker(marker);
	}

	/**
	 * function to load map. If map is not created it will create it for you
	 * */
	private void initilizeMap() {
		if (googleMap == null) {
			googleMap = ((MapFragment) getFragmentManager().findFragmentById(
					R.id.map)).getMap();

			// check if map is created successfully or not
			if (googleMap == null) {
				Toast.makeText(getApplicationContext(),
						"Sorry! unable to create maps", Toast.LENGTH_SHORT)
						.show();
			}
		}
	}

//	@Override
//	protected void onResume() {
//		super.onResume();
//		initilizeMap();
//	}

	@Override
	public void onBackPressed() {
		finish();
//		startActivity(new Intent(this, OverviewActivity.class));
		super.onBackPressed();
	}

	
}
