package com.example.rito.sitaca;

import android.animation.LayoutTransition;
import android.app.SearchManager;
import android.content.Context;
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


public class SummaryBukuActivity extends ActionBarActivity {

    PlaceholderFragment fragment = new PlaceholderFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary_buku);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_summary_buku, menu);

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
                fragment.adapterSummaryBuku.filter(newText);
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
        public ListView mListViewSummaryBuku;
        public SummaryBukuListAdapter adapterSummaryBuku;
        public List<SummaryBuku> listSummaryBuku;

        public PlaceholderFragment() {
        }

        public class SummaryBukuListAdapter extends ArrayAdapter<SummaryBuku> {

            public ArrayList<SummaryBuku> arraylist;
            public List<SummaryBuku> mListSummaryBuku;

            public SummaryBukuListAdapter(List<SummaryBuku> mListSummaryBuku) {
                super(getActivity(),
                        R.layout.list_item_summarybuku,
                        // R.id.listItemPeminjamanTextview,
                        mListSummaryBuku);
                this.mListSummaryBuku = mListSummaryBuku;
                this.arraylist = new ArrayList<SummaryBuku>();
                this.arraylist.addAll(mListSummaryBuku);
            }

            public class ViewHolder {//--->perubahan delete(Untuk nyimpan view  data tiap view ke holder jadi ga dibuat ulang tiap scrolling)
                TextView tanggal;
                TextView individu;
                TextView organisasi;
                TextView beliSendiri;
                TextView yayasan;
                //CheckBox cb;
            }

            public View getView(final int position, View view, ViewGroup parent) {

                ViewHolder viewHolder = null;// Tambahin Holder

                final SummaryBuku currentSummaryBuku = mListSummaryBuku.get(position);

                if (view == null) {//---> Kalo View belum ada buat holder baru

                    view = getActivity().getLayoutInflater().inflate(R.layout.list_item_summarybuku, parent, false);
                    viewHolder = new ViewHolder();
                    viewHolder.tanggal = (TextView) view.findViewById(R.id.viewTanggal);
                    viewHolder.individu = (TextView) view.findViewById(R.id.viewIndividu);
                    viewHolder.organisasi = (TextView) view.findViewById(R.id.viewOrganisasi);
                    viewHolder.beliSendiri = (TextView) view.findViewById(R.id.viewBeliSendiri);
                    viewHolder.yayasan = (TextView) view.findViewById(R.id.viewYayasan);
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
                viewHolder.tanggal.setText(currentSummaryBuku.getTanggal());
                viewHolder.individu.setText(""+currentSummaryBuku.getIndividu());
                viewHolder.organisasi.setText(""+currentSummaryBuku.getOrganisasi());
                viewHolder.beliSendiri.setText(""+currentSummaryBuku.getBeli_sendiri());
                viewHolder.yayasan.setText(""+currentSummaryBuku.getYayasan());

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
                mListSummaryBuku.clear();
                if (charText.length() == 0) {
                    mListSummaryBuku.addAll(arraylist);
                } else {
                    for (SummaryBuku summaryBuku : arraylist) {
                        if (summaryBuku.getTanggal().toLowerCase(Locale.getDefault()).contains(charText)){
                            mListSummaryBuku.add(summaryBuku);
                        }
                    }
                }
                notifyDataSetChanged();
            }
        }

        public void populateList() throws ExecutionException, InterruptedException {
            mListViewSummaryBuku = (ListView) rootView.findViewById(R.id.listViewSummaryBuku);
            listSummaryBuku = new ArrayList<>();

            Bundle intent = getActivity().getIntent().getExtras();
            int id = intent.getInt("id");
            List<NameValuePair> params = new ArrayList<NameValuePair>();

            params.add(new BasicNameValuePair("tag", "summary"));
            params.add(new BasicNameValuePair("id", "" + id));
            RequestData requestData = new RequestData(
                    "http://ritotom.be/sitacaapi/getdata.php",
                    params,
                    getActivity(),
                    "Daftar Ringkasan Buku") {
                @Override
                protected void onPostExecute(JSONArray data) {
                    pDialog.dismiss();
                    if (data != null){
                        JSONArray jsonArray = data;
                        //og.d("cekid", ""+jsonArray);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            Log.d("cekid", "" + i);
                            try {
                                JSONObject o = jsonArray.getJSONObject(i);
                                SummaryBuku summaryBuku = new SummaryBuku(
                                        o.getInt("id"),
                                        o.getInt("id_tb"),
                                        o.getInt("individu"),
                                        o.getInt("organisasi"),
                                        o.getInt("beli_sendiri"),
                                        o.getInt("yayasan"),
                                        o.getString("tanggal")
                                );
                                listSummaryBuku.add(summaryBuku);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    else {
                        Toast.makeText(
                                getActivity(),
                                "Tidak Ada Ringkasan Buku",
                                Toast.LENGTH_SHORT
                        ).show();
                    }


                    Collections.reverse(listSummaryBuku);
                    adapterSummaryBuku = new SummaryBukuListAdapter(listSummaryBuku);
                    mListViewSummaryBuku.setAdapter(adapterSummaryBuku);

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
            rootView = inflater.inflate(R.layout.fragment_summary_buku, container, false);

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
