package com.example.rito.sitaca;

import android.animation.LayoutTransition;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class UserActivity extends ActionBarActivity {

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
                fragment.adapterUser.filter(newText);
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.delete) {
            if (fragment.deleteList != null && fragment.deleteList.size() == 0) {
                Toast.makeText(
                        getApplicationContext(),
                        "Peringatan : Belum ada pengguna yang dipilih.",
                        Toast.LENGTH_SHORT
                ).show();
            } else {
                new AlertDialog.Builder(this)
                        .setTitle("Konfirmasi")
                        .setMessage("Apakah " + fragment.deleteList.size() + " pengguna yang anda pilih ingin dihapus?")
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
        public ListView mListViewUser;
        public UserListAdapter adapterUser;
        public List<User> listUser;
        public List<User> deleteList;


        public PlaceholderFragment() {
        }

        public void delete() {
            if (deleteList.size() != 0 && deleteList != null) {
                String deleteId = null;
                int cnt = 0;
                for (int i = 0; i < deleteList.size(); i++) {
                    if(deleteList.get(i).getId_tb() == -1){
                    if (deleteId == null) {
                        deleteId = ""+ deleteList.get(i).getId_user();
                    } else {
                        deleteId = deleteId+","+ deleteList.get(i).getId_user();
                    }
                        cnt++;
                    }
                    else
                    {
                        Toast.makeText(
                                getActivity(),
                                "Kesalahan : "+ deleteList.get(i).getNama() +" tidak dapat dihapus karena sedang mengelola taman baca.",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                }
                  // Log.d("del",deleteId);
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("aksi", "hapus"));
                params.add(new BasicNameValuePair("del_id", deleteId));
                RequestData requestData = new RequestData(
                        "userdao.php",
                        params,
                        getActivity(),
                        "Menghapus Pengguna")
                {
                    @Override
                    protected void onPostExecute(JSONArray data) {
                        pDialog.dismiss();
                        deleteList = new ArrayList<>();
                        if(data == null)
                        {
                            Toast.makeText(
                                    getActivity(),
                                    "Kesalahan : Pilihan tidak dapat dihapus.",
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
            mListViewUser = (ListView) rootView.findViewById(R.id.listViewUser);
            listUser = new ArrayList<>();

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("tag", "user"));
            RequestData requestData = new RequestData(
                    "getdata.php",
                    params,
                    getActivity(),
                    "Memuat Pengguna") {
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
                                User user = new User(
                                        o.getInt("id"),
                                        o.getString("nama"),
                                        o.getString("alamat"),
                                        o.getString("jabatan"),
                                        o.getString("notelp"),
                                        o.getString("email"),
                                        o.getString("password"),
                                        o.getInt("status"),
                                        o.getInt("id_tb")
                                );
                                listUser.add(user);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    else
                    {
                        Toast.makeText(
                                getActivity(),
                                "Tidak ada pengguna.",
                                Toast.LENGTH_SHORT
                        ).show();
                    }

                    Collections.reverse(listUser);
                    adapterUser = new UserListAdapter(listUser);
                    mListViewUser.setAdapter(adapterUser);
                    mListViewUser.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                            if(new Connection().checkConnection(getActivity())) {
                                Intent intent = new Intent(rootView.getContext(), TerimaUserActivity.class);
                                intent.putExtra("id", listUser.get(position).getId_user());
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


                }
            };

            requestData.execute();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            rootView = inflater.inflate(R.layout.fragment_user, container, false);
            deleteList = new ArrayList<User>();

            try {
                populateList();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return rootView;
        }

        public class UserListAdapter extends ArrayAdapter<User> {

            public ArrayList<User> arraylist;
            public List<User> mListUser;

            public UserListAdapter(List<User> mListUser) {
                super(getActivity(),
                        R.layout.list_item_user,
                        mListUser);
                this.mListUser = mListUser;
                this.arraylist = new ArrayList<User>();
                this.arraylist.addAll(mListUser);
            }

            @Override
            public View getView(final int position, View view, ViewGroup parent) {

                ViewHolder viewHolder = null;// Tambahin Holder

                final User currentUser = mListUser.get(position);

                if (view == null) {//---> Kalo View belum ada buat holder baru
                    view = getActivity().getLayoutInflater().inflate(R.layout.list_item_user, parent, false);
                    viewHolder = new ViewHolder();
                    viewHolder.nama = (TextView) view.findViewById(R.id.viewNamaUser);
                    viewHolder.notelp = (TextView) view.findViewById(R.id.viewNoTelp);
                    viewHolder.email = (TextView) view.findViewById(R.id.viewEmail);
                    viewHolder.cb = (CheckBox) view.findViewById(R.id.delCb);
                    view.setTag(viewHolder);
                } else { //--> Kalo udah ada viewnya, holdernya diambil dari tag
                    viewHolder = (ViewHolder) view.getTag();
                }


                viewHolder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            if (!deleteList.contains(currentUser))

                                deleteList.add(currentUser);
                        } else {
                            deleteList.remove(currentUser);
                        }
                    }
                });

                //Simpan Datanya ke Holder
                viewHolder.nama.setText(currentUser.getNama());
                viewHolder.notelp.setText(currentUser.getNoTelp());
                viewHolder.email.setText(currentUser.getEmail());
                if (deleteList.contains(currentUser))
                    viewHolder.cb.setChecked(true);
                if (!deleteList.contains(currentUser))
                    viewHolder.cb.setChecked(false);
                view.setTag(viewHolder);
                return view;
            }

            //---------------------------------------//Filter untuk search//----------------------------------------------//
            public void filter(String charText) {
                charText = charText.toLowerCase(Locale.getDefault());
                mListUser.clear();
                if (charText.length() == 0) {
                    mListUser.addAll(arraylist);
                } else {
                    for (User user : arraylist) {
                        if (user.getNama().toLowerCase(Locale.getDefault()).contains(charText)) {
                            mListUser.add(user);
                        }
                    }
                }
                notifyDataSetChanged();
            }

            public class ViewHolder {
                TextView nama;
                TextView notelp;
                TextView email;
                CheckBox cb;
            }
        }
    }
}
