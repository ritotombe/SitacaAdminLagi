package com.example.rito.sitaca;

/**
 * Created by AYU ARYSTYANA on 11/04/2015.
 */
public class User {
    // Labels table name
    public static final String TABLE = "User";

    // Labels Table Columns names
    public static final String KEY_ID_user = "id_user";
    public static final String KEY_nama = "nama";
    public static final String KEY_alamat = "alamat";
    public static final String KEY_jabatan = "jabatan";
    public static final String KEY_noTelp = "noTelp";
    public static final String KEY_email = "email";
    public static final String KEY_password = "password";

    // property help us to keep data
    public int id_user;
    public String nama;
    public String alamat;
    public String jabatan;
    public String noTelp;
    public String email;
    public String password;
    public int status;
    public int id_tb;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public User(int id_user, String nama, String alamat, String jabatan, String noTelp, String email, String password, int status, int id_tb){
        this.id_user = id_user;
        this.nama = nama;
        this.alamat = alamat;
        this.id_tb = id_tb;
        this.jabatan = jabatan;
        this.noTelp = noTelp;
        this.email = email;
        this.password = password;
        this.status = status;
    }

    public int getId_tb() {
        return id_tb;
    }

    public void setId_tb(int id_tb) {
        this.id_tb = id_tb;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
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

    public String getJabatan() {
        return jabatan;
    }

    public void setJabatan(String jabatan) {
        this.jabatan = jabatan;
    }

    public String getNoTelp() {
        return noTelp;
    }

    public void setNoTelp(String noTelp) {
        this.noTelp = noTelp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

