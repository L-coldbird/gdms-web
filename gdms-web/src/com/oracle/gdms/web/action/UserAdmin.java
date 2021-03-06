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
		// 解析请求中传来的附件对象
		ServletFileUpload sfu = new ServletFileUpload(fileItemFactory);
		sfu.setFileSizeMax(102400); // 指定文件最大尺寸
		// 指定一个保存路径
		String path = this.getServletContext().getRealPath("/images");
		path += "/upload";
//		System.out.println("path=" + path);
		File f = new File(path);
		if (!f.exists()) {
			f.mkdir(); // 如果这个文件夹不存在，就创建它
		}

		try {
			// 开始解析
			List<DiskFileItem> list = sfu.parseRequest(request);
			for (DiskFileItem item : list) {
				if (!item.isFormField()) { // 如果这个item对象不是表单项，就把他当文件处理
					String fileName = GDMSUtil.generic(24);// 生成一个随机的文件名
					int i = item.getName().lastIndexOf("."); // 先找到文件名中最后的一个小数点的
					fileName += item.getName().substring(i);// 从这个位置开始把后面的内容取出来做后缀名
					File file = new File(path + "/" + fileName);

					item.write(file); // 把源文件写入到到新文件中

					ResponseEntity entity = new ResponseEntity();
					entity.setCode(0);
					entity.setMessage(fileName);
					entity.setData("<img width='200' height='200' src='images/upload/" + fileName + "'>");
					JSONObject j = new JSONObject();
					j.put("entity", entity);
					response.setContentType("application/json; charset=UTF-8");
					response.getWriter().print(j.toJSONString()); // 把上传成功的新文件名字返回
					System.out.println(file.getAbsolutePath());

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().print("上传失败");
		}
	}
	// public static void main(String[] args) {
	// //构造一个JSON
	// JSONObject json = new JSONObject();
	// json.put("name", "zuheng");
	// json.put("sex", "男");
	//
	// System.out.println(json.toJSONString());
	//
	// }
	//

}
