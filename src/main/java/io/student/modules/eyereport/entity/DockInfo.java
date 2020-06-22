package io.student.modules.eyereport.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

@TableName("school_docker_camera_info")
public class DockInfo implements Serializable  {
	private static final long serialVersionUID = 1L;
	@TableId("docker_id")
	private String dockerId;
	private String dockerName;
	private String cameraId;
	private String cameraAddr;
	private String dt;
	public String getDockerId() {
		return dockerId;
	}
	public void setDockerId(String dockerId) {
		this.dockerId = dockerId;
	}
	public String getDockerName() {
		return dockerName;
	}
	public void setDockerName(String dockerName) {
		this.dockerName = dockerName;
	}
	public String getCameraId() {
		return cameraId;
	}
	public void setCameraId(String cameraId) {
		this.cameraId = cameraId;
	}
	public String getCameraAddr() {
		return cameraAddr;
	}
	public void setCameraAddr(String cameraAddr) {
		this.cameraAddr = cameraAddr;
	}
	public String getDt() {
		return dt;
	}
	public void setDt(String dt) {
		this.dt = dt;
	}
	
	

}
