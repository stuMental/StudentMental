package io.student.modules.eyereport.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.student.common.exception.RRException;
import io.student.common.utils.PageUtils;
import io.student.common.utils.R;
import io.student.modules.eyereport.service.AttendanceService;
import io.student.modules.sys.service.SysDeptService;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {
	
	@Autowired
	private AttendanceService attendanceService;
	
	@Autowired
	private SysDeptService sysDeptService;
	
	@PostMapping("list")
	public R list(@RequestBody Map<String, Object> param)
	{
		Integer deptid=(Integer)param.get("deptId");
		if(deptid==null||deptid==0)
		{
			throw new RRException("不能为根目录");
		} 
		param.putAll(sysDeptService.getgrade(deptid.longValue()));
		
		List<Map<String, Object>> list=attendanceService.list(param);
		return R.ok().put("data", list);
	}

}
