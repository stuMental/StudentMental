package io.student.modules.datacenter.service;

import com.baomidou.mybatisplus.service.IService;

import io.student.modules.datacenter.entity.Kimage;
import io.student.modules.datacenter.entity.Studentimage;

public interface KimageService extends IService<Kimage> {
	
	int insertobj(Kimage kimage);
	Kimage querybyid(String uuid);
	int deleteobj(String uid);
	int inseroo(Studentimage image);

}
