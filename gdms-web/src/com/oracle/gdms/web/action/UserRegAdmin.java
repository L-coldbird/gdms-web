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
	    
		
		//所有提交过来的参数接受以后封装成一个usermodel实体
		String account = request.getParameter("name");
		String password = request.getParameter("password");
		String password2 = request.getParameter("password2");
		String realname = request.getParameter("realname");
		String slogo = request.getParameter("slogo");
		String idnumber = request.getParameter("idnumber");
		String place = request.getParameter("prov");
		String city = request.getParameter("city");
		 
		int pid =Integer.parseInt(place);  //省份id
		String pname = new AreaServiceImpl().findNameById(pid).getName(); //查询出省份名称
		int cid = Integer.parseInt(city); //城市的id
		String cname = new AreaServiceImpl().findNameById(cid).getName(); //查询出城市名称
		place = pname +"-" +cname;
//		System.out.println(place);
//		if(1 == 1) return ;
		
		String code = request.getParameter("code");
		
		//拿到服务器的session中的正确验证码
		HttpSession session = request.getSession(); //取得当前会话
		String yzm = (String) session.getAttribute("code"); //从会话中取得正确的验证码
		
		//用过的验证码无效，销毁
		session.removeAttribute("code");
		
		if ( yzm == null ||   !yzm.equalsIgnoreCase(code)  ) {
			request.setAttribute("err_msg", "验证码无效");
			//执行转发
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
		
		//传给业务逻辑层的对象
		UserModel user= new UserModel();
		user.setAccount(account);
		try {
			user.setLogpwd(GDMSUtil.getMD5(password.getBytes()));
		} catch (Exception e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}
		user.setIdnumber(idnumber);
		user.setPhoto(slogo);
		user.setPlace(place);
		user.setRealname(realname);
		
		//取得性别
		char c= idnumber.charAt(idnumber.length()-2);//取身份证倒数第二位判断性别
		int i = Integer.parseInt(String.valueOf(c));//c转化成字符串再转换成int
		String sex = i % 2 == 0? "女":"男";
		
		user.setRowflag("oraU" + GDMSUtil.generic(20));
		user.setRoleid(2);
		user.setSex(sex);
		
		//取得生日
		String s = idnumber.substring(6,14); //取子串  包含第6个不包含第14个
		//把字符串类型的日期转为Timestamp类型
//		SimpleDateFormat sdf = new SimpleDateFormat();
		try {
			int yy = Integer.parseInt(s.substring(0,4)); //获取年份
			int mm = Integer.parseInt(s.substring(4,6)); //获取月份
			int dd = Integer.parseInt(s.substring(6));  //获取天数
			Date d = new Date(yy-1900,mm-1,dd);
			Timestamp birthday = new Timestamp(d.getTime());//gettime拿到毫秒数
			Timestamp regtime = GDMSUtil.now();
			user.setBirthday(birthday);
			user.setRegtime(regtime);
			user.setLastlogin(regtime);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		user.setStatus(false);
		
//		传递给业务逻辑层对象
		
		UserService userservice = new UserServiceImpl();
		int count = userservice.add(user);
		if( count > 0){
			request.setAttribute("msg", "恭喜注册成功");
			
		}else {
			request.setAttribute("err_msg", "sorry,注册失败，稍后尝试");
		}
		
		request.getRequestDispatcher("../../index.jsp").forward(request, response);
		
	}

	
}	