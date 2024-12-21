package controller;

import java.io.IOException;
import java.io.InputStream;
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
            return mapper.getAllKurir();
        }
    }

    public Kurir getKurirById(int id) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            KurirMapper mapper = session.getMapper(KurirMapper.class);
            return mapper.getKurirById(id);
        }
    }
}
