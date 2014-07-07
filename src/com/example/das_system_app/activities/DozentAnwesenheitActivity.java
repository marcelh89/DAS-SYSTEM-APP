package com.example.das_system_app.activities;

import java.util.Calendar;
import java.util.List;

import com.example.das_system_app.R;
import com.example.das_system_app.rest.DasSystemRESTAccessor;
import com.example.das_system_app.rest.IDasSystemRESTAccessor;
import com.example.das_system_app.rest.valueobject.TeilnehmerIn;
import com.example.das_system_app.rest.valueobject.User;
import com.example.das_system_app.rest.valueobject.Vorlesung;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
/**
 * @author marcman & patman
 *
 */
public class DozentAnwesenheitActivity extends Activity implements OnClickListener{
	private static final int DATE_PICKER_ID = 1234;
	
	private ListView listTeilnehmer;
	private Spinner listVorlesung;
	private ProgressDialog mDialog;
	private Button anzBut;
	private HoleVorlesungenTask mVorTask;
	private CheckTeilnehmerTask mCheckTeilnehmerTask;
	private TextView datePick;
	
	private int year;
    private int month;
    private int day;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dozent_anwesenheit);

		listVorlesung = (Spinner) findViewById(R.id.spinnerVorlesung);
		listTeilnehmer = (ListView) findViewById(R.id.listeTeilnehmer);
		anzBut = (Button) findViewById(R.id.tAnzeigen);
		anzBut.setOnClickListener(this);
		datePick = (TextView) findViewById(R.id.datePick);
		datePick.setOnClickListener(this);
		
		mDialog = new ProgressDialog(this);
		mVorTask = new HoleVorlesungenTask();
		mVorTask.execute();
		
		final Calendar c = Calendar.getInstance();
        year  = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day   = c.get(Calendar.DAY_OF_MONTH);


     // Show selected date
        StringBuilder sb = new StringBuilder();
        if(day < 10){
        	sb.append("0"+day);
        }else{
        	sb.append(day);
        }
        sb.append(".");
        if(month < 10){
        	sb.append("0"+(month+1));
        }else{
        	sb.append(month+1);
        }
        sb.append(".");
        sb.append(year);
        datePick.setText(sb.toString());
        
        listVorlesung.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				Vorlesung vor = (Vorlesung)parent.getItemAtPosition(position);
				if(vor.getAnmeldecode()!=null){
					listTeilnehmer.setAdapter(null);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
	
	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.datePick){
			showDialog(DATE_PICKER_ID);
		}
		if(v.getId() == R.id.tAnzeigen){
//			mDialog = new ProgressDialog(this);
			if(!listVorlesung.getAdapter().isEmpty()){
				mCheckTeilnehmerTask = new CheckTeilnehmerTask();
				mCheckTeilnehmerTask.execute();	
			}else{
				Toast toast = Toast.makeText(getApplicationContext(), 
						"Keine Vorlesung ausgewählt!", Toast.LENGTH_SHORT);
				toast.show();	
			}
			
		}
	}
	
	@Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
        case DATE_PICKER_ID:
            return new DatePickerDialog(this, pickerListener, year, month,day);
        }
        return null;
    }
 
    private DatePickerDialog.OnDateSetListener pickerListener = new DatePickerDialog.OnDateSetListener() {
 
        // when dialog box is closed, below method will be called.
        @Override
        public void onDateSet(DatePicker view, int selectedYear,
                int selectedMonth, int selectedDay) {
             
            year  = selectedYear;
            month = selectedMonth;
            day   = selectedDay;
 
            // Show selected date
            StringBuilder sb = new StringBuilder();
            if(day < 10){
            	sb.append("0"+day);
            }else{
            	sb.append(day);
            }
            sb.append(".");
            if(month < 10){
            	sb.append("0"+(month+1));
            }else{
            	sb.append(month+1);
            }
            sb.append(".");
            sb.append(year);
            datePick.setText(sb.toString());
     
           }
        };

	public class HoleVorlesungenTask extends AsyncTask<Void, Void, List<Vorlesung>> {
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mDialog.setMessage("Lade Vorlesungen");
			mDialog.show();
		}
		
		@Override
		protected List<Vorlesung> doInBackground(Void... params) {
			SharedPreferences settings = getSharedPreferences(LoginActivity.PREFS_NAME, 0);
			int userid = settings.getInt("UserId", 0);
			IDasSystemRESTAccessor acc = new DasSystemRESTAccessor();
			List<Vorlesung> vorlesungen = acc.getVorlesungByDozent(userid);
			return vorlesungen;
		}

		@Override
		protected void onPostExecute(List<Vorlesung> resultList) {
			super.onPostExecute(resultList);
			mDialog.dismiss();
			mVorTask = null;
			if(resultList != null){
				ArrayAdapter<Vorlesung> dataAdapter = new ArrayAdapter<Vorlesung>(DozentAnwesenheitActivity.this,android.R.layout.simple_spinner_item, resultList);
				dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				listVorlesung.setAdapter(dataAdapter);
			}else{
				Toast toast = Toast.makeText(getApplicationContext(), 
						"Konnte keine Vorlesungen finden!", Toast.LENGTH_SHORT);
				toast.show();
			}
			
		}
		
	}
	public class CheckTeilnehmerTask extends AsyncTask<Void, Void, List<User>> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
//			mDialog.setMessage("Lade Teilnehmer");
//			mDialog.show();
		}
		
		@Override
		protected List<User> doInBackground(Void... params) {
			Vorlesung vorlesung = (Vorlesung)listVorlesung.getSelectedItem();
			String date = datePick.getText().toString();
			System.out.println(vorlesung.getVid()+" "+date);			
			IDasSystemRESTAccessor acc = new DasSystemRESTAccessor();
			TeilnehmerIn tin = new TeilnehmerIn();
			tin.setDatum(date);
			tin.setVorlesungId(vorlesung.getVid());
			return acc.getVorlesungTeilnehmer(tin);
		}

		@Override
		protected void onPostExecute(List<User> result) {
			super.onPostExecute(result);
//			mDialog.hide();
			mCheckTeilnehmerTask = null;
			if(result != null){
				if(result.size()>0){
					ArrayAdapter<User> dataAdapter = new ArrayAdapter<User>(DozentAnwesenheitActivity.this,android.R.layout.simple_list_item_1, result);
					listTeilnehmer.setAdapter(dataAdapter);
				}else{
					Toast toast = Toast.makeText(getApplicationContext(), 
							"Keine Teilnehmer angemeldet!", Toast.LENGTH_SHORT);
					toast.show();	
				}
			}else{
				Toast toast = Toast.makeText(getApplicationContext(), 
						"Keine Teilnehmer angemeldet!", Toast.LENGTH_SHORT);
				toast.show();	
			}
		}
		
	}
	
}
