package io.student.modules.datacenter.dao;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;

import io.student.modules.datacenter.entity.StudentCourseInfo;

import java.util.List;
import java.util.Map;

@Mapper
public interface StudentCourseInfoDao extends BaseMapper<StudentCourseInfo> {
    int deleteCourseByStudentId(String student_id);
    List<Map<String, Object>> getCourseByTeacher(Map<String, Object> params);

}
