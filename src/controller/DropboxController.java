package controller;

import model.Dropbox;
import model.DropboxMapper;
import org.apache.ibatis.session.SqlSession;
import model.MyBatisUtil;

import java.util.List;

public class DropboxController {

    // CREATE: Menambahkan Dropbox baru
    public void createDropbox(Dropbox dropbox) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            DropboxMapper mapper = session.getMapper(DropboxMapper.class);
            mapper.insertDropbox(dropbox);
            session.commit();
            System.out.println("Dropbox berhasil ditambahkan.");
        } catch (Exception e) {
            System.err.println("Gagal menambahkan Dropbox: " + e.getMessage());
        }
    }

    // READ: Mendapatkan semua Dropbox
    public List<Dropbox> getAllDropbox() {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            DropboxMapper mapper = session.getMapper(DropboxMapper.class);
            return mapper.getAllDropbox();
        } catch (Exception e) {
            System.err.println("Gagal mendapatkan daftar Dropbox: " + e.getMessage());
            return null;
        }
    }

    // READ: Mendapatkan Dropbox berdasarkan ID
    public Dropbox getDropboxById(String idTps) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            DropboxMapper mapper = session.getMapper(DropboxMapper.class);
            return mapper.getDropboxById(Integer.parseInt(idTps));
        } catch (Exception e) {
            System.err.println("Gagal mendapatkan Dropbox dengan ID: " + idTps + ". Error: " + e.getMessage());
            return null;
        }
    }

    // UPDATE: Memperbarui Dropbox berdasarkan ID
    public void updateDropbox(Dropbox dropbox) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            DropboxMapper mapper = session.getMapper(DropboxMapper.class);
            mapper.updateDropbox(dropbox);
            session.commit();
            System.out.println("Dropbox berhasil diperbarui.");
        } catch (Exception e) {
            System.err.println("Gagal memperbarui Dropbox: " + e.getMessage());
        }
    }

    // DELETE: Menghapus Dropbox berdasarkan ID
    public void deleteDropbox(String idTps) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            DropboxMapper mapper = session.getMapper(DropboxMapper.class);
            mapper.deleteDropbox(Integer.parseInt(idTps));
            session.commit();
            System.out.println("Dropbox berhasil dihapus.");
        } catch (Exception e) {
            System.err.println("Gagal menghapus Dropbox dengan ID: " + idTps + ". Error: " + e.getMessage());
        }
    }
}
