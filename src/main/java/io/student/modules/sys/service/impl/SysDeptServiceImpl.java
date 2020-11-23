package io.student.modules.sys.service.impl;

import com.qiniu.util.StringUtils;

import io.student.common.annotation.DataFilter;
import io.student.common.exception.RRException;
import io.student.modules.sys.dao.SysDeptDao;
import io.student.modules.sys.entity.SysDeptEntity;
import io.student.modules.sys.service.SysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@Service("sysDeptService")
public class SysDeptServiceImpl implements SysDeptService {
	@Autowired
	private SysDeptDao sysDeptDao;
	
	@Override
	public SysDeptEntity queryObject(Long deptId){
		return sysDeptDao.queryObject(deptId);
	}
	
	@Override
	@DataFilter(tableAlias = "d", user = false)
	public List<SysDeptEntity> queryList(Map<String, Object> map){
		return sysDeptDao.queryList(map);
	}
	
	@Override
	public void save(SysDeptEntity sysDept){
		sysDeptDao.save(sysDept);
	}
	
	@Override
	public void update(SysDeptEntity sysDept){
		sysDeptDao.update(sysDept);
	}
	
	@Override
	public void delete(Long deptId){
		sysDeptDao.delete(deptId);
	}

	@Override
	public List<Long> queryDetpIdList(Long parentId) {
		return sysDeptDao.queryDetpIdList(parentId);
	}

	@Override
	public Long queryId(String name, Long preId) {
		return sysDeptDao.queryId(name, preId);
	}

	@Override
	public String getSubDeptIdList(Long deptId){
		//部门及子部门ID列表
		List<Long> deptIdList = new ArrayList<>();

		//获取子部门ID
		List<Long> subIdList = queryDetpIdList(deptId);
		getDeptTreeList(subIdList, deptIdList);

		//添加本部门
		deptIdList.add(deptId);

		String deptFilter = StringUtils.join(deptIdList, ",");
		return deptFilter;
	}

	/**
	 * 递归
	 */
	private void getDeptTreeList(List<Long> subIdList, List<Long> deptIdList){
		for(Long deptId : subIdList){
			List<Long> list = queryDetpIdList(deptId);
			if(list.size() > 0){
				getDeptTreeList(list, deptIdList);
			}

			deptIdList.add(deptId);
		}
	}

	@Override
	public Map<String, Object> getgrade(Long deptId) {
		Map<String, Object> param=new HashMap<String, Object>();
		if(deptId==0L)
		{
			throw new RRException("deptid不能为0");
		}
		
		SysDeptEntity sysDeptEntity = this.queryObject(deptId);
		if(sysDeptEntity==null)
		{
			throw new RRException("未查到相关部门,请刷新页面");
		}
		if(sysDeptEntity.getParentId()==0)
		{
		  param.put("college_name", sysDeptEntity.getName());
		}else
		{
			SysDeptEntity dept2=this.queryObject(sysDeptEntity.getParentId());
			if(dept2==null)
			{
				throw new RRException("未查到相关部门,请刷新页面");
			}
			if(dept2.getParentId()==0)
			{
				param.put("college_name", dept2.getName());
				param.put("grade_name", sysDeptEntity.getName());
			}else
			{
				SysDeptEntity dept3=this.queryObject(dept2.getParentId());
				param.put("college_name", dept3.getName());
				param.put("grade_name", dept2.getName());
				param.put("class_name", sysDeptEntity.getName());
			}
		}
		return param;
	}

	@Override
	public int selectleavel(Long dept_id) {
		
		return  sysDeptDao.selectleavel(dept_id);
	}
}
