package io.student.modules.datacenter.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

@TableName("k_school_award_info")
public class SchoolawardinfoEntity {
	@TableId(type=IdType.INPUT,value="student_number")
	private String studentNumber;
	private String awardType;
	private String awardTypeName;
	public String getAwardTypeName() {
		return awardTypeName;
	}
	public void setAwardTypeName(String awardTypeName) {
		this.awardTypeName = awardTypeName;
	}
	public String getAwardLevelName() {
		return awardLevelName;
	}
	public void setAwardLevelName(String awardLevelName) {
		this.awardLevelName = awardLevelName;
	}
	private String awardLevel;
	private String awardLevelName;
	private String awardName;
	private Date dt;
	public String getStudentNumber() {
		return studentNumber;
	}
	public void setStudentNumber(String studentNumber) {
		this.studentNumber = studentNumber;
	}
	public String getAwardType() {
		return awardType;
	}
	public void setAwardType(String awardType) {
		this.awardType = awardType;
	}
	public String getAwardLevel() {
		return awardLevel;
	}
	public void setAwardLevel(String awardLevel) {
		this.awardLevel = awardLevel;
	}
	public String getAwardName() {
		return awardName;
	}
	public void setAwardName(String awardName) {
		this.awardName = awardName;
	}
	public Date getDt() {
		return dt;
	}
	public void setDt(Date dt) {
		this.dt = dt;
	}
}
