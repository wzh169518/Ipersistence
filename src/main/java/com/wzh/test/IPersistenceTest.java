package com.wzh.test;

import com.wzh.dao.IUserDao;
import com.wzh.io.Resources;
import com.wzh.pojo.User;
import com.wzh.sqlSession.SqlSession;
import com.wzh.sqlSession.SqlSessionFactory;
import com.wzh.sqlSession.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

/**
 * 调用自定义持久层框架
 *
 */
public class IPersistenceTest {

    @Test
    public void test() throws Exception {
        //根据配置文件的路径，调用持久层获取配置文件的字节输入流
        InputStream resourceAsSteam = Resources.getResourceAsSteam("sqlMapConfig.xml");
        //获取会话工厂
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsSteam);
        //通过工厂拿到会话对象
        SqlSession sqlSession = sqlSessionFactory.openSession();

        User user = new User();
        user.setId(2);
        user.setUsername("tom");



        //返回的代理对象
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);
//        List<User> users = userDao.finAll();
//        for (User user1 : users) {
//            System.out.println(user1);
//        }
//        //代理对象调用任意接口的时候，执行的都是invoke方法
////        List<User> users = userDao.finAll();
        Integer integer = userDao.remove(user);

        System.out.println(integer);

    }
}
