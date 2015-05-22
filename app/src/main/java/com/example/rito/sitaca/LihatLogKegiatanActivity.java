package com.example.rito.sitaca;

import android.animation.LayoutTransition;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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


public class LihatLogKegiatanActivity extends ActionBarActivity {

    PlaceholderFragment fragment = new PlaceholderFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_log_kegiatan);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lihat_log_kegiatan, menu);

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
                fragment.adapterLogKegiatan.filter(newText);
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

        if (id == R.id.search)
        {
            ////////////////////////////ubah search/ada yang dihapus disini!!!!!!!!!!!!!!!!!!!!!!!
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public View rootView;
        public ListView mListViewLogKegiatan;
        public LogKegiatanListAdapter adapterLogKegiatan;
        public List<LogKegiatan> listLogKegiatan;


        public PlaceholderFragment() {
        }

        public void populateList() throws ExecutionException, InterruptedException {
            mListViewLogKegiatan = (ListView) rootView.findViewById(R.id.listViewLihatLogKegiatan);
            listLogKegiatan = new ArrayList<>();

            Bundle intent = getActivity().getIntent().getExtras();
            int id = intent.getInt("id");
            List<NameValuePair> params = new ArrayList<NameValuePair>();

            params.add(new BasicNameValuePair("tag", "log_kegiatan"));
            params.add(new BasicNameValuePair("id", "" + id));
            RequestData requestData = new RequestData(
                    "getdata.php",
                    params,
                    getActivity(),
                    "Memuat Log Kegiatan")
            {
                @Override
                protected void onPostExecute(JSONArray data) {
                    pDialog.dismiss();
                    if(data != null) {
                        JSONArray jsonArray = data;
                        //Log.d("cekid", ""+jsonArray);
                        for (int i = 0; i < jsonArray.length(); i++) {

                            try {
                                JSONObject o = jsonArray.getJSONObject(i);
                                LogKegiatan logKegiatan = new LogKegiatan(
                                        o.getInt("id"),
                                        o.getInt("id_taman_baca"),
                                        o.getString("nama"),
                                        o.getString("tanggal"),
                                        o.getString("deskripsi")
                                );
                                Log.d("cekidd", logKegiatan+"");
                                listLogKegiatan.add(logKegiatan);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    else
                    {
                        Toast.makeText(
                                getActivity(),
                                "Tidak ada kegiatan.",
                                Toast.LENGTH_SHORT
                        ).show();
                    }

                    Collections.reverse(listLogKegiatan);
                    adapterLogKegiatan = new LogKegiatanListAdapter(listLogKegiatan);
                    mListViewLogKegiatan.setAdapter(adapterLogKegiatan);

                }
            };

            requestData.execute();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            rootView = inflater.inflate(R.layout.fragment_lihat_log_kegiatan, container, false);

            try {
                populateList();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return rootView;
        }

        public class LogKegiatanListAdapter extends ArrayAdapter<LogKegiatan> {

            ////////////////////////////ubah search// ada yang dihilangkan di sini!!!!!!!!!!!!!!!!!!!!!!!

            public ArrayList<LogKegiatan> arraylist;
            public List<LogKegiatan> mListLogKegiatan;

            public LogKegiatanListAdapter(List<LogKegiatan> mListLogKegiatan) {
                super(getActivity(),
                        R.layout.list_item_lihatlogkegiatan,
                        mListLogKegiatan);
                this.mListLogKegiatan = mListLogKegiatan;
                this.arraylist = new ArrayList<LogKegiatan>();
                this.arraylist.addAll(mListLogKegiatan);
            }

            @Override
            public View getView(final int position, View view, ViewGroup parent) {

                ViewHolder viewHolder = null;// Tambahin Holder

                final LogKegiatan currentLogKegiatan = mListLogKegiatan.get(position);

                if (view == null) {//---> Kalo View belum ada buat holder baru
                    view = getActivity().getLayoutInflater().inflate(R.layout.list_item_lihatlogkegiatan, parent, false);
                    viewHolder = new ViewHolder();
                    viewHolder.namaKegiatan = (TextView) view.findViewById(R.id.viewNamaKegiatan);
                    viewHolder.tanggal = (TextView) view.findViewById(R.id.viewTanggal);
                    viewHolder.deskripsi = (TextView) view.findViewById(R.id.viewDeskripsi);

                    view.setTag(viewHolder);
                }
                else { //--> Kalo udah ada viewnya, holdernya diambil dari tag
                    viewHolder = (ViewHolder) view.getTag();
                }

                viewHolder.namaKegiatan.setText(currentLogKegiatan.getNama());
                viewHolder.tanggal.setText(currentLogKegiatan.getTanggal());
                viewHolder.deskripsi.setText(currentLogKegiatan.getDeskripsi());


                view.setTag(viewHolder);

                return view;

            }

            //---------------------------------------//Filter untuk search//----------------------------------------------//
            public void filter(String charText) {
                charText = charText.toLowerCase(Locale.getDefault());
                //Pake list yang di passing ke adapter dari fragment
                mListLogKegiatan.clear();
                if (charText.length() == 0) {
                    mListLogKegiatan.addAll(arraylist);
                } else {
                    for (LogKegiatan logKegiatan : arraylist) {
                        if (logKegiatan.getNama().toLowerCase(Locale.getDefault()).contains(charText)) {
                            mListLogKegiatan.add(logKegiatan);
                        }
                    }
                }
                notifyDataSetChanged();
            }
        }
        public class ViewHolder {//--->perubahan delete(Untuk nyimpan view  data tiap view ke holder jadi ga dibuat ulang tiap scrolling)
            TextView namaKegiatan;
            TextView tanggal;
            TextView deskripsi;
            CheckBox cb;
        }
    }
}
