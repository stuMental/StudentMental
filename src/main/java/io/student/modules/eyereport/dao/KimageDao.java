package io.student.modules.eyereport.dao;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;

import io.student.modules.datacenter.entity.Kimage;

@Mapper
public interface KimageDao extends BaseMapper<Kimage> {

}
