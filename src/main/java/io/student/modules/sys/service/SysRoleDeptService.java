package io.student.modules.sys.service;

import java.util.List;


/**
 * 角色与部门对应关系
 * 
 * @author duanxin
 * @email duanxin@gennlife.com
 * @date 2018/06/12
 */
public interface SysRoleDeptService {
	
	void saveOrUpdate(Long roleId, List<Long> deptIdList);
	
	/**
	 * 根据角色ID，获取部门ID列表
	 */
	List<Long> queryDeptIdList(Long roleId);
	
}
