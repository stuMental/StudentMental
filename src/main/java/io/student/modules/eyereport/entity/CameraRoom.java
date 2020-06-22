package io.student.modules.eyereport.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;

public class CameraRoom {
	@TableId("camera_id")
	private int cameraId;
	
	private String cameraAddr;
	private int roomId;
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
	public int getRoomId() {
		return roomId;
	}
	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}
	public Date getDt() {
		return dt;
	}
	public void setDt(Date dt) {
		this.dt = dt;
	}

}
