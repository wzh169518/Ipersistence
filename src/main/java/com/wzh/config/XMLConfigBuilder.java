package com.wzh.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.wzh.io.Resources;
import com.wzh.pojo.Configuration;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.List;
import java.util.Properties;



public class XMLConfigBuilder {

    private Configuration configuration;

    /**
     * 无参构造
     */
    public XMLConfigBuilder() {
        this.configuration = new Configuration();
    }


    /**
     * 该方法就是使用dom4j将配置文件进行解析，封装成Configuration对象
     *
     * @param inputStream
     * @return
     */
    public Configuration parseConfig(InputStream inputStream) throws Exception {

        Document document = new SAXReader().read(inputStream);
        //拿到了根对象，也就是<configuration>
        Element rootElement = document.getRootElement();
        //Xpath的表达式//property标识在配置文件的任意位置都能拿到,也就是<property>
        //通过properties文件可以填充Properties类。
        //也可以通过xml文件来填充Properties类。
        List<Element> list = rootElement.selectNodes("//property");
        //Properties类实现了Map接口,类似于简单的Map容器
        Properties properties = new Properties();
        for (Element element : list) {
            String name = element.attributeValue("name");
            String value = element.attributeValue("value");
            properties.setProperty(name, value);
        }
        //解决数据库连接创建，释放频繁的问题，使用连接池技术，这里使用c3p0的技术
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        comboPooledDataSource.setDriverClass(properties.getProperty("driverClass"));
        comboPooledDataSource.setJdbcUrl(properties.getProperty("jdbcUrl"));
        comboPooledDataSource.setUser(properties.getProperty("username"));
        comboPooledDataSource.setPassword(properties.getProperty("password"));

        //可以在这里创建Configurration对象 但是不建议，因为该对象后面还要用，因此在成员变量上生成该对象
        //封装数据源信息
        configuration.setDataSource(comboPooledDataSource);


        //解析mapper.xml文件
        //拿到路径--》字节输入流--》dom4j进行解析
        List<Element> mapperList = rootElement.selectNodes("//mapper");

        for (Element element : mapperList) {
            //mapper路径
            String mapperPath = element.attributeValue("resource");
            InputStream resourceAsSteam = Resources.getResourceAsSteam(mapperPath);
            //将解析好的mapperStatement封装到configuration里面
            XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(configuration);
            //mapperStatement 已经封装好了 所以不用返回值
            xmlMapperBuilder.parse(resourceAsSteam);
        }
        //返回
        return configuration;

    }
}
