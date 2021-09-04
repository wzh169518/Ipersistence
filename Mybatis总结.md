## MyBatis

### MyBatis是什么？

Mybatis是一款优秀的持久层框架，一个半ORM（对象关系映射）框架，它支持定制化SQL，存储过程以及高级映射。Mybatis避免了几乎所有的JDBC代码和手动设置参数以及获取结果集。MyBatis可以使用简单的XML或注解来配置和映射原生类型，接口和Java的POJO为数据库的记录。

### ORM是什么？

ORM（Object Relational Mapping）对象关系映射，是一种为了解决关系型数据库数据与简单JAVA对象（POJO）的映射关系的技术。简单的说，就是ORM是通过使用描述对象和数据库之间映射的元数据，将程序中的对象自动持久化到关系型数据库当中。

### 为什么说Mybatis是半自动ORM映射工具？它与全自动的区别在哪里？

Hibernate属于全自动ORM映射工具，使用Hibernate查询关联对象或者关联集合对象时，可以根据对象关系模型直接获取，所以它是全自动的。

而Mybatis在查询关联对象或关联集合对象时，需要手动编写sql来完成，所以，称之为半自动ORM映射工具。

### 传统JDBC开发存在的问题

-  频繁创建数据库连接对象，释放，容易造成系统资源的浪费，影响系统性能。可以使用连接池解决这个问题，但是使用jdbc需要自己实现连接池。

- sql语句的定义，参数设置，结果集处理存在硬编码。实际项目中sql语句变化的可能性很大，一旦变化就需要修改java代码，系统就要和重新编译，重新发布。不好维护。

- 使用preparedStatement向有占位符传参数存在硬编码，因为sql语句的where条件不一定。修改sql还要修改代码，系统不容易维护

- 结果集处理存在重复代码，处理麻烦，如果可以映射成Java对象比较方便

### JDBC存在哪些不足，MyBatis是怎么处理的？

1、数据库链接创建、释放频繁造成系统资源浪费从而影响系统性能，如果使用数据库连接池可解决此问题。

解决：在mybatis-config.xml中配置数据链接池，使用连接池管理数据库连接

2、Sql语句写在代码中造成代码不易维护，实际应用sql变化的可能较大，sql变动需要改变java代码。

解决：将Sql语句配置在XXXXmapper.xml文件中与java代码分离。

3、向sql语句传参数麻烦，因为sql语句的where条件不一定，可能多也可能少，占位符需要和参数一一对应。

解决： Mybatis自动将java对象映射至sql语句。

4、对结果集解析麻烦，sql变化导致解析代码变化，且解析前需要遍历，如果能将数据库记录封装成pojo对象解析比较方便。

解决：Mybatis自动将sql执行结果映射至java对象。

### Mybatis优缺点

与传统的数据库访问技术相比，ORM有以下优点：

- 基于SQL语句编程，相当灵活，不会对应用程序或者数据库的现有设计造成任何影响，SQL写在XML里，解除sql与程序代码的耦合，便于统一管理；提供XML标签，支持编写动态SQL语句，并可重用
- 与JDBC相比，减少了50%以上的代码量，消除了JDBC大量冗余的代码，不需要手动开关连接
- 很好的与各种数据库兼容（因为MyBatis使用JDBC来连接数据库，所以只要JDBC支持的数据库MyBatis都支持）
- 提供映射标签，支持对象与数据库的ORM字段关系映射；提供对象关系映射标签，支持对象关系组件维护
- 能够与Spring很好的集成



### MyBatis编程步骤是什么样的？

1. 创建SqlSessionFactory

2. 通过SqlSessionFactory创建SqlSession

 3.通过SqlSession执行数据库操作

4. 通过SqlSession.commit()提交事务

5. 通过SqlSession.close()关闭会话

### MyBatis的工作原理

在学习 MyBatis 程序之前，需要了解一下 MyBatis 工作原理，以便于理解程序。MyBatis 的工作原理如下图

1）读取 MyBatis 配置文件：mybatis-config.xml 为 MyBatis 的全局配置文件，配置了 MyBatis 的运行环境等信息，例如数据库连接信息。

