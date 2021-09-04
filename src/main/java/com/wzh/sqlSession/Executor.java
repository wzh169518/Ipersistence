package com.wzh.sqlSession;

import com.wzh.pojo.Configuration;
import com.wzh.pojo.MapperStatement;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * JDBC的底层执行器
 */
public interface Executor {

    /**
     *
     *
     * @param configuration 里面封装了数据库配置信息以及sql配置信息
     * @param <E>
     * @return
     */
    <E> List<E> query(Configuration configuration, MapperStatement mapperStatement, Object... params) throws SQLException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException, Exception;
}
