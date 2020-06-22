package io.student.modules.eyereport.service;

import java.util.List;
import java.util.Map;

public interface BehaviorService {
	
	List<Map<String, Object>> getheaddata(Map<String, Object> param);

	List<Map<String, Object>> getbodydata(Map<String, Object> param);
	List<Map<String, Object>> getsmalldata(Map<String, Object> param);

}