2）加载映射文件。映射文件即 SQL 映射文件，该文件中配置了操作数据库的 SQL 语句，需要在 MyBatis 配置文件 mybatis-config.xml 中加载。mybatis-config.xml 文件可以加载多个映射文件，每个文件对应数据库中的一张表。

3）构造会话工厂：通过MyBatis的环境等配置信息构建会话工厂SqlSessionFactory。

4）创建会话对象：通过会话工厂创建SqlSession对象，该对象中包含了执行SQL语句的所有方法。

5）Executor 执行器：MyBatis 底层定义了一个 Executor 接口来操作数据库，它将根据 SqlSession 传递的参数动态地生成需要执行的 SQL 语句，同时负责查询缓存的维护。

6）MappedStatement 对象：在 Executor 接口的执行方法中有一个 MappedStatement 类型的参数，该参数是对映射信息的封装，用于存储要映射的 SQL 语句的 id、参数等信息。

7）输入参数映射：输入参数类型可以是 Map、List 等集合类型，也可以是基本数据类型和 POJO 类型。输入参数映射过程类似于 JDBC 对 preparedStatement 对象设置参数的过程。

8）输出结果映射：输出结果类型可以是 Map、 List 等集合类型，也可以是基本数据类型和 POJO 类型。输出结果映射过程类似于 JDBC 对结果集的解析过程。

### MyBatis的功能架构是怎样的

MyBatis的功能架构可以分为三层：

- API接口层：提供给外部使用的接口API，开发人员通过这些本地的API来操作数据库。接口层一接收到调用的请求就会调用数据处理层来完成具体的数据处理。

- 数据处理层：负责具体的SQL查找，SQL解析，SQL执行和执行结果映射处理等。它主要的目的是根据调用的请求完成一次数据库操作。

- 基础支撑层：负责最基础的功能支撑，包括连接管理，事务管理，配置加载和缓存处理，这些功用的东西，将他们抽取出来作为基础的组件。为上层的数据处理层提供最基础的支撑。

### 为什么需要预编译

#### 一. "#{}“和”${}"的区别

"#{}"是将传入的值按照字符串的形式进行处理，如下面这条语句：

```
select user_id,user_name from t_user where user_id = #{user_id}
```

MyBaits会首先对其进行预编译，将#{user_ids}替换成?占位符，然后在执行时替换成实际传入的user_id值，**并在两边加上单引号，以字符串方式处理。**下面是MyBatis执行日志：

```
10:27:20.247 [main] DEBUG william.mybatis.quickstart.mapper.UserMapper.selectById - ==>  Preparing: select id, user_name from t_user where id = ? 
10:27:20.285 [main] DEBUG william.mybatis.quickstart.mapper.UserMapper.selectById - ==> Parameters: 1(Long)
```

因为"#{}"会在传入的值两端加上单引号，所以可以很大程度上防止SQL注入。有关SQL注入的知识会在后文进行说明。因此在大多数情况下，建议使用"#{}"。

"${}"是做简单的字符串替换，即将传入的值直接拼接到SQL语句中，且不会自动加单引号。将上面的SQL语句改为：

```
select user_id,user_name from t_user where user_id = ${user_id}
```

观察MyBatis的执行日志：

```
10:41:32.242 [main] DEBUG william.mybatis.quickstart.mapper.UserMapper.selectById - ==>  Preparing: select id, user_name, real_name, sex, mobile, email, note, position_id from t_user where id = 1 
10:41:32.288 [main] DEBUG william.mybatis.quickstart.mapper.UserMapper.selectById - ==> Parameters: 
```

参数是直接替换的，且没有单引号处理，这样就有SQL注入的风险。

但是在一些特殊情况下，使用${}是更适合的方式，如表名、orderby等。见下面这个例子：

```
select user_id,user_name from ${table_name} where user_id = ${user_id}
```

这里如果想要动态处理表名，就只能使用"${}"，因为如果使用"#{}"，就会在表名字段两边加上单引号，变成下面这样：

```
select user_id,user_name from 't_user' where user_id = ${user_id}
```

这样SQL语句就会报错。

#### 二. MyBatis预编译源码分析

MyBatis对SQL语句解析的处理在XMLStatementBuilder类中，见源码：

