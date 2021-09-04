package com.wzh.dao;

import com.wzh.pojo.User;

import java.util.List;

/**
 * 持久层接口
 *
 */
public interface IUserDao {

    //查询所有用户
    List<User> finAll() throws Exception;

    //根据条件进行用户查询
    User findByCondition(User user) throws Exception;

    //修改
    Integer updateById(User user) throws Exception;

    //新增
    Integer save(User user) throws Exception;

    //删除
    Integer remove(User user) throws Exception;
}
