package com.oracle.gdms.service;

import com.oracle.gdms.entity.UserModel;
/**
 * ������ʵ���û�ҵ����صĹ���
 * @author DD
 *
 */
public interface UserService {
  /**
   * ���û�ע��ҵ��
   * @param user
   * @return
   */
   int add(UserModel user);

UserModel login(UserModel user);    
}
