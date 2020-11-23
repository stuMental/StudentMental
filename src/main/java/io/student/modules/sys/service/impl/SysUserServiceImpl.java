package io.student.modules.sys.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import io.student.common.annotation.DataFilter;
import io.student.common.exception.RRException;
import io.student.common.utils.Constant;
import io.student.common.utils.PageUtils;
import io.student.common.utils.Query;
import io.student.modules.datacenter.entity.Studentimage;
import io.student.modules.datacenter.service.StudentimageService;
import io.student.modules.eyereport.dao.StudentimageDao;
import io.student.modules.sys.dao.SysUserDao;
import io.student.modules.sys.entity.SysUserEntity;
import io.student.modules.sys.service.SysDeptService;
import io.student.modules.sys.service.SysRoleService;
import io.student.modules.sys.service.SysUserRoleService;
import io.student.modules.sys.service.SysUserService;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;

import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


/**
 * 系统用户
 * 
 * @author duanxin
 * @email sxz147@163.com
 * @date 2016年9月18日 上午9:46:09
 */
@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUserEntity> implements SysUserService {
	@Autowired
	private SysUserRoleService sysUserRoleService;
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private SysDeptService SysDeptService;
	@Autowired
	private SysUserDao sysUserDao;
	@Autowired
	private io.student.modules.datacenter.service.StudentimageService StudentimageService;
	@Autowired
	private StudentimageDao studentimageDao;
	
	@Override
	public SysUserEntity queryObject(Long userId)
	{
		return sysUserDao.queryObject(userId);
	}
	
	
	@Override
	public PageUtils queryPage(Map<String, Object> params) {
	/*	String username = (String)params.get("username");
		Long createUserId = (Long)params.get("createUserId");

		Page<SysUserEntity> page = this.selectPage(
			new Query<SysUserEntity>(params).getPage(),
			new EntityWrapper<SysUserEntity>()
				.setSqlSelect(" sys_user.*, (select d.name from sys_dept d where d.dept_id = sys_user.dept_id)  as dept_name ")
				.like(StringUtils.isNotBlank(username),"sys_user.username", username)
				.eq(createUserId != null,"sys_user.create_user_id", createUserId)
		);
*/
		Query query = new Query(params);
		
		return new PageUtils(queryList(params),queryTotal(params),query.getLimit(), query.getCurrPage());
	}
	
	
	@DataFilter(tableAlias = "u", user = false)
	private List<SysUserEntity> queryList(Map<String, Object> map){
		return sysUserDao.queryList(map);
	}
	
	@DataFilter(tableAlias = "u", user = false)
	private int queryTotal(Map<String, Object> map) {
		return sysUserDao.queryTotal(map);
	}
	
	

	@Override
	public List<String> queryAllPerms(Long userId) {
		return baseMapper.queryAllPerms(userId);
	}

	@Override
	public List<Long> queryAllMenuId(Long userId) {
		return baseMapper.queryAllMenuId(userId);
	}

	@Override
	public SysUserEntity queryByUserName(String username) {
		return baseMapper.queryByUserName(username);
	}

//	@Override
//	@Transactional
//	public void save(SysUserEntity user) {
//		user.setCreateTime(new Date());
//		//sha256加密
//		String salt = RandomStringUtils.randomAlphanumeric(20);
//		user.setPassword(new Sha256Hash(user.getPassword(), salt).toHex());
//		user.setSalt(salt);
//		this.insert(user);
//
//		//检查角色是否越权
//		checkRole(user);
//
//		//保存用户与角色关系
//		sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
//	}

	@Override
	@Transactional
	public boolean save(SysUserEntity user, List<Studentimage> studentimages) {
		System.out.println("AAaaa");
		user.setCreateTime(new Date());
		//sha256加密
		String salt = RandomStringUtils.randomAlphanumeric(20);
		user.setPassword(new Sha256Hash(user.getPassword(), salt).toHex());
		user.setSalt(salt);
		System.out.println("bbbbbb");
		this.insert(user);
		System.out.println("ccccc");
		//检查角色是否越权
		checkRole(user);
		System.out.println("dddd");
		//保存用户与角色关系
		sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
		System.out.println("eeee");
//		保存标准照
		StudentimageService.delete(new EntityWrapper<Studentimage>().eq("student_number",
				user.getUsername()));
		System.out.println("ffff");
		if (studentimages != null && studentimages.size() != 0) {
			for (Studentimage studentimage : studentimages) {
				studentimage.setStudentNumber(user.getUsername());
				studentimage.setStat("1");
			}
			StudentimageService.insertBatch(studentimages);
		}
		System.out.println("ggggg");
		return true;
	}

//	@Override
//	@Transactional
//	public void update(SysUserEntity user) {
//		user.setCreateTime(new Date());
//		if(StringUtils.isBlank(user.getPassword())){
//			user.setPassword(null);
//		}else{
//			user.setPassword(new Sha256Hash(user.getPassword(), user.getSalt()).toHex());
//		}
//		this.updateById(user);
//		//this.updateAllColumnById(user);
//		//检查角色是否越权
//		checkRole(user);
//
//		//保存用户与角色关系
//		sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
//	}

	@Override
	@Transactional
	public boolean update(SysUserEntity user, List<Studentimage> studentimages) {
//		System.out.println(user);
//		System.out.println(studentimages);

//		保存user表信息
		user.setCreateTime(new Date());
		if(StringUtils.isBlank(user.getPassword())){
			user.setPassword(null);
		}else{
			user.setPassword(new Sha256Hash(user.getPassword(), user.getSalt()).toHex());
		}
		this.updateById(user);
		//检查角色是否越权
		checkRole(user);
		//保存用户与角色关系
		sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());

//		保存标准照
		StudentimageService.delete(new EntityWrapper<Studentimage>().eq("student_number",
				user.getUsername()));
		if (studentimages != null && studentimages.size() != 0) {
			for (Studentimage studentimage : studentimages) {
				studentimage.setStudentNumber(user.getUsername());
				studentimage.setStat("1");
			}
			StudentimageService.insertBatch(studentimages);
		}
		return true;
	}

	@Override
	public void deleteBatch(Long[] userIds) {

		for (int j = 0; j < userIds.length; j++) {
			String username = sysUserDao.getUsernameById(userIds[j]);
			studentimageDao.deleteStudentImageByStudentId(username);
		}
//		后删学生信息
		this.deleteBatchIds(Arrays.asList(userIds));
	}

	@Override
	public boolean updatePassword(Long userId, String password, String newPassword) {
		SysUserEntity userEntity = new SysUserEntity();
		userEntity.setPassword(newPassword);
		return this.update(userEntity,
				new EntityWrapper<SysUserEntity>().eq("user_id", userId).eq("password", password));
	}
	
	/**
	 * 检查角色是否越权
	 */
	private void checkRole(SysUserEntity user){
		if(user.getRoleIdList() == null || user.getRoleIdList().size() == 0){
			return;
		}
		//如果不是超级管理员，则需要判断用户的角色是否自己创建
		if(user.getCreateUserId() == Constant.SUPER_ADMIN){
			return ;
		}
		
		//查询用户创建的角色列表
		List<Long> roleIdList = sysRoleService.queryRoleIdList(user.getCreateUserId());

		//判断是否越权
		if(!roleIdList.containsAll(user.getRoleIdList())){
			throw new RRException("新增用户所选角色，不是本人创建");
		}
	}


	@Override
	public List<Map<String, Object>> dict(Map<String, Object> param) {
		String deptId = (String) param.get("deptId");
		String deptidsString ="";
		if(StringUtils.isNotBlank(deptId))
		{
		 deptidsString = SysDeptService.getSubDeptIdList(Long.valueOf(deptId));
		param.put("dept_id", deptidsString);
		}
		
		return sysUserDao.dict(param);
	}

	@Override
	public List<Map<String, Object>> getTeacherList(Map<String, Object> param) {
		return sysUserDao.getTeacherList(param);
	}
}
