package io.student.modules.eyereport.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;

public class SchoolCRoom {
	@TableId("room_id")
	private int roomId;
	
	private String roomName;
	private String roomStat;
	private Date dt;
	public int getRoomId() {
		return roomId;
	}
	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	public String getRoomStat() {
		return roomStat;
	}
	public void setRoomStat(String roomStat) {
		this.roomStat = roomStat;
	}
	public Date getDt() {
		return dt;
	}
	public void setDt(Date dt) {
		this.dt = dt;
	}

}
