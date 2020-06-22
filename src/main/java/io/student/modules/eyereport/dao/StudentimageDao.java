package io.student.modules.eyereport.dao;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;

import io.student.modules.datacenter.entity.Studentimage;

@Mapper
public interface StudentimageDao extends BaseMapper<Studentimage> {
    int deleteStudentImageByStudentId(String student_id);
}
