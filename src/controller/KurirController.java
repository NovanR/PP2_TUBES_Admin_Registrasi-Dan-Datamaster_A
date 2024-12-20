package controller;


import model.Kurir;
import model.KurirMapper;


import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.util.List;

public class KurirController {
    private SqlSessionFactory sqlSessionFactory;

    public KurirController() {
        try {
            String resource = "mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (Exception e) {
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
