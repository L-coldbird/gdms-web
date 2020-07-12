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
import com.oracle.gdms.service.impl.GoodsServiceImpl;

@WebServlet("/admin/goods/push.php")
public class GoodsPush extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("application/json;charset=UTF-8");  //��Ӧ�Ľ������
		ResponseEntity ent = new ResponseEntity();   //׼��һ����ͷ��˷��صĽ������
		JSONObject json = new JSONObject();          //json����
		PrintWriter out = response.getWriter();      //��ͻ��������������
		
		
		String gid = request.getParameter("goodsid");   //�������еõ�Ҫ���͵���Ʒid
		if( gid == null) {
			ent.setCode(1001);
			ent.setMessage("��ƷIDȱʧ");
		}else {
			GoodsService service = new GoodsServiceImpl();
			int goodsid = Integer.parseInt(gid);
			String msg = service.pushGoods(goodsid);
		}	
		json.put("data", ent);
		out.print(json.toJSONString()); //��json���������ͻ���
		
	}

}
