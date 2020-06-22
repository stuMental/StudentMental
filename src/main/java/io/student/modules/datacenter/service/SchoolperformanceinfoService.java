package io.student.modules.datacenter.service;

import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

import io.student.common.utils.PageUtils;
import io.student.modules.datacenter.entity.Schoolperformanceinfo;
import io.student.modules.eyereport.entity.Scamerainfo;

public interface SchoolperformanceinfoService extends IService<Schoolperformanceinfo> {

	PageUtils queryPage(Map<String, Object> params);

}
