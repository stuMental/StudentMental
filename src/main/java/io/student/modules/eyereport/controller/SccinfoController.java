package io.student.modules.eyereport.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.student.common.annotation.SysLog;
import io.student.common.utils.PageUtils;
import io.student.common.utils.R;
import io.student.common.validator.ValidatorUtils;
import io.student.modules.eyereport.entity.DockInfo;
import io.student.modules.eyereport.entity.Scamerainfo;
import io.student.modules.eyereport.service.DockInfoService;
import io.student.modules.eyereport.service.SccinfoService;
import io.student.modules.sys.controller.AbstractController;
import io.student.modules.sys.entity.SysConfigEntity;
import io.student.modules.sys.entity.SysDeptEntity;
import io.student.modules.sys.service.SysDeptService;

@RestController
@RequestMapping("/eyereportdata/sccinfo")
public class SccinfoController extends AbstractController {

	@Autowired
	private SccinfoService sccinfoService;
	@Autowired
	private SysDeptService sysDeptService;
	
	@Autowired
	private DockInfoService dockInfoService;
	
	
	@GetMapping("/docker/list")
	public R dockerlist(@RequestParam Map<String, Object> params)
	{
		PageUtils page = dockInfoService.queryPage(params);

		return R.ok().put("page", page);
	}
	
	@GetMapping("/docker/info/{id}")
	public R dockerinfo(@PathVariable("id") Long id){
		DockInfo dockInfo = dockInfoService.selectById(id);
		
		return R.ok().put("data", dockInfo);
	}
	@PostMapping("/docker/save")
	public R dockersave(@RequestBody DockInfo dockInfo){
		ValidatorUtils.validateEntity(dockInfo);

		dockInfoService.save(dockInfo);
		
		return R.ok();
	}
	
	@PostMapping("/docker/update")
	public R dockerupdate(@RequestBody DockInfo dockInfo){
		ValidatorUtils.validateEntity(dockInfo);
		
		dockInfoService.update(dockInfo);
		
		return R.ok();
	}
	@PostMapping("/docker/delete")
	public R dockerdelete(@RequestBody Long[] ids){
		dockInfoService.deleteBatch(ids);
		
		return R.ok();
	}
	
	
	@GetMapping("/all")
	public R all()
	{
		return R.ok().put("data",sccinfoService.getall());
	}
	
	

	@GetMapping("/list")
	public R list(@RequestParam Map<String, Object> params) {
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

		PageUtils page = sccinfoService.queryPage(params);

		return R.ok().put("page", page);
	}

	@GetMapping("/info/{camera_id}")
	public R info(@PathVariable(value = "camera_id") Long camera_id) {
		try {
			Scamerainfo scamerainfo=sccinfoService.selectById(camera_id);
			

			return R.ok().put("data", scamerainfo);
		} catch (Exception e) {
			e.printStackTrace();
			return R.error(e.toString());
		}

	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	public R save(@RequestBody Scamerainfo scamerainfo){
		try {
			SysDeptEntity sysDeptEntity=sysDeptService.queryObject(Long.parseLong(scamerainfo.getClass_id()));
			if(sysDeptEntity==null)
			{
				return R.error("请选择班级");
			}
			scamerainfo.setClass_name(sysDeptEntity.getName());
			scamerainfo.setGrade_name(sysDeptService.queryObject(sysDeptEntity.getParentId()).getName());
			scamerainfo.setDt(new Date());
			sccinfoService.save(scamerainfo);		
			
		} catch (Exception e) {
			return R.error(e.toString());
			// TODO: handle exception
		}
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@PostMapping("/update")
	public R update(@RequestBody Scamerainfo scamerainfo){
		scamerainfo.setDt(new Date());
		ValidatorUtils.validateEntity(scamerainfo);
		
		sccinfoService.update(scamerainfo);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping("/delete")
	public R delete(@RequestBody Long[] ids){
		sccinfoService.deleteBatch(ids);
		return R.ok();
	}

}
