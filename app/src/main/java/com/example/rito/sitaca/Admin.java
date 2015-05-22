package com.example.rito.sitaca;

/**
 * Created by AYU ARYSTYANA on 11/04/2015.
 */
public class Admin{


    // property help us to keep data
    public int id_admin;
    public String nama;
    public String alamat;
    public String jabatan;
    public String noTelp;
    public String email;
    public String password;
    public int status;


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Admin(int id_admin, String nama, String alamat, String jabatan, String noTelp, String email, String password, int status){
        this.id_admin= id_admin;
        this.nama = nama;
        this.alamat = alamat;
        this.jabatan = jabatan;
        this.noTelp = noTelp;
        this.email = email;
        this.password = password;
        this.status = status;
    }


    public int getId_admin() {
        return id_admin;
    }

    public void setId_admin(int id_admin) {
        this.id_admin = id_admin;
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

