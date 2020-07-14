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

	private static ResourceBundle rb;    //资源绑定的对象
    
	public static  String getString(String key) {
		return rb.getString(key);
		
	}
    public void contextDestroyed(ServletContextEvent arg0)  { 
       /*  System.out.println("准备销毁");*/
    }

    public void contextInitialized(ServletContextEvent arg0)  { 
    	//读取一下application.properties文件
       rb = ResourceBundle.getBundle("config/application");   //把application.porperties与rb对象关联起来
        
       URL url = AppListener.class.getClassLoader().getResource(rb.getString("log4jpath")) ;
		//我的log4j在哪，告诉框架
       PropertyConfigurator.configure(url);
    /*   System.out.println("初始化过程ok");*/
      
       
       //下面把应用程序需要的一些参数放进全局环境中
       String href = "http://" +rb.getString("host")+":"
    		   +rb.getString("port")+
    		   "/" + 
    		   rb.getString("context")+ "/";
       
       arg0.getServletContext().setAttribute("href", href);  //全局内存环境变量中绑定一个对象
       
    }
	
}
