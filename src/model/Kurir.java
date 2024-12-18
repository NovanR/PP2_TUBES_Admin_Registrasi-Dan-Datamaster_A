package model;

import java.util.Date;

public class Kurir {
    private int idKurir;
    private String namaKurir;
    private String jenisKelamin;
    private Date tanggalLahir;
    private String noHP;
    private String alamat;
    private String image;
    public enum Status {
        DISETUJUI, DITOLAK, PENDING
    }
    private Status status;

    public Kurir(int idKurir, String namaKurir, String jenisKelamin, Date tanggalLahir, String noHP, String alamat, String image, Status status) {
        this.idKurir = idKurir;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    
}
