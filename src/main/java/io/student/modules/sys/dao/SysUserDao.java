package io.student.modules.sys.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import io.student.modules.sys.entity.SysUserEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 系统用户
 * 
 * @author duanxin
 * @email sxz147@163.com
 * @date 2016年9月18日 上午9:34:11
 */
@Mapper
public interface SysUserDao extends BaseDao<SysUserEntity> {
	
	/**
	 * 查询用户的所有权限
	 * @param userId  用户ID
	 */
	List<String> queryAllPerms(Long userId);
	
	/**
	 * 查询用户的所有菜单ID
	 */
	List<Long> queryAllMenuId(Long userId);
	
	/**
	 * 根据用户名，查询系统用户
	 */
	SysUserEntity queryByUserName(String username);
	
	
	List<Map<String,Object>> dict(Map<String, Object> params);
	List<Map<String,Object>> getTeacherList(Map<String, Object> params);


	/**
	 * 根据用户id，查询用户名
	 */
	String getUsernameById(long user_id);


}