```java
/**
   * 解析mapper中的SQL语句
   */
  public void parseStatementNode() {
    //SQL语句id,对应着Mapper接口的方法
    String id = context.getStringAttribute("id");

    //校验databaseId是否匹配
    String databaseId = context.getStringAttribute("databaseId");
    if (!databaseIdMatchesCurrent(id, databaseId, this.requiredDatabaseId)) {
      return;
    }

    //SQL标签属性解析
    Integer fetchSize = context.getIntAttribute("fetchSize");
    Integer timeout = context.getIntAttribute("timeout");
    String parameterMap = context.getStringAttribute("parameterMap");
    String parameterType = context.getStringAttribute("parameterType");
    Class<?> parameterTypeClass = resolveClass(parameterType);  //参数类型
    String resultMap = context.getStringAttribute("resultMap");
    String resultType = context.getStringAttribute("resultType");
    String lang = context.getStringAttribute("lang");
    LanguageDriver langDriver = getLanguageDriver(lang);

    Class<?> resultTypeClass = resolveClass(resultType);    //结果类型
    String resultSetType = context.getStringAttribute("resultSetType");

    //Statement类型,默认PreparedStatement
    StatementType statementType = StatementType.valueOf(context.getStringAttribute("statementType", StatementType.PREPARED.toString()));
    ResultSetType resultSetTypeEnum = resolveResultSetType(resultSetType);

    String nodeName = context.getNode().getNodeName();
    //SQL命令类型:增删改查
    SqlCommandType sqlCommandType = SqlCommandType.valueOf(nodeName.toUpperCase(Locale.ENGLISH));
    boolean isSelect = sqlCommandType == SqlCommandType.SELECT;
    boolean flushCache = context.getBooleanAttribute("flushCache", !isSelect);
    boolean useCache = context.getBooleanAttribute("useCache", isSelect);
    boolean resultOrdered = context.getBooleanAttribute("resultOrdered", false);

    // Include Fragments before parsing
    XMLIncludeTransformer includeParser = new XMLIncludeTransformer(configuration, builderAssistant);
    includeParser.applyIncludes(context.getNode());

    // Parse selectKey after includes and remove them.
    processSelectKeyNodes(id, parameterTypeClass, langDriver);
    
    // Parse the SQL (pre: <selectKey> and <include> were parsed and removed)
    //重要:解析SQL语句,封装成一个SqlSource
    SqlSource sqlSource = langDriver.createSqlSource(configuration, context, parameterTypeClass);
    String resultSets = context.getStringAttribute("resultSets");
    String keyProperty = context.getStringAttribute("keyProperty");
    String keyColumn = context.getStringAttribute("keyColumn");
    KeyGenerator keyGenerator;
    String keyStatementId = id + SelectKeyGenerator.SELECT_KEY_SUFFIX;
    keyStatementId = builderAssistant.applyCurrentNamespace(keyStatementId, true);
    if (configuration.hasKeyGenerator(keyStatementId)) {
      keyGenerator = configuration.getKeyGenerator(keyStatementId);
    } else {
      keyGenerator = context.getBooleanAttribute("useGeneratedKeys",
          configuration.isUseGeneratedKeys() && SqlCommandType.INSERT.equals(sqlCommandType))
          ? Jdbc3KeyGenerator.INSTANCE : NoKeyGenerator.INSTANCE;
    }

    //解析完毕,最后通过MapperBuilderAssistant创建MappedStatement对象,统一保存到Configuration的mappedStatements属性中
    builderAssistant.addMappedStatement(id, sqlSource, statementType, sqlCommandType,
        fetchSize, timeout, parameterMap, parameterTypeClass, resultMap, resultTypeClass,
        resultSetTypeEnum, flushCache, useCache, resultOrdered, 
        keyGenerator, keyProperty, keyColumn, databaseId, langDriver, resultSets);
  }

```
 SqlSource sqlSource = langDriver.createSqlSource(configuration, context, parameterTypeClass)方法会通过LanguageDriver对SQL语句进行解析，生成一个SqlSource。SqlSource封装了映射文件或者注解中定义的SQL语句，它不能直接交给数据库执行，因为里面可能包含动态SQL或者占位符等元素。而MyBatis在实际执行SQL语句时，会调用SqlSource的getBoundSql()方法获取一个BoundSql对象，BoundSql是将SqlSource中的动态内容经过处理后，返回的实际可执行的SQL语句，其中包含?占位符List封装的有序的参数映射关系，此外还有一些额外信息标识每个参数的属性名称等。

