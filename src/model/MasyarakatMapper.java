package model;

import java.util.List;
import org.apache.ibatis.annotations.*;

public interface MasyarakatMapper {

    // CREATE: Menambahkan data baru ke tabel masyarakat
    @Insert("INSERT INTO masyarakat (idMasyarakat, namaMasyarakat, jenisKelamin, tanggalLahir, noHP, alamat, image, status) VALUES (#{idMasyarakat}, #{namaMasyarakat}, #{jenisKelamin}, #{tanggalLahir}, #{noHP}, #{alamat}, #{image}, #{status})")
    void insertMasyarakat(Masyarakat masyarakat);

    // READ: Mengambil semua data dari tabel masyarakat
    @Select("SELECT * FROM masyarakat")
    List<Masyarakat> getAllMasyarakat();

    // READ: Mengambil data masyarakat berdasarkan ID
    @Select("SELECT * FROM masyarakat WHERE idMasyarakat = #{idMasyarakat}")
    Masyarakat getMasyarakatById(@Param("idMasyarakat") int idMasyarakat);

    // UPDATE: Memperbarui data masyarakat berdasarkan ID
    @Update("UPDATE masyarakat SET namaMasyarakat = #{namaMasyarakat}, jenisKelamin = #{jenisKelamin}, tanggalLahir = #{tanggalLahir}, noHP = #{noHP}, alamat = #{alamat}, image = #{image}, status = #{status} WHERE idMasyarakat = #{idMasyarakat}")
    void updateMasyarakat(Masyarakat masyarakat);

    // DELETE: Menghapus data masyarakat berdasarkan ID
    @Delete("DELETE FROM masyarakat WHERE idMasyarakat = #{idMasyarakat}")
    void deleteMasyarakat(@Param("idMasyarakat") int idMasyarakat);

    void updateKurir(Kurir kurir);
}