# Ipersostemce (自定义持久层框架编码实践)
 

示例工程å

## 代码目录结构

`eg.`后端Java代码工程为例:

```java
├─com.wzh
│    │ 
│    ├─config   配置类
│    │    ├─BoundSql     对mapper.xml文件的sql进行解析#{}替换成?将解析后的sql语句和参数名进行封装
│    │    ├─XMLConfigBuilder   sqlMapConfig.xml解析器,获取数据库配置信息
│    │    └─XMLMapperBuilder   Mapper.xml解析器,获取mapper配置信息
│    │ 
│    ├─io   io层
│    │    ├─Resources  根据配置文件的路径，将配置文件加载成字节输入流，存储在内存中
│    │ 
│    ├─pojo   对象
│    │    ├─Configuration     封装数据源配置信息  
│    │    ├─MapperStatement   封装mapper.xml配置信息
│    │ 
│    ├─sqlSession  jdbc会话层
│    │    ├─SqlSessionFactoryBuilder   解析配置文件，获取配置信息，并返回会话工厂对象
│    │    ├─SqlSessionFactory     会话工厂接口
│    │    │    ├─DefaultSqlSessionFactory    默认的会话工厂实现
│    │    ├─SqlSession JDBC会话接口
│    │    │    ├─DefaultSqlSession JDBC会话接口实现
│    │    ├─Executor JDBC底层执行器接口
│    │    │    ├─SimpleExecutor JDBC底层执行器接口实现
│    │
│    └─utils  工具类
│        ├─GenericTokenParser  标准的sql解析器
│        ├─ParameterMapping  sql解析后的参数名称sql中#{}里面的名称
│        └─ParameterMappingTokenHandler  配套使用
│        └─TokenHandler  配套使用
```
