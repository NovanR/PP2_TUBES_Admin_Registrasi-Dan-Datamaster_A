package model;

public class Sampah {
    private String idSampah;
    private String kategoriSampah;
    private int berat;
    private int poin;
    private String idMasyarakat;
    private String idTps;
    private String idKurir;

    public Sampah(String idSampah, String kategoriSampah, int berat, int poin, String idMasyarakat, String idTps, String idKurir) {
        this.idSampah = idSampah;
        this.kategoriSampah = kategoriSampah;
        this.berat = berat;
        this.poin = poin;
        this.idMasyarakat = idMasyarakat;
        this.idTps = idTps;
        this.idKurir = idKurir;
    }

    public String getIdSampah() {
        return idSampah;
    }

    public void setIdSampah(String idSampah) {
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

    public String getIdMasyarakat() {
        return idMasyarakat;
    }

    public void setIdMasyarakat(String idMasyarakat) {
        this.idMasyarakat = idMasyarakat;
    }

    public String getIdTps() {
        return idTps;
    }

    public void setIdTps(String idTps) {
        this.idTps = idTps;
    }

    public String getIdKurir() {
        return idKurir;
    }

    public void setIdKurir(String idKurir) {
        this.idKurir = idKurir;
    }
    
}
