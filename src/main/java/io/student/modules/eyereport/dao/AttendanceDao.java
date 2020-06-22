package io.student.modules.eyereport.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;



@Mapper
public interface AttendanceDao  {
	
	List<Map<String, Object>> getabsencename(Map<String, Object> param);
	List<Map<String, Object>> getattendancename(Map<String, Object> param);
	List<Map<String, Object>> getstudentlist(Map<String, Object> param);
	List<Map<String, Object>> getstudentlist2(Map<String, Object> param);

}
