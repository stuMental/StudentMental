package io.student.modules.eyereport.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import io.student.common.utils.PageUtils;
import io.student.common.utils.Query;
import io.student.common.utils.R;
import io.student.datasources.DataSourceNames;
import io.student.datasources.annotation.DataSource;
import io.student.modules.eyereport.dao.CourseDao;
import io.student.modules.eyereport.entity.CourseInfo;
import io.student.modules.eyereport.service.CourseService;

@Service("CourseService")
public class CourseServiceImpl extends ServiceImpl<CourseDao,CourseInfo> implements CourseService{

	@Autowired
	private CourseDao courseDao;
	
	
	
	@Override
	@DataSource(name=DataSourceNames.SECOND)
	public PageUtils queryPage(Map<String, Object> params) {
		String studentName=(String)params.get("studentName");
		String studentNumber=(String)params.get("studentNumber");
		
		Page<CourseInfo> page=this.selectPage(new Query<CourseInfo>(params).getPage(), new EntityWrapper<CourseInfo>()
				.eq(StringUtils.isNotBlank(studentName), "student_name", studentName)
				.eq(StringUtils.isNotBlank(studentNumber), "student_number", studentNumber));
		return new PageUtils(page);
	}

	@Override
	@DataSource(name=DataSourceNames.SECOND)
	public boolean delete(List<CourseInfo> courseInfos) {
		for (CourseInfo courseInfo : courseInfos) {
			Map<String, Object> keys=new HashMap<>();
			keys.put("student_number", courseInfo.getStudentNumber());
			keys.put("course_name", courseInfo.getCourseName());
			keys.put("start_time", courseInfo.getStartTime());
			keys.put("room_addr", courseInfo.getRoomAddr());
			keys.put("weekday", courseInfo.getWeekday());
			this.deleteByMap(keys);
		}
		return true;
	}

	@Override
	@DataSource(name=DataSourceNames.SECOND)
	public boolean save(CourseInfo courseInfo) {
		Map<String, Object> keys=new HashMap<>();
		keys.put("student_number", courseInfo.getStudentNumber());
		keys.put("course_name", courseInfo.getCourseName());
		keys.put("start_time", courseInfo.getStartTime());
		keys.put("room_addr", courseInfo.getRoomAddr());
		keys.put("weekday", courseInfo.getWeekday());
		this.deleteByMap(keys);
		return this.insert(courseInfo);
	}

	@Override
	@DataSource(name=DataSourceNames.SECOND)
	public List<Map<String, Object>> getcourselist(Map<String, Object> param) {
		
		String college_name=(String)param.get("college_name");
		String grade_name=(String)param.get("grade_name");
		String class_name=(String)param.get("class_name");
		
		
		return this.selectMaps(new EntityWrapper<CourseInfo>()
				.eq(StringUtils.isNotBlank(college_name), "college_name", college_name)
				.eq(StringUtils.isNotBlank(grade_name), "grade_name", grade_name)
				.eq(StringUtils.isNotBlank(class_name), "class_name", class_name)
				.groupBy("course_id")
				.groupBy("course_name"));
		
		
	}

	@Override
	@DataSource(name=DataSourceNames.SECOND)
	public PageUtils getstudentall(Map<String, Object> param) {
//		
//		Page<CourseInfo> page= this.selectPage(new Query<CourseInfo>(param).getPage(), new EntityWrapper<CourseInfo>()
//				.eq(StringUtils.isNotBlank(college_name), "college_name", college_name)
//				.eq(StringUtils.isNotBlank(grade_name), "grade_name", grade_name)
//				.eq(StringUtils.isNotBlank(class_name), "class_name", class_name)
//				.orderBy("student_number")
//				.groupBy("student_number")
//				.groupBy("student_name"));
//		
		Query query = new Query(param);
		return new PageUtils(courseDao.studentall(query),courseDao.studentalltotal(query),query.getLimit(),query.getCurrPage());
		
		
	}

	@Override
	@DataSource(name=DataSourceNames.SECOND)
	public CourseInfo getcourseinfo(String Studentname) {
		return this.selectOne(new EntityWrapper<CourseInfo>()
				.eq("student_number", Studentname)
		);
	}

}
