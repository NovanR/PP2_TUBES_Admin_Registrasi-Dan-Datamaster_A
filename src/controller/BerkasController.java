package controller;

import model.BerkasKurir;
import model.BerkasMapper;
import model.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class BerkasController {

    // Insert berkas baru
    public void insertBerkas(BerkasKurir berkas) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            BerkasMapper mapper = session.getMapper(BerkasMapper.class);
            mapper.insertBerkas(berkas);
            session.commit();
        } catch (Exception e) {
            throw new RuntimeException("Gagal menambahkan berkas baru: " + e.getMessage(), e);
        }
    }

    // ambil data berkas
    public List<BerkasKurir> getAllBerkas() {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            BerkasMapper mapper = session.getMapper(BerkasMapper.class);
            return mapper.getAllBerkas();
        } catch (Exception e) {
            throw new RuntimeException("Gagal memuat data berkas: " + e.getMessage(), e);
        }
    }

    // Update data berkas
    public void updateBerkas(BerkasKurir berkas) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            BerkasMapper mapper = session.getMapper(BerkasMapper.class);
            mapper.updateBerkas(berkas);
            session.commit();
        } catch (Exception e) {
            throw new RuntimeException("Gagal memperbarui berkas: " + e.getMessage(), e);
        }
    }

    // Hapus berkas berdasarkan ID
    public void deleteBerkas(int idBerkas) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            BerkasMapper mapper = session.getMapper(BerkasMapper.class);
            mapper.deleteBerkas(idBerkas);
            session.commit();
        } catch (Exception e) {
            throw new RuntimeException("Gagal menghapus berkas dengan ID: " + idBerkas, e);
        }
    }

    // Validasi data berkas (ID KTP & ID SIM)
    public void validateBerkasData(String idKtp, String idSim) {
        if (idKtp == null || idKtp.trim().isEmpty() || !idKtp.matches("\\d{16}")) {
            throw new IllegalArgumentException("ID KTP harus terdiri dari 16 digit angka.");
        }
        if (idSim == null || idSim.trim().isEmpty() || !idSim.matches("\\d{12}")) {
            throw new IllegalArgumentException("ID SIM harus terdiri dari 12 digit angka.");
        }
    }

    // ambil daftar ID Kurir
    public List<Integer> getKurirIds() {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            return session.selectList("getKurirIds");
        } catch (Exception e) {
            throw new RuntimeException("Gagal memuat data kurir: " + e.getMessage(), e);
        }
    }
}
