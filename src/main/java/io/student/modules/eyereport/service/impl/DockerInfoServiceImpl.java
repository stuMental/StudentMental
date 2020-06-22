package io.student.modules.eyereport.service.impl;


import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import io.student.common.exception.RRException;
import io.student.common.utils.PageUtils;
import io.student.common.utils.Query;
import io.student.datasources.DataSourceNames;
import io.student.datasources.annotation.DataSource;
import io.student.modules.eyereport.dao.DockInfoDao;
import io.student.modules.eyereport.entity.DockInfo;
import io.student.modules.eyereport.service.DockInfoService;

@Service("DockerInfoService")
public class DockerInfoServiceImpl extends ServiceImpl<DockInfoDao,DockInfo> implements DockInfoService {

	@Autowired
	private DockInfoDao dockInfoDao;
	
	@Override
	@DataSource(name=DataSourceNames.SECOND)
	public int deleteBatch(Long[] ids) {
		return dockInfoDao.deleteBatchIds(Arrays.asList(ids));
		 
	}

	@Override
	@DataSource(name=DataSourceNames.SECOND)
	public int update(DockInfo dockInfo) {
		return dockInfoDao.updateAllColumnById(dockInfo);		  
	}

	@Override
	@DataSource(name=DataSourceNames.SECOND)
	public int save(DockInfo dockInfo) {
		return	dockInfoDao.insert(dockInfo);
		  
	}

	@Override
	@DataSource(name=DataSourceNames.SECOND)
	public DockInfo selectById(Long id) {
		return dockInfoDao.selectById(id);
	}

	@Override
	@DataSource(name=DataSourceNames.SECOND)
	public PageUtils queryPage(Map<String, Object> params) {

		String dockerName = (String)params.get("dockerName");

		Page<DockInfo> page = this.selectPage(
				new Query<DockInfo>(params).getPage(),
				new EntityWrapper<DockInfo>()
					.like(StringUtils.isNotBlank(dockerName),"docker_name", dockerName)
		);

		return new PageUtils(page);
	}

	@Override
	@DataSource(name=DataSourceNames.SECOND)
	public boolean querydocker(String[] ids) {
		for (String id : ids) {
			List<DockInfo> dockInfos=	this.selectList(new EntityWrapper<DockInfo>().eq("camera_id",id));
			if(!(dockInfos==null||dockInfos.size()==0))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	@DataSource(name=DataSourceNames.SECOND)
	public DockInfo selectbycamerid(String id) {
		
		return this.selectOne(new EntityWrapper<DockInfo>().eq("camera_id", id));
	}

}
