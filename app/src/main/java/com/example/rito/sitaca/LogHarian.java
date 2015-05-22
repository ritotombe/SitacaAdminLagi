package com.example.rito.sitaca;

/**
 * Created by Asus on 5/11/2015.
 */
public class LogHarian {

    // Labels table name
    public static final String TABLE = "LogHarian";

    // Labels Table Columns names
    public static final String KEY_ID_logHarian = "id_logHarian";
    public static final String KEY_ID_tamanBaca = "id_tamanBaca";
    public static final String KEY_tanggal = "tanggal";
    public static final String KEY_jumlah_kehadiran = "jumlah_kehadiran";
    public static final String KEY_realisasi_jamBuka = "realisasi_jamBuka";
    public static final String KEY_realisasi_jamTutup = "realisasi_jamTutup";

    // property help us to keep data
    public int id_logHarian;
    public int id_tamanBaca;
    public String tanggal;
    public int jumlah_kehadiran;
    public String realisasi_jamBuka;
    public String realisasi_jamTutup;

    public LogHarian(int id_logHarian, int id_tamanBaca, String tanggal, int jumlah_kehadiran,
                     String realisasi_jamBuka, String realisasi_jamTutup){
        this.id_logHarian = id_logHarian;
        this.id_tamanBaca = id_tamanBaca;
        this.tanggal = tanggal;
        this.jumlah_kehadiran = jumlah_kehadiran;
        this.realisasi_jamBuka = realisasi_jamBuka;
        this.realisasi_jamTutup = realisasi_jamTutup;
    }

    public int getId_logHarian() {
        return id_logHarian;
    }

    public void setId_logHarian(int id_logHarian) {
        this.id_logHarian = id_logHarian;
    }

    public int getId_tamanBaca() {
        return id_tamanBaca;
    }

    public void setId_tamanBaca(int id_tamanBaca) {
        this.id_tamanBaca = id_tamanBaca;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public int getJumlah_kehadiran() {
        return jumlah_kehadiran;
    }

    public void setJumlah_kehadiran(int jumlah_kehadiran) {
        this.jumlah_kehadiran = jumlah_kehadiran;
    }

    public String getRealisasi_jamBuka() {
        return realisasi_jamBuka;
    }

    public void setRealisasi_jamBuka(String realisasi_jamBuka) {
        this.realisasi_jamBuka = realisasi_jamBuka;
    }

    public String getRealisasi_jamTutup() {
        return realisasi_jamTutup;
    }

    public void setRealisasi_jamTutup(String realisasi_jamTutup) {
        this.realisasi_jamTutup = realisasi_jamTutup;
    }

}
