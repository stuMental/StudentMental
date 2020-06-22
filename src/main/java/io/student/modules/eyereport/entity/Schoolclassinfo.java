package io.student.modules.eyereport.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
@TableName("school_student_class_info")
public class Schoolclassinfo {
	private String gradeName;
	private String className;
	@TableId(value="student_number",type = IdType.INPUT)
	private String studentNumber;
	private String studentName;
	@TableField(el="dt,jdbcType=DATE")	
	private Date dt;
	public String getGrade_name() {
		return gradeName;
	}
	public void setGrade_name(String grade_name) {
		this.gradeName = grade_name;
	}
	public String getClass_name() {
		return className;
	}
	public void setClass_name(String class_name) {
		this.className = class_name;
	}
	public String getStudent_number() {
		return studentNumber;
	}
	public void setStudent_number(String student_number) {
		this.studentNumber = student_number;
	}
	public String getStudent_name() {
		return studentName;
	}
	public void setStudent_name(String student_name) {
		this.studentName = student_name;
	}
	public Date getDt() {
		return dt;
	}
	public void setDt(Date dt) {
		this.dt = dt;
	}

}
