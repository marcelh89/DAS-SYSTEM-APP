package com.example.das_system_app.view;

import java.util.Arrays;
import java.util.List;

import com.example.das_system_app.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class HomeFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_main, container,
				false);

		ListView mainListView = (ListView) rootView.findViewById(R.id.listView1);

		List<String> groups = Arrays.asList("Gruppe1", "Gruppe2", "Gruppe3");

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
				this.getActivity(), R.layout.simplerow, groups);

		mainListView.setAdapter(dataAdapter);

		return rootView;

		// Inflate the layout for this fragment
		// return inflater.inflate(R.layout.fragment_main, container, false);
	}
}
