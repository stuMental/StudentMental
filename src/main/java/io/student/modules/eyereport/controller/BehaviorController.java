package io.student.modules.eyereport.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.student.common.utils.R;
import io.student.modules.eyereport.service.BehaviorService;
import io.student.modules.sys.controller.AbstractController;
import io.student.modules.sys.dao.SysDeptDao;
import io.student.modules.sys.entity.SysDeptEntity;
import io.student.modules.sys.service.SysDeptService;

@RestController
@RequestMapping("/behavior")
public class BehaviorController extends AbstractController {
	@Autowired
	private BehaviorService behaviorService;

	@Autowired
	private SysDeptService sysDeptService;

	@PostMapping("/list") //class-id
	public R list(@RequestBody Map<String, Object> param) {
		Integer deptid = (Integer) param.get("deptid");
//		if (deptid !=0) {
//			SysDeptEntity sysDeptEntity = sysDeptService.queryObject(deptid.longValue());
//			if (sysDeptEntity.getParentId() == null || sysDeptEntity.getParentId() == 0) {
//				
//				List<Long> depts=sysDeptService.queryDetpIdList(deptid.longValue());
//				param.put("class_name", StringUtils.join(depts,","));
//			}else {
//				param.put("class_name", sysDeptEntity.getDeptId());
//			}
//
//		}
         param.putAll(sysDeptService.getgrade(deptid.longValue()));
		R r = new R().ok();
		r.put("headdata", behaviorService.getheaddata(param));
		r.put("bodydata", behaviorService.getbodydata(param));
		r.put("smalldata", behaviorService.getsmalldata(param));
		return r;
	}

}
