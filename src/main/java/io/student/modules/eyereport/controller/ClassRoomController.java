package io.student.modules.eyereport.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.student.common.exception.RRException;
import io.student.common.utils.PageUtils;
import io.student.common.utils.R;
import io.student.modules.eyereport.entity.CameraClassRoom;
import io.student.modules.eyereport.entity.DockInfo;
import io.student.modules.eyereport.service.ClassRoomService;
import io.student.modules.eyereport.service.DockInfoService;
import io.student.modules.sys.controller.AbstractController;

@RestController
@RequestMapping("/croom")
public class ClassRoomController extends AbstractController {

	@Autowired
	private ClassRoomService classroomservice;

	@Autowired
	private DockInfoService dockInfoService;

	@GetMapping("/list")
	public R list(@RequestParam Map<String, Object> params) {
		PageUtils page = classroomservice.queryPage(params);
		return R.ok().put("page", page);
	}

	@PostMapping("/delete")
	public R delete(@RequestBody Integer[] ids) {
//		String[] idds=
//		for (Integer integer : ids) {
//			
//		}
		if (dockInfoService.querydocker(Arrays.copyOf(ids, ids.length, String[].class))) {
			throw new RRException("请先在docker管理页面删除对应docker");
		}
		if (classroomservice.delete(ids)) {
			return R.ok();
		} else {
			return R.error();
		}
	}

	@PostMapping("save")
	public R save(@RequestBody CameraClassRoom cameraClassRoom) {
		DockInfo dockInfo = dockInfoService.selectbycamerid(cameraClassRoom.getCameraId());
		if (dockInfo != null) {
			dockInfo.setCameraId(cameraClassRoom.getCameraId());
			dockInfo.setCameraAddr(cameraClassRoom.getCameraAddr());
			dockInfoService.update(dockInfo);
		}
		if (classroomservice.saveorupdate(cameraClassRoom)) {
			return R.ok();
		} else {
			return R.error();
		}
	}

	@GetMapping("/info/{cameraId}")
	public R info(@PathVariable(value = "cameraId") Integer roomId) {
		return R.ok().put("data", classroomservice.queryobj(roomId));
	}

	@GetMapping("cameraall")
	public R all() {
		return R.ok().put("data", classroomservice.all());
	}
	
	@GetMapping("roomall")
	public R roomall()
	{
		return R.ok().put("data", classroomservice.roomall());
	}
	
	

}
