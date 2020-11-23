package io.student.modules.sys.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import io.student.modules.sys.entity.SysRoleEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 角色管理
 * 
 * @author duanxin
 * @email sxz147@163.com
 * @date 2016年9月18日 上午9:33:33
 */
@Mapper
public interface SysRoleDao extends BaseDao<SysRoleEntity> {
	
	/**
	 * 查询用户创建的角色ID列表
	 */
	List<Long> queryRoleIdList(Long createUserId);
	Long queryRoleId(String roleName);
}
