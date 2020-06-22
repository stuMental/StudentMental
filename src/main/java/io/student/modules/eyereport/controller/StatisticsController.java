package io.student.modules.eyereport.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.student.common.utils.R;
import io.student.modules.eyereport.dao.ProDao;
import io.student.modules.eyereport.service.ReportService;
import io.student.modules.sys.controller.AbstractController;

@RestController
@RequestMapping("/eyereportdata/statistics")
public class StatisticsController extends AbstractController {
	
	@Autowired
	private ReportService reportService;
	
	@PostMapping("list")
	public R getstatisticspage(@RequestBody Map<String, Object> params)
	{
		return R.ok().put("data", reportService.getstatisticspage(params));
	}
	
	@GetMapping("listss")
	public R getstatistics()
	{
		return R.ok().put("data", reportService.getstatistics());
	}

}
