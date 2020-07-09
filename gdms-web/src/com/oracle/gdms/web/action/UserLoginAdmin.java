package com.oracle.gdms.web.action;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oracle.gdms.entity.UserModel;
import com.oracle.gdms.service.UserService;
import com.oracle.gdms.service.impl.AreaServiceImpl;
import com.oracle.gdms.service.impl.UserServiceImpl;
import com.oracle.gdms.util.GDMSUtil;


@WebServlet("/admin/user/login.php")
public class UserLoginAdmin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		request.setCharacterEncoding("utf-8");
		//�����ύ�����Ĳ��������Ժ��װ��һ��usermodelʵ��
		String account = request.getParameter("name");
		String password = request.getParameter("password");
		String code = request.getParameter("code");
		
		//�õ���������session�е���ȷ��֤��
		HttpSession session = request.getSession(); //ȡ�õ�ǰ�Ự
		String yzm = (String) session.getAttribute("code"); //�ӻỰ��ȡ����ȷ����֤��
		//�ù�����֤����Ч,����
		session.removeAttribute("code");
		
		if ( yzm == null ||  !yzm.equalsIgnoreCase(code)  ) {
			request.setAttribute("login_msg", "��֤����Ч");
			//ִ��ת��
			request.getRequestDispatcher("../../index.jsp").forward(request, response);;
		    return;
		}
		
		
//	     System.out.println("account=" +account);
//	     System.out.println("password=" +password);
//
//	     System.out.println("code=" +code);	
		//����ҵ���߼���Ķ���
		UserModel user= new UserModel();
		user.setAccount(account);
		try {
			user.setLogpwd(GDMSUtil.getMD5(password.getBytes()));
		} catch (Exception e1) {
			// TODO �Զ����ɵ� catch ��
			e1.printStackTrace();
		}
		
		//���ݸ�ҵ���߼������
		
		UserService userservice = new UserServiceImpl();
		
		UserModel loginuser = userservice.login(user);
		if (loginuser != null) {
			//�û���¼�ɹ��Ͱ��û��������Ự��
			session.setAttribute("loginUser", loginuser); //��¼�ɹ��Ͱ��û����session�Ự��
			response.sendRedirect("../main.jsp");//�ض���main.jsp
		}else {
			request.setAttribute("login_msg", "�û�����������Ч");//ִ��ת��
			//ִ��ת��
			request.getRequestDispatcher("../../index.jsp").forward(request, response);
		}

		
		
	}

	
}	