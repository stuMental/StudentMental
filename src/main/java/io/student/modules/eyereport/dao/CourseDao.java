package io.student.modules.eyereport.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import io.student.modules.eyereport.entity.CourseInfo;

@Mapper
public interface CourseDao extends BaseMapper<CourseInfo> {
	List<Map<String, Object>> studentall(Map<String, Object> param);
	Integer studentalltotal(Map<String, Object> param);

}
