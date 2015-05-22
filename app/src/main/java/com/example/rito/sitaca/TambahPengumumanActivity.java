package com.example.rito.sitaca;

import android.content.Intent;
import android.content.SharedPreferences;
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

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class TambahPengumumanActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_pengumuman);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tambah_pengumuman, menu);
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
        private EditText judulPengumuman, isiPengumuman;
        private View rootView;
        public PlaceholderFragment() {
        }

        void errorToast(String e){
            Toast.makeText(
                    rootView.getContext(),
                    "" + e,
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        public void buatPengumuman(){
            SharedPreferences pref = getActivity().getSharedPreferences("sitacaadmin",0);
            int id_admin = pref.getInt("id",-1);
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("aksi", "buat"));
            params.add(new BasicNameValuePair("id_admin", ""+id_admin));//masih dummy krn login belom kelar
            params.add(new BasicNameValuePair("judul", judulPengumuman.getText().toString()));
            params.add(new BasicNameValuePair("isi", isiPengumuman.getText().toString()));
            RequestData requestData = new RequestData(
                    "pengumumandao.php",
                    params,
                    getActivity(),
                    "Membuat Pengumuman") {
                @Override
                protected void onPostExecute(JSONArray data) {
                    pDialog.dismiss();

                    if(data != null) {
                        try {
                            errorToast(data.get(0).toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    else {
                        errorToast("Tidak Ada Data");
                    }
                    Intent intent = new Intent(getActivity(), PengumumanActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            };
            requestData.execute();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            rootView = inflater.inflate(R.layout.fragment_tambah_pengumuman, container, false);
            judulPengumuman = (EditText) rootView.findViewById(R.id.judulPengumuman);
            isiPengumuman = (EditText) rootView.findViewById(R.id.isiPengumuman);
            Button button = (Button) rootView.findViewById(R.id.button);
            button.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {

                    boolean goodToGo = true;

                    if(String.valueOf(judulPengumuman.getText()).equalsIgnoreCase("")){
                        errorToast("Nama pengguna belum diisi.");
                        goodToGo = false;
                    }
                    if(String.valueOf(isiPengumuman.getText()).equalsIgnoreCase("")){
                        errorToast("Alamat pengguna belum diisi.");
                        goodToGo = false;
                    }
                    if(goodToGo){
                        buatPengumuman();
                    }
                }
            });
            return rootView;
        }
    }
}
