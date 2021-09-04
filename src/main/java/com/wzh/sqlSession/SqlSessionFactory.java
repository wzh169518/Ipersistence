package com.wzh.sqlSession;

/**
 * 会话对象工厂接口
 *
 */
public interface SqlSessionFactory {

    /**
     * 获取会话对象
     *
     * @return
     */
    SqlSession openSession();
}
