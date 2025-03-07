package model;

import java.util.Date;

public class Kurir {
    private int idKurir;
    private int noKtp;
    private int noSim;
    private int npwp;
    private String namaKurir;
    private String jenisKelamin;
    private Date tanggalLahir;
    private String noHP;
    private String alamat;
    private String image;
    private String status;

    public Kurir(int idKurir, int noKtp, int noSim, int npwp, String namaKurir, String jenisKelamin, Date tanggalLahir,
            String noHP, String alamat,
            String image, String status) {
        this.idKurir = idKurir;
        this.noKtp = noKtp;
        this.noSim = noSim;
        this.npwp = npwp;
        this.namaKurir = namaKurir;
        this.jenisKelamin = jenisKelamin;
        this.tanggalLahir = tanggalLahir;
        this.noHP = noHP;
        this.alamat = alamat;
        this.image = image;
        this.status = status;
    }

    public int getIdKurir() {
        return idKurir;
    }

    public void setIdKurir(int idKurir) {
        this.idKurir = idKurir;
    }

    public int getNoKtp() {
        return noKtp;
    }

    public void setNoKtp(int noKtp) {
        this.noKtp = noKtp;
    }

    public int getNoSim() {
        return noSim;
    }

    public void setNoSim(int noSim) {
        this.noSim = noSim;
    }

    public int getNpwp() {
        return npwp;
    }

    public void setNpwp(int npwp) {
        this.npwp = npwp;
    }

    public String getNamaKurir() {
        return namaKurir;
    }

    public void setNamaKurir(String namaKurir) {
        this.namaKurir = namaKurir;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public Date getTanggalLahir() {
        return tanggalLahir;
    }

    public void setTanggalLahir(Date tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }

    public String getNoHP() {
        return noHP;
    }

    public void setNoHP(String noHP) {
        this.noHP = noHP;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
