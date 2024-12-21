package model;

import java.util.List;
import org.apache.ibatis.annotations.*;

public interface BerkasMapper {

    // CREATE: Menambahkan data baru ke tabel berkas
    @Insert("INSERT INTO berkas (idBerkas, idKtp, idSim, status, idKurir) VALUES (#{idBerkas}, #{idKtp}, #{idSim}, #{status}, #{idKurir})")
    void insertBerkas(BerkasKurir berkas);

    // READ: Mengambil semua data dari tabel berkas
    @Select("SELECT * FROM berkas")
    List<BerkasKurir> getAllBerkas();

    // READ: Mengambil data berkas berdasarkan ID
    @Select("SELECT * FROM berkas WHERE idBerkas = #{idBerkas}")
    BerkasKurir getBerkasById(@Param("idBerkas") int idBerkas);

    // UPDATE: Memperbarui data berkas berdasarkan ID
    @Update("UPDATE berkas SET idKtp = #{idKtp}, idSim = #{idSim}, status = #{status}, idKurir = #{idKurir} WHERE idBerkas = #{idBerkas}")
    void updateBerkas(BerkasKurir berkas);

    // DELETE: Menghapus data berkas berdasarkan ID
    @Delete("DELETE FROM berkas WHERE idBerkas = #{idBerkas}")
    void deleteBerkas(@Param("idBerkas") int idBerkas);
}