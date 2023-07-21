package com.example.myapplication;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Api {
    public static void getJSON (String url, final ReadDataHandler rdh){
        AsyncTask<String, Void, String> task = new AsyncTask<String, Void, String>() {
            String response = "";

            @Override
            protected String doInBackground(String... strings) {
                try{
                    URL link = new URL(strings[0]);
                    HttpURLConnection connection = (HttpURLConnection) link.openConnection();
                    BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String row;
                    while((row = br.readLine()) != null){
                        response += row + "\n";
                    }

                    br.close();
                    connection.disconnect();
                } catch (Exception e){
                    response = "[]";
                }

                return response;
            }

            @Override
            protected void onPostExecute(String s) {
                rdh.setJson(response);
                rdh.sendEmptyMessage(0);
            }
        };
        task.execute(url);
    }
}
