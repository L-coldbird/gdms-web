package com.oracle.gdms.web.listener;

import java.net.URL;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.ibatis.session.Configuration;
import org.apache.log4j.PropertyConfigurator;

@WebListener
public class AppListener implements ServletContextListener {

    public void contextDestroyed(ServletContextEvent arg0)  { 
       /*  System.out.println("准备销毁");*/
    }

	
    public void contextInitialized(ServletContextEvent arg0)  { 
       URL url = AppListener.class.getClassLoader().getResource("config/log4j.properties") ;
		//我的log4j在哪，告诉框架
    	PropertyConfigurator.configure(url  );
    }
	
}
