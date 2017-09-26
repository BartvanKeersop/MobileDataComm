package com.androidhive.xmlparsing;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class SingleMenuItemActivity  extends Activity {

    static final String KEY_HOME_NAME = "home_team";
    static final String KEY_VISITOR_NAME = "visitor_team";
    static final String KEY_GOALS_HOME = "home_goals";
    static final String KEY_GOALS_VISITORS = "visitor_goals";

	@Override
    public void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.single_list_item);

            // getting intent data
            Intent in = getIntent();

            // Get XML values from previous intent
            String homeName = in.getStringExtra(KEY_HOME_NAME);
            String visitorName = in.getStringExtra(KEY_VISITOR_NAME);
            String homeScore = in.getStringExtra(KEY_GOALS_HOME);
            String visitorScore = in.getStringExtra(KEY_GOALS_VISITORS);

            StringBuilder stringBuilder = new StringBuilder();
            String message = homeName;

            String result;

            if (Integer.parseInt(homeScore) > Integer.parseInt(visitorScore)) {
                result = "won";
            } else if (Integer.parseInt(homeScore) < Integer.parseInt(visitorScore)) {
                result = "lost";
            } else {
                result = "drawn";
            }

            stringBuilder.append(homeName)
                    .append(" has ")
                    .append(result)
                    .append(" against ")
                    .append(visitorName)
                    .append(". They scored ")
                    .append(homeScore)
                    .append(" goals, and ")
                    .append(visitorName)
                    .append(" scored ")
                    .append(visitorScore)
                    .append(" goals.");

            // Displaying all values on the screen
            TextView information = (TextView) findViewById(R.id.tvInfo);
            information.setText(stringBuilder.toString());
        }
        catch(Exception e){
            Log.d("----ERROR----", e.toString());
        }
    }
}
