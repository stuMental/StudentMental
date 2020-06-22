package io.student.modules.eyereport.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.entity.Column;
import com.baomidou.mybatisplus.entity.Columns;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.student.common.utils.DateUtils;
import io.student.common.utils.PageUtils;
import io.student.common.utils.Query;
import io.student.datasources.DataSourceNames;
import io.student.datasources.annotation.DataSource;
import io.student.modules.eyereport.dao.CameraClassRoomDao;
import io.student.modules.eyereport.entity.CameraClassRoom;
import io.student.modules.eyereport.service.ClassRoomService;

@Service("ClassRoomService")
public class ClassRoomServiceImpl extends ServiceImpl<CameraClassRoomDao,CameraClassRoom> implements ClassRoomService {

	
	
	@Override
	@DataSource(name=DataSourceNames.SECOND)
	public PageUtils queryPage(Map<String, Object> params) {
		Page<CameraClassRoom> page=this.selectPage(
				new Query<CameraClassRoom>(params).getPage(),
				new EntityWrapper<CameraClassRoom>()
				);
		
		return new PageUtils(page);
	}

	@Override
	@DataSource(name=DataSourceNames.SECOND)
	public CameraClassRoom queryobj(Integer roomId) {
		
		return this.selectById(roomId);
	}

	@Override
	@DataSource(name=DataSourceNames.SECOND)
	public boolean delete(Integer[] ids) {
		
		return this.deleteBatchIds(Arrays.asList(ids));
		
	}

	@Override
	@DataSource(name=DataSourceNames.SECOND)
	public boolean saveorupdate(CameraClassRoom cameraClassRoom) {
		cameraClassRoom.setDt(DateUtils.format(new Date()));
		return this.insertOrUpdate(cameraClassRoom);
		
	}

	@Override
	@DataSource(name=DataSourceNames.SECOND)
	public List<Map<String, Object>> all() {
		
		
		
//		return this.selectMaps(new EntityWrapper<CameraClassRoom>().groupBy("camera_id").groupBy("camera_addr"));
		return this.selectMaps(new EntityWrapper<CameraClassRoom>()
				.setSqlSelect("camera_id as cameraId,concat(room_addr,'@',camera_addr) as cameraAddr")
				.groupBy("camera_id").groupBy("camera_addr"));
	}
	
	
	@Override
	@DataSource(name=DataSourceNames.SECOND)
	public List<Map<String, Object>> roomall()
	{
		List<Map<String, Object>> ast=this.selectMaps(new EntityWrapper<CameraClassRoom>().groupBy("room_addr")
				.groupBy("room_id"));
//		List<Map<String, Object>> results=new ArrayList();
		
		
//		for (Map<String, Object> map : ast) {
//			Map<String, Object> result=new HashMap<String, Object>();
//			result.put("carmelist", new ArrayList<>());
//			result.putAll(map);
//			results.add(result);
//		}
		return ast;
	}

	
	

}
