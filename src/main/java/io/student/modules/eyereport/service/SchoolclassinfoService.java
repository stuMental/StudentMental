package io.student.modules.eyereport.service;

import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

import io.student.common.utils.PageUtils;
import io.student.modules.eyereport.entity.Schoolclassinfo;

public interface SchoolclassinfoService extends IService<Schoolclassinfo> {

	PageUtils queryPage(Map<String, Object> params);
	Schoolclassinfo queryobj(String studentnumber);
	int insertobj(Schoolclassinfo schoolclassinfo);
	 boolean save(Map<String,Object> params) throws Exception ;
	 boolean update(Map<String,Object> params) throws Exception ;
	 
	 boolean delpho(String uid) throws Exception;
	 boolean del(Long[] ids) throws Exception;
	 
	 boolean updata(Map<String, Object> param) throws Exception;
	 
}
