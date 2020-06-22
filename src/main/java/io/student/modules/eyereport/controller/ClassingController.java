package io.student.modules.eyereport.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.student.common.utils.R;
import io.student.modules.sys.controller.AbstractController;
@RestController
@RequestMapping("/classing")
public class ClassingController extends AbstractController {
	
	
	
	
	@PostMapping("list")
	public R getlist(@RequestBody Map<String, Object> param)
	{
		return R.ok();
	}

}