LanguageDriver的默认实现类是XMLLanguageDriver

```java
//创建SqlSource
@Override
public SqlSource createSqlSource(Configuration configuration, XNode script, Class<?> parameterType) {
  //创建XMLScriptBuilder对象
  XMLScriptBuilder builder = new XMLScriptBuilder(configuration, script, parameterType);

  //通过XMLScriptBuilder解析SQL脚本
  return builder.parseScriptNode();
}

```

这里通过XMLScriptBuilder对象的parseScriptNode()方法进行SQL脚本的解析

```java
/**
   * 解析SQL脚本
   */
public SqlSource parseScriptNode() {
  //解析动态标签,包括动态SQL和${}。执行后动态SQL和${}已经被解析完毕。
  //此时SQL语句中的#{}还没有处理,#{}会在SQL执行时动态解析
  MixedSqlNode rootSqlNode = parseDynamicTags(context);

  //如果是dynamic的,则创建DynamicSqlSource,否则创建RawSqlSource
  SqlSource sqlSource = null;
  if (isDynamic) {
    sqlSource = new DynamicSqlSource(configuration, rootSqlNode);
  } else {
    sqlSource = new RawSqlSource(configuration, rootSqlNode, parameterType);
  }
  return sqlSource;
}

```


parseScriptNode的功能就是判断该SQL节点是否是动态的，然后根据是否动态返回DynamicSqlSource或

RawSqlSource。是否为动态SQL的判断在parseDynamicTags()方法中：

```java
protected MixedSqlNode parseDynamicTags(XNode node) {
  List<SqlNode> contents = new ArrayList<>();
  NodeList children = node.getNode().getChildNodes();
  for (int i = 0; i < children.getLength(); i++) {
    XNode child = node.newXNode(children.item(i));

    //处理文本节点(SQL语句)
    if (child.getNode().getNodeType() == Node.CDATA_SECTION_NODE || child.getNode().getNodeType() == Node.TEXT_NODE) {
      //把SQL封装到TextSqlNode
      String data = child.getStringBody("");
      TextSqlNode textSqlNode = new TextSqlNode(data);

      //如果包含${},则是dynamic的
      if (textSqlNode.isDynamic()) {
        contents.add(textSqlNode);
        isDynamic = true;
      } else {
        //除了${}外,其他的SQL都是静态的
        contents.add(new StaticTextSqlNode(data));
      }
    } else if (child.getNode().getNodeType() == Node.ELEMENT_NODE) { // issue #628
      String nodeName = child.getNode().getNodeName();
      NodeHandler handler = nodeHandlerMap.get(nodeName);
      if (handler == null) {
        throw new BuilderException("Unknown element <" + nodeName + "> in SQL statement.");
      }
      handler.handleNode(child, contents);
      isDynamic = true;
    }
  }
  return new MixedSqlNode(contents);
}

```
在这个方法中，会对SQL语句进行动态标签的解析。以<select>标签为例，会获取标签中的文本节点(即具体的SQL语句)，将其封装成TextSqlNode，然后调用isDynamic()方法判断是否为动态标签

```java
public boolean isDynamic() {
  DynamicCheckerTokenParser checker = new DynamicCheckerTokenParser();
  GenericTokenParser parser = createParser(checker);
  parser.parse(text);
  return checker.isDynamic();
}
```

底层的文本解析，我们仅需看下createParser()这个方法：

```java
private GenericTokenParser createParser(TokenHandler handler) {
  return new GenericTokenParser("${", "}", handler);
}
```

该方法会创建一个以"${}“为token的解析器GenericTokenParser，对指定的SQL语句进行解析，如果解析成功，说明语句中包含”${}"，则将其标记为动态SQL标签。

如果是动态标签，创建的SqlSource就是DynamicSqlSource，其获取的BoundSql就是直接进行字符串的替换。对于非动态标签，则创建RawSqlSource，对应?占位符的SQL语句。

#### 三. SQL注入问题

问题演示

