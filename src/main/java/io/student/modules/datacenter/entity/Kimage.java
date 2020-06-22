package io.student.modules.datacenter.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

@TableName("image")
public class Kimage  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@TableId(value="id",type=IdType.INPUT)
	private String id;
	private byte[] image;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public byte[] getImage() {
		return image;
	}
	public void setImage(byte[] image) {
		this.image = image;
	}
	

}
