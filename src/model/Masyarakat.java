package model;

import java.util.Date;

public class Masyarakat {
    private int idMasyarakat;
    private String namaMasyarakat;
    private String jenisKelamin;
    private Date tanggalLahir;
    private String noHP;
    private String alamat;
    private String image;
    public enum Status {
        DISETUJUI, DITOLAK, PENDING
    }
    private Status status;

    public Masyarakat(int idMasyarakat, String namaMasyarakat, String jenisKelamin, Date tanggalLahir, String noHP, String alamat, String image, Status status) {
        this.idMasyarakat = idMasyarakat;
        this.namaMasyarakat = namaMasyarakat;
        this.jenisKelamin = jenisKelamin;
        this.tanggalLahir = tanggalLahir;
        this.noHP = noHP;
        this.alamat = alamat;
        this.image = image;
        this.status = status;
    }

    public int getIdMasyarakat() {
        return idMasyarakat;
    }

    public void setIdMasyarakat(int idMasyarakat) {
        this.idMasyarakat = idMasyarakat;
    }

    public String getNamaMasyarakat() {
        return namaMasyarakat;
    }

    public void setNamaMasyarakat(String namaMasyarakat) {
        this.namaMasyarakat = namaMasyarakat;
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
