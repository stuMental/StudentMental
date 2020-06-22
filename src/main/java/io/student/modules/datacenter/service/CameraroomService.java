package io.student.modules.datacenter.service;

import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

import io.student.common.utils.PageUtils;
import io.student.modules.datacenter.entity.CameraroomEntity;

public interface CameraroomService extends IService<CameraroomEntity> {
	PageUtils queryPage(Map<String, Object> params);

	public void save(CameraroomEntity cameraroomEntity);
	public void update(CameraroomEntity cameraroomEntity);
	public void deleteBatch(Long[] ids) ;

}
