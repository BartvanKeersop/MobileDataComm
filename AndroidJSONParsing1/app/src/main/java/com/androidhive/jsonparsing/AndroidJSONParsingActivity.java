package com.androidhive.jsonparsing;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.TargetApi;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class AndroidJSONParsingActivity extends ListActivity {

	ProgressBar _progressBar;

	// url to make request
	private static String url = "http://api.androidhive.info/contacts/";
	
	// JSON Node names
	private static final String TAG_CONTACTS = "contacts";
	private static final String TAG_ID = "id";
	private static final String TAG_NAME = "name";
	private static final String TAG_EMAIL = "email";
	private static final String TAG_ADDRESS = "address";
	private static final String TAG_GENDER = "gender";
	private static final String TAG_PHONE = "phone";
	private static final String TAG_PHONE_MOBILE = "mobile";
	private static final String TAG_PHONE_HOME = "home";
	private static final String TAG_PHONE_OFFICE = "office";

	// contacts JSONArray
	JSONArray contacts = null;

	@TargetApi(Build.VERSION_CODES.CUPCAKE)
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		_progressBar = (ProgressBar) findViewById(R.id.JSONloadingBar);

		new HttpFetcher(this).execute();
	}

	/**
	 * Fetches HTML from the passed URL.
	 */
	@TargetApi(Build.VERSION_CODES.CUPCAKE)
	private class HttpFetcher extends AsyncTask<URL, Integer, ArrayList<HashMap<String, String>>> {

		private Context _context;

		public HttpFetcher (Context context){
			_context = context;
		}

		@Override
		protected ArrayList<HashMap<String, String>> doInBackground(URL... URLS) {

			ArrayList<HashMap<String, String>> contactList = new ArrayList<HashMap<String, String>>();

			try {
				// Creating JSON Parser instance
				JSONParser jParser = new JSONParser();

				// getting JSON string from URL
				JSONObject json = jParser.getJSONFromUrl(url);

				// Getting Array of Contacts
				contacts = json.getJSONArray(TAG_CONTACTS);

				// looping through All Contacts
				for(int i = 0; i < contacts.length(); i++){
					JSONObject c = contacts.getJSONObject(i);

					// Storing each json item in variable
					String id = c.getString(TAG_ID);
					String name = c.getString(TAG_NAME);
					String email = c.getString(TAG_EMAIL);
					String address = c.getString(TAG_ADDRESS);
					String gender = c.getString(TAG_GENDER);

					// Phone number is agin JSON Object
					JSONObject phone = c.getJSONObject(TAG_PHONE);
					String mobile = phone.getString(TAG_PHONE_MOBILE);
					String home = phone.getString(TAG_PHONE_HOME);
					String office = phone.getString(TAG_PHONE_OFFICE);

					// creating new HashMap
					HashMap<String, String> map = new HashMap<String, String>();

					// adding each child node to HashMap key => value
					map.put(TAG_ID, id);
					map.put(TAG_NAME, name);
					map.put(TAG_EMAIL, email);
					map.put(TAG_PHONE_MOBILE, mobile);

					// adding HashList to ArrayList
					contactList.add(map);
					publishProgress((i/contacts.length()));
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				return contactList;
			}

		protected void onProgressUpdate(Integer... progress) {
			_progressBar.setProgress(progress[0]);
		}

		protected void onPostExecute(ArrayList<HashMap<String, String>> contactList) {
			/**
			 * Updating parsed JSON data into ListView
			 * */
			ListAdapter adapter = new SimpleAdapter(_context, contactList,
					R.layout.list_item,
					new String[] { TAG_NAME, TAG_EMAIL, TAG_PHONE_MOBILE }, new int[] {
					R.id.name, R.id.email, R.id.mobile });

			setListAdapter(adapter);

			// selecting single ListView item
			ListView lv = getListView();

			// Launching new screen on Selecting Single ListItem
			lv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
										int position, long id) {
					// getting values from selected ListItem
					String name = ((TextView) view.findViewById(R.id.name)).getText().toString();
					String cost = ((TextView) view.findViewById(R.id.email)).getText().toString();
					String description = ((TextView) view.findViewById(R.id.mobile)).getText().toString();

					// Starting new intent
					Intent in = new Intent(getApplicationContext(), SingleMenuItemActivity.class);
					in.putExtra(TAG_NAME, name);
					in.putExtra(TAG_EMAIL, cost);
					in.putExtra(TAG_PHONE_MOBILE, description);
					startActivity(in);
				}
			});
		}
	}
}