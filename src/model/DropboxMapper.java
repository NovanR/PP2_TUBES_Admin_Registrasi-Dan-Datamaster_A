package model;

import java.util.List;
import org.apache.ibatis.annotations.*;

public interface DropboxMapper {
    @Select("SELECT * FROM dropbox")
    List<Dropbox> getAllDropbox();

    @Insert("INSERT INTO dropbox (idTps, namaTps, noHpTps, alamatTps) VALUES (#{idTps}, #{namaTps}), #{noHpTps}), #{alamatTps})")
    void insertDropbox(Dropbox dropbox);
   
}
