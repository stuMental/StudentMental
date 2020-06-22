package io.student.modules.eyereport.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;

import io.student.common.utils.PageUtils;
import io.student.common.utils.R;
import io.student.modules.app.annotation.Login;
import io.student.modules.eyereport.service.LogService;
import io.student.modules.sys.controller.AbstractController;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/log")
public class LogController   extends AbstractController {
	
	@Autowired
	private LogService logservice;
	
	
	
	@GetMapping("list")
	public R list(@RequestParam Map<String, Object> params)
	{

		//logger.info( JSON.toJSONString(params));
		PageUtils page = logservice.queryPage(params); 
		return R.ok().put("page", page);
	}

}
