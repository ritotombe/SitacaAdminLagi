package com.example.rito.sitaca;

import android.animation.LayoutTransition;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class AdminActivity extends ActionBarActivity {

    PlaceholderFragment fragment = new PlaceholderFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, fragment)
                    .commit();
        }
    }

    protected void onResume()
    {
        super.onResume();
        try {
            fragment.populateList();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user, menu);

        //untuk search
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
                fragment.adapterAdmin.filter(newText);
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

        if (id == R.id.delete) {
            if (fragment.deleteList != null && fragment.deleteList.size() == 0) {
                Toast.makeText(
                        getApplicationContext(),
                        "Peringatan: Belum ada admin yang dipilih",
                        Toast.LENGTH_SHORT
                ).show();
            } else {
                new AlertDialog.Builder(this)
                        .setTitle("Konfirmasi")
                        .setMessage("Apakah " + fragment.deleteList.size() + " admin yang anda pilih ingin dihapus?")
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
        public TextView namaAdmin;
        public ListView mListViewAdmin;
        public AdminListAdapter adapterAdmin;
        public List<Admin> listAdmin;
        public List<Admin> deleteList;
        int id;
        // public String nama;



        public PlaceholderFragment() {
        }

        public void delete() {
            if (deleteList.size() != 0 && deleteList != null) {
                String deleteId = null;
                int cnt = 0;
                for (int i = 0; i < deleteList.size(); i++) {

                    if (deleteId == null) {
                        deleteId = ""+ deleteList.get(i).getId_admin();
                    } else {
                        deleteId = deleteId+","+ deleteList.get(i).getId_admin();
                    }
                    cnt++;

                }
                // Log.d("del",deleteId);
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("aksi", "hapus"));
                params.add(new BasicNameValuePair("del_id", deleteId));
                RequestData requestData = new RequestData(
                        "http://ritotom.be/sitacaapi/userdao.php",
                        params,
                        getActivity(),
                        "Menghapus Admin")
                {
                    @Override
                    protected void onPostExecute(JSONArray data) {
                        pDialog.dismiss();
                        deleteList = new ArrayList<>();
                        if(data == null)
                        {
                            Toast.makeText(
                                    getActivity(),
                                    "Kesalahan : Pilihan tidak dapat dihapus",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                        else {
                            try {
                                Toast.makeText(
                                        getActivity(),
                                        data.get(0).toString(),
                                        Toast.LENGTH_SHORT
                                ).show();
                            } catch (JSONException e) {
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
                    }
                };
                requestData.execute();
            }
        }


        public void populateList() throws ExecutionException, InterruptedException {
            mListViewAdmin = (ListView) rootView.findViewById(R.id.listViewAdmin);
            listAdmin = new ArrayList<>();
            namaAdmin = (TextView) rootView.findViewById(R.id.namaAdmin);
            //Log.d("test",nama);
            namaAdmin.setText("");


            namaAdmin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (new Connection().checkConnection(getActivity())) {
                        Intent intent = new Intent(rootView.getContext(), UbahAdminActivity.class);
                        intent.putExtra("id", id);
                        startActivity(intent);
                        getActivity().finish();
                    } else {
                        Toast.makeText(
                                getActivity(),
                                "Kesalahan: Anda tidak tersambung ke internet",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                }
            });

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("tag", "admin"));
            RequestData requestData = new RequestData(
                    "getdata.php",
                    params,
                    getActivity(),
                    "Memuat Admin") {
                @Override
                protected void onPostExecute(JSONArray data) {
                    pDialog.dismiss();
                    if(data != null) {
                        JSONArray jsonArray = data;
                        Log.d("cekid", "" + jsonArray);
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
                                if(o.getInt("id")!=id)
                                    listAdmin.add(admin);
                                else{
                                    //nama = admin.getNama();
                                    namaAdmin.setText(admin.getNama());
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    else
                    {
                        Toast.makeText(
                                getActivity(),
                                "Tidak Ada Admin",
                                Toast.LENGTH_SHORT
                        ).show();
                    }

                    Collections.reverse(listAdmin);
                    adapterAdmin = new AdminListAdapter(listAdmin);
                    mListViewAdmin.setAdapter(adapterAdmin);
                    mListViewAdmin.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                            if (new Connection().checkConnection(getActivity())) {
                                Intent intent = new Intent(rootView.getContext(), DetilAdminActivity.class);
                                intent.putExtra("id", listAdmin.get(position).getId_admin());
                                startActivity(intent);
                                //getActivity().finish();
                            }
                            else{
                                Toast.makeText(
                                        getActivity(),
                                        "Kesalahan: Anda tidak tersambung ke internet",
                                        Toast.LENGTH_SHORT
                                ).show();
                            }

                        }
                    });



                }
            };

            requestData.execute();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            rootView = inflater.inflate(R.layout.fragment_admin, container, false);
            SharedPreferences pref = getActivity().getSharedPreferences("sitacaadmin", 0);
            id = pref.getInt("id", -1);
            //nama = pref.getString("nama",null);
            deleteList = new ArrayList<Admin>();

            try {
                populateList();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return rootView;
        }

        public class AdminListAdapter extends ArrayAdapter<Admin> {

            public ArrayList<Admin> arraylist;
            public List<Admin> mListAdmin;

            public AdminListAdapter(List<Admin> mListAdmin) {
                super(getActivity(),
                        R.layout.list_item_admin,
                        mListAdmin);
                this.mListAdmin = mListAdmin;
                this.arraylist = new ArrayList<Admin>();
                this.arraylist.addAll(mListAdmin);
            }

            @Override
            public View getView(final int position, View view, ViewGroup parent) {

                ViewHolder viewHolder = null;// Tambahin Holder

                final Admin currentAdmin = mListAdmin.get(position);

                if (view == null) {//---> Kalo View belum ada buat holder baru
                    view = getActivity().getLayoutInflater().inflate(R.layout.list_item_admin, parent, false);
                    viewHolder = new ViewHolder();
                    viewHolder.nama = (TextView) view.findViewById(R.id.viewNamaAdmin);
                    viewHolder.notelp = (TextView) view.findViewById(R.id.viewNoTelp);
                    viewHolder.email = (TextView) view.findViewById(R.id.viewEmail);
                    viewHolder.status = (TextView) view.findViewById(R.id.viewStatus);

                    viewHolder.cb = (CheckBox) view.findViewById(R.id.delCb);
                    view.setTag(viewHolder);
                } else { //--> Kalo udah ada viewnya, holdernya diambil dari tag
                    viewHolder = (ViewHolder) view.getTag();
                }


                viewHolder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            if (!deleteList.contains(currentAdmin))

                                deleteList.add(currentAdmin);
                        } else {
                            deleteList.remove(currentAdmin);
                        }
                    }
                });

                //Simpan Datanya ke Holder
                viewHolder.nama.setText(currentAdmin.getNama());
                viewHolder.notelp.setText(currentAdmin.getNoTelp());
                viewHolder.email.setText(currentAdmin.getEmail());
                int st = currentAdmin.getStatus();
                if(st == 0){
                    viewHolder.status.setText("Belum disetujui");
                }
                else{
                    viewHolder.status.setText("Aktif");
                }
                if (deleteList.contains(currentAdmin))
                    viewHolder.cb.setChecked(true);
                if (!deleteList.contains(currentAdmin))
                    viewHolder.cb.setChecked(false);
                view.setTag(viewHolder);
                return view;
            }

            //---------------------------------------//Filter untuk search//----------------------------------------------//
            public void filter(String charText) {
                charText = charText.toLowerCase(Locale.getDefault());
                mListAdmin.clear();
                if (charText.length() == 0) {
                    mListAdmin.addAll(arraylist);
                } else {
                    for (Admin admin : arraylist) {
                        if (admin.getNama().toLowerCase(Locale.getDefault()).contains(charText)) {
                            mListAdmin.add(admin);
                        }
                    }
                }
                notifyDataSetChanged();
            }

            public class ViewHolder {
                TextView nama;
                TextView notelp;
                TextView email;
                TextView status;
                CheckBox cb;
            }
        }
    }
}
