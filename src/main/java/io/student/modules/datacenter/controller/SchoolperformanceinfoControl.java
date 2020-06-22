package io.student.modules.datacenter.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.student.common.utils.PageUtils;
import io.student.common.utils.R;
import io.student.modules.datacenter.service.SchoolperformanceinfoService;
import io.student.modules.eyereport.service.SchoolclassinfoService;
import io.student.modules.sys.controller.AbstractController;

@RestController
@RequestMapping("/datacenter/schoolperformanceinfo")
public class SchoolperformanceinfoControl extends AbstractController {

	@Autowired
	private SchoolperformanceinfoService schoolperformanceinfoService;
	
	@GetMapping("/list")
	public R list(@RequestParam Map<String, Object> params){
		PageUtils page = schoolperformanceinfoService.queryPage(params);

		return R.ok().put("page", page);
	}
	
	
}
