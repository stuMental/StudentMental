package io.student.modules.eyereport.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import io.student.common.utils.PageUtils;
import io.student.datasources.DataSourceNames;
import io.student.datasources.annotation.DataSource;
import io.student.modules.eyereport.dao.AttendanceDao;
import io.student.modules.eyereport.service.AttendanceService;
@Service("AttendanceService")
public class AttendanceServiceImpl implements AttendanceService {

	
	@Autowired
	private AttendanceDao attendanceDao;
	
	@Override
	@DataSource(name=DataSourceNames.SECOND)
	public List<Map<String, Object>> list(Map<String, Object> param) {
		
		
		List<Map<String, Object>> absencename=attendanceDao.getabsencename(param);
		List<Map<String, Object>> attendancename=attendanceDao.getattendancename(param);
		Map<String, Map<String, Object>> results=new HashMap<>();
		//取absencename 中的值
		for (Map<String, Object> map : absencename) {
			Map<String, Object> result=new HashMap<>();
			result.putAll(map);
			for (Map<String, Object> map2 : attendancename) {
				if(map2.get("time_grap").equals(result.get("time_grap"))&&map2.get("course_name").equals((result.get("course_name"))))
				{
					result.put("exisnum", map2.get("exisnum"));
					result.put("attendancename", map2.get("attendancename"));
				}
			}
			results.put((String)map.get("time_grap")+(String)map.get("course_name"),result);
		}
		//取attendancename中的值
		for (Map<String, Object> map3 : attendancename) {
			if(results.get((String)map3.get("time_grap")+(String)map3.get("course_name"))==null)
			{
				results.put((String)map3.get("time_grap")+(String)map3.get("course_name"), map3);
			}
		}
		Map<String, Map<String, Object>> rMap=new LinkedHashMap<String, Map<String,Object>>();
		 Set set=results.keySet();
	        Object[] arr=set.toArray();
	        Arrays.sort(arr);
	        for(Object key:arr){
	        	rMap.put(key.toString(), results.get(key));
	        }
		
		return new ArrayList(rMap.values());
	}

	@Override
	@DataSource(name=DataSourceNames.SECOND)
	public List<Map<String, Object>> getstudentlist(Map<String, Object> param) {
		
		return attendanceDao.getstudentlist(param);
	}
	@Override
	@DataSource(name=DataSourceNames.SECOND)
	public List<Map<String, Object>> getstudentlist2(Map<String, Object> param) {
		
		return attendanceDao.getstudentlist2(param);
	}

}
