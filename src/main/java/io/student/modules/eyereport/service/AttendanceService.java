package io.student.modules.eyereport.service;

import java.util.List;
import java.util.Map;

import io.student.common.utils.PageUtils;

public interface AttendanceService {
	
	//PageUtils list(Map<String, Object> param);
	
	List<Map<String, Object>> list(Map<String, Object> param);
	
	List<Map<String, Object>> getstudentlist(Map<String, Object> param);
	List<Map<String, Object>> getstudentlist2(Map<String, Object> param);

}
