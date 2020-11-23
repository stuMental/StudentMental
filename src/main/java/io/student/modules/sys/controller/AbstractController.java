package io.student.modules.sys.controller;

import io.student.modules.sys.entity.SysUserEntity;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controller公共组件
 * 
 * @author duanxin
 * @email sxz147@163.com
 * @date 2016年11月9日 下午9:42:26
 */
public abstract class AbstractController {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	protected SysUserEntity getUser() {

		return (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
	}

	protected Long getUserId() {
		System.out.println("sdedede");
		System.out.println(SecurityUtils.getSubject());
		System.out.println(SecurityUtils.getSubject().getSession());
		System.out.println(SecurityUtils.getSubject().getPrincipal());
		System.out.println((SysUserEntity) SecurityUtils.getSubject().getPrincipal());
		System.out.println(getUser());
		System.out.println(getUser().getUserId());
		return getUser().getUserId();
	}
	protected Long getDeptId() {
		return getUser().getDeptId();
	}
}
