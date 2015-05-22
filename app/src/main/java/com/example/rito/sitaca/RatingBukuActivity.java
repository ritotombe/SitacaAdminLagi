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


public class RatingBukuActivity extends ActionBarActivity {

    PlaceholderFragment fragment = new PlaceholderFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_buku);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rating_buku, menu);

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
                // --> fragment.adapterBuku.filter(newText);
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
        public ListView mListViewBuku;
        public BukuListAdapter adapterBuku;
        public List<Buku> listBuku;

        public PlaceholderFragment() {
        }

        public class BukuListAdapter extends ArrayAdapter<Buku> {

            public ArrayList<Buku> arraylist;
            public List<Buku> mListBuku;

            public BukuListAdapter(List<Buku> mListBuku) {
                super(getActivity(),
                        R.layout.list_item_buku,
                        // R.id.listItemPeminjamanTextview,
                        mListBuku);
                this.mListBuku = mListBuku;
                this.arraylist = new ArrayList<Buku>();
                this.arraylist.addAll(mListBuku);
            }

            public class ViewHolder {//--->perubahan delete(Untuk nyimpan view  data tiap view ke holder jadi ga dibuat ulang tiap scrolling)
                TextView kategori;
                TextView judul;
                TextView rating;
                //CheckBox cb;
            }

            public View getView(final int position, View view, ViewGroup parent) {

                ViewHolder viewHolder = null;// Tambahin Holder

                final Buku currentBuku = mListBuku.get(position);

                if (view == null) {//---> Kalo View belum ada buat holder baru

                    view = getActivity().getLayoutInflater().inflate(R.layout.list_item_buku, parent, false);
                    viewHolder = new ViewHolder();
                    viewHolder.kategori = (TextView) view.findViewById(R.id.viewKategori);
                    viewHolder.judul = (TextView) view.findViewById(R.id.viewJudul);
                    viewHolder.rating = (TextView) view.findViewById(R.id.viewRating);
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
                viewHolder.kategori.setText(""+currentBuku.getNamaKategori());
                viewHolder.judul.setText(""+currentBuku.getJudul());
                viewHolder.rating.setText(""+currentBuku.getRating());

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
                mListBuku.clear();
                if (charText.length() == 0) {
                    mListBuku.addAll(arraylist);
                } else {
                    for (Buku buku : arraylist) {
                        if (buku.getJudul().toLowerCase(Locale.getDefault()).contains(charText)){
                            mListBuku.add(buku);
                        }
                    }
                }
                notifyDataSetChanged();
            }
        }

        public void populateList() throws ExecutionException, InterruptedException {
            mListViewBuku = (ListView) rootView.findViewById(R.id.listViewBuku);
            listBuku = new ArrayList<>();

            Bundle intent = getActivity().getIntent().getExtras();
            int id = intent.getInt("id");
            List<NameValuePair> params = new ArrayList<NameValuePair>();

            params.add(new BasicNameValuePair("tag", "buku"));
            params.add(new BasicNameValuePair("id", "" + id));
            RequestData requestData = new RequestData(
                    "getdata.php",
                    params,
                    getActivity(),
                    "Memuat Buku") {
                @Override
                protected void onPostExecute(JSONArray data) {
                    pDialog.dismiss();
                    if (data != null){
                        JSONArray jsonArray = data;
                        Log.d("cekid", ""+jsonArray);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            Log.d("cekid", "" + i);
                            try {
                                JSONObject o = jsonArray.getJSONObject(i);
                                Buku buku = new Buku(
                                        o.getInt("id"),
                                        o.getInt("id_kategori"),
                                        o.getString("judul"),
                                        o.getString("pengarang"),
                                        o.getString("tahun_terbit"),
                                        o.getString("penerbit"),
                                        o.getInt("poin"),
                                        o.getString("status"),
                                        o.getInt("rating"),
                                        o.getString("nama_kategori")
                                );
                                listBuku.add(buku);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    else {
                        Toast.makeText(
                                getActivity(),
                                "Tidak ada buku.",
                                Toast.LENGTH_SHORT
                        ).show();
                    }


                    Collections.reverse(listBuku);
                    adapterBuku = new BukuListAdapter(listBuku);
                    mListViewBuku.setAdapter(adapterBuku);

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
            rootView = inflater.inflate(R.layout.fragment_rating_buku, container, false);

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
