package controller;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.*;
import java.util.List;

import model.Masyarakat;
import model.MasyarakatMapper;

public class MasyarakatController {
    private SqlSessionFactory sqlSessionFactory;

    public MasyarakatController() {
        try {
            String resource = "mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Masyarakat> getAllMasyarakat() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            MasyarakatMapper mapper = session.getMapper(MasyarakatMapper.class);
            List<Masyarakat> masyarakatList = mapper.getAllMasyarakat();

            // Format tanggal lahir menjadi yyyy-MM-dd
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            for (Masyarakat masyarakat : masyarakatList) {
                if (masyarakat.getTanggalLahir() != null) {
                    masyarakat.setTanggalLahir(java.sql.Date.valueOf(dateFormat.format(masyarakat.getTanggalLahir())));
                }
            }

            return masyarakatList;
        }
    }

    public Masyarakat getMasyarakatById(int id) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            MasyarakatMapper mapper = session.getMapper(MasyarakatMapper.class);
            return mapper.getMasyarakatById(id);
        }
    }

    public void addMasyarakat(Masyarakat masyarakat) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            MasyarakatMapper mapper = session.getMapper(MasyarakatMapper.class);
            mapper.insertMasyarakat(masyarakat);
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateMasyarakat(Masyarakat masyarakat) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            MasyarakatMapper mapper = session.getMapper(MasyarakatMapper.class);
            mapper.updateMasyarakat(masyarakat);
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteMasyarakat(int id) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            MasyarakatMapper mapper = session.getMapper(MasyarakatMapper.class);
            mapper.deleteMasyarakat(id);
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
