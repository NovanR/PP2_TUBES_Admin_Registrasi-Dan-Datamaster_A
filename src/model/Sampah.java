// Model
package model;

public class Sampah {
    private int idSampah;
    private String kategoriSampah;
    private String jenisSampah;
    private int idTps;

    // Constructor
    public Sampah(int idSampah, String kategoriSampah, String jenisSampah, int idTps) {
        this.idSampah = idSampah;
        this.kategoriSampah = kategoriSampah;
        this.jenisSampah = jenisSampah;
        this.idTps = idTps;
    }

    // Getters and Setters
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

    public String getJenisSampah() {
        return jenisSampah;
    }

    public void setJenisSampah(String jenisSampah) {
        this.jenisSampah = jenisSampah;
    }

    public int getIdTps() {
        return idTps;
    }

    public void setIdTps(int idTps) {
        this.idTps = idTps;
    }
}