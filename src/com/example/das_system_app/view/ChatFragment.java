package com.example.das_system_app.view;

import com.example.das_system_app.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ChatFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_main, container,
				false);

		return rootView;

		// Inflate the layout for this fragment
		// return inflater.inflate(R.layout.fragment_main, container, false);
	}

}
