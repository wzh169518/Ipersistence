package com.wzh.pojo;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据库配置文件解析出来的数据，封装成对象
 *
 */
public class Configuration {

    /**
     * 通过获取解析到的数据源配置信息获取到数据源配置对象
     */
    private DataSource dataSource;

    /**
     * key值就是mapper.xml的命名空间和mapperid  nameSpace.id 来组成 也就是statementId
     * value：就是封装好的mapperStatement对象
     *
     */
    Map<String, MapperStatement> mapperStatementMap = new HashMap<>();

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Map<String, MapperStatement> getMapperStatementMap() {
        return mapperStatementMap;
    }

    public void setMapperStatementMap(Map<String, MapperStatement> mapperStatementMap) {
        this.mapperStatementMap = mapperStatementMap;
    }
}
