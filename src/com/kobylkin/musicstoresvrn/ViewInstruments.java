package com.kobylkin.musicstoresvrn;



import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.ListActivity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class ViewInstruments extends ListActivity {
	static int id;
	static ArrayList<Instrument> instrumentList = new ArrayList<Instrument>();
	static ArrayList<String> instrumentNames = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.instruments_view);
		Intent theIntent = getIntent();
		id = (Integer) theIntent.getIntExtra("id",0);
		new MyAsyncTaskInstruments().execute();
		
	}
	private class MyAsyncTaskInstruments extends AsyncTask<String, Void, ArrayList<String> > {

		@Override
		protected ArrayList<String> doInBackground(String... params) {
			String url = "http://aschoolapi.appspot.com/stores/"+id+"/instruments";
			DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
			HttpGet httpget = new HttpGet(url);
			httpget.setHeader("Content-type", "application/json");
			InputStream inputStream = null;
			String result = "";

			try {
				
				HttpResponse response = httpclient.execute(httpget);        
				HttpEntity entity = response.getEntity();
				inputStream = entity.getContent();
				BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
				StringBuilder theStringBuilder = new StringBuilder();
				String line = null;
				while ((line = reader.readLine()) != null)
				{
					theStringBuilder.append(line + "\n");
				}
				result = theStringBuilder.toString();

			} catch (Exception e) { 
				e.printStackTrace();
			}
			finally {
				try{if(inputStream != null)inputStream.close();}
				catch(Exception e){}
			}

			try {
				Log.v("JSONParser RESULT ", result);
				JSONArray jsonArray = new JSONArray(result);
				
				for (int i=0; i<jsonArray.length(); i++) {
					JSONObject jsonObject1 = jsonArray.getJSONObject(i);
					JSONObject jsonObject = jsonObject1.getJSONObject("instrument");
					int id = jsonObject.getInt("id");
					String brand = jsonObject.getString("brand");
					String model = jsonObject.getString("model");
					String type = jsonObject.getString("type");
					int price = jsonObject.getInt("price");
					int quantity = jsonObject1.getInt("quantity");
					instrumentList.add(new Instrument(id,brand,model,type,price,quantity));
				}
				
				for(Instrument i:instrumentList){
					instrumentNames.add(i.brand+" "+i.type);
					Log.v("JSONParser RESULT ", ((Integer)instrumentNames.size()).toString());
				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return instrumentNames;
		}
		protected void onPostExecute(ArrayList<String> instrumentNames){
			ListView listView = getListView();
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(ViewInstruments.this,android.R.layout.simple_list_item_1,instrumentNames);
			listView.setAdapter(adapter);
			listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View View,
						int position, long id) {
					Intent  theIntent = new Intent(getApplication(),InstrumentItem.class);
					theIntent.putExtra("id",position);
					theIntent.putExtra("brand",instrumentList.get(position).brand);
					theIntent.putExtra("model",instrumentList.get(position).model);
					theIntent.putExtra("type",instrumentList.get(position).type);
					theIntent.putExtra("price",instrumentList.get(position).price);
					theIntent.putExtra("quantity",instrumentList.get(position).quantity);
					startActivity(theIntent); 
					
				}
				
			});
		
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.view_instruments, menu);
		return true;
	}
	protected void onDestroy(){
		instrumentList.clear();
		instrumentNames.clear();
		super.onDestroy();
	}



}
