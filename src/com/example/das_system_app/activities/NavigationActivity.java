/**
 * 
 */
package com.example.das_system_app.activities;

import com.example.das_system_app.R;
import com.example.das_system_app.R.layout;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.navigation);

		try {
			// Loading map
			initilizeMap();

			latFrom = 52.4167;
			lonFrom = 12.5500;

			latTo = 52.0;
			lonTo = 12.5167;

			addMarker(latFrom, lonFrom, "Eigener Standort",
					BitmapDescriptorFactory.HUE_GREEN);
			addMarker(latTo, lonTo, "Freund Standort",
					BitmapDescriptorFactory.HUE_RED);

			googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
					new LatLng(latFrom, lonFrom), 14.0f));

		} catch (Exception e) {
			e.printStackTrace();
		}

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

	@Override
	protected void onResume() {
		super.onResume();
		initilizeMap();
	}

}
