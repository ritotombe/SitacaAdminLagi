package com.example.rito.sitaca;

import android.animation.LayoutTransition;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;


public class LogHarianActivity extends ActionBarActivity {

    PlaceholderFragment fragment = new PlaceholderFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_harian);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_log_harian, menu);

        ////////////////////////////ubah search!!!!!!!!!!!!!!!!!!!!!!!
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        int searchIconId = searchView.getContext().getResources().
                getIdentifier("android:id/search_button", null, null);
        ImageView searchIcon = (ImageView) searchView.findViewById(searchIconId);
        searchIcon.setImageResource(R.mipmap.ic_search);
        searchIcon.setScaleX(0.7f);
        searchIcon.setScaleY(0.7f);

        searchView.setIconifiedByDefault(true);
        int searchPlateId = searchView.getContext().getResources().getIdentifier("android:id/search_plate", null, null);
        // Getting the 'search_plate' LinearLayout.
        LinearLayout searchPlate = (LinearLayout) searchView.findViewById(searchPlateId);
        // Setting background of 'search_plate' to earlier defined drawable.
        searchPlate.setBackgroundResource(R.drawable.search);
        searchPlate.setLayoutTransition(new LayoutTransition());

        SearchView.OnQueryTextListener textChangeListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                // this is your adapter that will be filtered
                fragment.adapterLogHarian.filter(newText);
                //System.out.println("on text chnge text: " + newText);
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }
        };
        searchView.setOnQueryTextListener(textChangeListener);

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

        public View rootView;
        public ListView mListViewLogHarian;
        public LogHarianListAdapter adapterLogHarian;
        public List<LogHarian> listLogHarian;

        public PlaceholderFragment() {
        }

        public class LogHarianListAdapter extends ArrayAdapter<LogHarian> {

            public ArrayList<LogHarian> arraylist;
            public List<LogHarian> mListLogHarian;

            public LogHarianListAdapter(List<LogHarian> mListLogHarian) {
                super(getActivity(),
                        R.layout.list_item_logharian,
                        // R.id.listItemPeminjamanTextview,
                        mListLogHarian);
                this.mListLogHarian = mListLogHarian;
                this.arraylist = new ArrayList<LogHarian>();
                this.arraylist.addAll(mListLogHarian);
            }

            public class ViewHolder {//--->perubahan delete(Untuk nyimpan view  data tiap view ke holder jadi ga dibuat ulang tiap scrolling)
                TextView tanggal;
                TextView jumlahKehadiran;
                TextView realisasiJamBuka;
                TextView realisasiJamTutup;
                //CheckBox cb;
            }

            public View getView(final int position, View view, ViewGroup parent) {

                ViewHolder viewHolder = null;// Tambahin Holder

                final LogHarian currentLogHarian = mListLogHarian.get(position);

                if (view == null) {//---> Kalo View belum ada buat holder baru

                    view = getActivity().getLayoutInflater().inflate(R.layout.list_item_logharian, parent, false);
                    viewHolder = new ViewHolder();
                    viewHolder.tanggal = (TextView) view.findViewById(R.id.viewTanggal);
                    viewHolder.jumlahKehadiran = (TextView) view.findViewById(R.id.viewJumlahKehadiran);
                    viewHolder.realisasiJamBuka = (TextView) view.findViewById(R.id.viewRealisasiJamBuka);
                    viewHolder.realisasiJamTutup = (TextView) view.findViewById(R.id.viewRealisasiJamTutup);
                    //viewHolder.cb = (CheckBox) view.findViewById(R.id.delCb);
                    view.setTag(viewHolder);

                } else { //--> Kalo udah ada viewnya, holdernya diambil dari tag
                    viewHolder = (ViewHolder) view.getTag();
                }


               /* viewHolder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            if (!deleteList.contains(currentPeminjaman))
                                deleteList.add(currentPeminjaman);
                            ////////////////////////////ubah search/ada yang dihilangkan disini!!!!!!!!!!!!!!!!!!!!!!!
                        } else {
                            deleteList.remove(currentPeminjaman);
                            ////////////////////////////ubah search/ada yang dihilangkan disini!!!!!!!!!!!!!!!!!!!!!!!
                        }
                    }
                });*/

                //Simpan Datanya ke Holder
                viewHolder.tanggal.setText(currentLogHarian.getTanggal());
                viewHolder.jumlahKehadiran.setText(""+currentLogHarian.getJumlah_kehadiran());
                viewHolder.realisasiJamBuka.setText(currentLogHarian.getRealisasi_jamBuka());
                viewHolder.realisasiJamTutup.setText(currentLogHarian.getRealisasi_jamTutup());
                // viewHolder.tglKembali.setText(currentPeminjaman.getTanggal_kembali());
                ////////////////////////////ubah search/ada yang ditambah disini!!!!!!!!!!!!!!!!!!!!!!!
               /* if (deleteList.contains(currentPeminjaman))
                    viewHolder.cb.setChecked(true);
                if (!deleteList.contains(currentPeminjaman))
                    viewHolder.cb.setChecked(false);*/
                view.setTag(viewHolder);
                return view;
            }

            public void filter(String charText) {
                charText = charText.toLowerCase(Locale.getDefault());
                //Pake list yang di passing ke adapter dari fragment
                mListLogHarian.clear();
                if (charText.length() == 0) {
                    mListLogHarian.addAll(arraylist);
                } else {
                    for (LogHarian logHarian : arraylist) {
                        if (logHarian.getTanggal().toLowerCase(Locale.getDefault()).contains(charText)){
                            mListLogHarian.add(logHarian);
                        }
                    }
                }
                notifyDataSetChanged();
            }
        }

        public void populateList() throws ExecutionException, InterruptedException {
            mListViewLogHarian = (ListView) rootView.findViewById(R.id.listViewLogHarian);
            listLogHarian = new ArrayList<>();

            Bundle intent = getActivity().getIntent().getExtras();
            int id = intent.getInt("id");
            List<NameValuePair> params = new ArrayList<NameValuePair>();

            params.add(new BasicNameValuePair("tag", "log_harian"));
            params.add(new BasicNameValuePair("id", "" + id));
            RequestData requestData = new RequestData(
                    "getdata.php",
                    params,
                    getActivity(),
                    "Memuat Log Harian") {
                @Override
                protected void onPostExecute(JSONArray data) {
                    pDialog.dismiss();
                    if (data != null){
                        JSONArray jsonArray = data;

                        for (int i = 0; i < jsonArray.length(); i++) {

                            try {

                                JSONObject o = jsonArray.getJSONObject(i);
                                LogHarian logHarian = new LogHarian(
                                        o.getInt("id"),
                                        o.getInt("id_tb"),
                                        o.getString("tanggal"),
                                        o.getInt("jumlah_kehadiran"),
                                        o.getString("realisasi_jam_buka"),
                                        o.getString("realisasi_jam_tutup")
                                );
                                Log.d("cekidd", logHarian+"");
                                listLogHarian.add(logHarian);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    else {
                        Toast.makeText(
                                getActivity(),
                                "Tidak Ada Log Harian",
                                Toast.LENGTH_SHORT
                        ).show();
                    }

                    Collections.reverse(listLogHarian);
                    adapterLogHarian = new LogHarianListAdapter(listLogHarian);
                    mListViewLogHarian.setAdapter(adapterLogHarian);

                }
            };

            requestData.execute();

               /* mListViewUser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                        Intent intent = new Intent(rootView.getContext(), UbahPeminjamanActivity.class);
                        intent.putExtra("id", listPeminjaman.get(position).getId_peminjaman());
                        startActivity(intent);
                        getActivity().finish();
                    }
                });*/
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            rootView = inflater.inflate(R.layout.fragment_log_harian, container, false);

            try {
                populateList();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return rootView;
        }
    }
}
