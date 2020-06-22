package io.student.modules.eyereport.entity;

import java.io.Serializable;
import java.sql.JDBCType;
import java.util.Date;

import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.springframework.format.annotation.DateTimeFormat;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

@TableName("school_camera_class_info")
public class Scamerainfo  implements Serializable {
	private static final long serialVersionUID = 1L;
	@TableId("camera_id")
	private String cameraId;
	private String cameraAddr;
	private String classId;
	private String className;
	private String gradeName;
	
	@TableField(el="dt,jdbcType=DATE")	
	private Date dt;
	public String getCamera_id() {
		return cameraId;
	}
	public void setCamera_id(String camera_id) {
		this.cameraId = camera_id;
	}
	public String getCamera_addr() {
		return cameraAddr;
	}
	public void setCamera_addr(String camera_addr) {
		this.cameraAddr = camera_addr;
	}
	public String getClass_id() {
		return classId;
	}
	public void setClass_id(String class_id) {
		this.classId = class_id;
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
	public Date getDt() {
		return dt;
	}
	public void setDt(Date dt) {
		this.dt = dt;
	}
	

}
