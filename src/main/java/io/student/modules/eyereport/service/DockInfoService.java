package io.student.modules.eyereport.service;

import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

import io.student.common.utils.PageUtils;
import io.student.modules.eyereport.entity.DockInfo;

public interface DockInfoService extends IService<DockInfo> {

	int deleteBatch(Long[] ids);
	int update(DockInfo dockInfo);
	int save(DockInfo dockInfo);
	DockInfo selectById(Long id);
	PageUtils  queryPage(Map<String, Object> params);
	boolean querydocker(String[] ids);
	DockInfo selectbycamerid(String id);
	
}
