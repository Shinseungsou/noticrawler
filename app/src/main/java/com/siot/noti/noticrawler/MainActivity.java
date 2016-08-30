package com.siot.noti.noticrawler;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashSet;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends Activity {

    @BindView(R.id.content) protected EditText contents;
    @BindView(R.id.time_picker) protected TextView timePicker;
    @BindView(R.id.submit) protected TextView submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSoup();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this);
//                builder.setSmallIcon(R.mipmap.ic_launcher);
//                builder.setContentTitle("hello");
//                builder.setContentText("world?");
//                NotificationManager manager = ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE));
//                manager.notify(101, builder.build());
            }
        });

    }

    private void getSoup(){
        String url = "http://www.naver.com";
        new ParseURL().execute(url);
    }

    private class ParseURL extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params) {
            StringBuffer buffer = new StringBuffer();
            try {
                Document doc = Jsoup.connect(params[0]).postDataCharset("UTF-8").get();
                String title = doc.title();
                buffer.append(title);
                Elements rank = doc.select("div dl");
                rank = doc.select("span");

                Log.d("out", ""+rank.size());
                for(Element e : rank) {
                    Log.d("output", e.text());
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return buffer.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
        }
    }
}
