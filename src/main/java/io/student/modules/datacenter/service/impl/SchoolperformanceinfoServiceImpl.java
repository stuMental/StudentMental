package io.student.modules.datacenter.service.impl;

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
import io.student.modules.datacenter.dao.SchoolperformanceinfoDao;
import io.student.modules.datacenter.entity.Schoolperformanceinfo;
import io.student.modules.datacenter.service.SchoolperformanceinfoService;
import io.student.modules.eyereport.dao.SchoolclassinfoDao;
import io.student.modules.eyereport.entity.Schoolclassinfo;
import io.student.modules.eyereport.service.SchoolclassinfoService;

@Service("SchoolperformanceinfoService")
public class SchoolperformanceinfoServiceImpl extends ServiceImpl<SchoolperformanceinfoDao,Schoolperformanceinfo> implements SchoolperformanceinfoService {
	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		String student_number="";
		try {
			 student_number=params.get("student_number").toString();
		} catch (Exception e) {
			// TODO: handle exception
		}
		Page<Schoolperformanceinfo> page = this.selectPage(
				new Query<Schoolperformanceinfo>(params).getPage(),
				new EntityWrapper<Schoolperformanceinfo>().eq(StringUtils.isNotBlank(student_number), "student_number", student_number)
		);

		return new PageUtils(page);
	}

}
