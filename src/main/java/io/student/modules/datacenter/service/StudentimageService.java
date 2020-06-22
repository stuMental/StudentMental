package io.student.modules.datacenter.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

import io.student.modules.datacenter.entity.Studentimage;

public interface StudentimageService extends IService<Studentimage> {
	
	List<Studentimage> queryall(Map<String, Object> params);
	
	void resolv(List<String> filenames) throws Exception;
	int insertobj(Studentimage studentimage);
}
