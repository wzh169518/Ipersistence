package com.wzh.dao.impl;

import com.wzh.dao.IUserDao;
import com.wzh.io.Resources;
import com.wzh.pojo.User;
import com.wzh.sqlSession.SqlSession;
import com.wzh.sqlSession.SqlSessionFactory;
import com.wzh.sqlSession.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.util.List;

public class UserDaoImpl implements IUserDao {
    /**
     * 查询所有
     *
     * @return
     */
    @Override
    public List<User> finAll() throws Exception {
        //根据配置文件的路径，调用持久层获取配置文件的字节输入流
        InputStream resourceAsSteam = Resources.getResourceAsSteam("sqlMapConfig.xml");
        //获取会话工厂
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsSteam);
        //通过工厂拿到会话对象
        SqlSession sqlSession = sqlSessionFactory.openSession();
        return sqlSession.selectList("User.selectList");
    }

    /**
     * 条件查询
     *
     * @return
     */
    @Override
    public User findByCondition(User user) throws Exception {
        //根据配置文件的路径，调用持久层获取配置文件的字节输入流
        InputStream resourceAsSteam = Resources.getResourceAsSteam("sqlMapConfig.xml");
        //获取会话工厂
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsSteam);
        //通过工厂拿到会话对象
        SqlSession sqlSession = sqlSessionFactory.openSession();

        return sqlSession.selectOne("User.selectOne", user);
    }

    /**
     * 修改
     *
     * @param user
     * @return
     * @throws Exception
     */
    @Override
    public Integer updateById(User user) throws Exception {
        return null;
    }

    /**
     * 新增
     *
     * @param user
     * @return
     * @throws Exception
     */
    @Override
    public Integer save(User user) throws Exception {
        return null;
    }

    /**
     * 删除
     *
     * @param user
     * @return
     * @throws Exception
     */
    @Override
    public Integer remove(User user) throws Exception {
        return null;
    }
}
