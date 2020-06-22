package io.student.modules.eyereport.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.student.common.utils.R;
import io.student.modules.eyereport.service.ReportService;
import io.student.modules.sys.controller.AbstractController;
import io.student.modules.sys.service.SysDeptService;

@RestController
@RequestMapping("/eyereportdata/teacheff")
public class TeacheffController extends AbstractController {

	@Autowired
	private ReportService reportService;
	
	@Autowired
	private SysDeptService sysDeptService;
	
	@PostMapping("Radarjxxg")
	public R getRadarjxxg(@RequestBody Map<String, Object> param)
	{
		param.putAll(sysDeptService.getgrade(Long.parseLong(param.get("deptid").toString())));
		
		
		
		return R.ok().put("data", reportService.getRadarjxxg(param));
	}
	
	
	@PostMapping("xsqxzt")
	public R getxsqxzt(@RequestBody Map<String,Object> param)
	{
		param.putAll(sysDeptService.getgrade(Long.parseLong(param.get("deptid").toString())));
		return R.ok().put("data",reportService.getxsqxzts(param));
	}
	
	@PostMapping("xsqxztqx")
	public R getxsqxztqx(@RequestBody Map<String,Object> param)
	{
		param.putAll(sysDeptService.getgrade(Long.parseLong(param.get("deptid").toString())));
		return R.ok().put("data",reportService.getxsqxztqx(param));
	}
	@PostMapping("xsxxzt")
	public R getxsxxzt(@RequestBody Map<String,Object> param)
	{
		param.putAll(sysDeptService.getgrade(Long.parseLong(param.get("deptid").toString())));
		return R.ok().put("data",reportService.getxsxxzt(param));
	}
	
	@PostMapping("xsxxztqx")
	public R getxsxxztqx(@RequestBody Map<String,Object> param)
	{
		param.putAll(sysDeptService.getgrade(Long.parseLong(param.get("deptid").toString())));
		return R.ok().put("data",reportService.getxsxxztqx(param));
	}
	
	@PostMapping("xsjszt")
	public R getxsjszt(@RequestBody Map<String,Object> param)
	{
		param.putAll(sysDeptService.getgrade(Long.parseLong(param.get("deptid").toString())));
		return R.ok().put("data",reportService.getxsjszt(param));
	}
	
	@PostMapping("xsjsztqx")
	public R getxsjsztqx(@RequestBody Map<String,Object> param)
	{
		param.putAll(sysDeptService.getgrade(Long.parseLong(param.get("deptid").toString())));
		return R.ok().put("data",reportService.getxsjsztqx(param));
	}
	
	
	@PostMapping("ktjjx")
	public R getktjjx(@RequestBody Map<String,Object> param)
	{
		param.putAll(sysDeptService.getgrade(Long.parseLong(param.get("deptid").toString())));
		return R.ok().put("data",reportService.getktjjx(param));
	}
	
	@PostMapping("ktjjxqx")
	public R getktjjxqx(@RequestBody Map<String,Object> param)
	{
		param.putAll(sysDeptService.getgrade(Long.parseLong(param.get("deptid").toString())));
		return R.ok().put("data",reportService.getktjjxqx(param));
	}
	
	@PostMapping("ktzzd")
	public R getktzzd(@RequestBody Map<String,Object> param)
	{
		param.putAll(sysDeptService.getgrade(Long.parseLong(param.get("deptid").toString())));
		return R.ok().put("data",reportService.getktzzd(param));
	}
	
	@PostMapping("ktzzdqx")
	public R getktzzdqx(@RequestBody Map<String,Object> param)
	{
		param.putAll(sysDeptService.getgrade(Long.parseLong(param.get("deptid").toString())));
		return R.ok().put("data",reportService.getktzzdqx(param));
	}
	@PostMapping("kthdx")
	public R getkthdx(@RequestBody Map<String,Object> param)
	{
		param.putAll(sysDeptService.getgrade(Long.parseLong(param.get("deptid").toString())));
		return R.ok().put("data",reportService.getkthdx(param));
	}
	
	@PostMapping("kthdxqx")
	public R getkthdxqx(@RequestBody Map<String,Object> param)
	{
		param.putAll(sysDeptService.getgrade(Long.parseLong(param.get("deptid").toString())));
		return R.ok().put("data",reportService.getkthdxqx(param));
	}
	@PostMapping("ktztxxfx")
	public R getktztxxfx(@RequestBody Map<String,Object> param)
	{
		param.putAll(sysDeptService.getgrade(Long.parseLong(param.get("deptid").toString())));
		return R.ok().put("data",reportService.getktztxxfx(param));
	}
}
