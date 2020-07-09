package com.oracle.gdms.web.action;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.oracle.gdms.entity.ResponseEntity;
import com.oracle.gdms.util.GDMSUtil;

@WebServlet("/admin/user")
public class UserAdmin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		request.setCharacterEncoding("utf-8");
		FileItemFactory fileItemFactory = new DiskFileItemFactory();
		// ���������д����ĸ�������
		ServletFileUpload sfu = new ServletFileUpload(fileItemFactory);
		sfu.setFileSizeMax(102400); // ָ���ļ����ߴ�
		// ָ��һ������·��
		String path = this.getServletContext().getRealPath("/images");
		path += "/upload";
//		System.out.println("path=" + path);
		File f = new File(path);
		if (!f.exists()) {
			f.mkdir(); // �������ļ��в����ڣ��ʹ�����
		}

		try {
			// ��ʼ����
			List<DiskFileItem> list = sfu.parseRequest(request);
			for (DiskFileItem item : list) {
				if (!item.isFormField()) { // ������item�����Ǳ���Ͱ������ļ�����
					String fileName = GDMSUtil.generic(24);// ����һ��������ļ���
					int i = item.getName().lastIndexOf("."); // ���ҵ��ļ���������һ��С�����
					fileName += item.getName().substring(i);// �����λ�ÿ�ʼ�Ѻ��������ȡ��������׺��
					File file = new File(path + "/" + fileName);

					item.write(file); // ��Դ�ļ�д�뵽�����ļ���

					ResponseEntity entity = new ResponseEntity();
					entity.setCode(0);
					entity.setMessage(fileName);
					entity.setData("<img width='200' height='200' src='images/upload/" + fileName + "'>");
					JSONObject j = new JSONObject();
					j.put("entity", entity);
					response.setContentType("application/json; charset=UTF-8");
					response.getWriter().print(j.toJSONString()); // ���ϴ��ɹ������ļ����ַ���
					System.out.println(file.getAbsolutePath());

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().print("�ϴ�ʧ��");
		}
	}
	// public static void main(String[] args) {
	// //����һ��JSON
	// JSONObject json = new JSONObject();
	// json.put("name", "zuheng");
	// json.put("sex", "��");
	//
	// System.out.println(json.toJSONString());
	//
	// }
	//

}
