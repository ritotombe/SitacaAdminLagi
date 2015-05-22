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
import java.util.List;

public class DetilAdminActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detil_admin);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detil_admin, menu);
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
        private View rootView;
        int id_admin;
        Button terima;

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

        public void terimaAdmin(){
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("aksi", "terima"));
            params.add(new BasicNameValuePair("id", ""+id_admin));
            RequestData requestData = new RequestData(
                    "admindao.php",
                    params,
                    getActivity(),
                    "Menerima Admin")
            {
                @Override
                protected void onPostExecute(JSONArray data) {
                    pDialog.dismiss();
                    Log.d("asd", "" + data);
                    if(data == null)
                    {
                        errorToast("Kesalahan: Admin tidak berhasil diubah");
                    }
                    else
                    {
                        try {
                            errorToast(data.get(0).toString());
                            if (new Connection().checkConnection(getActivity())) {
                                Intent intent = new Intent(getActivity(), AdminActivity.class);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(
                                        getActivity(),
                                        "Kesalahan: Anda tidak tersambung ke internet",
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

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            rootView = inflater.inflate(R.layout.fragment_detil_admin, container, false);
            nama = (EditText) rootView.findViewById(R.id.viewNamaLengkap);
            alamat = (EditText) rootView.findViewById(R.id.viewAlamat);
            jabatan = (EditText) rootView.findViewById(R.id.viewJabatan);
            noTelp = (EditText) rootView.findViewById(R.id.viewNoTelp);
            email = (EditText) rootView.findViewById(R.id.viewEmail);
            terima = (Button) rootView.findViewById(R.id.buttonTerima);

            terima.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(new Connection().checkConnection(getActivity()))
                        terimaAdmin();
                    else
                        errorToast("Kesalahan : Anda tidak tersambung ke internet");
                }
            });

            Bundle intent = getActivity().getIntent().getExtras();
            id_admin = intent.getInt("id");

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("aksi", "lihat"));
            params.add(new BasicNameValuePair("id", ""+id_admin));
            RequestData requestData = new RequestData(
                    "admindao.php",
                    params,
                    getActivity(),
                    "Memuat Admin")
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
                            Admin admin = new Admin(
                                    o.getInt("id"),
                                    o.getString("nama"),
                                    o.getString("alamat"),
                                    o.getString("jabatan"),
                                    o.getString("notelp"),
                                    o.getString("email"),
                                    o.getString("password"),
                                    o.getInt("status")
                            );

                            nama.setText(admin.getNama());
                            alamat.setText(admin.getAlamat());
                            jabatan.setText(admin.getJabatan());
                            noTelp.setText(admin.getNoTelp());
                            email.setText(admin.getEmail());
                            Log.d("test status", admin.getNama()+" "+admin.getStatus());
                            if(admin.getStatus()==1)
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
