package io.student.modules.sys.controller;

import io.student.common.annotation.SysLog;
import io.student.common.utils.Constant;
import io.student.common.utils.PageUtils;
import io.student.common.utils.R;
import io.student.common.validator.ValidatorUtils;
import io.student.modules.sys.entity.SysRoleEntity;
import io.student.modules.sys.service.SysRoleDeptService;
import io.student.modules.sys.service.SysRoleMenuService;
import io.student.modules.sys.service.SysRoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 角色管理
 * 
 * @author duanxin
 * @email sxz147@163.com
 * @date 2016年11月8日 下午2:18:33
 */
@RestController
@RequestMapping("/sys/role")
public class SysRoleController extends AbstractController {
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private SysRoleMenuService sysRoleMenuService;
	@Autowired
	private SysRoleDeptService sysRoleDeptService;

	/**
	 * 角色列表
	 */
	@GetMapping("/list")
	@RequiresPermissions("sys:role:list")
	public R list(@RequestParam Map<String, Object> params){
		//如果不是超级管理员，则只查询自己创建的角色列表
		if(getUserId() != Constant.SUPER_ADMIN){
			params.put("createUserId", String.valueOf(getUserId()));
		}

		PageUtils page = sysRoleService.queryPage(params);

		return R.ok().put("page", page);
	}
	
	/**
	 * 角色列表
	 */
	@GetMapping("/select")
	@RequiresPermissions("sys:role:select")
	public R select(){
		Map<String, Object> map = new HashMap<>();
		
		//如果不是超级管理员，则只查询自己所拥有的角色列表
		if(getUserId() != Constant.SUPER_ADMIN){
			map.put("create_user_id", String.valueOf(getUserId()));
		}
		List<SysRoleEntity> list = sysRoleService.selectByMap(map);
		
		return R.ok().put("list", list);
	}
	
	/**
	 * 角色信息
	 */
	@GetMapping("/info/{roleId}")
	@RequiresPermissions("sys:role:info")
	public R info(@PathVariable("roleId") Long roleId){
		SysRoleEntity role = sysRoleService.selectById(roleId);
		
		//查询角色对应的菜单
		List<Long> menuIdList = sysRoleMenuService.queryMenuIdList(roleId);
		role.setMenuIdList(menuIdList);
		//查询角色对应的部门
		List<Long> deptIdList = sysRoleDeptService.queryDeptIdList(roleId);
		role.setDeptIdList(deptIdList);
		return R.ok().put("role", role);
	}
	
	/**
	 * 保存角色
	 */
	@SysLog("保存角色")
	@PostMapping("/save")
	@RequiresPermissions("sys:role:save")
	public R save(@RequestBody SysRoleEntity role){
		ValidatorUtils.validateEntity(role);
		
		role.setCreateUserId(getUserId());
		sysRoleService.save(role);
		
		return R.ok();
	}
	
	/**
	 * 修改角色
	 */
	@SysLog("修改角色")
	@PostMapping("/update")
	@RequiresPermissions("sys:role:update")
	public R update(@RequestBody SysRoleEntity role){
		ValidatorUtils.validateEntity(role);
		
		role.setCreateUserId(getUserId());
		sysRoleService.update(role);
		
		return R.ok();
	}
	
	/**
	 * 删除角色
	 */
	@SysLog("删除角色")
	@PostMapping("/delete")
	@RequiresPermissions("sys:role:delete")
	public R delete(@RequestBody Long[] roleIds){
		sysRoleService.deleteBatch(roleIds);
		
		return R.ok();
	}
}
