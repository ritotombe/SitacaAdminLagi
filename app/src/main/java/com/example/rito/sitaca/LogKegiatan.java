package com.example.rito.sitaca;

/**
 * Created by AYU ARYSTYANA on 13/05/2015.
 */
public class LogKegiatan {
    // Labels table name
    public static final String TABLE = "Kegiatan";

    // Labels Table Columns names
    public static final String KEY_ID_kegiatan = "id_kegiatan";
    public static final String KEY_ID_tamanBaca = "id_tamanBaca";
    public static final String KEY_nama = "nama";
    public static final String KEY_tanggal = "tanggal";
    public static final String KEY_deskripsi = "deskripsi";

    // property help us to keep data
    public int id_kegiatan;
    public int id_tamanBaca;
    public String nama;
    public String tanggal;
    public String deskripsi;

    public LogKegiatan(int id_kegiatan, int id_tamanBaca, String nama, String tanggal, String deskripsi){
        this.id_kegiatan = id_kegiatan;
        this.id_tamanBaca = id_tamanBaca;
        this.nama = nama;
        this.tanggal = tanggal;
        this.deskripsi = deskripsi;
    }

    public int getId_kegiatan() {
        return id_kegiatan;
    }

    public void setId_kegiatan(int id_kegiatan) {
        this.id_kegiatan = id_kegiatan;
    }

    public int getId_tamanBaca() {
        return id_tamanBaca;
    }

    public void setId_tamanBaca(int id_tamanBaca) {
        this.id_tamanBaca = id_tamanBaca;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }
}

