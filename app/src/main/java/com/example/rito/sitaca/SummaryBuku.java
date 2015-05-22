package com.example.rito.sitaca;

/**
 * Created by Asus on 5/11/2015.
 */
public class SummaryBuku {

    public static final String TABLE = "SummaryBuku";

    public static final String KEY_ID_summaryBuku = "id_summaryBuku";
    public static final String KEY_ID_tamanBaca = "id_tamanBaca";
    public static final String KEY_individu = "individu";
    public static final String KEY_organisasi = "organisasi";
    public static final String KEY_beli_sendiri = "beli_sendiri";
    public static final String KEY_yayasan = "yayasan";
    public static final String KEY_tanggal = "tanggal";

    public int id_summaryBuku;
    public int id_tamanBaca;
    public int individu;
    public int organisasi;
    public int beli_sendiri;
    public int yayasan;
    public String tanggal;

    public SummaryBuku(int id_summaryBuku, int id_tamanBaca, int individu, int organisasi, int beli_sendiri, int yayasan, String tanggal){
        this.id_summaryBuku = id_summaryBuku;
        this.id_tamanBaca = id_tamanBaca;
        this.individu = individu;
        this.organisasi = organisasi;
        this.beli_sendiri = beli_sendiri;
        this.yayasan = yayasan;
        this.tanggal = tanggal;
    }

    public int getId_summaryBuku() {
        return id_summaryBuku;
    }

    public void setId_summaryBuku(int id_summaryBuku) {
        this.id_summaryBuku = id_summaryBuku;
    }

    public int getId_tamanBaca() {
        return id_tamanBaca;
    }

    public void setId_tamanBaca(int id_tamanBaca) {
        this.id_tamanBaca = id_tamanBaca;
    }

    public int getIndividu() {
        return individu;
    }

    public void setIndividu(int individu) {
        this.individu = individu;
    }

    public int getOrganisasi() {
        return organisasi;
    }

    public void setOrganisasi(int organisasi) {
        this.organisasi = organisasi;
    }

    public int getBeli_sendiri() {
        return beli_sendiri;
    }

    public void setBeli_sendiri(int beli_sendiri) {
        this.beli_sendiri = beli_sendiri;
    }

    public int getYayasan() {
        return yayasan;
    }

    public void setYayasan(int yayasan) {
        this.yayasan = yayasan;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

}
