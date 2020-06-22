package io.student.modules.datacenter.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

import io.student.common.utils.PageUtils;
import io.student.modules.datacenter.entity.CameraroomEntity;
import io.student.modules.datacenter.entity.SchoolstudentEntity;

public interface SchoolstudentService extends IService<SchoolstudentEntity> {
	PageUtils queryPage(Map<String, Object> params);

	public void save(SchoolstudentEntity schoolstudentEntity);
	public void update(SchoolstudentEntity schoolstudentEntity);
	public void deleteBatch(String[] ids);
	public boolean delpho(String uid) throws Exception;
	public boolean updata(Map<String, Object> params)throws Exception;
	public List<SchoolstudentEntity> dict(Map<String, Object> params);

}
