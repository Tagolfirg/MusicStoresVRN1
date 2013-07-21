package com.kobylkin.musicstoresvrn;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

public class InstrumentItem extends Activity {
	static int instrumentId;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.v("instrumentID",((Integer)instrumentId).toString());
		setContentView(R.layout.instrument_item);
		Intent theIntent = getIntent();
		instrumentId = (Integer) theIntent.getIntExtra("id",0);
		String instrumentBrand = theIntent.getStringExtra("brand");
		String instrumentModel = theIntent.getStringExtra("model");
		String instrumentType = theIntent.getStringExtra("type");
		String instrumentPrice = theIntent.getStringExtra("price");
		String instrumentQuantity = theIntent.getStringExtra("quantity");
		TextView brand = (TextView) findViewById(R.id.textView2);
		TextView model = (TextView) findViewById(R.id.textView4);
		TextView type = (TextView) findViewById(R.id.textView6);
		TextView price = (TextView) findViewById(R.id.textView8);
		TextView quantity = (TextView) findViewById(R.id.textView10);
		brand.setText(instrumentBrand);
		model.setText(instrumentModel);
		type.setText(instrumentType);
		price.setText(instrumentPrice);
		quantity.setText(instrumentQuantity);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.instrument_item, menu);
		return true;
	}

}
