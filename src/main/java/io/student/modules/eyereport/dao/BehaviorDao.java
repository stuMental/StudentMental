package io.student.modules.eyereport.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BehaviorDao {
	List<Map<String, Object>> getheaddata(Map<String, Object> param);
	List<Map<String, Object>> getbodydata(Map<String, Object> param);
	List<Map<String, Object>> getsmalldata(Map<String, Object> param);

}
