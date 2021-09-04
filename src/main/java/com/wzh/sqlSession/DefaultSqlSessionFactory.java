package com.wzh.sqlSession;

import com.wzh.pojo.Configuration;

/**
 * 会话对象工厂接口
 *
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {

    /**
     * 该对象要被当做有参构造一直传到最底层
     */
    private Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration){
        this.configuration = configuration;
    }

    /**
     * 获取会话对象
     *
     * @return
     */
    @Override
    public SqlSession openSession() {
        return new DefaultSqlSession(configuration);
    }
}
