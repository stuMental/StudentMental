package io.student.modules.datacenter.entity;

import java.util.Date;

import javax.validation.constraints.NotBlank;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

@TableName("school_camera_room_info")
public class CameraroomEntity {
	@TableId
	private int cameraId;
	@NotBlank(message="摄像头地址不能为空")
	private String cameraAddr;
	private String roomId;
	@NotBlank(message="教室地址不能为空")
	private String roomAddr;
	@NotBlank(message="docker不能为空")
	private String docker;
	private Date dt;
	public int getCameraId() {
		return cameraId;
	}
	public void setCameraId(int cameraId) {
		this.cameraId = cameraId;
	}
	public String getCameraAddr() {
		return cameraAddr;
	}
	public void setCameraAddr(String cameraAddr) {
		this.cameraAddr = cameraAddr;
	}
	public String getRoomId() {
		return roomId;
	}
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	public String getRoomAddr() {
		return roomAddr;
	}
	public void setRoomAddr(String roomAddr) {
		this.roomAddr = roomAddr;
	}
	public String getDocker() {
		return docker;
	}
	public void setDocker(String docker) {
		this.docker = docker;
	}
	public Date getDt() {
		return dt;
	}
	public void setDt(Date dt) {
		this.dt = dt;
	}

}
