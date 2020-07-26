package io.student.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import io.student.common.utils.PageUtils;
import io.student.modules.datacenter.entity.Studentimage;
import io.student.modules.sys.entity.SysUserEntity;

import java.util.List;
import java.util.Map;


/**
 * 系统用户
 * 
 * @author duanxin
 * @email sxz147@163.com
 * @date 2016年9月18日 上午9:43:39
 */
public interface SysUserService extends IService<SysUserEntity> {

	PageUtils queryPage(Map<String, Object> params);

	SysUserEntity queryObject(Long userId);
	
	
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

	/**
	 * 保存用户
	 */
//	void save(SysUserEntity user);
	boolean save(SysUserEntity user, List<Studentimage> studentimages);

	/**
	 * 修改用户
	 */
//	void update(SysUserEntity user);
	boolean update(SysUserEntity user, List<Studentimage> studentimages);

	/**
	 * 修改用户标准照
	 */
//	boolean update_image(Map<String, Object> param);
	
	/**
	 * 删除用户
	 */
	void deleteBatch(Long[] userIds);

	/**
	 * 修改密码
	 * @param userId       用户ID
	 * @param password     原密码
	 * @param newPassword  新密码
	 */
	boolean updatePassword(Long userId, String password, String newPassword);
	
	List<Map<String, Object>> dict(Map<String, Object> param);
	//	获取所有教师列表
	List<Map<String, Object>> getTeacherList(Map<String, Object> param);
}
