package model;

import java.util.List;
import org.apache.ibatis.annotations.*;

public interface sampahMapper {

    // CREATE: Menambahkan data baru ke tabel sampah
    @Insert("INSERT INTO sampah (idSampah, kategoriSampah, berat, poin, idMasyarakat, idTps, idKurir) VALUES (#{idSampah}, #{kategoriSampah}, #{berat}, #{poin}, #{idMasyarakat}, #{idTps}, #{idKurir})")
    void insertSampah(Sampah sampah);

    // READ: Mengambil semua data dari tabel sampah
    @Select("SELECT * FROM sampah")
    List<Sampah> getAllSampah();

    // READ: Mengambil data sampah berdasarkan ID
    @Select("SELECT * FROM sampah WHERE idSampah = #{idSampah}")
    Sampah getSampahById(@Param("idSampah") String idSampah);

    // UPDATE: Memperbarui data sampah berdasarkan ID
    @Update("UPDATE sampah SET kategoriSampah = #{kategoriSampah}, berat = #{berat}, poin = #{poin}, idMasyarakat = #{idMasyarakat}, idTps = #{idTps}, idKurir = #{idKurir} WHERE idSampah = #{idSampah}")
    void updateSampah(Sampah sampah);

    // DELETE: Menghapus data sampah berdasarkan ID
    @Delete("DELETE FROM sampah WHERE idSampah = #{idSampah}")
    void deleteSampah(@Param("idSampah") String idSampah);
}