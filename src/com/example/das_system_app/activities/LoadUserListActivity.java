package com.example.das_system_app.activities;

import java.util.ArrayList;

import com.example.das_system_app.R;
import com.example.das_system_app.rest.DasSystemRESTAccessor;
import com.example.das_system_app.rest.IDasSystemRESTAccessor;
import com.example.das_system_app.rest.valueobject.User;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

public class LoadUserListActivity extends Activity {
	private int child;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.load);
		child = getIntent().getExtras().getInt("child");
		(new GetUsersTask()).execute();
	}

	public class GetUsersTask extends AsyncTask<Void, Void, ArrayList<User>>{

		private ArrayList<User> u;
		@Override
		protected ArrayList<User> doInBackground(Void... params) {
			IDasSystemRESTAccessor acc = new DasSystemRESTAccessor();
			u = (ArrayList<User>) acc.getUser();
			return u;
		}

		@Override
		protected void onPostExecute(ArrayList<User> result) {
			System.out.println(u.size());
			Intent intent = null;
			if(child == 0){
				intent = new Intent(LoadUserListActivity.this, NavigationActivity.class);	
			}
			if(child == 1){
				intent = new Intent(LoadUserListActivity.this, NavigierenFreundActivity.class);
			}
			intent.putExtra("userList", result);
			startActivity(intent);
			LoadUserListActivity.this.finish();
		}
		
	}
}
