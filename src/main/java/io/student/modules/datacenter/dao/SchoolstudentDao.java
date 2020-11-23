package io.student.modules.datacenter.dao;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;

import io.student.modules.datacenter.entity.SchoolstudentEntity;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SchoolstudentDao extends BaseMapper<SchoolstudentEntity> {
    /**
     * 查询id
     * @param studentId
     * @param studentRoomId
     */
    SchoolstudentEntity queryId(@Param("studentId") Long studentId, @Param("studentRoomId") Long studentRoomId);
}
