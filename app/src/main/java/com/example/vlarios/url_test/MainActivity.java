package com.example.vlarios.url_test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.net.*;
import java.io.*;



public class MainActivity extends Activity {

    String msg = "";
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void testUrl(View view) {
        new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    URL url = new URL("http://192.168.1.67/urlTest.php");
                    HttpURLConnection con = (HttpURLConnection) url
                            .openConnection();
                    readStream(con.getInputStream());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        //textView.setText(msg);

    }

    private void readStream(InputStream in) {

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                //textView.setText(line);
                System.out.println(line);
                msg = line;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView = (TextView) findViewById(R.id.textView);
                        textView.setText(msg);
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }




}
