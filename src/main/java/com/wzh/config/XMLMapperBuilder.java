package com.wzh.config;

import com.wzh.pojo.Configuration;
import com.wzh.pojo.MapperStatement;
import com.wzh.pojo.SqlCommandType;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;

public class XMLMapperBuilder {

    /**
     * 数据源对象
     */
    private Configuration configurration;

    public XMLMapperBuilder(Configuration configurration) {
        this.configurration = configurration;
    }

    public void parse(InputStream inputStream) throws Exception {
        Document document = new SAXReader().read(inputStream);

        Element rootElement = document.getRootElement();
        for (SqlCommandType sqlCommandType : SqlCommandType.values()) {
            List<Element> list = rootElement.selectNodes("//" + sqlCommandType);
            String namespace = rootElement.attributeValue("namespace");
            for (Element element : list) {
                String id = element.attributeValue("id");
                String resultType = element.attributeValue("resultType");
                String paramterType = element.attributeValue("parameterType");
                //去除空格
                String sqlText = element.getTextTrim();
                MapperStatement mapperStatement = new MapperStatement();
                mapperStatement.setId(id);
                mapperStatement.setResultType(resultType);
                mapperStatement.setParameterType(paramterType);
                mapperStatement.setSql(sqlText);
                //设置标签类型
                mapperStatement.setSqlCommandType(sqlCommandType);
                //key值  也就是statementId  确定MapperStatement的唯一标识
                String key = namespace + "." + id;
                configurration.getMapperStatementMap().put(key, mapperStatement);
            }
        }
    }



}
