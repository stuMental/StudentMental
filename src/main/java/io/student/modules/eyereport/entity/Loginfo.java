package io.student.modules.eyereport.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

@TableName("log_info")
public class Loginfo  implements Serializable {
	private static final long serialVersionUID = 1L;
	@TableId("id")
	private Integer id;
	
	private String logType;
	private String logKey;
	private String logLevel;
	private String time;
	private String msg;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getLogType() {
		return logType;
	}
	public void setLogType(String logType) {
		this.logType = logType;
	}
	public String getLogKey() {
		return logKey;
	}
	public void setLogKey(String logKey) {
		this.logKey = logKey;
	}
	public String getLogLevel() {
		return logLevel;
	}
	public void setLogLevel(String logLevel) {
		this.logLevel = logLevel;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	

}
