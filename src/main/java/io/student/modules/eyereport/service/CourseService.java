package io.student.modules.eyereport.service;

import java.util.List;
import java.util.Map;

import io.student.common.utils.PageUtils;
import io.student.modules.eyereport.entity.CourseInfo;

public interface CourseService {

	PageUtils queryPage(Map<String, Object> params);
	boolean delete(List<CourseInfo> courseInfos);
	boolean save(CourseInfo courseInfo);

	List<Map<String, Object>> getcourselist(Map<String, Object> param);
	
	PageUtils getstudentall(Map<String, Object> param);
	
	CourseInfo getcourseinfo(String Studentname);
	
}
