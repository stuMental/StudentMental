package io.student.modules.datacenter.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.student.common.utils.PageUtils;
import io.student.common.utils.R;
import io.student.modules.datacenter.service.SchoolawardinfoService;
import io.student.modules.sys.controller.AbstractController;

@RestController
@RequestMapping("/datacenter/Schoolaward")
public class SchoolawardController extends AbstractController {
	
	@Autowired
	private SchoolawardinfoService SchoolawardinfoService;
	
	
	
	@GetMapping("/list")
	public R list(@RequestParam Map<String, Object> params){
		PageUtils page = SchoolawardinfoService.queryPage(params);

		return R.ok().put("page", page);
	}
}
