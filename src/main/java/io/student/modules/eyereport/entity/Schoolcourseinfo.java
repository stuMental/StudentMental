package io.student.modules.eyereport.entity;


import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
@TableName("school_course_info")
public class Schoolcourseinfo {
	@TableId
	private String id;
	private String courseId;
	private String courseName;
	private String teaId;
	private String teaName;
	private String className;
	private String gradeName;
	private String startTime;
	private String endTime;
	@TableField(el="dt,jdbcType=DATE")
	private Date dt;
	private String weekday;
	public String getCourse_id() {
		return courseId;
	}
	public void setCourse_id(String course_id) {
		this.courseId = course_id;
	}
	public String getCourse_name() {
		return courseName;
	}
	public void setCourse_name(String course_name) {
		this.courseName = course_name;
	}
	public String getTea_id() {
		return teaId;
	}
	public void setTea_id(String tea_id) {
		this.teaId = tea_id;
	}
	public String getTea_name() {
		return teaName;
	}
	public void setTea_name(String tea_name) {
		this.teaName = tea_name;
	}
	public String getClass_name() {
		return className;
	}
	public void setClass_name(String class_name) {
		this.className = class_name;
	}
	public String getGrade_name() {
		return gradeName;
	}
	public void setGrade_name(String grade_name) {
		this.gradeName = grade_name;
	}
	public String getStart_time() {
		return startTime;
	}
	public void setStart_time(String start_time) {
		this.startTime = start_time;
	}
	public String getEnd_time() {
		return endTime;
	}
	public void setEnd_time(String end_time) {
		this.endTime = end_time;
	}
	public Date getDt() {
		return dt;
	}
	public void setDt(Date dt) {
		this.dt = dt;
	}
	public String getWeekday() {
		return weekday;
	}
	public void setWeekday(String weekday) {
		this.weekday = weekday;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

}