前面说到了使用#{}可以有效防止SQL注入。那么SQL注入到底是什么呢？

考虑下面这个常见的场景：用户登录。根据前端传过来的用户名和密码，去数据库进行校验，如果查到是有效用户，则通知前端登录成功。这个场景相信大家都经历过。在数据库会执行这样一段SQL：

```
 select * from users where username='admin' and password=md5('admin')
```

如果前端传如正确的用户名和密码，可以登录成功，这样在正常情况下没有问题。

那么如果有人恶意攻击，在用户名框输入了’or 1=1#，而密码框随意输入，这个SQL语句就变为：

```
 select * from users where username='' or 1=1#' and password=md5('')
```

“#”在mysql中是注释符，这样"#"后面的内容将被mysql视为注释内容，就不会去执行了。换句话说，上面的SQL语句等价于：

```
select * from users where username='' or 1=1
```

由于1=1恒成立，因此SQL语句可以被进一步简化为：

```
select * from users
```


这样一来，这段SQL语句可以执行成功，用户就可以恶意登录了。这样就实现了简单的SQL注入。

通过MyBatis预编译防SQL注入

如前文所述，在MyBatis中，采用"${}“是简单的字符串替换，肯定无法应对SQL注入。那么”#{}"是怎样解决SQL注入的呢？

将上面的查询语句在MyBatis中实现为：

```x'm'l
 select * from users where username=#{username} and password=md5(#{password})
```

这样一来，当用户再次输入’or 1=1#，MyBatis执行SQL语句时会将其替换成：

```
 select * from users where username=''or 1=1#' and password=md5('')
```

由于在两端加了双引号，因此输入的内容就是一个普通字符串，其中的#注释和or 1=1都不会生效，这样就无法登陆成功了，从而有效防止了SQL注入。


### Mybatis都有哪些Executor执行器，它们之间的区别是什么

MyBatis有三种基本的Executor执行器，SimpleExecutor，ReuseExecutor，BatchExecutor。

**SimpleExecutor：**每执行一次update或select，就开启一个Statement对象，用完立刻关闭Statement对象。

**ReuseExecutor：**执行update或select，以sql作为key查找Statement对象，存在就使用，不存在就创建，用完后，不关闭Statement对象，而是放置于Map<String, Statement>内，供下一次使用。简言之，就是重复使用Statement对象。

**BatchExecutor：**执行update（没有select，JDBC批处理不支持select），将所有sql都添加到批处理中（addBatch()），等待统一执行（executeBatch()），它缓存了多个Statement对象，每个Statement对象都是addBatch()完毕后，等待逐一执行executeBatch()批处理。与JDBC批处理相同。

作用范围：Executor的这些特点，都严格限制在SqlSession生命周期范围内。

### Mybatis中如何指定使用哪一种Executor执行器

在Mybatis配置文件中，在设置（settings）可以指定默认的ExecutorType执行器类型，也可以手动给DefaultSqlSessionFactory的创建SqlSession的方法传递ExecutorType类型参数，如SqlSession openSession(ExecutorType execType)。

配置默认的执行器。SIMPLE 就是普通的执行器（默认）；REUSE 执行器会重用预处理语句（prepared statements）； BATCH 执行器将重用语句并执行批量更新。

### Mybatis延迟加载实现原理

Mybatis仅支持association关联对象和collection关联集合对象的延迟加载，association指的就是一对一，collection指的就是一对多查询。在Mybatis配置文件中，可以配置是否启用延迟加载lazyLoadingEnabled=true|false。

它的原理是，使用CGLIB创建目标对象的代理对象，当调用目标方法时，进入拦截器方法，比如调用a.getB().getName()，拦截器invoke()方法发现a.getB()是null值，那么就会单独发送事先保存好的查询关联B对象的sql，把B查询上来，然后调用a.setB(b)，于是a的对象b属性就有值了，接着完成a.getB().getName()方法的调用。这就是延迟加载的基本原理。

当然了，不光是Mybatis，几乎所有的包括Hibernate，支持延迟加载的原理都是一样的。

### 使用MyBatis的mapper接口调用时有哪些要求

1、Mapper接口方法名和mapper.xml中定义的每个sql的id相同。

