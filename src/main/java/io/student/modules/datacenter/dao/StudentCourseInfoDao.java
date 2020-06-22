package io.student.modules.datacenter.dao;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;

import io.student.modules.datacenter.entity.StudentCourseInfo;

@Mapper
public interface StudentCourseInfoDao extends BaseMapper<StudentCourseInfo> {
    int deleteCourseByStudentId(String student_id);
}
