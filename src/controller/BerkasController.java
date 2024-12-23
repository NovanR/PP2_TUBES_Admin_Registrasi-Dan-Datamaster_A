package controller;

import model.BerkasKurir;
import model.BerkasMapper;
import model.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class BerkasController {

    public void insertBerkas(BerkasKurir berkas) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            BerkasMapper mapper = session.getMapper(BerkasMapper.class);
            mapper.insertBerkas(berkas);
            session.commit();
        }
    }

    public List<BerkasKurir> getAllBerkas() {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            BerkasMapper mapper = session.getMapper(BerkasMapper.class);
            return mapper.getAllBerkas();
        }
    }

    public void updateBerkas(BerkasKurir berkas) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            BerkasMapper mapper = session.getMapper(BerkasMapper.class);
            mapper.updateBerkas(berkas);
            session.commit();
        }
    }

    public void deleteBerkas(int idBerkas) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            BerkasMapper mapper = session.getMapper(BerkasMapper.class);
            mapper.deleteBerkas(idBerkas);
            session.commit();
        }
    }
}
