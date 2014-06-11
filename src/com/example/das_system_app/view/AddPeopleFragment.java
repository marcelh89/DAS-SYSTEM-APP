package com.example.das_system_app.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.das_system_app.R;

import android.app.Fragment;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class AddPeopleFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_people, container, false);

		Spinner spinner = (Spinner) rootView.findViewById(R.id.spinner);
		Spinner spinner2 = (Spinner) rootView.findViewById(R.id.spinner2);
		
		List<String> groups = Arrays.asList("Gruppe1","Gruppe2","Gruppe3");
		List<String> friends = Arrays.asList("Chuck Norris", "Dr. Cooper", "Mr. Crabs");

		
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
                this.getActivity(), android.R.layout.simple_spinner_item,
                groups);
		
		ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(
                this.getActivity(), android.R.layout.simple_spinner_item,
                friends);
		
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		
		spinner.setAdapter(dataAdapter);
		spinner2.setAdapter(dataAdapter2);
		
		return rootView;

	}
}
