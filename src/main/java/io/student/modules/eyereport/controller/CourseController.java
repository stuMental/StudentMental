package io.student.modules.eyereport.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import io.student.common.exception.RRException;
import io.student.common.utils.Constant;
import io.student.common.utils.PageUtils;
import io.student.common.utils.R;
import io.student.modules.eyereport.entity.CourseInfo;
import io.student.modules.eyereport.service.AttendanceService;
import io.student.modules.eyereport.service.CourseService;
import io.student.modules.sys.controller.AbstractController;
import io.student.modules.sys.entity.SysDeptEntity;
import io.student.modules.sys.service.SysDeptService;

@RestController
@RequestMapping("/course")
public class CourseController  extends AbstractController  {
	
	@Autowired
	private CourseService courseService;
	@Autowired
	private SysDeptService sysDeptService;
	
	@Autowired AttendanceService attendanceService;
	
	@GetMapping("/list")
	public R list(@RequestParam Map<String, Object> params)
	{
		PageUtils page = courseService.queryPage(params);
		return R.ok().put("page", page);
	}
	
	
	@PostMapping("/save")
	public R save(@RequestBody CourseInfo params)
	{
		if(courseService.save(params))
		{
			return R.ok();
		}else {
			
			return R.error();
		}
	}
	
	
	@GetMapping("/info")
	public R info(@RequestParam Map<String, Object> params)
	{
		return R.ok();
	}
	
	@PostMapping("/delete")
	public R delete(@RequestBody List<CourseInfo> courseInfos)
	{
		if(courseService.delete(courseInfos))
		{
			return R.ok();
		}else
		{
			return R.error();
		}
		
	}
	
	@PostMapping("/courselist")
	public R getcourselist(@RequestBody Map<String, Object> param)
	{
		Integer deptid = (Integer) param.get("deptid");
		if(deptid==null)
		{
			throw new RRException("deptid不能为空");
		}
		param.putAll(sysDeptService.getgrade(deptid.longValue()));
		if(getUserId()!=Constant.SUPER_ADMIN)
		{param.put("teacher_id", getUser().getUsername());}
		return R.ok().put("data", attendanceService.getstudentlist(param));
	}
	@PostMapping("/courselistt")
	public R getcourselistt(@RequestBody Map<String, Object> param)
	{
		Integer deptid = (Integer) param.get("deptid");
		if(deptid==null)
		{
			throw new RRException("deptid不能为空");
		}
		param.putAll(sysDeptService.getgrade(deptid.longValue()));
		if(getUserId()!=Constant.SUPER_ADMIN)
		{param.put("teacher_id", getUser().getUsername());}
		return R.ok().put("data", attendanceService.getstudentlist2(param));
	}
	
	@GetMapping("studentall")
	public R getstudentall(@RequestParam Map<String, Object> param)
	{Integer deptid =Integer.parseInt((String) param.get("deptId"));
	if(deptid==0)
	{
		throw new RRException("deptid不能为空");
	}
	param.putAll(sysDeptService.getgrade(deptid.longValue()));
	
	
	return R.ok().put("data", courseService.getstudentall(param));
	}

}
