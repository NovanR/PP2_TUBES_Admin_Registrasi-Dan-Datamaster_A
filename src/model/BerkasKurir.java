package model;

public class BerkasKurir {
    private int idBerkas;
    private String idKtp;
    private String idSim;
    private String status;
    private int idKurir;

    public BerkasKurir(int idBerkas, String idKtp, String idSim, String status, int idKurir) {
        this.idBerkas = idBerkas;
        this.idKtp = idKtp;
        this.idSim = idSim;
        this.status = status;
        this.idKurir = idKurir;
    }

    public int getIdBerkas() {
        return idBerkas;
    }

    public void setIdBerkas(int idBerkas) {
        this.idBerkas = idBerkas;
    }

    public String getIdKtp() {
        return idKtp;
    }

    public void setIdKtp(String idKtp) {
        this.idKtp = idKtp;
    }

    public String getIdSim() {
        return idSim;
    }

    public void setIdSim(String idSim) {
        this.idSim = idSim;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getIdKurir() {
        return idKurir;
    }

    public void setIdKurir(int idKurir) {
        this.idKurir = idKurir;
    }
    
}
