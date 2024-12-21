package controller;

import model.Masyarakat;
import model.MasyarakatMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.List;

public class MasyarakatController {
    private SqlSessionFactory sqlSessionFactory;

    public MasyarakatController() {
        try {
            String resource = "mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (Exception e) {
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
