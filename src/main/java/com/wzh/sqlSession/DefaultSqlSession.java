package com.wzh.sqlSession;

import com.wzh.pojo.Configuration;
import com.wzh.pojo.MapperStatement;

import java.lang.reflect.*;
import java.util.List;

/**
 * 会话接口实现类
 */
public class DefaultSqlSession implements SqlSession {

    private Configuration configuration;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
    }

    /**
     * 查询所有
     *
     * @param statementId
     * @param <E>
     * @return
     */
    @Override
    public <E> List<E> selectList(String statementId, Object... params) throws Exception {

        //将要去完成对simpleExecutor里的query方法进行调用
        SimpleExecutor simpleExecutor = new SimpleExecutor();
        //获取要调用的sql信息
        MapperStatement mapperStatement = configuration.getMapperStatementMap().get(statementId);
        //执行jdbc
        List<Object> list = simpleExecutor.query(configuration, mapperStatement, params);
        return (List<E>) list;
    }

    /**
     * 查询单个
     *
     * @param statementId
     * @param params
     * @param <T>
     * @return
     */
    @Override
    public <T> T selectOne(String statementId, Object... params) throws Exception {
        List<Object> objects = selectList(statementId, params);
        if (objects.size() == 1) {
            return (T) objects.get(0);
        } else {
            throw new RuntimeException("查询结果为空或返回结果过多");
        }
    }

    /**
     * 修改方法
     *
     * @param statementId
     * @param params
     * @return
     */
    @Override
    public Integer update(String statementId, Object... params) throws Exception {
        //获取执行器
        SimpleExecutor simpleExecutor = new SimpleExecutor();
        //获取要调用的sql信息：也就是mapperStatement
        MapperStatement mapperStatement = configuration.getMapperStatementMap().get(statementId);
        //调用update方法; 返回修改条数
        Integer count = simpleExecutor.update(configuration, mapperStatement, params);
        return count;
    }

    /**
     * 插入
     *
     * @param statementId
     * @param params
     * @return
     */
    @Override
    public Integer insert(String statementId, Object... params) throws Exception {
        //获取执行器
        SimpleExecutor simpleExecutor = new SimpleExecutor();
        //获取要调用的sql信息：也就是mapperStatement
        MapperStatement mapperStatement = configuration.getMapperStatementMap().get(statementId);
        //调用update方法; 返回修改条数
        Integer count = simpleExecutor.update(configuration, mapperStatement, params);
        return count;
    }

    /**
     * 删除
     *
     * @param statementId
     * @param params
     * @return
     */
    @Override
    public Integer delete(String statementId, Object... params) throws Exception {
        //获取执行器
        SimpleExecutor simpleExecutor = new SimpleExecutor();
        //获取要调用的sql信息：也就是mapperStatement
        MapperStatement mapperStatement = configuration.getMapperStatementMap().get(statementId);
        //调用update方法; 返回修改条数
        Integer count = simpleExecutor.update(configuration, mapperStatement, params);
        return count;
    }

    /**
     * 为Dao接口生成代理实现类
     *
     * @param mapperClass 接口的class文件
     * @param <T>
     * @return
     */
    @Override
    public <T> T getMapper(Class<?> mapperClass) {
        //使用JDK动态代理为Dao接口生成代理对象，并返回 o:生成的代理对象
        Object proxyInstance = Proxy.newProxyInstance(DefaultSqlSession.class.getClassLoader(), new Class[]{mapperClass}, new InvocationHandler() {
            //匿名内部类

            /**
             * 代理方法
             *
             * @param proxy 当前代理对象的应用
             * @param method 当前被调用方法的引用 -》比如findAll()方法
             * @param args 传递的参数 调用findAll()方法的参数
             * @return
             * @throws Throwable
             */
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                /*
                 * 底层还是去执行JDBC代码 //根据不同情况来调用不同的方法
                 * 参数1：statementId sql语句的唯一标识 : 在invoke方法中是没有办法获取到namespace 和id
                 * 但是可以借用method对象获取到接口的方法名以及接口所在的全限定名
                 * */

                //方法名
                String methodName = method.getName();
                //该对象的class对象
                Class<?> declaringClass = method.getDeclaringClass();
                //接口的全限定
                String className = declaringClass.getName();
                //拼接组装成statementId
                String statementId = className + "." + methodName;

                //获取被调用方法的返回值类型
                Type genericReturnType = method.getGenericReturnType();
                //判断是否进行了 泛型类型参数化 ：判断是否有泛型 有泛型就是集合，没有泛型就是单个对象
                MapperStatement mapperStatement = configuration.getMapperStatementMap().get(statementId);
                switch (mapperStatement.getSqlCommandType()) {
                    case delete:
                        return delete(statementId, args);
                    case update:
                        return update(statementId, args);
                    case insert:
                        return insert(statementId, args);
                    case select:
                        if (genericReturnType instanceof ParameterizedType) {
                            return selectList(statementId, args);
                        }
                        return selectOne(statementId, args);
                    default:break;
                }
                return null;
            }
        });

        return (T) proxyInstance;
    }
}
