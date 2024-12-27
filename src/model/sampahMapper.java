package model;

import java.util.List;
import org.apache.ibatis.annotations.*;

public interface sampahMapper {

    // CREATE: Menambahkan data baru ke tabel sampah
    @Insert("INSERT INTO sampah (idSampah, kategoriSampah, jenisSampah, idTps) VALUES (#{idSampah}, #{kategoriSampah}, #{jenisSampah}, #{idTps})")
    void insertSampah(Sampah sampah);

    // READ: Mengambil semua data sampah
    @Select("SELECT idSampah, kategoriSampah, jenisSampah, idTps FROM sampah")
    List<Sampah> getAllSampah();

    // READ: Mengambil data sampah berdasarkan ID
    @Select("SELECT * FROM sampah WHERE idSampah = #{idSampah}")
    Sampah getSampahById(@Param("idSampah") int idSampah);

    // UPDATE: Memperbarui data sampah berdasarkan ID
    @Update("UPDATE sampah SET kategoriSampah = #{kategoriSampah}, jenisSampah = #{jenisSampah}, idTps = #{idTps} WHERE idSampah = #{idSampah}")
    void updateSampah(Sampah sampah);

    // DELETE: Menghapus data sampah berdasarkan ID
    @Delete("DELETE FROM sampah WHERE idSampah = #{idSampah}")
    void deleteSampah(@Param("idSampah") int idSampah);

    // NEW: Mengambil daftar ID TPS dari tabel TPS
    @Select("SELECT idTps FROM dropbox")
    List<Integer> getAllTpsIds();
}
