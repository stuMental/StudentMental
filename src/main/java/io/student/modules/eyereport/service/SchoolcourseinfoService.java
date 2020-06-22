package io.student.modules.eyereport.service;

import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

import io.student.common.utils.PageUtils;
import io.student.modules.eyereport.entity.Scamerainfo;
import io.student.modules.eyereport.entity.Schoolcourseinfo;

public interface SchoolcourseinfoService extends IService<Schoolcourseinfo> {

	PageUtils queryPage(Map<String, Object> params);
	Schoolcourseinfo queryobject(String id);
	int insertobj(Schoolcourseinfo schoolcourseinfo);
	int update(Schoolcourseinfo schoolcourseinfo);
	void deleteBatch(Long[] ids);

}
