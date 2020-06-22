package io.student.modules.datacenter.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import io.student.common.utils.Constant;
import io.student.common.utils.PageUtils;
import io.student.common.utils.Query;
import io.student.modules.datacenter.dao.StudentCourseInfoDao;
import io.student.modules.datacenter.entity.StudentCourseInfo;
import io.student.modules.datacenter.service.StudentCourseInfoService;
import io.student.modules.sys.service.SysConfigService;
import io.student.modules.sys.service.SysDeptService;

@Service("studentcourseinfo")
public class StudentCourseInfoServiceImpl extends ServiceImpl<StudentCourseInfoDao,StudentCourseInfo> implements StudentCourseInfoService {
	@Autowired
	private SysDeptService SysDeptService;
	
	@Autowired
	private SysConfigService SysConfigService;
	
	
	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		String deptId = (String) params.get("deptId");
		String studentnumber = (String) params.get("studentNumber");
		String courseid=(String)params.get("courseId");
		String teacherid=(String)params.get("teacherId");
		String roomid=(String)params.get("roomId");
		String mood=(String)params.get("mood");
		String weekday=(String)params.get("weekday");
		String deptidsString ="";
		if(StringUtils.isNotBlank(deptId))
		{
		 deptidsString = SysDeptService.getSubDeptIdList(Long.valueOf(deptId));
		}
		Page<StudentCourseInfo> page = this.selectPage(new Query<StudentCourseInfo>(params).getPage(),
				new EntityWrapper<StudentCourseInfo>()
						.eq(StringUtils.isNotBlank(studentnumber), "student_number", studentnumber)
						.in(StringUtils.isNotBlank(deptidsString), "dept_id", deptidsString)
						.eq(StringUtils.isNotBlank(courseid), "course_id", courseid)
						.eq(StringUtils.isNotBlank(teacherid), "teacher_id", teacherid)
						.eq(StringUtils.isNotBlank(roomid), "room_id", roomid)
						.eq(StringUtils.isNotBlank(mood), "mood", mood)
						.eq(StringUtils.isNotBlank(weekday), "weekday", weekday));

		return new PageUtils(page);
	}

	@Override
	public void deleteBatch(Long[] ids) {
		this.deleteBatchIds(Arrays.asList(ids));
		
	}

	@Override
	public void save(StudentCourseInfo studentCourseInfo) {
		this.insert(studentCourseInfo);
		
	}

	@Override
	public void update(StudentCourseInfo studentCourseInfo) {
	this.updateAllColumnById(studentCourseInfo);
	}

	@Override
	public List<Map<String, Object>> dict(Map<String, Object> params) {
		String deptId = (String) params.get("deptid");
		String deptidsString ="";
		if(StringUtils.isNotBlank(deptId))
		{
		 deptidsString = SysDeptService.getSubDeptIdList(Long.valueOf(deptId));
		}
		String teacherid=(String)params.get("teacherId");
		String datetime=(String)params.get("date");
		String weekday="";
		if(StringUtils.isNotBlank(datetime))
		{
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		 Date datet = null;
	        try {
	            datet = f.parse(datetime);
	            cal.setTime(datet);
	            weekday= String.valueOf(cal.get(Calendar.DAY_OF_WEEK));
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		}
		
		List<StudentCourseInfo> courseInfos=this.selectList(new EntityWrapper<StudentCourseInfo>()
				.in(StringUtils.isNotBlank(deptidsString), "dept_id", deptidsString)
				.eq(StringUtils.isNotBlank(teacherid), "teacher_id", teacherid)
				.eq(StringUtils.isNotBlank(weekday), "weekday", weekday)
				);
		List<Map<String, Object>> returns=new ArrayList<Map<String,Object>>();
		for (StudentCourseInfo studentCourseInfo : courseInfos) {
			Map<String, Object> map=new HashedMap<String, Object>();
			map.put("courseId", studentCourseInfo.getCourseId());
			map.put("courseName",SysConfigService.getValue("course", studentCourseInfo.getCourseId()));
			returns.add(map);
		}
		return returns;
	}

}
