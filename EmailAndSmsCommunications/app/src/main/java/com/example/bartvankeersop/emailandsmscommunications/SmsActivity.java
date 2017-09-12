package com.example.bartvankeersop.emailandsmscommunications;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SmsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);
        ButterKnife.bind(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.miEmail:
                startActivity(new Intent(SmsActivity.this, MainActivity.class));
                return true;
            case R.id.miSMS:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @BindView(R.id.etPhoneNumber)
    EditText etPhoneNumber;
    @BindView(R.id.etSmsMessage)
    EditText etSmsMessage;

    @OnClick(R.id.btnSmsSend)
    public void sendSmsMethod(){
        SubscriptionManager subscriptionManager = SubscriptionManager.from(getApplicationContext());
        List<SubscriptionInfo> subscriptionInfoList = subscriptionManager.getActiveSubscriptionInfoList();
        for (SubscriptionInfo subscriptionInfo : subscriptionInfoList) {
            int subscriptionId = subscriptionInfo.getSubscriptionId();
            Log.d("apipas","subscriptionId:"+subscriptionId);
        }
        try {
            SmsManager.getSmsManagerForSubscriptionId(subscriptionInfoList.get(0).getSubscriptionId()).sendTextMessage("+31637194807", "+31637194807", "Test", null, null);
        }
        catch (Exception e){
            Log.d("Exception", e.toString());
        }
    }
}
