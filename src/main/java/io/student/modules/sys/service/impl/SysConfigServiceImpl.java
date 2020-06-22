/**
 * 
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package io.student.modules.sys.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.student.common.exception.RRException;
import io.student.common.utils.PageUtils;
import io.student.common.utils.Query;
import io.student.modules.sys.dao.SysConfigDao;
import io.student.modules.sys.entity.SysConfigEntity;
import io.student.modules.sys.redis.SysConfigRedis;
import io.student.modules.sys.service.SysConfigService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service("sysConfigService")
public class SysConfigServiceImpl extends ServiceImpl<SysConfigDao, SysConfigEntity> implements SysConfigService {
	@Autowired
	private SysConfigRedis sysConfigRedis;

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		String paramKey = (String)params.get("paramKey");
		String paramType=(String)params.get("paramType");

		Page<SysConfigEntity> page = this.selectPage(
				new Query<SysConfigEntity>(params).getPage(),
				new EntityWrapper<SysConfigEntity>()
					.like(StringUtils.isNotBlank(paramKey),"param_key", paramKey)
					.eq(StringUtils.isNotBlank(paramType),"param_type", paramType)
					.ne("param_type", "dict")
					.eq("status", 1)
		);

		return new PageUtils(page);
	}
	
	
	@Override
	public List<SysConfigEntity> gettype()
	{
		List<SysConfigEntity> sysConfigEntities=new ArrayList<SysConfigEntity>();
		SysConfigEntity sysConfigEntity=new SysConfigEntity();
		sysConfigEntity.setParamKey("");
		sysConfigEntity.setParamValue("全部");
		sysConfigEntities=this.selectList(new EntityWrapper<SysConfigEntity>().eq("param_type", "dict"));
		sysConfigEntities.add(sysConfigEntity);
		
		return sysConfigEntities;
	}

	public List<SysConfigEntity> getroom(){
		List<SysConfigEntity> sysConfigEntities=new ArrayList<SysConfigEntity>();
		SysConfigEntity sysConfigEntity=new SysConfigEntity();
		sysConfigEntity.setParamKey("");
		sysConfigEntity.setParamValue("全部");
		sysConfigEntities=this.selectList(new EntityWrapper<SysConfigEntity>().eq("param_type", "room"));
		sysConfigEntities.add(sysConfigEntity);
		
		return sysConfigEntities;
		
	}
	
	@Override
	public void save(SysConfigEntity config) {
		this.insert(config);
		sysConfigRedis.saveOrUpdate(config);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void update(SysConfigEntity config) {
		this.updateAllColumnById(config);
		sysConfigRedis.saveOrUpdate(config);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateValueByKey(String Type,String key, String value) {
		baseMapper.updateValueByKey(Type,key, value);
		sysConfigRedis.delete(key);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteBatch(Long[] ids) {
		for(Long id : ids){
			SysConfigEntity config = this.selectById(id);
			sysConfigRedis.delete(config.getParamKey());
		}

		this.deleteBatchIds(Arrays.asList(ids));
	}

	@Override
	public String getValue(String Type,String key) {
		SysConfigEntity config = sysConfigRedis.get(key);
		if(config == null){
			config = baseMapper.queryByKey(Type,key);
			sysConfigRedis.saveOrUpdate(config);
		}

		return config == null ? null : config.getParamValue();
	}
	
	@Override
	public <T> T getConfigObject(String Type,String key, Class<T> clazz) {
		String value = getValue(Type,key);
		if(StringUtils.isNotBlank(value)){
			return JSON.parseObject(value, clazz);
		}

		try {
			return clazz.newInstance();
		} catch (Exception e) {
			throw new RRException("获取参数失败");
		}
	}


	@Override
	public List<SysConfigEntity> getawardtype() {
		List<SysConfigEntity> sysConfigEntities=new ArrayList<SysConfigEntity>();
		SysConfigEntity sysConfigEntity=new SysConfigEntity();
		sysConfigEntity.setParamKey("");
		sysConfigEntity.setParamValue("全部");
		sysConfigEntities=this.selectList(new EntityWrapper<SysConfigEntity>().eq("param_type", "awardType"));
		sysConfigEntities.add(sysConfigEntity);
		
		return sysConfigEntities;
	}
	@Override
	public List<SysConfigEntity> getawardLevel() {
		List<SysConfigEntity> sysConfigEntities=new ArrayList<SysConfigEntity>();
		SysConfigEntity sysConfigEntity=new SysConfigEntity();
		sysConfigEntity.setParamKey("");
		sysConfigEntity.setParamValue("全部");
		sysConfigEntities=this.selectList(new EntityWrapper<SysConfigEntity>().eq("param_type", "awardLevel"));
		sysConfigEntities.add(sysConfigEntity);
		
		return sysConfigEntities;
	}
	@Override
	public List<SysConfigEntity> getcourselist() {
		List<SysConfigEntity> sysConfigEntities=new ArrayList<SysConfigEntity>();
		SysConfigEntity sysConfigEntity=new SysConfigEntity();
		sysConfigEntity.setParamKey("");
		sysConfigEntity.setParamValue("全部");
		sysConfigEntities=this.selectList(new EntityWrapper<SysConfigEntity>().eq("param_type", "course"));
		sysConfigEntities.add(sysConfigEntity);
		
		return sysConfigEntities;
	}


	@Override
	public List<SysConfigEntity> getmood() {
		List<SysConfigEntity> sysConfigEntities=new ArrayList<SysConfigEntity>();
		SysConfigEntity sysConfigEntity=new SysConfigEntity();
		sysConfigEntity.setParamKey("");
		sysConfigEntity.setParamValue("全部");
		sysConfigEntities=this.selectList(new EntityWrapper<SysConfigEntity>().eq("param_type", "mood"));
		sysConfigEntities.add(sysConfigEntity);
		
		return sysConfigEntities;
	}
}
