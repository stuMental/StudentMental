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
import io.student.common.utils.PageUtils;
import io.student.common.utils.Query;
import io.student.datasources.DataSourceNames;
import io.student.datasources.annotation.DataSource;
import io.student.modules.eyereport.dao.SccinfoDao;
import io.student.modules.eyereport.entity.Scamerainfo;
import io.student.modules.eyereport.service.SccinfoService;

@Service("SccinfoService")
public class SccinfoServiceImpl extends ServiceImpl<SccinfoDao,Scamerainfo> implements SccinfoService {

	
	@Autowired
	private SccinfoDao sccinfoDao;
	
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
		
		Page<Scamerainfo> page = this.selectPage(
				
				new Query<Scamerainfo>(params).getPage(),
				new EntityWrapper<Scamerainfo>().eq(StringUtils.isNotBlank(class_name), "class_name", class_name)
				.eq(StringUtils.isNotBlank(grade_name), "grade_name", grade_name)
		);

		return new PageUtils(page);
	}
	
	@Override
	@DataSource(name= DataSourceNames.SECOND)
	public Scamerainfo selectById( Long camera_id)
	{
		return sccinfoDao.selectById(camera_id);
	}
	
	@Override
	@DataSource(name= DataSourceNames.SECOND)
	public int save(Scamerainfo scamerainfo)
	{
		return sccinfoDao.insert(scamerainfo);
	}
	
	@Override
	@DataSource(name= DataSourceNames.SECOND)
	public int update(Scamerainfo scamerainfo)
	{
		return sccinfoDao.updateAllColumnById(scamerainfo);
	}
	
	@Override
	@DataSource(name=DataSourceNames.SECOND)
	public  void deleteBatch(Long[] ids)
	{
		
		 sccinfoDao.deleteBatchIds(Arrays.asList(ids));
		
	}

	@Override
	@DataSource(name = DataSourceNames.SECOND)
	public List<Scamerainfo> getall() {
		return sccinfoDao.selectList(null);
	}

}
