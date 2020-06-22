package io.student.modules.datacenter.dao;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;

import io.student.modules.datacenter.entity.CameraroomEntity;

@Mapper
public interface CameraroomDao  extends BaseMapper<CameraroomEntity> {

}
