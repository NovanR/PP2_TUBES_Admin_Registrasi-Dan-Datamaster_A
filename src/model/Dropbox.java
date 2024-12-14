package model;

public class Dropbox {
    private String idTps;
    private String namaTps;
    private String noHpTps;
    private String alamatTps;

    public Dropbox(String idTps, String namaTps, String noHpTps, String alamatTps) {
        this.idTps = idTps;
        this.namaTps = namaTps;
        this.noHpTps = noHpTps;
        this.alamatTps = alamatTps;
    }

    public String getIdTps() {
        return idTps;
    }

    public void setIdTps(String idTps) {
        this.idTps = idTps;
    }

    public String getNamaTps() {
        return namaTps;
    }

    public void setNamaTps(String namaTps) {
        this.namaTps = namaTps;
    }

    public String getNoHpTps() {
        return noHpTps;
    }

    public void setNoHpTps(String noHpTps) {
        this.noHpTps = noHpTps;
    }

    public String getAlamatTps() {
        return alamatTps;
    }

    public void setAlamatTps(String alamatTps) {
        this.alamatTps = alamatTps;
    }
    
}
