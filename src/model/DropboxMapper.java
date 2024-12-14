package model;

import java.util.List;
import org.apache.ibatis.annotations.*;

public interface DropboxMapper {

    // CREATE: Menambahkan data baru ke tabel dropbox
    @Insert("INSERT INTO dropbox (idTps, namaTps, noHpTps, alamatTps) VALUES (#{idTps}, #{namaTps}, #{noHpTps}, #{alamatTps})")
    void insertDropbox(Dropbox dropbox);

    // READ: Mengambil semua data dari tabel dropbox
    @Select("SELECT * FROM dropbox")
    List<Dropbox> getAllDropbox();

    // READ: Mengambil data dropbox berdasarkan ID
    @Select("SELECT * FROM dropbox WHERE idTps = #{idTps}")
    Dropbox getDropboxById(@Param("idTps") int idTps);

    // UPDATE: Memperbarui data dropbox berdasarkan ID
    @Update("UPDATE dropbox SET namaTps = #{namaTps}, noHpTps = #{noHpTps}, alamatTps = #{alamatTps} WHERE idTps = #{idTps}")
    void updateDropbox(Dropbox dropbox);

    // DELETE: Menghapus data dropbox berdasarkan ID
    @Delete("DELETE FROM dropbox WHERE idTps = #{idTps}")
    void deleteDropbox(@Param("idTps") int idTps);
}
