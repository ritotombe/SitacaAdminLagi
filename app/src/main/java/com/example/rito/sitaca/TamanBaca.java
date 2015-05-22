package com.example.rito.sitaca;

/**
 * Created by AYU ARYSTYANA on 09/05/2015.
 */
public class TamanBaca {
    // Labels table name
    public static final String TABLE = "TamanBaca";

    // Labels Table Columns names
    public static final String KEY_ID_tamanBaca = "id_tamanBaca";
    public static final String KEY_ID_user = "id_user";
    public static final String KEY_nama_user = "namaUser";
    public static final String KEY_nama = "nama";
    public static final String KEY_alamat = "alamat";
    public static final String KEY_twitter = "twitter";
    public static final String KEY_facebook = "facebook";

    // property help us to keep data
    public int id_tamanBaca;
    public int id_user;
    public String namaUser;
    public String nama;
    public String alamat;
    public String twitter;
    public String facebook;

    public TamanBaca(int id_tamanBaca, int id_user, String namaUser, String nama, String alamat, String twitter, String facebook) {
        this.id_tamanBaca = id_tamanBaca;
        this.id_user = id_user;
        this.namaUser = namaUser;
        this.nama = nama;
        this.alamat = alamat;
        this.twitter = twitter;
        this.facebook = facebook;
    }

    public int getId_tamanBaca() {
        return id_tamanBaca;
    }

    public void setId_tamanBaca(int id_tamanBaca) {
        this.id_tamanBaca = id_tamanBaca;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getNamaUser() {
        return namaUser;
    }

    public void setNamaUser(String namaUser) {
        this.namaUser = namaUser;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }
}



