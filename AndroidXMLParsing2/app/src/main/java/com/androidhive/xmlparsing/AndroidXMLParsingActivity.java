package com.androidhive.xmlparsing;

import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class AndroidXMLParsingActivity extends ListActivity {

	// All static variables
	static final String URL = "http://172.20.240.4:7002/";
	// XML node keys
	static final String KEY_ITEM = "match"; // parent node
	static final String KEY_HOME_NAME = "home_team";
	static final String KEY_VISITOR_NAME = "visitor_team";
	static final String KEY_GOALS_HOME = "home_goals";
	static final String KEY_GOALS_VISITORS = "visitor_goals";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		ArrayList<HashMap<String, String>> menuItems = new ArrayList<HashMap<String, String>>();

		XMLParser parser = new XMLParser();
		String xml = parser.getXmlFromUrl(URL); // getting XML
		Document doc = parser.getDomElement(xml); // getting DOM element

		NodeList nl = doc.getElementsByTagName(KEY_ITEM);
		// looping through all item nodes <item>
		for (int i = 0; i < nl.getLength(); i++) {
			// creating new HashMap
			HashMap<String, String> map = new HashMap<String, String>();
			Element e = (Element) nl.item(i);
			// adding each child node to HashMap key => value
			map.put(KEY_HOME_NAME, parser.getValue(e, KEY_HOME_NAME));
			map.put(KEY_VISITOR_NAME, parser.getValue(e, KEY_VISITOR_NAME));
			map.put(KEY_GOALS_HOME, parser.getValue(e, KEY_GOALS_HOME));
			map.put(KEY_GOALS_VISITORS, parser.getValue(e, KEY_GOALS_VISITORS));

			// adding HashList to ArrayList
			menuItems.add(map);
		}

		// Adding menuItems to ListView
		ListAdapter adapter = new SimpleAdapter(this, menuItems,
				R.layout.list_item,
				new String[] {KEY_HOME_NAME, KEY_VISITOR_NAME, KEY_GOALS_HOME, KEY_GOALS_VISITORS}, new int[] {
						R.id.tvHomeName, R.id.tvVisitorName, R.id.tvScoreHome, R.id.tvScoreVisitor });

		setListAdapter(adapter);

		// selecting single ListView item
		ListView lv = getListView();

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String homeName = ((TextView) view.findViewById(R.id.tvHomeName)).getText().toString();
				String visitorName = ((TextView) view.findViewById(R.id.tvVisitorName)).getText().toString();
				String homeScore = ((TextView) view.findViewById(R.id.tvScoreHome)).getText().toString();
				String visitorScore = ((TextView) view.findViewById(R.id.tvScoreVisitor)).getText().toString();
				
				// Starting new intent
				Intent in = new Intent(getApplicationContext(), SingleMenuItemActivity.class);
				in.putExtra(KEY_HOME_NAME, homeName);
				in.putExtra(KEY_GOALS_HOME, homeScore);
				in.putExtra(KEY_VISITOR_NAME, visitorName);
				in.putExtra(KEY_GOALS_VISITORS, visitorScore);
				startActivity(in);
			}
		});
	}
}