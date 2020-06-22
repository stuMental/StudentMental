package io.student.modules.sys.dao;

import org.apache.ibatis.annotations.Mapper;

import io.student.modules.sys.entity.SysRoleDeptEntity;

import java.util.List;

/**
 * 角色与部门对应关系
 * 
 * @author duanxin
 * @email duanxin@gennlife.com
 * @date 2018/06/12
 */
@Mapper
public interface SysRoleDeptDao extends BaseDao<SysRoleDeptEntity> {
	
	/**
	 * 根据角色ID，获取部门ID列表
	 */
	List<Long> queryDeptIdList(Long roleId);
}
