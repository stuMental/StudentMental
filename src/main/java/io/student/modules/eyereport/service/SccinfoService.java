package io.student.modules.eyereport.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

import io.student.common.utils.PageUtils;
import io.student.modules.eyereport.entity.Scamerainfo;

public interface SccinfoService extends IService<Scamerainfo> {

	PageUtils queryPage(Map<String, Object> params);
	 Scamerainfo selectById( Long camera_id);
	 int save(Scamerainfo scamerainfo);
	 int update(Scamerainfo scamerainfo);
	 void deleteBatch(Long[] ids);
	 List<Scamerainfo> getall();

}
