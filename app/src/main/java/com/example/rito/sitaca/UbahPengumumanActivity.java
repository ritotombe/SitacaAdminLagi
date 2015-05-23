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


public class UbahPengumumanActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_pengumuman);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ubah_pengumuman, menu);
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

        private EditText judul, isi;
        private int id_pengumuman;
        private View rootView;

        void errorToast(String e){
            Toast.makeText(
                    rootView.getContext(),
                    "" + e,
                    Toast.LENGTH_SHORT
            ).show();
            return;

        }

        public void ubahPengumuman(){
            SharedPreferences pref = getActivity().getSharedPreferences("sitacaadmin",0);
            int id_admin = pref.getInt("id",-1);

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("aksi", "update"));
            params.add(new BasicNameValuePair("id", ""+id_pengumuman));//masih dummy krn login belom kelar
            params.add(new BasicNameValuePair("judul", judul.getText().toString()));
            params.add(new BasicNameValuePair("isi", isi.getText().toString()));
            params.add(new BasicNameValuePair("id_admin", ""+id_admin));
            RequestData requestData = new RequestData(
                    "pengumumandao.php",
                    params,
                    getActivity(),
                    "Mengubah Pengumuman") {
                @Override
                protected void onPostExecute(JSONArray data) {
                    pDialog.dismiss();
                    Intent intent = new Intent(getActivity(), PengumumanActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            };
            requestData.execute();
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.fragment_ubah_pengumuman, container, false);
            judul = (EditText) rootView.findViewById(R.id.viewJudul);
            id_pengumuman = -1;
            isi = (EditText) rootView.findViewById(R.id.viewIsi);
            /*alamatTB = (EditText) rootView.findViewById(R.id.alamatTB);
            twitter = (EditText) rootView.findViewById(R.id.twitter);
            facebook = (EditText) rootView.findViewById(R.id.facebook);*/
            Bundle intent = getActivity().getIntent().getExtras();
            int id = intent.getInt("id");
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("aksi", "lihat"));
            params.add(new BasicNameValuePair("id", ""+id));
            RequestData requestData = new RequestData(
                    "pengumumandao.php",
                    params,
                    getActivity(),
                    "Memuat Pengumuman")
            {
                @Override
                protected void onPostExecute(JSONArray data) {
                    pDialog.dismiss();
                    JSONArray jsonArray = data;
                    Log.d("ceklala", "" + jsonArray);
                        for (int i = 0; i < jsonArray.length(); i++) {
                        Log.d("cekid", " " + i);
                        try {
                            JSONObject o = jsonArray.getJSONObject(i);
                            Pengumuman pengumuman = new Pengumuman(
                                    o.getInt("id"),
                                    o.getInt("id_admin"),
                                    o.getString("nama"),
                                    o.getString("judul"),
                                    o.getString("isi"),
                                    null
                            );

                            id_pengumuman = pengumuman.getId_pengumuman();

                            judul.setText(pengumuman.getJudul());
                            isi.setText(pengumuman.getIsi());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
            requestData.execute();

            Button button = (Button) rootView.findViewById(R.id.button);
            button.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {

                    boolean goodToGo = true;

                    if(String.valueOf(judul.getText()).equalsIgnoreCase("")){
                        errorToast("Kesalahan : Nama pengguna belum terisi.");
                        goodToGo = false;
                    }
                    if(String.valueOf(isi.getText()).equalsIgnoreCase("")){
                        errorToast("Kesalahan : Alamat pengguna belum terisi.");
                        goodToGo = false;
                    }
                    if(goodToGo){
                        if(new Connection().checkConnection(getActivity())) {
                            ubahPengumuman();
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
            });
            return rootView;
        }
    }
}
