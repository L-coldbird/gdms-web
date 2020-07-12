package com.oracle.gdms.service;

import com.oracle.gdms.entity.GoodsModel;
import com.oracle.gdms.entity.PageModel;

public interface GoodsService {
	/**
	 * ��ҳ��ʾ��Ʒ����
	 * @param offset ��ʼλ��
	 * @param rows  ÿҳ��ʾ��������¼
	 * @return
	 */
	PageModel<GoodsModel>findByPage(int offsetNumber,int rows);

	/**
	 * ����ָ��id����Ʒ
	 * @param goodsid
	 * @return �ɹ���ϢΪ�մ���ʧ��Ϊ��Ϣ����
	 */
	String pushGoods(int goodsid);
	
}
