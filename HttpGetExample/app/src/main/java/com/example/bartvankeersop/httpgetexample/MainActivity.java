package com.example.bartvankeersop.httpgetexample;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

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

    @BindView(R.id.etURL)
    EditText etURL;

    @BindView(R.id.txtResult)
    TextView txtResult;

    /**
     * Creates and starts an ASYNCTASK that fetches the HTML from a website and displays it.
     */
    @OnClick(R.id.btnFetch)
    public void getData(){
        try {
            new HttpFetcher().execute(new URL(etURL.getText().toString()));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Fetches HTML from the passed URL.
     */
    private class HttpFetcher extends AsyncTask<URL, Integer, String>{

        @Override
        protected String doInBackground(URL... URLS) {
            try {

                //use HttpURLConnection so we can check the response code
                HttpURLConnection urlConnection = (HttpURLConnection) URLS[0].openConnection();

                if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                    StreamReader reader = new StreamReader();
                    return reader.readStream(urlConnection.getInputStream());
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onProgressUpdate(Integer... progress) {
            //Update a progressbar or whatever.
        }

        protected void onPostExecute(String httpResult) {
            //Done
            txtResult.setText(httpResult);
        }
    }
}
