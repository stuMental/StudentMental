package io.student.modules.datacenter.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;

import io.student.common.utils.PageUtils;
import io.student.common.utils.R;
import io.student.common.validator.ValidatorUtils;
import io.student.modules.datacenter.entity.CameraroomEntity;
import io.student.modules.datacenter.service.CameraroomService;
import io.student.modules.sys.controller.AbstractController;

@RestController
@RequestMapping("/datacenter/camera")
public class CameraroomController extends AbstractController {
	@Autowired
	private CameraroomService CameraroomService;
	
	/**
	 * 所有配置列表
	 */
	@GetMapping("/list")
	public R list(@RequestParam Map<String, Object> params){
		
		PageUtils page = CameraroomService.queryPage(params);

		return R.ok().put("page", page);
	}
	
	
	@GetMapping("/info/{id}")
	public R info(@PathVariable("id") Long id){
	CameraroomEntity cameraroomEntity=CameraroomService.selectById(id);
	return R.ok().put("camer", cameraroomEntity);
		
	}
	
	@PostMapping("/save")
	public R save(@RequestBody CameraroomEntity cameraroomEntity)
	{
		ValidatorUtils.validateEntity(cameraroomEntity);
	CameraroomEntity oldCameraroomEntity=	CameraroomService.selectOne(new EntityWrapper<CameraroomEntity>().eq("camera_addr", cameraroomEntity.getCameraAddr()));
		if(oldCameraroomEntity!=null)
		{
			return R.error("数据中已存在此摄像头地址");
		}
		
		CameraroomService.save(cameraroomEntity);
		return R.ok();
		
	}
	@PostMapping("/update")
	public R update(@RequestBody CameraroomEntity cameraroomEntity)
	{	
		ValidatorUtils.validateEntity(cameraroomEntity);
		CameraroomEntity cameraroomEntity2=CameraroomService.selectById(cameraroomEntity.getCameraId());
		if(cameraroomEntity2.getCameraAddr().equals(cameraroomEntity.getCameraAddr()))
		{
			CameraroomService.update(cameraroomEntity);
			return R.ok();
		}else {
			
			CameraroomEntity obs=CameraroomService.selectOne(new EntityWrapper<CameraroomEntity>().eq("camera_addr", cameraroomEntity.getCameraAddr()));
			if(obs!=null)
			{
				return R.error("数据中已存在此摄像头地址");
			}else {
				CameraroomService.update(cameraroomEntity);
				return R.ok();
				
			}
		}
		
		
		
	}
	

	@PostMapping("/delete")
	public R delete(@RequestBody Long[] ids){
		CameraroomService.deleteBatch(ids);
		return R.ok();
	}
	
}
