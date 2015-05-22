package com.example.rito.sitaca;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class LoginActivity extends Activity {

    EditText emailLogin, passLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("sitacaadmin", 0);
        int id = pref.getInt("id",-1);
        if(id != -1)
        {

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }

        emailLogin = (EditText)  findViewById(R.id.emailLogin);
        passLogin = (EditText) findViewById(R.id.passLogin);
        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();




        return super.onOptionsItemSelected(item);
    }

    void errorToast(String e){
        Toast.makeText(
                this,
                "" + e,
                Toast.LENGTH_SHORT
        ).show();
        return;
    }

    public void login()
    {
        boolean goodToGo = true;
         if(emailLogin.getText().toString().equalsIgnoreCase(""))
         {
             errorToast("Kesalahan : Email belum terisi.");
             goodToGo = false;
         }
        else if(!isValidEmail(emailLogin.getText().toString()))
         {
             errorToast("Kesalahan : Format email tidak sesuai.");
             goodToGo = false;
         }
         if(passLogin.getText().toString().equalsIgnoreCase(""))
         {
             errorToast("Kesalahan : Kata sandi belum terisi.");
             goodToGo = false;
         }

        //finally
        if(goodToGo)
        {
            if(new Connection().checkConnection(this))
            {
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("tag", "login"));
                params.add(new BasicNameValuePair("email", emailLogin.getText().toString()));
                params.add(new BasicNameValuePair("pass", passLogin.getText().toString()));
                Log.d("cekid", emailLogin.getText().toString() + passLogin.getText().toString());
                RequestData requestData = new RequestData(
                        "getdata.php",
                        params,
                        this,
                        "Otorisasi")
                {
                    @Override
                    protected void onPostExecute(JSONArray data) {
                        Log.d("cekid2", ""+data);
                        pDialog.dismiss();
                        if(data == null)
                        {
                            errorToast("Kesalahan : Email / kata sandi salah.");
                        }
                        else {
                            try {
                                JSONObject o = data.getJSONObject(0);
                                if (o.getInt("status") == 0) {
                                    errorToast("Kesalahan : Anda belum diterima sebagai admin, silahkan hubungi admin lain.");
                                } else {
                                    SharedPreferences pref = getApplicationContext().getSharedPreferences("sitacaadmin", 0); // 0 - for private mode
                                    SharedPreferences.Editor editor = pref.edit();
                                    editor.putInt("id",o.getInt("id"));
                                    editor.putString("nama", o.getString("nama"));
                                    editor.putString("notelp",o.getString("notelp"));
                                    editor.putString("email",o.getString("email"));
                                    editor.putString("alamat",o.getString("alamat"));
                                    editor.putString("jabatan",o.getString("jabatan"));
                                    editor.commit();

                                    errorToast("Selamat datang " + pref.getString("nama",null));
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                    finish();

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                };
                requestData.execute();
            }
            else
            {
                errorToast("Kesalahan : Anda sedang tidak tersambung ke internet.");
            }
        }
    }

    public boolean isValidEmail(String string) {
        if (string.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+") && string.length() > 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public void showTambahAdmin(View view){
        Intent intent = new Intent(this, TambahAdminActivity.class);
        startActivity(intent);
    }
}
