package io.student.modules.datacenter.dao;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;

import io.student.modules.datacenter.entity.SchoolstudentEntity;

@Mapper
public interface SchoolstudentDao extends BaseMapper<SchoolstudentEntity> {

}
