package com.oracle.gdms.web.listener;

import java.net.URL;
import java.util.ResourceBundle;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.ibatis.session.Configuration;
import org.apache.log4j.PropertyConfigurator;

@WebListener
public class AppListener implements ServletContextListener {

	private static ResourceBundle rb;    //��Դ�󶨵Ķ���
    
	public static  String getString(String key) {
		return rb.getString(key);
		
	}
    public void contextDestroyed(ServletContextEvent arg0)  { 
       /*  System.out.println("׼������");*/
    }

    public void contextInitialized(ServletContextEvent arg0)  { 
    	//��ȡһ��application.properties�ļ�
       rb = ResourceBundle.getBundle("config/application");   //��application.porperties��rb�����������
        
       URL url = AppListener.class.getClassLoader().getResource(rb.getString("log4jpath")) ;
		//�ҵ�log4j���ģ����߿��
       PropertyConfigurator.configure(url);
    /*   System.out.println("��ʼ������ok");*/
      
       
       //�����Ӧ�ó�����Ҫ��һЩ�����Ž�ȫ�ֻ�����
       String href = "http://" +rb.getString("host")+":"
    		   +rb.getString("port")+
    		   "/" + 
    		   rb.getString("context")+ "/";
       
       arg0.getServletContext().setAttribute("href", href);  //ȫ���ڴ滷�������а�һ������
       
    }
	
}
