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

import com.example.rito.sitaca.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UbahAdminActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_admin);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ubah_admin, menu);
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
        private EditText nama, alamat, email, jabatan, noTelp, pass, nPass, nKonpass;
        private Admin admin;
        private View rootView;
        private int id;

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

        public void ubahAdmin( String password){
            boolean goodToGo = true;

            if(String.valueOf(nama.getText()).equalsIgnoreCase("")){
                errorToast("Kesalahan : Nama pengguna belum terisi.");
                goodToGo = false;
            }
            if(String.valueOf(alamat.getText()).equalsIgnoreCase("")){
                errorToast("Kesalahan : Alamat pengguna belum terisi.");
                goodToGo = false;
            }
            if(String.valueOf(jabatan.getText()).equalsIgnoreCase("")){
                errorToast("Kesalahan : Jabatan pengguna belum terisi.");
                goodToGo = false;
            }
            if(String.valueOf(noTelp.getText()).equalsIgnoreCase("")){
                errorToast("Kesalahan : Nomor telepon pengguna belum terisi.");
                goodToGo = false;
            }
            if(String.valueOf(email.getText()).equalsIgnoreCase("")){
                errorToast("Kesalahan : Email pengguna belum terisi.");
                goodToGo = false;
            }
            if(!isValidEmail(String.valueOf(email.getText()))){
                errorToast("Kesalahan : Format email tidak sesuai.");
                goodToGo = false;
            }
            if(goodToGo){
                if(new Connection().checkConnection(rootView.getContext()))
                {
                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("aksi", "update"));
                    //Log.d("idcek",""+id);
                    params.add(new BasicNameValuePair("id", ""+id));
                    params.add(new BasicNameValuePair("nama", String.valueOf(nama.getText())));
                    params.add(new BasicNameValuePair("alamat", String.valueOf(alamat.getText())));
                    params.add(new BasicNameValuePair("jabatan",  String.valueOf(jabatan.getText())));
                    params.add(new BasicNameValuePair("notelp",  String.valueOf(noTelp.getText())));
                    params.add(new BasicNameValuePair("email",String.valueOf(email.getText())));
                    params.add(new BasicNameValuePair("password",  password));
                    RequestData requestData = new RequestData(
                            "admindao.php",
                            params,
                            getActivity(),
                            "Mengubah Admin") {
                        @Override
                        protected void onPostExecute(JSONArray data) {
                            pDialog.dismiss();
                            if(data!=null) {
                                try {
                                    if (data.get(0).toString().equalsIgnoreCase("Perubahan Admin berhasil disimpan.")) {
                                        Toast.makeText(
                                                rootView.getContext(),
                                                data.get(0).toString(),
                                                Toast.LENGTH_SHORT
                                        ).show();

                                        Intent intent = new Intent(rootView.getContext(), AdminActivity.class);
                                        startActivity(intent);
                                        getActivity().finish();
                                    } else {
                                        Toast.makeText(
                                                rootView.getContext(),
                                                data.get(0).toString(),
                                                Toast.LENGTH_SHORT
                                        ).show();
                                    }

                                    // errorToast("Perubahan Admin berhasil disimpan");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            else{
                                Toast.makeText(
                                        rootView.getContext(),
                                        "DATA NULL",
                                        Toast.LENGTH_SHORT
                                ).show();
                            }


                            //kenapa data null tapi isinya tetap berubah
                            /**try {
                             if(data.get(0).toString().equals(""+admin.getId_admin())){
                             errorToast("Perubahan Admin berhasil disimpan");
                             }
                             else{
                             errorToast(data.get(0).toString());
                             }
                             } catch (JSONException e) {
                             e.printStackTrace();
                             }**/


                            /** try {
                             data.getJSONObject(0).toString();
                             //errorToast("Perubahan Admin berhasil disimpan");
                             } catch (JSONException e) {
                             e.printStackTrace();
                             }**/

                            /**}
                             else {
                             // errorToast("Email anda sudah terpakai");
                             }**/
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

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            rootView = inflater.inflate(R.layout.fragment_ubah_admin, container, false);

            nama = (EditText) rootView.findViewById(R.id.ubahAdminNama);
            alamat = (EditText) rootView.findViewById(R.id.ubahAdminAlamat);
            email = (EditText) rootView.findViewById(R.id.ubahAdminEmail);
            noTelp = (EditText) rootView.findViewById(R.id.ubahAdminNoTelp);
            pass = (EditText) rootView.findViewById(R.id.ubahAdminPass);
            jabatan = (EditText) rootView.findViewById(R.id.ubahAdminJabatan);
            nKonpass = (EditText) rootView.findViewById(R.id.ubahAdminNewPassK);
            nPass = (EditText) rootView.findViewById(R.id.ubahAdminNewPass);

            Bundle intent = getActivity().getIntent().getExtras();
            id = intent.getInt("id");
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("aksi", "lihat"));
            Log.d("testt", "" + id);
            params.add(new BasicNameValuePair("id", ""+id));
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
                    Log.d("ceklala", "" + jsonArray);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Log.d("cekid", " " + i);
                        try {
                            JSONObject o = jsonArray.getJSONObject(i);
                            admin = new Admin(
                                    o.getInt("id"),
                                    o.getString("nama"),
                                    o.getString("alamat"),
                                    o.getString("jabatan"),
                                    o.getString("notelp"),
                                    o.getString("email"),
                                    o.getString("password"),
                                    1
                            );

                            nama.setText(admin.getNama());
                            alamat.setText(admin.getAlamat());
                            email.setText(admin.getEmail());
                            noTelp.setText(admin.getNoTelp());
                            jabatan.setText(admin.getJabatan());
                            pass.setText("");
                            nPass.setText("");
                            nKonpass.setText("");
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
                    // Log.d("Admin*", " "+pass.getText().toString());
                    if(pass.getText().toString().equalsIgnoreCase("")){
                        errorToast("Kesalahan : Kata sandi belum terisi.");
                    }
                    else if(pass.getText().toString().equals(admin.getPassword())){
                        if(nPass.getText().toString().equalsIgnoreCase("") && nKonpass.getText().toString().equalsIgnoreCase("")) {
                            ubahAdmin(admin.getPassword());
                        }
                        else if (nPass.getText().toString().equalsIgnoreCase("")) {
                            errorToast("Kesalahan : Kata sandi baru pengguna belum terisi.");
                        }
                        else if (nKonpass.getText().toString().equalsIgnoreCase("")) {
                            errorToast("Kesalahan : Konfirmasi kata sandi baru pengguna belum terisi.");
                        }
                        else{
                            if(nPass.getText().toString().equals(nKonpass.getText().toString()))
                            {
                                ubahAdmin(nPass.getText().toString());
                            }
                            else{
                                errorToast("Kesalahan : Kata sandi baru dan konfirmasi kata sandi baru tidak sesuai.");
                            }
                        }
                    }
                    else{
                        errorToast("Kesalahan : Kata sandi tidak sesuai.");
                    }
                }
            });
            return rootView;
        }
    }
}