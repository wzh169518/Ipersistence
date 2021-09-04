package com.wzh.sqlSession;

import com.wzh.config.BoundSql;
import com.wzh.pojo.Configuration;
import com.wzh.pojo.MapperStatement;
import com.wzh.utils.GenericTokenParser;
import com.wzh.utils.ParameterMapping;
import com.wzh.utils.ParameterMappingTokenHandler;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 执行器实现类
 *
 */
public class SimpleExecutor implements Executor {


    /**
     * 执行jdbc的操作
     *
     * @param configuration 里面封装了数据库配置信息以及sql配置信息
     * @param mapperStatement
     * @param params
     * @param <E>
     * @return
     * @throws Exception
     */
    @Override
    public <E> List<E> query(Configuration configuration, MapperStatement mapperStatement, Object... params) throws Exception {
        //1,注册驱动，获取连接
        Connection connection = configuration.getDataSource().getConnection();
        //2，获取sql语句:select * from user where id = #{id} and username = #{name}
            //转换sql语句  因为#{}占位符 jdbc识别不了 因此要转换为
            //select * from user where id = ? and username = ?
            //还要对#{}里面的值进行解析存储
        String sql = mapperStatement.getSql();
        BoundSql boundSql = getBoundSql(sql);
        //3,获取预处理对象：preparedStatement
        PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSqlText());
        //4,设置参数
            //参数的全路径
        String parameterType = mapperStatement.getParameterType();
            //?表示不确定
        Class<?> parameterClass = getClassType(parameterType);
        List<ParameterMapping> parameterMappingList = boundSql.getParameterMappingList();
        for (int i = 0; i < parameterMappingList.size(); i++) {
            ParameterMapping parameterMapping = parameterMappingList.get(i);
            //sql#{} 里面的属性名称
            String content = parameterMapping.getContent();

            //使用反射获取实体对象中的属性值，
            Field declaredField = parameterClass.getDeclaredField(content);
            //暴力获取
            declaredField.setAccessible(true);
            //目前只有一个参数对象，这里就第一个参数
            Object o = declaredField.get(params[0]);
            //参数设置
            preparedStatement.setObject(i+1, o);
        }
        //5，执行sql
        ResultSet resultSet = preparedStatement.executeQuery();

            //返回数据集合
        ArrayList<Object> objects = new ArrayList<>();

        //6,封装结果集
            //返回值的全路径
        String resultType = mapperStatement.getResultType();
        Class<?> resultTypeClass = getClassType(resultType);
        //查询到的数据行数：也就是查询了多少条
        while (resultSet.next()){
            //返回值对象:这里不能放到外面去，不然list集合添加的对象都指向同一个地址，数据是最后一行的数据
            Object o = resultTypeClass.newInstance();
            //取出元数据 也就是字段名称
            ResultSetMetaData metaData = resultSet.getMetaData();
            //列的个数: 也是select *  的字段的个数
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                //字段名
                String columnName = metaData.getColumnName(i);
                //字段值
                Object object = resultSet.getObject(columnName);

                //使用反射或者内省根据数据库表和实体的对应关系，完成封装
                    //内省库中的类
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(columnName, resultTypeClass);
                    //拿到写方法
                Method writeMethod = propertyDescriptor.getWriteMethod();
                    //将值封装到这个对象里面了
                writeMethod.invoke(o, object);

            }
            //添加到返回集合当中
            objects.add(o);
        }

        return (List<E>)objects;
    }

    /**
     * 根据全路径获取参数Class的对象
     *
     * @param parameterType
     * @return
     */
    private Class<?> getClassType(String parameterType) throws ClassNotFoundException {
        if (null != parameterType && !"".equals(parameterType)){
            Class<?> aClass = Class.forName(parameterType);
            return aClass;
        }
        return null;
    }

    /**
     * 完成对#{}的解析工作：
     * 1，将#{}用？代替
     * 2，解析出来的#{}里面的值进行存储
     *
     * @param sql
     * @return
     */
    private BoundSql getBoundSql(String sql) {
        //标记处理类 ：配置标记解析器来完成对占位符的解析处理工作
        ParameterMappingTokenHandler parameterMappingTokenHandler = new ParameterMappingTokenHandler();
        GenericTokenParser genericTokenParser = new GenericTokenParser("#{", "}", parameterMappingTokenHandler);
        //解析出来的sql
        String sqlText = genericTokenParser.parse(sql);
        //#{}里面解析数来的参数名称
        List<ParameterMapping> parameterMappings = parameterMappingTokenHandler.getParameterMappings();
        //封装成对象
        BoundSql boundSql = new BoundSql(sqlText, parameterMappings);
        return boundSql;
    }

    /**
     * update方法 执行jdbc操作
     *
     * @param configuration
     * @param mapperStatement
     * @param params
     * @return
     */
    public Integer update(Configuration configuration, MapperStatement mapperStatement,  Object... params) throws Exception {
        //首先获取驱动链接
        Connection connection = configuration.getDataSource().getConnection();
        //获取sql
        String sql = mapperStatement.getSql();
        //处理sql
        BoundSql boundSql = getBoundSql(sql);
        //获取预处理对象
        PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSqlText());
        //设置参数
            //获取参数的全路径
        String parameterType = mapperStatement.getParameterType();

        //通过封装好的方法获取参数的class对象
        Class<?> parameterClass = getClassType(parameterType);
        //获取sql语句参数名称
        List<ParameterMapping> parameterMappingList = boundSql.getParameterMappingList();
        for (int i = 0; i < parameterMappingList.size(); i++) {
            String content = parameterMappingList.get(i).getContent();
            //使用反射获取实体中的属性值
            Field declaredField = parameterClass.getDeclaredField(content);
            //暴力获取
            declaredField.setAccessible(true);
            //目前只有一个参数对象，这里就第一个参数
            Object o = declaredField.get(params[0]);
            //参数设置
            preparedStatement.setObject(i+1, o);
        }
        //执行sql
        Integer i = preparedStatement.executeUpdate();
        return i;
    }
}
