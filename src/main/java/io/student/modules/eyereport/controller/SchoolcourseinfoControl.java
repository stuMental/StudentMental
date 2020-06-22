package io.student.modules.eyereport.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.student.common.utils.PageUtils;
import io.student.common.utils.R;
import io.student.common.validator.ValidatorUtils;
import io.student.modules.eyereport.entity.Schoolcourseinfo;
import io.student.modules.eyereport.service.SchoolclassinfoService;
import io.student.modules.eyereport.service.SchoolcourseinfoService;
import io.student.modules.sys.controller.AbstractController;
import io.student.modules.sys.entity.SysDeptEntity;
import io.student.modules.sys.service.SysDeptService;

@RestController
@RequestMapping("/eyereportdata/schoolcourseinfo")
public class SchoolcourseinfoControl extends AbstractController {

	@Autowired
	private SchoolcourseinfoService schoolcourseinfoService;
	@Autowired
	private SysDeptService sysDeptService;
	
	@GetMapping("/list")
	public R list(@RequestParam Map<String, Object> params){
		long deptid = 0;
		try {
			deptid = Long.parseLong(params.get("dept_id").toString());
		} catch (Exception e) {
			deptid = 0;
		}

		// 判断是否root
		if (deptid != 0) {
			List<Long> deptids = sysDeptService.queryDetpIdList(deptid);
			// 判断是否子节点
			if (deptids.size() != 0) {
				return R.error("请选择子节点");
			} else if (deptids.size() == 0) {
				SysDeptEntity deptEntity = sysDeptService.queryObject(deptid);
				params.put("class_name", deptEntity.getName());
				SysDeptEntity deptparent=sysDeptService.queryObject(deptEntity.getParentId());
				params.put("grade_name", deptparent.getName());
			}
		}
		
		PageUtils page = schoolcourseinfoService.queryPage(params);

		return R.ok().put("page", page);
	}
	@GetMapping("/info/{id}")
	public R  info(@PathVariable(value="id") String id) {
		try {
			Schoolcourseinfo schoolcourseinfo=schoolcourseinfoService.queryobject(id);
			return R.ok().put("data",schoolcourseinfo );
		} catch (Exception e) {
			return R.error(e.toString());
		}
		
	}
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	public R save(@RequestBody Schoolcourseinfo schoolcourseinfo)
	{
		try {
			SysDeptEntity sysDeptEntity=sysDeptService.queryObject(Long.parseLong(schoolcourseinfo.getClass_name()));
			if(sysDeptEntity==null)
			{
				return R.error("请选择班级");
			}
			schoolcourseinfo.setClass_name(sysDeptEntity.getName());
			schoolcourseinfo.setGrade_name(sysDeptService.queryObject(sysDeptEntity.getParentId()).getName());
			schoolcourseinfo.setDt(new Date());
			
			schoolcourseinfoService.insertobj(schoolcourseinfo);
			
			return R.ok();
		} catch (Exception e) {
			return R.error(e.toString());
		}
		
	}
	
	/**
	 * 修改
	 */
	@PostMapping("/update")
	public R update(@RequestBody Schoolcourseinfo schoolcourseinfo) {
		try {
			SysDeptEntity sysDeptEntity=sysDeptService.queryObject(Long.parseLong(schoolcourseinfo.getClass_name()));
			if(sysDeptEntity==null)
			{
				return R.error("请选择班级");
			}
			schoolcourseinfo.setClass_name(sysDeptEntity.getName());
			schoolcourseinfo.setGrade_name(sysDeptService.queryObject(sysDeptEntity.getParentId()).getName());
			schoolcourseinfo.setDt(new Date());
			ValidatorUtils.validateEntity(schoolcourseinfo);
			
			schoolcourseinfoService.update(schoolcourseinfo);
			
			return R.ok();
		} catch (Exception e) {
			return R.error(e.toString());
		}
	}
	
	/**
	 * 删除
	 */
	@PostMapping("/delete")
	public R delete(@RequestBody Long[] ids){
		schoolcourseinfoService.deleteBatch(ids);
		return R.ok();
	}
	
	
}
