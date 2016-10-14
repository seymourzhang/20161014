package com.mtc.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mtc.app.service.BaseQueryService;

@Controller
@RequestMapping("/sys/test")
public class TestController {

	
	@Autowired
	private BaseQueryService mBaseQueryService;
	
	
	@RequestMapping("/test")
	@ResponseBody
	public String test(){
		
		
		return "BaseQueryService hashCode:" + mBaseQueryService.hashCode();
	}
}
