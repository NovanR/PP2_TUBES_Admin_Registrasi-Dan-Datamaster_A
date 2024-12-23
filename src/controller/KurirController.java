package controller;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.*;
import java.util.List;

import model.Kurir;
import model.KurirMapper;

public class KurirController {
    private SqlSessionFactory sqlSessionFactory;

    public KurirController() {
        try {
            String resource = "mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Kurir> getAllKurir() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            KurirMapper mapper = session.getMapper(KurirMapper.class);
            List<Kurir> kurirList = mapper.getAllKurir();

            // Format tanggal lahir menjadi yyyy-MM-dd
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            for (Kurir kurir : kurirList) {
                if (kurir.getTanggalLahir() != null) {
                    kurir.setTanggalLahir(java.sql.Date.valueOf(dateFormat.format(kurir.getTanggalLahir())));
                }
            }
            return kurirList;
        }
    }

    public Kurir getKurirById(int id) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            KurirMapper mapper = session.getMapper(KurirMapper.class);
            return mapper.getKurirById(id);
        }
    }

    public void addKurir(Kurir kurir) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            KurirMapper mapper = session.getMapper(KurirMapper.class);
            mapper.insertKurir(kurir);
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateKurir(Kurir kurir) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            KurirMapper mapper = session.getMapper(KurirMapper.class);
            mapper.updateKurir(kurir);
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteKurir(int id) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            KurirMapper mapper = session.getMapper(KurirMapper.class);
            mapper.deleteKurir(id);
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
