package com.oracle.gdms.service.impl;

import org.apache.ibatis.session.SqlSession;

import com.oracle.gdms.dao.UserDao;
import com.oracle.gdms.entity.UserModel;
import com.oracle.gdms.service.UserService;
import com.oracle.gdms.util.GDMSUtil;

public class UserServiceImpl extends BaseService implements UserService {
    private UserDao userdao;
    
	@Override
	public int add(UserModel user) {
		try {
			// TODO ����һ�����û���ҵ��
			session = GDMSUtil.getSession(); //��ȡ�Ự
			userdao = session.getMapper(UserDao.class);
	     	int c = userdao.add(user);
			session.commit();
			return c;
		} catch (Exception ex) {
		 ex.printStackTrace();
		 session.rollback(); //�ع�����
		} 
		finally {
           free();     
		}
		return 0;
	}

	@Override
	public UserModel login(UserModel user) {
		try {
			// TODO ����һ�����û���ҵ��
			session = GDMSUtil.getSession(); //��ȡ�Ự
			userdao = session.getMapper(UserDao.class);
			return userdao.login(user);
		} catch (Exception ex) {
		 ex.printStackTrace();
		 session.rollback(); //�ع�����
		} 
		finally {
           free();     
		}
		return null;

}
}






