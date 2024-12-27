package model;

public class Poin {
    private int idPoin;
    private int poin;
    private double berat;
    private int idMasyarakat;
    private int idSampah;
    private int idTps;

    // Constructor
    public Poin(int idPoin, int poin, double berat, int idMasyarakat, int idSampah, int idTps) {
        this.idPoin = idPoin;
        this.poin = poin;
        this.berat = berat;
        this.idMasyarakat = idMasyarakat;
        this.idSampah = idSampah;
        this.idTps = idTps;
    }
    
    // Getters and Setters
    public int getIdPoin() {
        return idPoin;
    }

    public void setIdPoin(int idPoin) {
        this.idPoin = idPoin;
    }

    public int getPoin() {
        return poin;
    }

    public void setPoin(int poin) {
        this.poin = poin;
    }

    public double getBerat() {
        return berat;
    }

    public void setBerat(double berat) {
        this.berat = berat;
    }

    public int getIdMasyarakat() {
        return idMasyarakat;
    }

    public void setIdMasyarakat(int idMasyarakat) {
        this.idMasyarakat = idMasyarakat;
    }

    public int getIdSampah() {
        return idSampah;
    }

    public void setIdSampah(int idSampah) {
        this.idSampah = idSampah;
    }

    public int getIdTps() {
        return idTps;
    }

    public void setIdTps(int idTps) {
        this.idTps = idTps;
    }
}