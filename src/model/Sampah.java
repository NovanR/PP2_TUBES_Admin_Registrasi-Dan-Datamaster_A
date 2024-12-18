package model;

public class Sampah {
    private int idSampah;
    private String kategoriSampah;
    private int berat;
    private int poin;
    private int idMasyarakat;
    private int idTps;
    private int idKurir;


    public Sampah(int idSampah, String kategoriSampah, int berat, int poin, int idMasyarakat, int idTps, int idKurir) {

     {

        this.idSampah = idSampah;
        this.kategoriSampah = kategoriSampah;
        this.berat = berat;
        this.poin = poin;
        this.idMasyarakat = idMasyarakat;
        this.idTps = idTps;
        this.idKurir = idKurir;
    }

    public int getIdSampah() {
        return idSampah;
    }

    public void setIdSampah(int idSampah) {
        this.idSampah = idSampah;
    }

    public String getKategoriSampah() {
        return kategoriSampah;
    }

    public void setKategoriSampah(String kategoriSampah) {
        this.kategoriSampah = kategoriSampah;
    }

    public int getBerat() {
        return berat;
    }

    public void setBerat(int berat) {
        this.berat = berat;
    }

    public int getPoin() {
        return poin;
    }

    public void setPoin(int poin) {
        this.poin = poin;
    }

    public int getIdMasyarakat() {
        return idMasyarakat;
    }

    public void setIdMasyarakat(int idMasyarakat) {
        this.idMasyarakat = idMasyarakat;
    }

    public int getIdTps() {
        return idTps;
    }

    public void setIdTps(int idTps) {
        this.idTps = idTps;
    }

    public int getIdKurir() {
        return idKurir;
    }

    public void setIdKurir(int idKurir) {
        this.idKurir = idKurir;
    }
}
