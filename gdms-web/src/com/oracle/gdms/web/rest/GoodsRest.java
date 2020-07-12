package com.oracle.gdms.web.rest;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

import com.oracle.gdms.entity.ResponseEntity;

@Path ("/goods")
public class GoodsRest {
   
	
	@Path("/push")

	@POST
	public String pushGoods(String jsonstr) {
		
		System.out.println("hello world     ppp==" + jsonstr);
	
		return "ÍÆËÍ³É¹¦";
	}
	@Path("/test")
	@POST
	public String aaa(ResponseEntity r) {
		System.out.println("r.code="+r.getCode()+"r.message"+r.getMessage());

		return "ok";
	}
	
	
}
