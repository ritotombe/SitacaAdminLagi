package com.example.rito.sitaca;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.internal.view.menu.ListMenuPresenter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

  //  DBHelper DB = new DBHelper(getApplicationContext())

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
            new AlertDialog.Builder(this)
                    .setTitle("Konfirmasi")
                    .setMessage("Anda yakin ingin keluar?")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton("Iya", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {
                            logout();
                        }

                    })
                    .setNegativeButton("Tidak", null).show();
            //slideDelete();

            return true;
        }
                return super.onOptionsItemSelected(item);

    }

    public void logout(){
        SharedPreferences pref = getApplicationContext().getSharedPreferences("sitacaadmin", 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }


    //Navigasi ke tiap halaman utama
    public void showPeminjaman(View view){
        if(new Connection().checkConnection(getApplicationContext())) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(
                getApplicationContext(),
                "Kesalahan: Anda tidak tersambung ke internet",
                Toast.LENGTH_SHORT
            ).show();
        }
    }

    public void showLogHarian(View view){
        if(new Connection().checkConnection(getApplicationContext())) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(
                getApplicationContext(),
                "Kesalahan: Anda tidak tersambung ke internet",
                Toast.LENGTH_SHORT
            ).show();
        }
    }
    public void showBuku(View view){
        if(new Connection().checkConnection(getApplicationContext())) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(
                getApplicationContext(),
                "Kesalahan: Anda tidak tersambung ke internet",
                Toast.LENGTH_SHORT
            ).show();
        }
    }
    public void showUser(View view){
        if(new Connection().checkConnection(getApplicationContext())) {
            Intent intent = new Intent(this, UserActivity.class);
            startActivity(intent);
        }
       else{
            Toast.makeText(
                getApplicationContext(),
                "Kesalahan: Anda tidak tersambung ke internet",
                Toast.LENGTH_SHORT
            ).show();
        }
    }
    public void showPertukaranBuku(View view){
        if(new Connection().checkConnection(getApplicationContext())) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(
                getApplicationContext(),
                "Kesalahan: Anda tidak tersambung ke internet",
                Toast.LENGTH_SHORT
            ).show();
        }
    }
    public void showTbSekitar(View view){
        if(new Connection().checkConnection(getApplicationContext())) {
            Intent intent = new Intent(this, LihatTamanBacaActivity.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(
                getApplicationContext(),
                "Kesalahan: Anda tidak tersambung ke internet",
                Toast.LENGTH_SHORT
            ).show();
        }
    }

    public void showPengumuman(View view){
        if(new Connection().checkConnection(getApplicationContext())) {
            Intent intent = new Intent(this, PengumumanActivity.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(
                getApplicationContext(),
                "Kesalahan: Anda tidak tersambung ke internet",
                Toast.LENGTH_SHORT
            ).show();
        }
    }
    public void showAdmin(View view){
        if(new Connection().checkConnection(getApplicationContext())) {
            Intent intent = new Intent (this, AdminActivity.class);
            startActivity(intent);}
        else{
            Toast.makeText(
                getApplicationContext(),
                "Kesalahan: Anda tidak tersambung ke internet",
                Toast.LENGTH_SHORT
            ).show();
        }
    }
}
