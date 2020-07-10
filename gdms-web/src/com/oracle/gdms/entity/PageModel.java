package com.oracle.gdms.entity;

import java.util.List;


public class PageModel<T> {    //t��ʾ����

	private int current;   //��ǰҳ��
	private int total;    //��ҳ��
	private List<T> data;  //���ݼ�
	
	public static final int ROWS = 10; //��ʾ�����ĳ���

	public int getCurrent() {
		return current;
	}

	public void setCurrent(int current) {
		this.current = current;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}
	
	
	
}
