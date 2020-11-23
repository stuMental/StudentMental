package io.student.modules.datacenter.dao;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;

import io.student.modules.datacenter.entity.CameraroomEntity;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CameraroomDao  extends BaseMapper<CameraroomEntity> {
    /**
     * 查询id
     * @param roomAddr    教室地址
     * @param cameraAddr  摄像头地址
     * @param dockerName  docker名称
     */
    Long queryCameraId(@Param("roomAddr") String roomAddr, @Param("cameraAddr") String cameraAddr, @Param("dockerName") String dockerName);

}
