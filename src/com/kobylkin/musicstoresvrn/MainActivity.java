package com.kobylkin.musicstoresvrn;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends ListActivity {

	static String url = "http://aschoolapi.appspot.com/stores/";
	static ArrayList<Store> storeList = new ArrayList<Store>();
	static ArrayList<String> names = new ArrayList<String>();
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		new MyAsyncTask().execute();
	}

	private class MyAsyncTask extends AsyncTask<String, Void, ArrayList<String> > {

		protected ArrayList<String> doInBackground(String... params) {

			DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
			HttpGet httpget = new HttpGet(url);
			httpget.setHeader("Content-type", "application/json");
			InputStream inputStream = null;
			String result = null;

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
				
				JSONArray jsonArray = new JSONArray(result);
				
				for (int i=0; i<jsonArray.length(); i++) {
				    //list.add( jsonArray.getString(i) );
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					int id = jsonObject.getInt("id");
					String name = jsonObject.getString("name");
					String address = jsonObject.getString("address");
					String phone = jsonObject.getString("phone");
					JSONObject location = jsonObject.getJSONObject("location");
					int latitude = location.getInt("latitude");
					int longitude = location.getInt("longitude");
					storeList.add(new Store(id,name,address,phone,latitude,longitude));
					Log.v("asdasdasd",storeList.get(i).name);
				}
				
				Log.v("pered zapuskom",storeList.get(0).name);
				for(Store i:storeList){
					names.add(i.name);
					Log.v("JSONParser RESULT ", ((Integer)names.size()).toString());
				}

			} catch (JSONException e) {

				e.printStackTrace();
			}

			return names;

		}

		protected void onPostExecute(ArrayList<String> names){
			ListView listView = getListView();
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,names);
			listView.setAdapter(adapter);
			listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View View,
						int position, long id) {
					Intent  theIntent = new Intent(getApplication(),ViewStore.class);
					theIntent.putExtra("id",position);
					theIntent.putExtra("name",storeList.get(position).name);
					theIntent.putExtra("address",storeList.get(position).address);
					theIntent.putExtra("phone",storeList.get(position).phone);
					startActivity(theIntent); 
					
				}
				
			});
		}
	}
}