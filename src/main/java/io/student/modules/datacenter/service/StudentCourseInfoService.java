package io.student.modules.datacenter.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

import io.student.common.utils.PageUtils;
import io.student.modules.datacenter.entity.CameraroomEntity;
import io.student.modules.datacenter.entity.StudentCourseInfo;

public interface StudentCourseInfoService  extends IService<StudentCourseInfo>{
	PageUtils queryPage(Map<String, Object> params);
	public void deleteBatch(Long[] ids) ;
	public void save(StudentCourseInfo studentCourseInfo);
	public void update(StudentCourseInfo studentCourseInfo);
	public List<Map<String, Object>> dict(Map<String, Object> params);
}
