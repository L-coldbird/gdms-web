package com.oracle.gdms.web.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.oracle.gdms.entity.GoodsModel;
import com.oracle.gdms.entity.ResponseEntity;
import com.oracle.gdms.service.GoodsService;
import com.oracle.gdms.service.UserService;
import com.oracle.gdms.service.impl.GoodsServiceImpl;
import com.oracle.gdms.service.impl.UserServiceImpl;

@WebServlet("/admin/user/mobile.php")
public class UserMobileCheck extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("application/json;charset=UTF-8");  //��Ӧ�Ľ������
		ResponseEntity ent = new ResponseEntity();   //׼��һ����ͷ��˷��صĽ������
		JSONObject json = new JSONObject();          //json����
		PrintWriter out = response.getWriter();      //��ͻ��������������
		
		
		String mobile = request.getParameter("mobile");   //�������еõ�Ҫ������ֻ���
		if( mobile == null) {
			ent.setCode(1002);
			ent.setMessage("�ֻ�����ȱʧ");
		}else {
			UserService service = new UserServiceImpl();
			boolean has = service.hasMobile(mobile);
			ent.setCode(has ? 1003 : 0 );
			ent.setMessage(has ? "<span style='color:red'>���ֻ������Ѿ���ע�ᣬ�����" : "<span style='color:green'>��ϲ,���ֻ��ſ���");
		}	
		json.put("data", ent);
		out.print(json.toJSONString()); //��json���������ͻ���
		
	}

}
