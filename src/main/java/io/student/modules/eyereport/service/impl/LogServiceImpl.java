package io.student.modules.eyereport.service.impl;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import io.student.common.utils.PageUtils;
import io.student.common.utils.Query;
import io.student.datasources.DataSourceNames;
import io.student.datasources.annotation.DataSource;
import io.student.modules.eyereport.dao.LoginfoDao;
import io.student.modules.eyereport.entity.Loginfo;
import io.student.modules.eyereport.service.LogService;
@Service("loginfoService")
public class LogServiceImpl extends ServiceImpl<LoginfoDao,Loginfo> implements LogService  {

	@Override
	@DataSource(name=DataSourceNames.SECOND)
	public PageUtils queryPage(Map<String, Object> params) {
		String logkey = (String)params.get("logKey");
		String logType = (String)params.get("logType");
		String logLevel = (String)params.get("logLevel");
		String stratime = (String)params.get("startime");
		String endtime = (String)params.get("endtime");
		
		Page<Loginfo> page=this.selectPage(
				new Query<Loginfo>(params).getPage(),
				new EntityWrapper<Loginfo>()
				.eq(StringUtils.isNotBlank(logkey),"log_key",logkey)
				.eq(StringUtils.isNotBlank(logType),"log_type",logType)
				.eq(StringUtils.isNotBlank(logLevel),"log_Level",logLevel)
				.gt(StringUtils.isNotBlank(stratime), "time", stratime)
				.le(StringUtils.isNotBlank(endtime), "time", endtime)
				.orderBy("time", false)
				);
		
		return new PageUtils(page);
	}

}
