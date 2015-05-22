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

import com.example.rito.sitaca.R;

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

public class LihatTamanBacaActivity extends ActionBarActivity {

    PlaceholderFragment fragment = new PlaceholderFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_taman_baca);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, fragment)
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lihat_taman_baca, menu);
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
                fragment.adapterTamanBaca.filter(newText);
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
        if(id == R.id.delete)
        {
            if(fragment.deleteList!= null && fragment.deleteList.size() == 0)
            {
                Toast.makeText(
                        getApplicationContext(),
                        "Peringatan: Belum ada taman baca yang dipilih.",
                        Toast.LENGTH_SHORT
                ).show();
            }
            else {
                new AlertDialog.Builder(this)
                        .setTitle("Konfirmasi")
                        .setMessage("Apakah "+fragment.deleteList.size()+" taman baca yang anda pilih ingin dihapus?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("Iya", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                fragment.delete();
                            }
                        })
                        .setNegativeButton("Tidak", null).show();
                //slideDelete();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public View rootView;
        public List<TamanBaca> deleteList;
        public ListView mListViewTamanBaca;
        public TamanBacaListAdapter adapterTamanBaca;
        public List<TamanBaca> listTamanBaca;


        public PlaceholderFragment() {
        }

        public class TamanBacaListAdapter extends ArrayAdapter<TamanBaca> {

            ////////////////////////////ubah search// ada yang dihilangkan di sini!!!!!!!!!!!!!!!!!!!!!!!

            public ArrayList<TamanBaca> arraylist;
            public List<TamanBaca> mListTamanBaca;

            public TamanBacaListAdapter(List<TamanBaca> mListTamanBaca) {
                super(getActivity(),
                        R.layout.list_item_taman_baca,
                        // R.id.listItemPeminjamanTextview,
                        mListTamanBaca);
                this.mListTamanBaca = mListTamanBaca;
                this.arraylist = new ArrayList<TamanBaca>();
                this.arraylist.addAll(mListTamanBaca);
            }

            public class ViewHolder {//--->perubahan delete(Untuk nyimpan view  data tiap view ke holder jadi ga dibuat ulang tiap scrolling)
                TextView namaTB;
                TextView namaUser;
                CheckBox cb;
            }


            @Override
            public View getView(final int position, View view, ViewGroup parent) {
                ViewHolder viewHolder = null;// Tambahin Holder


                    final TamanBaca currentTamanBaca = mListTamanBaca.get(position);

                    if (view == null) {//---> Kalo View belum ada buat holder baru
                        view = getActivity().getLayoutInflater().inflate(R.layout.list_item_taman_baca, parent, false);
                        viewHolder = new ViewHolder();
                        viewHolder.namaTB = (TextView) view.findViewById(R.id.viewNamaTB);
                        viewHolder.namaUser = (TextView) view.findViewById(R.id.viewNamaUser);
                        viewHolder.cb = (CheckBox) view.findViewById(R.id.delCb);
                        view.setTag(viewHolder);
                    }
                    else { //--> Kalo udah ada viewnya, holdernya diambil dari tag
                        viewHolder = (ViewHolder) view.getTag();
                    }

                    viewHolder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if(isChecked){
                                if(!deleteList.contains(currentTamanBaca)) {
                                    deleteList.add(currentTamanBaca);
                                }
                            }
                            else {
                                deleteList.remove(currentTamanBaca);
                            }
                        }
                    });

                    viewHolder.namaTB.setText(currentTamanBaca.getNama());
                    viewHolder.namaUser.setText(currentTamanBaca.getNamaUser());
                    ////////////////////////////ubah search/ada yang ditambah disini!!!!!!!!!!!!!!!!!!!!!!!
                    if(deleteList.contains(currentTamanBaca))
                        viewHolder.cb.setChecked(true);
                    if(!deleteList.contains(currentTamanBaca))
                        viewHolder.cb.setChecked(false);

                    view.setTag(viewHolder);

                return view;

            }

            //---------------------------------------//Filter untuk search//----------------------------------------------//
            public void filter(String charText) {
                charText = charText.toLowerCase(Locale.getDefault());
                //Pake list yang di passing ke adapter dari fragment
                mListTamanBaca.clear();
                if (charText.length() == 0) {
                    mListTamanBaca.addAll(arraylist);
                } else {
                    for (TamanBaca tamanBaca : arraylist) {
                        if (tamanBaca.getNama().toLowerCase(Locale.getDefault()).contains(charText)) {
                            mListTamanBaca.add(tamanBaca);
                        }
                    }
                }
                notifyDataSetChanged();
            }
        }

        public void delete() {
            if (deleteList.size() != 0 && deleteList != null) {
                String deleteId = null;
                for (int i = 0; i < deleteList.size(); i++) {
                    if (i == 0) {
                        deleteId = "" + deleteList.get(i).getId_tamanBaca();
                    } else {
                        deleteId = deleteId + "," + deleteList.get(i).getId_tamanBaca();
                    }
                }
                //Log.d("del",deleteId);
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("aksi", "hapus"));
                params.add(new BasicNameValuePair("del_id", deleteId));
                RequestData requestData = new RequestData(
                        "tbdao.php",
                        params,
                        getActivity(),
                        "Menghapus Taman Baca") {
                    @Override
                    protected void onPostExecute(JSONArray data) {
                        pDialog.dismiss();
                        deleteList = new ArrayList<>();
                        try {
                            Toast.makeText(
                                    getActivity(),
                                    data.getJSONObject(0).toString() + 12312,
                                    Toast.LENGTH_SHORT
                            ).show();
                            populateList();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                        try {
                            populateList();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                };
                requestData.execute();
            }
        }

        public void populateList() throws ExecutionException, InterruptedException {
            mListViewTamanBaca = (ListView) rootView.findViewById(R.id.listViewLihatTamanBaca);
            listTamanBaca = new ArrayList<>();

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("tag", "taman_baca"));
            RequestData requestData = new RequestData(
                    "getdata.php",
                    params,
                    getActivity(),
                    "Memuat Taman Baca")
            {
                @Override
                protected void onPostExecute(JSONArray data) {
                    pDialog.dismiss();
                    if(data != null) {
                        JSONArray jsonArray = data;
                        //og.d("cekid", ""+jsonArray);
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
                                listTamanBaca.add(tamanBaca);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    else
                    {
                        Toast.makeText(
                                getActivity(),
                                "Tidak ada taman baca.",
                                Toast.LENGTH_SHORT
                        ).show();
                    }

                    Collections.reverse(listTamanBaca);
                    adapterTamanBaca = new TamanBacaListAdapter(listTamanBaca);
                    mListViewTamanBaca.setAdapter(adapterTamanBaca);

                    mListViewTamanBaca.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                        if (new Connection().checkConnection(getActivity())) {
                            Intent intent = new Intent(rootView.getContext(), DetailTBActivity.class);
                            intent.putExtra("id", listTamanBaca.get(position).getId_tamanBaca());
                            intent.putExtra("mode", 0);
                            startActivity(intent);
                            getActivity().finish();
                        }
                        else{
                            Toast.makeText(
                                    getActivity(),
                                    "Kesalahan : Anda tidak tersambung ke internet.",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                        }
                    });

                    mListViewTamanBaca.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                        @Override
                        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                            final String items[] = {"Lihat Ringkasan Buku", "Lihat Rating Buku", "Lihat Log Harian", "Lihat Log Kegiatan"};
                            final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                            final int pos = position;
                            dialog.setItems(items, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface d, int choice) {
                                    if(choice == 0) {
                                        if (new Connection().checkConnection(getActivity())) {
                                            Intent mainIntent = new Intent(getActivity(), SummaryBukuActivity.class);
                                            mainIntent.putExtra("id", listTamanBaca.get(pos).getId_tamanBaca());
                                            startActivity(mainIntent);
                                            getActivity().finish();
                                        }
                                        else{
                                            Toast.makeText(
                                                    getActivity(),
                                                    "Kesalahan : Anda tidak tersambung ke internet.",
                                                    Toast.LENGTH_SHORT
                                            ).show();
                                        }
                                    }
                                    else if(choice == 1) {
                                        if (new Connection().checkConnection(getActivity())) {
                                            Intent mainIntent = new Intent(getActivity(), RatingBukuActivity.class);
                                            mainIntent.putExtra("id", listTamanBaca.get(pos).getId_tamanBaca());
                                            startActivity(mainIntent);
                                            getActivity().finish();
                                        }
                                        else{
                                            Toast.makeText(
                                                    getActivity(),
                                                    "Kesalahan : Anda tidak tersambung ke internet.",
                                                    Toast.LENGTH_SHORT
                                            ).show();
                                        }
                                    }
                                    else if(choice == 2) {
                                        if (new Connection().checkConnection(getActivity())) {
                                            Intent mainIntent = new Intent(getActivity(), LogHarianActivity.class);
                                            mainIntent.putExtra("id", listTamanBaca.get(pos).getId_tamanBaca());
                                            startActivity(mainIntent);
                                            getActivity().finish();
                                        }
                                        else{
                                            Toast.makeText(
                                                    getActivity(),
                                                    "Kesalahan : Anda tidak tersambung ke internet.",
                                                    Toast.LENGTH_SHORT
                                            ).show();
                                        }
                                    }
                                    else if(choice == 3) {
                                        if (new Connection().checkConnection(getActivity())) {
                                            Intent mainIntent = new Intent(getActivity(), LihatLogKegiatanActivity.class);
                                            mainIntent.putExtra("id", listTamanBaca.get(pos).getId_tamanBaca());
                                            startActivity(mainIntent);
                                            getActivity().finish();
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
                            //dialog.setView(R.layout.dialog_taman_baca);
                            dialog.show();
                            return true;
                        }
                    });

                }
            };

            requestData.execute();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            rootView = inflater.inflate(R.layout.fragment_lihat_taman_baca, container, false);
            deleteList = new ArrayList<TamanBaca>();
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
