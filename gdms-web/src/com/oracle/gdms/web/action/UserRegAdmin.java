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


@WebServlet("/admin/user/reg.action")
public class UserRegAdmin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		request.setCharacterEncoding("utf-8");
	    
		
		//�����ύ�����Ĳ��������Ժ��װ��һ��usermodelʵ��
		String account = request.getParameter("name");
		String password = request.getParameter("password");
		String password2 = request.getParameter("password2");
		String realname = request.getParameter("realname");
		String slogo = request.getParameter("slogo");
		String idnumber = request.getParameter("idnumber");
		String place = request.getParameter("prov");
		String city = request.getParameter("city");
		 
		int pid =Integer.parseInt(place);  //ʡ��id
		String pname = new AreaServiceImpl().findNameById(pid).getName(); //��ѯ��ʡ������
		int cid = Integer.parseInt(city); //���е�id
		String cname = new AreaServiceImpl().findNameById(cid).getName(); //��ѯ����������
		place = pname +"-" +cname;
//		System.out.println(place);
//		if(1 == 1) return ;
		
		String code = request.getParameter("code");
		
		//�õ���������session�е���ȷ��֤��
		HttpSession session = request.getSession(); //ȡ�õ�ǰ�Ự
		String yzm = (String) session.getAttribute("code"); //�ӻỰ��ȡ����ȷ����֤��
		
		//�ù�����֤����Ч������
		session.removeAttribute("code");
		
		if ( yzm == null ||   !yzm.equalsIgnoreCase(code)  ) {
			request.setAttribute("err_msg", "��֤����Ч");
			//ִ��ת��
			request.getRequestDispatcher("../../index.jsp").forward(request, response);;
		    return;
		}
		
		
//	     System.out.println("account=" +account);
//	     System.out.println("password=" +password);
//	     System.out.println("password2=" +password2);
//	     System.out.println("realname=" +realname);
//	     System.out.println("slogo=" +slogo);
//	     System.out.println("idnumber=" +idnumber);
//	     System.out.println("place=" +place);
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
		user.setIdnumber(idnumber);
		user.setPhoto(slogo);
		user.setPlace(place);
		user.setRealname(realname);
		
		//ȡ���Ա�
		char c= idnumber.charAt(idnumber.length()-2);//ȡ���֤�����ڶ�λ�ж��Ա�
		int i = Integer.parseInt(String.valueOf(c));//cת�����ַ�����ת����int
		String sex = i % 2 == 0? "Ů":"��";
		
		user.setRowflag("oraU" + GDMSUtil.generic(20));
		user.setRoleid(2);
		user.setSex(sex);
		
		//ȡ������
		String s = idnumber.substring(6,14); //ȡ�Ӵ�  ������6����������14��
		//���ַ������͵�����תΪTimestamp����
//		SimpleDateFormat sdf = new SimpleDateFormat();
		try {
			int yy = Integer.parseInt(s.substring(0,4)); //��ȡ���
			int mm = Integer.parseInt(s.substring(4,6)); //��ȡ�·�
			int dd = Integer.parseInt(s.substring(6));  //��ȡ����
			Date d = new Date(yy-1900,mm-1,dd);
			Timestamp birthday = new Timestamp(d.getTime());//gettime�õ�������
			Timestamp regtime = GDMSUtil.now();
			user.setBirthday(birthday);
			user.setRegtime(regtime);
			user.setLastlogin(regtime);
		} catch (Exception e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		user.setStatus(false);
		
//		���ݸ�ҵ���߼������
		
		UserService userservice = new UserServiceImpl();
		int count = userservice.add(user);
		if( count > 0){
			request.setAttribute("msg", "��ϲע��ɹ�");
			
		}else {
			request.setAttribute("err_msg", "sorry,ע��ʧ�ܣ��Ժ���");
		}
		
		request.getRequestDispatcher("../../index.jsp").forward(request, response);
		
	}

	
}	