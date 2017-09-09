package com.example.bartvankeersop.emailandsmscommunications;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
                return true;
            case R.id.miSMS:
                startActivity(new Intent(MainActivity.this, SmsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.etSubject)
    EditText etSubject;
    @BindView(R.id.etMessage)
    EditText etMessage;

    @OnClick(R.id.btnSend)
    public void sendMail(View view){
        Intent email = new Intent(Intent.ACTION_SENDTO,
                Uri.parse("mailto:" + etEmail.getText().toString()));
        
        email.putExtra(Intent.EXTRA_SUBJECT, etSubject.getText().toString());
        email.putExtra(Intent.EXTRA_TEXT, etMessage.getText().toString());
        startActivity(Intent.createChooser(email, "Choose an Email client :"));
    }
}
