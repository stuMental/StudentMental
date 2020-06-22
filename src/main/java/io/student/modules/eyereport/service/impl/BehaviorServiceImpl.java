package io.student.modules.eyereport.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.student.datasources.DataSourceNames;
import io.student.datasources.annotation.DataSource;
import io.student.modules.eyereport.dao.BehaviorDao;
import io.student.modules.eyereport.service.BehaviorService;
import io.student.modules.sys.dao.SysDeptDao;

@Service("BehaviorService")
public class BehaviorServiceImpl implements BehaviorService {

	@Autowired
	private BehaviorDao behaviorDao;
	
	
	@Override
	@DataSource(name = DataSourceNames.SECOND)
	public List<Map<String, Object>> getheaddata(Map<String, Object> param) {
		
		return behaviorDao.getheaddata(param);
	}

	@Override
	@DataSource(name = DataSourceNames.SECOND)
	public List<Map<String, Object>> getbodydata(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return behaviorDao.getbodydata(param);
	}

	@Override
	@DataSource(name = DataSourceNames.SECOND)
	public List<Map<String, Object>> getsmalldata(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return behaviorDao.getsmalldata(param);
	}

}
