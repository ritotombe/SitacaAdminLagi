package com.example.rito.sitaca;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;


public class TambahAdminActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_admin);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tambah_admin, menu);
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

        View rootView;
        EditText nama, alamat, email, jabatan, notelp, pass, konpass;

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
        public boolean isValidEmail(CharSequence target) {
            if (target == null)
                return false;

            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            rootView = inflater.inflate(R.layout.fragment_tambah_admin, container, false);
            nama = (EditText) rootView.findViewById(R.id.buatAdminNama);
            alamat = (EditText) rootView.findViewById(R.id.buatAdminAlamat);
            email = (EditText) rootView.findViewById(R.id.buatAdminEmail);
            jabatan = (EditText) rootView.findViewById(R.id.buatAdminJabatan);
            notelp = (EditText) rootView.findViewById(R.id.buatAdminNoTelp);
            pass = (EditText) rootView.findViewById(R.id.buatAdminKataSandi);
            konpass = (EditText) rootView.findViewById(R.id.buatAdminKonfirmasi);
            Button button = (Button) rootView.findViewById(R.id.button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buatAdmin();
                }
            });

            return rootView;
        }
        public void buatAdmin(){

            boolean goodToGo = true;

            if(String.valueOf(nama.getText()).equalsIgnoreCase("")){
                errorToast("Nama pengguna belum diisi.");
                goodToGo = false;
            }
            if(String.valueOf(alamat.getText()).equalsIgnoreCase("")){
                errorToast("Alamat pengguna belum diisi.");
                goodToGo = false;
            }
            if(String.valueOf(jabatan.getText()).equalsIgnoreCase("")){
                errorToast("Jabatan pengguna belum diisi.");
                goodToGo = false;
            }
            if(String.valueOf(notelp.getText()).equalsIgnoreCase("")){
                errorToast("Nomor telepon pengguna belum diisi.");
            }
            if(String.valueOf(email.getText()).equalsIgnoreCase("")){
                errorToast("Email pengguna belum diisi.");
                goodToGo = false;
            }
            if(!isValidEmail(String.valueOf(email.getText()))){
                errorToast("Format Email Salah");
                goodToGo = false;
            }
            if(pass.getText().toString().equalsIgnoreCase("")){
                errorToast("Kata sandi pengguna belum diisi.");
                goodToGo = false;
            }
            if(konpass.getText().toString().equalsIgnoreCase("")){
                errorToast("Konfirmasi kata sandi pengguna belum diisi.");
                goodToGo = false;
            }
            if(!(pass.getText().toString().equalsIgnoreCase("")) && !(konpass.getText().toString().equalsIgnoreCase("")))
            {
                if(!(pass.getText().toString().equals(konpass.getText().toString()))){
                    errorToast("Kata sandi dan konfirmasi kata sandi tidak sama.");
                    goodToGo = false;
                }
                if(goodToGo){
                    if(new Connection().checkConnection(rootView.getContext()))
                    {
                        List<NameValuePair> params = new ArrayList<NameValuePair>();
                        params.add(new BasicNameValuePair("aksi", "buat"));
                        params.add(new BasicNameValuePair("nama", String.valueOf(nama.getText())));
                        params.add(new BasicNameValuePair("alamat", String.valueOf(alamat.getText())));
                        params.add(new BasicNameValuePair("jabatan",  String.valueOf(jabatan.getText())));
                        params.add(new BasicNameValuePair("notelp",  String.valueOf(notelp.getText())));
                        params.add(new BasicNameValuePair("email",String.valueOf(email.getText())));
                        params.add(new BasicNameValuePair("pass",  String.valueOf(pass.getText())));
                        RequestData requestData = new RequestData(
                                "http://ritotom.be/sitacaapi/admindao.php",
                                params,
                                getActivity(),
                                "Membuat Admin") {
                            @Override
                            protected void onPostExecute(JSONArray data) {
                                pDialog.dismiss();
                                if(data != null) {
                                    try {
                                        errorToast(data.get(0).toString());
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    Intent intent = new Intent(rootView.getContext(), LoginActivity.class);
                                    try {
                                        errorToast(data.getJSONObject(0).toString());
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    startActivity(intent);
                                    getActivity().finish();
                                }
                                else {
                                    errorToast("Kesalahan : Email anda sudah terpakai");
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
        }
    }
}
