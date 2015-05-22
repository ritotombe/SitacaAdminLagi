package com.example.rito.sitaca;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.conn.ConnectionReleaseTrigger;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rito on 5/8/2015.
 */
public class RequestData extends AsyncTask<String, String, JSONArray> {

    private String url;
    private List<NameValuePair> params;
    private Context context;
    private String message;
    ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    JSONArray data = null;

    public RequestData(String url, List<NameValuePair> params, Context context, String message){

        this.url = url;
        this.params = params;
        this.context = context;
        this.message = message;
    }

    public void updateJSONData(JSONObject json) {
        try {

            data = json.getJSONArray("posts");
           // Log.d("cek", ""+data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONArray getData(){
        return this.data;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        pDialog = new ProgressDialog(context);
        pDialog.setMessage(message);
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();
    }

    @Override
    protected JSONArray doInBackground(String... arg0) {

        int success;
        try {


            //Posting user data to script
            JSONObject json = jsonParser.makeHttpRequest(
                    url, "POST", params);
            Log.d("cekid3",params.toString());
            //updateJSONData(json);
            success = json.getInt("success");
            if (success == 1) {
               // Log.d("User Created!", json.toString());
                updateJSONData(json);
                //return json.getString("message");
            }else{
               data = null;
                Log.d("Registering Failure!", json.getString("message"));
                //return json.getString("message");

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return this.data;
    }

    @Override
    protected void onPostExecute(JSONArray json) {
        pDialog.dismiss();
    }

}
