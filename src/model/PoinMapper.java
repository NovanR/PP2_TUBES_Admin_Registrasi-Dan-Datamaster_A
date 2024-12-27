package model;

import java.util.List;
import org.apache.ibatis.annotations.*;

public interface PoinMapper {

    // CREATE: Menambahkan data baru ke tabel konversi_poin
    @Insert("INSERT INTO konversi_poin (idPoin, poin, berat, idMasyarakat, idSampah, idTps) VALUES (#{idPoin}, #{poin}, #{berat} #{idMasyarakat}, #{idSampah}, #{idTps})")
    void insertPoin(Poin konversi_poin);

    // READ: Mengambil semua data konversi_poin
    @Select("SELECT * FROM konversi_poin")
    List<Poin> getAllPoin();

    // READ: Mengambil data konversi_poin berdasarkan ID
    @Select("SELECT * FROM konversi_poin WHERE idPoin = #{idPoin}")
    Poin getPoinById(@Param("idPoin") int idPoin);

    // UPDATE: Memperbarui data konversi_poin berdasarkan ID
    @Update("UPDATE konversi_poin SET poin = #{poin}, berat = #{berat}, idMasyarakat = #{idMasyarakat}, idSampah = #{idSampah}, idTps = #{idTps} WHERE idPoin = #{idPoin}")
    void updatePoin(Poin konversi_poin);

    // DELETE: Menghapus data konversi_poin berdasarkan ID
    @Delete("DELETE FROM konversi_poin WHERE idPoin = #{idPoin}")
    void deletePoin(@Param("idPoin") int idPoin);

    // NEW: Mengambil daftar ID TPS dari tabel TPS
    @Select("SELECT idPoin FROM konversi_poin")
    List<Integer> getAllPoinIds();
}