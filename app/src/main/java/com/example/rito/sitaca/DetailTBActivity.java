package com.example.rito.sitaca;

import android.content.Intent;
import android.graphics.Color;
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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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

public class DetailTBActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tb);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail_tb, menu);
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

        List<String> listUser = new ArrayList<String>();
        private EditText namaTB, alamatTB, twitter, facebook;
        private AutoCompleteTextView namaUser;
        private int idPreUser, idPostUser, id_tb, mode;
        private View rootView;

        public PlaceholderFragment() {
        }

        void errorToast(String e) {
            Toast.makeText(
                    rootView.getContext(),
                    "" + e,
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            rootView = inflater.inflate(R.layout.fragment_detail_tb, container, false);

            namaUser = (AutoCompleteTextView) rootView.findViewById(R.id.namaUser);
            namaTB = (EditText) rootView.findViewById(R.id.namaTB);
            alamatTB = (EditText) rootView.findViewById(R.id.alamatTB);
            twitter = (EditText) rootView.findViewById(R.id.twitter);
            facebook = (EditText) rootView.findViewById(R.id.facebook);

            Bundle intent = getActivity().getIntent().getExtras();
            id_tb = intent.getInt("id");
            mode = intent.getInt("mode");

            fillData(id_tb);
            fillUserSpinner();

            if (mode == 0) {
                namaUser.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View arg0) {
                        namaUser.showDropDown();

                    }
                });
            }

            Button button = (Button) rootView.findViewById(R.id.button);
            if (mode == 1) {
                button.setVisibility(View.INVISIBLE);
                namaUser.setClickable(false);
                namaUser.setFocusable(false);
                namaUser.setBackgroundColor(Color.TRANSPARENT);
            }
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateTb();
                }
            });
            return rootView;
        }

        public Boolean cekUser(int id) {
            Boolean bool = false;
            if (listUser.size() > 0) {
                for (int i = 0; i < listUser.size(); i++) {
                    String[] splitString = String.valueOf(listUser.get(i)).split(" - ");
                    int id_user = Integer.parseInt(splitString[0]);
                    if (id_user == id) {
                        return true;
                    }
                }
            }
            return bool;
        }

        public void updateTb() {
            boolean cek = true;//baru mulai sini
            try {
                String[] splitString = String.valueOf(namaUser.getText()).split(" - ");
                idPostUser = Integer.parseInt(splitString[0]);
                cek = true;
            } catch (NumberFormatException e) {
                cek = false;

            }
            if (String.valueOf(namaUser.getText()).equalsIgnoreCase("")) {
                cek = false;
                errorToast("Kesalahan : Pengguna belum terisi.");
            }

            if (!cekUser(idPostUser) && !String.valueOf(namaUser.getText()).equalsIgnoreCase("")) {
                cek = false;
                errorToast("Kesalahan : Pengguna yang anda pilih tidak tersedia.");
            }

            if (cek) {
                if (new Connection().checkConnection(getActivity())) {
                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("aksi", "ubah_user"));
                    params.add(new BasicNameValuePair("id_tb", "" + id_tb));
                    params.add(new BasicNameValuePair("id_pre", "" + idPreUser));
                    params.add(new BasicNameValuePair("id_post", "" + idPostUser));
                    RequestData requestData = new RequestData(
                            "tbdao.php",
                            params,
                            getActivity(),
                            "Membaharui Taman Baca") {
                        @Override
                        protected void onPostExecute(JSONArray data) {
                            pDialog.dismiss();
                            Log.d("asd", "" + data);
                            if (data == null) {
                                errorToast("Kesalahan : TB tidak berhasil diubah.");
                            } else {
                                try {
                                    errorToast(data.get(0).toString());

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            Intent intent = new Intent(getActivity(), LihatTamanBacaActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                        }
                    };
                    requestData.execute();
                } else {
                    errorToast("Kesalahan : Anda tidak tersambung ke internet.");
                }
            }

        }

        public void fillUserSpinner() {

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("tag", "spinner_user"));
            RequestData requestData = new RequestData(
                    "getdata.php",
                    params,
                    getActivity(),
                    "Memuat Taman Baca") {
                @Override
                protected void onPostExecute(JSONArray data) {
                    pDialog.dismiss();
                    if(data!=null) {
                        JSONArray jsonArray = data;

                        Log.d("cekid", "" + jsonArray);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            Log.d("cekid", "" + i);
                            try {
                                JSONObject o = jsonArray.getJSONObject(i);
                                listUser.add(o.getInt("id") + " - " + o.getString("nama"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        ArrayAdapter<String> userAdapter =
                                new ArrayAdapter<String>(
                                        getActivity(),
                                        R.layout.list_item_spinner_empty,
                                        listUser
                                );
                        userAdapter.setDropDownViewResource(R.layout.list_item_spinner_empty);
                        namaUser.setAdapter(userAdapter);
                        namaUser.setThreshold(1);
                    }
                }
            };

            requestData.execute();

        }

        public void fillData(int id) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("aksi", "lihat"));
            params.add(new BasicNameValuePair("id", "" + id));
            RequestData requestData = new RequestData(
                    "tbdao.php",
                    params,
                    getActivity(),
                    "Memuat Taman Baca") {
                @Override
                protected void onPostExecute(JSONArray data) {
                    pDialog.dismiss();
                    JSONArray jsonArray = data;
                    Log.d("cekid", "" + jsonArray);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Log.d("cekid", "" + i);
                        try {
                            JSONObject o = jsonArray.getJSONObject(i);
                            TamanBaca tamanBaca = new TamanBaca(
                                    o.getInt("id"),
                                    o.getInt("id_user"),
                                    o.getString("nama_user"),
                                    o.getString("nama_tb"),
                                    o.getString("alamat"),
                                    o.getString("twitter"),
                                    o.getString("facebook")
                            );

                            namaUser.setText(tamanBaca.getId_user() + " - " + tamanBaca.getNamaUser());
                            namaTB.setText(tamanBaca.getNama());
                            alamatTB.setText(tamanBaca.getAlamat());
                            twitter.setText(tamanBaca.getTwitter());
                            facebook.setText(tamanBaca.getFacebook());

                            idPreUser = tamanBaca.getId_user();
                            //listUser.add(tamanBaca.getId_user() + " - " + tamanBaca.getNamaUser());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };

            requestData.execute();
        }
    }
}