2、Mapper接口方法的输入参数类型和mapper.xml中定义的每个sql 的parameterType的类型相同。

3、Mapper接口方法的输出参数类型和mapper.xml中定义的每个sql的resultType的类型相同。

4、Mapper.xml文件中的namespace即是mapper接口的类路径。

### Xml映射文件，都会写一个Dao接口与之对应，请问，这个Dao接口的工作原理是什么？Dao接口里的方法，参数不同时，方法能重载吗

Dao接口，就是人们常说的Mapper接口，接口的全限名，就是映射文件中的namespace的值，接口的方法名，就是映射文件中MappedStatement的id值，接口方法内的参数，就是传递给sql的参数。Mapper接口是没有实现类的，当调用接口方法时，接口全限名+方法名拼接字符串作为key值，可唯一定位一个MappedStatement，举例：com.mybatis3.mappers.StudentDao.findStudentById，可以唯一找到namespace为com.mybatis3.mappers.StudentDao下面id = findStudentById的MappedStatement。在Mybatis中，每一个<font color='red'>`<select>、<insert>、<update>、<delete>`</font>标签，都会被解析为一个MappedStatement对象。

Dao接口里的方法，是不能重载的，因为是全限名+方法名的保存和寻找策略。

Dao接口的工作原理是JDK动态代理，Mybatis运行时会使用JDK动态代理为Dao接口生成代理proxy对象，代理对象proxy会拦截接口方法，转而执行MappedStatement所代表的sql，然后将sql执行结果返回。

### Mybatis的Xml映射文件和Mybatis内部数据结构之间的映射关系

Mybatis将所有Xml配置信息都封装到All-In-One重量级对象Configuration内部。在Xml映射文件中，<font color='red'>`<parameterMap>`</font>标签会被解析为ParameterMap对象，其每个子元素会被解析为ParameterMapping对象。<font color='red'>`<resultMap>`</font>标签会被解析为ResultMap对象，其每个子元素会被解析为ResultMapping对象。每一个<font color='red'>`<select>、<insert>、<update>、<delete>`</font>标签均会被解析为MappedStatement对象，标签内的sql会被解析为BoundSql对象。

### Mybatis是如何将sql执行结果封装为目标对象并返回的，几种映射形式

第一种是使用<font color='red'>`<resultMap>`</font>标签，逐一定义列名和对象属性名之间的映射关系。

第二种是使用sql列的别名功能，将列别名书写为对象属性名，比如T_NAME AS NAME，对象属性名一般是name，小写，但是列名不区分大小写，Mybatis会忽略列名大小写，智能找到与之对应对象属性名，你甚至可以写成T_NAME AS NaMe，Mybatis一样可以正常工作。

有了列名与属性名的映射关系后，Mybatis通过反射创建对象，同时使用反射给对象的属性逐一赋值并返回，那些找不到映射关系的属性，是无法完成赋值的。

## 高级查询

### MyBatis实现一对一，一对多有几种方式，怎么操作的？

有联合查询和嵌套查询。联合查询是几个表联合查询，只查询一次，通过在resultMap里面的association，collection节点配置一对一，一对多的类就可以完成

嵌套查询是先查一个表，根据这个表里面的结果的外键id，去再另外一个表里面查询数据，也是通过配置association，collection，但另外一个表的查询通过select节点配置。

**联合查询：**

连接查询使用时，使用偏向于a表所在方向的外连接，可获得a表所有信息，和对应的b表信息。该方式为饿汉式，内存占用较大，但对数据库访问次数较少而导致消耗时间少。

**嵌套查询：**

嵌套查询是将原来多表查询中的联合查询语句拆成单个表的查询，再使用MyBatis的语法嵌套在一起

嵌套查询使用时，先查询a表的信息，然后依赖a和b表的外键约束，利用in()，再次查询b表对应到a表上的信息。该方式可以改为饿汉式，内存使用较小，但需要多次访问数据库而导致消耗时间多。

### Mybatis映射Enum枚举类

Mybatis可以映射枚举类，不单可以映射枚举类，Mybatis可以映射任何对象到表的一列上。映射方式为自定义一个TypeHandler，实现TypeHandler的setParameter()和getResult()接口方法。

