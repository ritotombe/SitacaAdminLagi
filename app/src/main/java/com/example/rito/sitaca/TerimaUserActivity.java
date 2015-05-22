package com.example.rito.sitaca;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rito.sitaca.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TerimaUserActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terima_user);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_terima_user, menu);
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        private EditText nama, alamat, jabatan, noTelp, email;
        Button terima, lihatTb;
        private View rootView;
        int id_user,id_tb;

        void errorToast(String e){
            Toast.makeText(
                    rootView.getContext(),
                    "" + e,
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        public PlaceholderFragment() {
        }

        public void terimaUser(){
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("aksi", "terima"));
            params.add(new BasicNameValuePair("id", ""+id_user));
            RequestData requestData = new RequestData(
                    "userdao.php",
                    params,
                    getActivity(),
                    "Menerima User")
            {
                @Override
                protected void onPostExecute(JSONArray data) {
                    pDialog.dismiss();
                    Log.d("asd", ""+data);
                    if(data == null)
                    {
                        errorToast("Kesalahan : User tidak berhasil diubah.");
                    }
                    else
                    {
                        try {
                            errorToast(data.get(0).toString());
                            if (new Connection().checkConnection(getActivity())) {
                                Intent intent = new Intent(getActivity(), UserActivity.class);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(
                                        getActivity(),
                                        "Kesalahan : Anda tidak tersambung ke internet.",
                                        Toast.LENGTH_SHORT
                                ).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }
            };
            requestData.execute();
        }

        public void lihatTamanBaca()
        {
            if(id_tb == -1)
            {
                errorToast("User ini tidak mengelola taman baca.");
            }
            else {
                if (new Connection().checkConnection(getActivity())) {
                    Intent intent = new Intent(rootView.getContext(), DetailTBActivity.class);
                    intent.putExtra("id", id_tb);
                    intent.putExtra("mode", 1);
                    startActivity(intent);
                    getActivity().finish();
                }
                else{
                    Toast.makeText(
                            getActivity(),
                            "Kesalahan : Anda tidak tersambung ke internet.",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

           rootView = inflater.inflate(R.layout.fragment_terima_user, container, false);
            nama = (EditText) rootView.findViewById(R.id.terimaUserNamaLengkap);
            alamat = (EditText) rootView.findViewById(R.id.terimaUserAlamat);
            jabatan = (EditText) rootView.findViewById(R.id.terimaUserJabatan);
            noTelp = (EditText) rootView.findViewById(R.id.terimaUserNoTelp);
            email = (EditText) rootView.findViewById(R.id.terimaUserEmail);
            terima = (Button) rootView.findViewById(R.id.buttonTerima);

            terima.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(new Connection().checkConnection(getActivity()))
                    terimaUser();
                    else
                        errorToast("Kesalahan : Anda tidak tersambung ke internet.");
                }
            });
            lihatTb = (Button) rootView.findViewById(R.id.buttonLihatTb);
            lihatTb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lihatTamanBaca();
                }
            });

            Bundle intent = getActivity().getIntent().getExtras();
            int id = intent.getInt("id");
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("aksi", "lihat"));
            params.add(new BasicNameValuePair("id", ""+id));
            RequestData requestData = new RequestData(
                    "userdao.php",
                    params,
                    getActivity(),
                    "Memuat User")
            {
                @Override
                protected void onPostExecute(JSONArray data) {
                    pDialog.dismiss();
                    JSONArray jsonArray = data;
                    //og.d("cekid", ""+jsonArray);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Log.d("cekid", "" + i);
                        try {
                            JSONObject o = jsonArray.getJSONObject(i);
                            User user = new User(
                                    o.getInt("id"),
                                    o.getString("nama"),
                                    o.getString("alamat"),
                                    o.getString("jabatan"),
                                    o.getString("notelp"),
                                    o.getString("email"),
                                    o.getString("password"),
                                    o.getInt("status"),
                                    o.getInt("id_tb")
                            );
                            id_tb = user.getId_tb();
                            id_user = user.getId_user();
                            nama.setText(user.getNama());
                            alamat.setText(user.getAlamat());
                            jabatan.setText(user.getJabatan());
                            noTelp.setText(user.getNoTelp());
                            email.setText(user.getEmail());
                            if(user.getStatus()==1)
                                terima.setVisibility(View.INVISIBLE);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };

            requestData.execute();
            return rootView;
        }
    }
}
