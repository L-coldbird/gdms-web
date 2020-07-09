package com.oracle.gdms.service;

import com.oracle.gdms.entity.UserModel;
/**
 * 此类是实现用户业务相关的功能
 * @author DD
 *
 */
public interface UserService {
  /**
   * 新用户注册业务
   * @param user
   * @return
   */
   int add(UserModel user);

UserModel login(UserModel user);    
}
