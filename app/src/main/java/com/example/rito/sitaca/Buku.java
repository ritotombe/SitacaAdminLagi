package com.example.rito.sitaca;

/**
 * Created by Asus on 5/12/2015.
 */
public class Buku {

    public static final String TABLE = "Buku";

    public static final String KEY_ID_buku = "id_buku";
    public static final String KEY_ID_kategori = "id_kategori";
    public static final String KEY_judul = "judul";
    public static final String KEY_pengarang = "pengarang";
    public static final String KEY_tahun_terbit = "tahun_terbit";
    public static final String KEY_penerbit = "penerbit";
    public static final String KEY_poin = "poin";
    public static final String KEY_status = "status";
    public static final String KEY_rating = "rating";

    public int id_buku;
    public int id_kategori;
    public String namaKategori;
    public String judul;
    public String pengarang;
    public String tahun_terbit;
    public String penerbit;
    public int poin;
    public String status;
    public int rating;

    public Buku(int id_buku, int id_kategori, String judul, String pengarang, String tahun_terbit, String penerbit, int poin, String status, int rating, String namaKategori ){
        this.id_buku = id_buku;
        this.id_kategori = id_kategori;
        this.judul = judul;
        this.pengarang = pengarang;
        this.tahun_terbit = tahun_terbit;
        this.penerbit = penerbit;
        this.poin = poin;
        this.status = status;
        this.rating = rating;
        this.namaKategori = namaKategori;
    }

    public void setTahun_terbit(String tahun_terbit) {
        this.tahun_terbit = tahun_terbit;
    }

    public String getNamaKategori() {
        return namaKategori;
    }

    public void setNamaKategori(String namaKategori) {
        this.namaKategori = namaKategori;
    }

    public int getId_buku() {
        return id_buku;
    }

    public void setId_buku(int id_buku) {
        this.id_buku = id_buku;
    }

    public int getId_kategori() {
        return id_kategori;
    }

    public void setId_kategori(int id_kategori) {
        this.id_kategori = id_kategori;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getPengarang() {
        return pengarang;
    }

    public void setPengarang(String pengarang) {
        this.pengarang = pengarang;
    }

    public String getTahun_terbit() {
        return tahun_terbit;
    }

    public void setTahun_tanggal(String tahun_terbit) {
        this.tahun_terbit = tahun_terbit;
    }

    public String getPenerbit() {
        return penerbit;
    }

    public void setPenerbit(String penerbit) {
        this.penerbit = penerbit;
    }

    public int getPoin() {
        return poin;
    }

    public void setPoin(int poin) {
        this.poin = poin;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

}
