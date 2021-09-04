package com.wzh.sqlSession;

import java.sql.SQLException;
import java.util.List;

/**
 * 会话接口
 *
 * 与数据库进行交互
 *
 */
public interface SqlSession {

    /**
     * 查询所有
     *
     * @param statementId
     * @param <E>
     * @return
     */
    <E> List<E> selectList(String statementId, Object... params) throws SQLException, Exception;

    /**
     * 查询单个
     *
     * @param statementId
     * @param params
     * @param <T>
     * @return
     */
    <T> T selectOne(String statementId, Object... params) throws Exception;

    /**
     * 修改方法
     *
     * @param statementId
     * @param params
     * @return
     */
    Integer update(String statementId, Object... params) throws Exception;

    /**
     * 插入方法
     *
     * @param statementId
     * @param params
     * @return
     */
    Integer insert(String statementId, Object... params) throws Exception;

    /**
     * 删除方法
     *
     * @param statementId
     * @param params
     * @return
     */
    Integer delete(String statementId, Object... params) throws Exception;

    /**
     * 为Dao接口生成代理实现类
     *
     * @param mapperClass 接口的class文件
     * @param <T>
     * @return
     */
    <T> T getMapper(Class<?> mapperClass);
}
