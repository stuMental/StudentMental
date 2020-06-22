package io.student.modules.eyereport.service;

import java.util.List;
import java.util.Map;

import io.student.common.utils.PageUtils;
import io.student.modules.eyereport.entity.CameraClassRoom;

public interface ClassRoomService {
	
	 PageUtils queryPage(Map<String, Object> params);
	 
	 CameraClassRoom queryobj(Integer roomId);
	 boolean delete(Integer[] ids);
	 boolean saveorupdate(CameraClassRoom cameraClassRoom);
	 List<Map<String, Object>> all();
	 
	 List<Map<String, Object>> roomall();
	 
	

}
