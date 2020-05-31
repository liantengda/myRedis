package com.lian.myredis.service;

import com.lian.myredis.model.User;

import java.util.List;

/**
 *
 */
public interface UserService {
   /**
    * 根据用户id查找用户，如果用户不存在，则向redis中缓存一个空对象，设置较短的过期时间。
    * 这样做，在高并发的情况下，可以防止缓存穿透。
    * 缓存穿透：一条数据库和缓存中都没有的数据，如果不断查询该数据，则会不断地请求数据库，对数据库的访问压力就会增大。
    *
    * @param id 用户信息主键
    * @return
    */
   User findUserIfNotStoreBlank(int id);

   /**
    * 使用自制布伦过滤器，防止缓存穿透
    * @param id
    * @return
    */
   User findUserStoreBloomFilter(int id);

   /**
    * 获取用户信息列表
    * @return
    */
   List<User> list();

   /**
    * 添加用户
    * @param user
    * @return
    */
   User add(User user);
   /**
    * 更新用户
    * @param user
    * @return
    */
   User upd(User user);

   /**
    * 删除用户
     * @param id
    * @return
    */
   User del(Integer id);
}