TypeHandler有两个作用，一是完成从javaType至jdbcType的转换，二是完成jdbcType至javaType的转换，体现为setParameter()和getResult()两个方法，分别代表设置sql问号占位符参数和获取列查询结果。

## 动态SQL

### Mybatis动态sql是做什么的？都有哪些动态sql？能简述一下动态sql的执行原理不？

Mybatis动态sql可以让我们在Xml映射文件内，以标签的形式编写动态sql，完成逻辑判断和动态拼接sql的功能，Mybatis提供了9种动态sql标签trim|where|set|foreach|if|choose|when|otherwise|bind。

其执行原理为，使用OGNL从sql参数对象中计算表达式的值，根据表达式的值动态拼接sql，以此来完成动态sql的功能。

## 插件模块

### Mybatis是如何进行分页的，分页插件的原理是什么

Mybatis使用RowBounds对象进行分页，它是针对ResultSet结果集执行的内存分页，而非物理分页，可以在sql内直接书写带有物理分页的参数来完成物理分页功能，也可以使用分页插件来完成物理分页。

分页插件的基本原理是使用Mybatis提供的插件接口，实现自定义插件，在插件的拦截方法内拦截待执行的sql，然后重写sql，根据dialect方言，添加对应的物理分页语句和物理分页参数。

举例：select * from student，拦截sql后重写为：select t.* from (select * from student) t limit 0, 10

#### 什么是物理分页和逻辑分页

**1.物理分页**

物理分页依赖的是某一物理实体，这个物理实体就是数据库，比如MySQL数据库提供了limit关键字，程序员只需要编写带有limit关键字的SQL语句，数据库返回的就是分页结果。

**2.逻辑分页（内存分页）**

逻辑分页依赖的是程序员编写的代码。数据库返回的不是分页结果，而是全部数据，然后再由程序员通过代码获取分页数据，常用的操作是一次性从数据库中查询出全部数据并存储到List集合中，因为List集合有序，再根据索引获取指定范围的数据。

#### 物理分页与逻辑分页对比

**1.数据库负担**

物理分页每次都访问数据库，逻辑分页只访问一次数据库，物理分页对数据库造成的负担大。
**2.服务器负担**

逻辑分页一次性将数据读取到内存，占用了较大的内容空间，物理分页每次只读取一部分数据，占用内存空间较小。

**3.实时性**

逻辑分页一次性将数据读取到内存，数据发生改变，数据库的最新状态不能实时反映到操作中，实时性差。物理分页每次需要数据时都访问数据库，能够获取数据库的最新状态，实时性强。

**4.适用场合**

逻辑分页主要用于数据量不大、数据稳定的场合，物理分页主要用于数据量较大、更新频繁的场合。

### 简述Mybatis的插件运行原理，以及如何编写一个插件。

Mybatis仅可以编写针对ParameterHandler、ResultSetHandler、StatementHandler、Executor这4种接口的插件，Mybatis使用JDK的动态代理，为需要拦截的接口生成代理对象以实现接口方法拦截功能，每当执行这4种接口对象的方法时，就会进入拦截方法，具体就是InvocationHandler的invoke()方法，当然，只会拦截那些你指定需要拦截的方法。

实现Mybatis的Interceptor接口并复写intercept()方法，然后在给插件编写注解，指定要拦截哪一个接口的哪些方法即可，记住，别忘了在配置文件中配置你编写的插件。

## 缓存

### Mybatis的一级、二级缓存

1）一级缓存: 基于 PerpetualCache 的 HashMap 本地缓存，其存储作用域为 Session，当 Session flush 或 close 之后，该 Session 中的所有 Cache 就将清空，默认打开一级缓存。

2）二级缓存与一级缓存其机制相同，默认也是采用 PerpetualCache，HashMap 存储，不同在于其存储作用域为 Mapper(Namespace)，并且可自定义存储源，如 Ehcache。默认不打开二级缓存，要开启二级缓存，使用二级缓存属性类需要实现Serializable序列化接口(可用来保存对象的状态),可在它的映射文件中配置<cache/> ；

3）对于缓存数据更新机制，当某一个作用域(一级缓存 Session/二级缓存Namespaces)的进行了C/U/D 操作后，默认该作用域下所有 select 中的缓存将被 clear。

