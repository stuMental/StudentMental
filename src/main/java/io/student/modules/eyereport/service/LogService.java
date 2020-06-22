package io.student.modules.eyereport.service;

import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

import io.student.common.utils.PageUtils;
import io.student.modules.eyereport.entity.Loginfo;

public interface LogService  extends IService<Loginfo> {
	
	
	PageUtils queryPage(Map<String, Object> params);
	

}
