package io.student.modules.eyereport.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

@TableName("school_camera_room_info")
public class CameraClassRoom  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@TableId(type=IdType.INPUT,value="camera_id")
	private String cameraId;
	private String cameraAddr;
	private String roomId;
	private String roomAddr;
	private String dt;
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
	public String getRoomId() {
		return roomId;
	}
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	public String getDt() {
		return dt;
	}
	public void setDt(String dt) {
		this.dt = dt;
	}
	public String getRoomAddr() {
		return roomAddr;
	}
	public void setRoomAddr(String roomAddr) {
		this.roomAddr = roomAddr;
	}
	
	
	
	
	
	

}
