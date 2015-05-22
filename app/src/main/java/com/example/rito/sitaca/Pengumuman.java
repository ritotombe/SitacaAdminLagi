package com.example.rito.sitaca;

import android.text.format.Time;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by theo on 5/4/2015.
 */
public class Pengumuman {
    // Labels table name
    public static final String TABLE = "Pengumuman";

    // Labels Table Columns names
    public static final String KEY_ID_pengumuman = "id_pengumuman";
    public static final String KEY_ID_admin = "id_admin";
    public static final String KEY_namaAdmin = "namaAdmin";
    public static final String KEY_judul = "nama_judul";
    public static final String KEY_isi = "isi";
    public static final String KEY_waktu = "waktu";


    // property help us to keep data
    public int id_pengumuman;
    public int id_admin;
    public String namaAdmin;
    public String judul;
    public String isi;
    public String waktu;

    public int getId_pengumuman() {
        return id_pengumuman;
    }

    public void setId_pengumuman(int id_pengumuman) {
        this.id_pengumuman = id_pengumuman;
    }

    public int getId_admin() {
        return id_admin;
    }

    public String getNamaAdmin() {
        return namaAdmin;
    }

    public void setNamaAdmin(String namaAdmin) {
        this.namaAdmin = namaAdmin;
    }

    public void setId_admin(int id_admin) {
        this.id_admin = id_admin;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getIsi() {
        return isi;
    }

    public void setIsi(String isi) {
        this.isi = isi;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }

    public Pengumuman(int id_pengumuman, int id_admin, String namaAdmin, String judul, String isi, String waktu) {
        this.id_pengumuman = id_pengumuman;
        this.id_admin = id_admin;
        this.judul = judul;
        this.namaAdmin = namaAdmin;
        this.isi = isi;
        this.waktu = waktu;

    }

}
