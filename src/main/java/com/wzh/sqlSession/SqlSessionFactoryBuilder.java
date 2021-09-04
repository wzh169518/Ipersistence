package com.wzh.sqlSession;

import com.wzh.config.XMLConfigBuilder;
import com.wzh.pojo.Configuration;
import org.dom4j.DocumentException;

import java.io.InputStream;

/**
 * 解析配置文件，获取配置信息
 */
public class SqlSessionFactoryBuilder {

    public SqlSessionFactory build(InputStream in) throws Exception {

        //第一：使用dom4j解析配置，将解析出来的内容封装到Configuration对象当中
        //解析过程可以直接写在这下面，也可以创建一个对象，使用dom4j进行解析，进行更深层次的封装
        XMLConfigBuilder xmlConfigBuilder = new XMLConfigBuilder();
        //数据源信息  mapper的相关信息都已经封装好了
        Configuration configuration = xmlConfigBuilder.parseConfig(in);

        //第二：创建sqlSessionFactory对象：工厂类：生产sqlSession会话对象，
        //与数据库交互封装在sqlSession里面
        SqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(configuration);
        return sqlSessionFactory;
    }
}
