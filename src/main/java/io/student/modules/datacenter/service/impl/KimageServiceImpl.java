package io.student.modules.datacenter.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import io.student.datasources.DataSourceNames;
import io.student.datasources.annotation.DataSource;
import io.student.modules.datacenter.entity.Kimage;
import io.student.modules.datacenter.entity.Studentimage;
import io.student.modules.datacenter.service.KimageService;
import io.student.modules.eyereport.dao.KimageDao;
import io.student.modules.eyereport.dao.StudentimageDao;

@Service("kimageservice")
public class KimageServiceImpl extends ServiceImpl<KimageDao,Kimage> implements KimageService {

	@Autowired
	private KimageDao kimagedao;
	
	@Autowired
	private StudentimageDao studentimageDao;
	
	@Override
	public int insertobj(Kimage kimage) {
		
		return kimagedao.insertAllColumn(kimage);
	}

	@Override
	public Kimage querybyid(String uuid) {
		// TODO Auto-generated method stub
		return kimagedao.selectById(uuid);
	}

	@Override
	public int deleteobj(String uid) {
		
		return kimagedao.deleteById(uid);
	}

	@Override
	public int inseroo(Studentimage image) {
		return studentimageDao.insertAllColumn(image);
	}
	

}
