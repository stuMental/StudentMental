package io.student.modules.eyereport.service.impl;

import java.util.Arrays;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import io.student.common.utils.PageUtils;
import io.student.common.utils.Query;
import io.student.datasources.DataSourceNames;
import io.student.datasources.annotation.DataSource;
import io.student.modules.eyereport.dao.SccinfoDao;
import io.student.modules.eyereport.dao.SchoolcourseinfoDao;
import io.student.modules.eyereport.entity.Scamerainfo;
import io.student.modules.eyereport.entity.Schoolcourseinfo;
import io.student.modules.eyereport.service.SccinfoService;
import io.student.modules.eyereport.service.SchoolcourseinfoService;

@Service("SchoolcourseinfoService")
public class SchoolcourseinfoServiceImpl extends ServiceImpl<SchoolcourseinfoDao,Schoolcourseinfo> implements SchoolcourseinfoService {

	@Autowired
	private SchoolcourseinfoDao schoolcourseinfoDao;
	
	@Override
	@DataSource(name = DataSourceNames.SECOND)
	public PageUtils queryPage(Map<String, Object> params) {

		String class_name="";
		String grade_name="";
		try {
			class_name=(String)params.get("class_name");
			grade_name=(String)params.get("grade_name");
		} catch (Exception e) {
			// TODO: handle exception
		}
		Page<Schoolcourseinfo> page = this.selectPage(
				new Query<Schoolcourseinfo>(params).getPage(),
				new EntityWrapper<Schoolcourseinfo>().eq(StringUtils.isNotBlank(class_name), "class_name", class_name)
				.eq(StringUtils.isNotBlank(grade_name), "grade_name", grade_name)
		);

		return new PageUtils(page);
	}

	@Override
	@DataSource(name = DataSourceNames.SECOND)
	public Schoolcourseinfo queryobject(String id) {
		
		return schoolcourseinfoDao.selectById(id);
	}

	@Override
	@DataSource(name = DataSourceNames.SECOND)
	public int insertobj(Schoolcourseinfo schoolcourseinfo) {
		
		return schoolcourseinfoDao.insert(schoolcourseinfo);
	}

	@Override
	@DataSource(name = DataSourceNames.SECOND)
	public int update(Schoolcourseinfo schoolcourseinfo) {
		return schoolcourseinfoDao.updateAllColumnById(schoolcourseinfo);
		
	}

	@Override
	@DataSource(name = DataSourceNames.SECOND)
	public void deleteBatch(Long[] ids) {
		schoolcourseinfoDao.deleteBatchIds(Arrays.asList(ids));
	}

}
