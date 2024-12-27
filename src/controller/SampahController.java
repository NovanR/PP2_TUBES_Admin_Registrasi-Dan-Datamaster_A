package controller;

import model.Sampah;
import model.sampahMapper;
import model.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class SampahController {

    public List<Sampah> getAllSampah() {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            sampahMapper mapper = session.getMapper(sampahMapper.class);
            return mapper.getAllSampah();
        }
    }

    public void insertSampah(Sampah sampah) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            sampahMapper mapper = session.getMapper(sampahMapper.class);
            mapper.insertSampah(sampah);
            session.commit();
        }
    }

    public void updateSampah(Sampah sampah) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            sampahMapper mapper = session.getMapper(sampahMapper.class);
            mapper.updateSampah(sampah);
            session.commit();
        }
    }

    public void deleteSampah(int idSampah) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            sampahMapper mapper = session.getMapper(sampahMapper.class);
            mapper.deleteSampah(idSampah);
            session.commit();
        }
    }

    public List<Integer> getAllTpsIds() {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            sampahMapper mapper = session.getMapper(sampahMapper.class);
            return mapper.getAllTpsIds();
        }
    }
}
