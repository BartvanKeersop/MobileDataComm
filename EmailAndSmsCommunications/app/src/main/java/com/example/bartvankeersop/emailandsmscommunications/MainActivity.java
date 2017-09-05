package com.example.bartvankeersop.emailandsmscommunications;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.etSubject)
    EditText etSubject;
    @BindView(R.id.etMessage)
    EditText etMessage;

    @OnClick(R.id.btnSend)
    public void sendMail(View view){
        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_EMAIL, new String[]{etEmail.getText().toString()});
        email.putExtra(Intent.EXTRA_SUBJECT, etSubject.getText().toString());
        email.putExtra(Intent.EXTRA_TEXT, etMessage.getText().toString());
        email.setType("message/rfc822");
        startActivity(Intent.createChooser(email, "Choose an Email client :"));
    }
}
