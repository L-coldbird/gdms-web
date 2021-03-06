package com.oracle.gdms.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;
import com.oracle.gdms.dao.GoodsDao;
import com.oracle.gdms.entity.GoodsEntity;
import com.oracle.gdms.entity.GoodsModel;
import com.oracle.gdms.entity.PageModel;
import com.oracle.gdms.entity.ResponseEntity;
import com.oracle.gdms.service.GoodsService;
import com.oracle.gdms.util.GDMSUtil;
import com.oracle.gdms.web.listener.AppListener;

public class GoodsServiceImpl extends BaseService implements GoodsService {

	@Override
	public PageModel<GoodsModel> findByPage(int pageNumber, int rows) {
		PageModel<GoodsModel> obj = new PageModel<GoodsModel>() ;
		obj.setCurrent(pageNumber);   //当前页填充
		try {
			session = GDMSUtil.getSession();
			GoodsDao dao = session.getMapper(GoodsDao.class);
			//查询总记录行数
			int count = dao.findCount(); //查询总行数
            int total = count% rows ==0 ? count/rows : count/rows+1;   //计算总页数
            obj.setTotal(total);
            
            int offset = (pageNumber - 1)*rows;
            
            Map<String,Integer> map = new HashMap<>();
            map.put("offset", offset);
            map.put("rows",  rows);
            obj.setData(dao.findByPage(map));  //查出数据集
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			free();
		}
		return obj;
	}
  /*   public static void main(String[] args) {
		 GoodsService ser =  new GoodsServiceImpl();
		 PageModel<GoodsModel> p = ser.findByPage(3, 2);
		 System.out.println("总页数=" + p.getTotal());
		 for ( GoodsModel m : p.getData()) {
			 System.out.println("goodsid=" + m.getGoodsid() +  "\n"  +"name="+ m.getName());
			 System.out.println("类别是" + m.getType().getName());
		 }
	}*/

	@Override
	public String pushGoods(int goodsid) {
		try {
			session = GDMSUtil.getSession();
			GoodsDao dao = session.getMapper(GoodsDao.class);
			GoodsModel goods = dao.findById(goodsid);
			JSONObject json = new JSONObject();
			json.put("goods", goods);
			String jsonstr = json.toJSONString();
			ResponseEntity result =  push(jsonstr);  //执行推送
			if( result !=null && result.getCode() == 0) {
				dao.updatePush(goodsid);     //如果推送成功就更新push表为已推送
			    session.commit();
			}
			return result.getMessage();
		}   
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			free();
		}
		return "推送失败";
		

	}

	private ResponseEntity push(String jsonstr) {
		String url = AppListener.getString("pushurl");
		
		HttpPost post = new HttpPost(url);
		StringEntity entity = new StringEntity(jsonstr,"UTF-8") ;
		entity.setContentType("application/json"); 
		post.setEntity(entity);
		HttpClient client = new DefaultHttpClient();
	
		try {
			HttpResponse resp = client.execute(post);
			HttpEntity resent =  resp.getEntity();
			
			String str = EntityUtils.toString(resent);
			
			ResponseEntity re = JSONObject.parseObject(str,ResponseEntity.class);
//			System.out.println("code=" +re.getCode() + "msg=" +re.getMessage());
			return re;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int add(GoodsEntity goods) {
		try {
		session = GDMSUtil.getSession();
		GoodsDao dao = session.getMapper(GoodsDao.class);
		int c = dao.add(goods);
		session.commit();
		return  c;
	} 
	catch (Exception e) {
		e.printStackTrace();
	}
	finally {
		free();
	}
		return 0;
	}
}
