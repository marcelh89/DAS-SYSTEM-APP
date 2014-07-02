package com.example.das_system_app.adapters;

import java.util.List;

import com.example.das_system_app.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ChatListAdapter extends ArrayAdapter<String> {
	private final Context context;
	private final List<String> values;
	private final List<String> labelValues;

	public ChatListAdapter(Context context, List<String> values,
			List<String> labelValues) {
		super(context, R.layout.chat_item, values);
		this.context = context;
		this.values = values;
		this.labelValues = labelValues;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.chat_item, parent, false);
		TextView mLabelView = (TextView) rowView.findViewById(R.id.label);
		TextView mTextView = (TextView) rowView.findViewById(R.id.secondLine);

		ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
		mTextView.setText(values.get(position));
		mLabelView.setText(labelValues.get(position));

		// change the icon if System or user msg
		String s = values.get(position);
		if (s.startsWith("SYSTEM")) {
			imageView
					.setImageResource(R.drawable.common_signin_btn_icon_focus_dark);
		} else {
			imageView
					.setImageResource(R.drawable.common_signin_btn_icon_focus_light);
		}

		return rowView;
	}
}