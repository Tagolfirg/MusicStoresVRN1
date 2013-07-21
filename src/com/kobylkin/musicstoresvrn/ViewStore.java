package com.kobylkin.musicstoresvrn;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class ViewStore extends Activity {
	static int storeId;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.store_view1);
		Intent theIntent = getIntent();
		storeId = (Integer) theIntent.getIntExtra("id",0);
		String storeName = theIntent.getStringExtra("name");
		String storeAddress = theIntent.getStringExtra("address");
		String storePhone = theIntent.getStringExtra("phone");
		TextView name = (TextView) findViewById(R.id.textView4);
		TextView address = (TextView) findViewById(R.id.textView5);
		TextView phone = (TextView) findViewById(R.id.textView6);
		name.setText(storeName);
		address.setText(storeAddress);
		phone.setText(storePhone);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.view_store, menu);
		return true;
	}
	public void instrumentsButtonPressed(View view){
		Intent  theIntent = new Intent(getApplication(),ViewInstruments.class);
		theIntent.putExtra("id",storeId);
		startActivity(theIntent); 
		
	}

}
