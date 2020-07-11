package io.student.modules.datacenter.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;

import io.student.common.exception.RRException;
import io.student.common.utils.Constant;
import io.student.common.utils.PageUtils;
import io.student.common.utils.R;
import io.student.common.validator.ValidatorUtils;
import io.student.modules.datacenter.entity.CameraroomEntity;
import io.student.modules.datacenter.entity.SchoolstudentEntity;
import io.student.modules.datacenter.entity.StudentCourseInfo;
import io.student.modules.datacenter.entity.Studentimage;
import io.student.modules.datacenter.service.SchoolstudentService;
import io.student.modules.datacenter.service.StudentCourseInfoService;
import io.student.modules.eyereport.entity.Schoolcourseinfo;
import io.student.modules.sys.controller.AbstractController;
import io.student.modules.sys.service.SysDeptService;

@RestController
@RequestMapping("/datacenter/StudentCourseInfo")
public class StudentCourseInfoController extends AbstractController {

	@Autowired
	private StudentCourseInfoService StudentCourseInfoService;
	@Autowired
	private SysDeptService sysDeptService;
	@Autowired
	private SchoolstudentService schoolstudentService;

	@GetMapping("list")
	public R list(@RequestParam Map<String, Object> params) {
		try {
			PageUtils page = StudentCourseInfoService.queryPage(params);
			return R.ok().put("page", page);
		} catch (Exception e) {
			return R.error(e.toString());
		}

	}

	@PostMapping("/delete")
	public R delete(@RequestBody Long[] ids) {
		StudentCourseInfoService.deleteBatch(ids);
		return R.ok();
	}

	@PostMapping("/save")
	public R save(@RequestBody StudentCourseInfo studentCourseInfo) {

		if (studentCourseInfo.getStudentNumber().equals("")) {
			studentCourseInfo.setStudentNumber("按班级添加");
		}

		ValidatorUtils.validateEntity(studentCourseInfo);

		if (studentCourseInfo.getStudentNumber().equals("按班级添加")) {

			if (sysDeptService.queryDetpIdList(Long.parseLong(String.valueOf(studentCourseInfo.getDeptId())))
					.size() != 0) {
				throw new RRException("请选择到具体班级");
			}
			if (sysDeptService.selectleavel(Long.parseLong(String.valueOf(studentCourseInfo.getDeptId()))) != 3) {
				throw new RRException("班级应该是三层，清选择对应的班级");
			}

			List<SchoolstudentEntity> students = schoolstudentService
					.selectList(new EntityWrapper<SchoolstudentEntity>().eq("dept_id", studentCourseInfo.getDeptId()));
			List<StudentCourseInfo> StudentCourseInfos = new ArrayList<StudentCourseInfo>();
			if(students.size()==0)
			{
				throw new RRException("该班级下没有对应学生，请先添加学生。");
			}
			
			
			for (SchoolstudentEntity schoolstudentEntity : students) {
				StudentCourseInfo studentCourseInfonew = new StudentCourseInfo();
				try {
					studentCourseInfonew.setCourseId(studentCourseInfo.getCourseId());
					studentCourseInfonew.setDeptId(studentCourseInfo.getDeptId());
					studentCourseInfonew.setMood(studentCourseInfo.getMood());
					studentCourseInfonew.setRoomId(studentCourseInfo.getRoomId());
					studentCourseInfonew.setTeacherId(studentCourseInfo.getTeacherId());
					studentCourseInfonew.setWeekday(studentCourseInfo.getWeekday());
					
					studentCourseInfonew.setStudentNumber(schoolstudentEntity.getStudentNumber());
					StudentCourseInfos.add(studentCourseInfonew);
				} catch (Exception ex) {
					throw new RRException(ex.getMessage());
				}
			}
			boolean checked = true;
			for (StudentCourseInfo studentCourseInfo2 : StudentCourseInfos) {
				if (!checkdata(studentCourseInfo2)) {
					checked = false;
				}
			}
			if (checked) {
				StudentCourseInfoService.insertBatch(StudentCourseInfos);
			}

		} else {
			if (checkdata(studentCourseInfo)) {
				StudentCourseInfoService.save(studentCourseInfo);
			} else {
				return R.error("数据错误");
			}
		}

		return R.ok();

	}

	@PostMapping("/update")
	public R update(@RequestBody StudentCourseInfo studentCourseInfo) {
		ValidatorUtils.validateEntity(studentCourseInfo);
		if (checkdata(studentCourseInfo)) {
			StudentCourseInfoService.update(studentCourseInfo);
		} else {
			return R.error("数据错误");
		}
		return R.ok();
	}

	//	教学效果报表：根据班年级和日期获取课程
	@PostMapping("dict")
	public R dict(@RequestBody Map<String, Object> params) {
		if (getUserId() != Constant.SUPER_ADMIN) {
			params.put("teacherId", getUser().getUserId());
		}

		return R.ok().put("data", StudentCourseInfoService.dict(params));
	}

	//	教师报表页面：根据班年级和教师id获取课程
	@PostMapping("getCourseByTeacher")
	public R getCourseByTeacher(@RequestBody Map<String, Object> params) {
		params.putAll(sysDeptService.getgrade(Long.parseLong(params.get("deptid").toString())));
		return R.ok().put("data", StudentCourseInfoService.getCourseByTeacher(params));
	}

	private boolean checkdata(StudentCourseInfo studentCourseInfo) throws RRException {

		if (studentCourseInfo.getId() != null) {
			StudentCourseInfo old = StudentCourseInfoService.selectById(studentCourseInfo.getId());
			if (old.getStudentNumber().equals(studentCourseInfo.getStudentNumber())
					&& old.getWeekday().equals(studentCourseInfo.getWeekday())&&old.getMood().equals(studentCourseInfo.getMood())) {
				
				//修改部分非主键信息
				if (sysDeptService.queryDetpIdList(Long.parseLong(String.valueOf(studentCourseInfo.getDeptId()))).size() != 0) {
					throw new RRException("请选择到具体班级");
				}
				if (sysDeptService.selectleavel(Long.parseLong(String.valueOf(studentCourseInfo.getDeptId()))) != 3) {
					throw new RRException("班级应该是三层，清选择对应的班级");
				}
				return true;
			}
		}

		// 判断该学生再同一天同一时刻是否有该课
		int cot = StudentCourseInfoService.selectCount(
				new EntityWrapper<StudentCourseInfo>().eq("student_number", studentCourseInfo.getStudentNumber())
						.eq("weekday", studentCourseInfo.getWeekday()).eq("mood", studentCourseInfo.getMood())
						

		);
		if (cot != 0) {
			throw new RRException("存在同一时间学生课程");
		}
		if (sysDeptService.queryDetpIdList(Long.parseLong(String.valueOf(studentCourseInfo.getDeptId()))).size() != 0) {
			throw new RRException("请选择到具体班级");
		}
		if (sysDeptService.selectleavel(Long.parseLong(String.valueOf(studentCourseInfo.getDeptId()))) != 3) {
			throw new RRException("班级应该是三层，清选择对应的班级");
		}

		return true;
	}

}
