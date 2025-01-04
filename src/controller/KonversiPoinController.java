package controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import model.Poin;
import model.PoinMapper;

public class KonversiPoinController {
    private SqlSessionFactory sqlSessionFactory;

    public KonversiPoinController() {
        try {
            String resource = "mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Mendapatkan semua data poin
    public List<Poin> getAllPoin() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            PoinMapper mapper = session.getMapper(PoinMapper.class);
            return mapper.getAllPoin();
        }
    }

    // Menambahkan poin baru
    public void addPoin(Poin poin) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            PoinMapper mapper = session.getMapper(PoinMapper.class);
            mapper.insertPoin(poin);
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Memperbarui poin berdasarkan ID
    public void updatePoin(Poin poin) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            PoinMapper mapper = session.getMapper(PoinMapper.class);
            mapper.updatePoin(poin);
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Menghapus poin berdasarkan ID
    public void deletePoin(int idPoin) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            PoinMapper mapper = session.getMapper(PoinMapper.class);
            mapper.deletePoin(idPoin);
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Mendapatkan data poin berdasarkan ID
    public Poin getPoinById(int idPoin) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            PoinMapper mapper = session.getMapper(PoinMapper.class);
            return mapper.getPoinById(idPoin);
        }
    }

    // Mendapatkan semua ID TPS
    public List<Integer> getAllIdTps() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            PoinMapper mapper = session.getMapper(PoinMapper.class);
            return mapper.getidTps();
        }
    }

    // Mendapatkan semua ID masyarakat
    public List<Integer> getIdMasyarakat() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            PoinMapper mapper = session.getMapper(PoinMapper.class);
            return mapper.getIdMasyarakat();
        }
    }

    // Mendapatkan semua ID kategori sampah
    public List<Integer> getAllIdSampah() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            PoinMapper mapper = session.getMapper(PoinMapper.class);
            return mapper.getIdSampah();
        }
    }
}
