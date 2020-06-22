package io.student.modules.sys.controller;

import io.student.common.utils.MacAddress;
import io.student.common.utils.R;
import io.student.common.utils.SecurityUtil;
import io.student.common.utils.StringUtil;
import io.student.modules.sys.entity.SysUserEntity;
import io.student.modules.sys.form.SysLoginForm;
import io.student.modules.sys.service.SysCaptchaService;
import io.student.modules.sys.service.SysConfigService;
import io.student.modules.sys.service.SysUserService;
import io.student.modules.sys.service.SysUserTokenService;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 登录相关
 * 
 * @author duanxin
 * @email sxz147@163.com
 * @date 2016年11月10日 下午1:15:31
 */
@RestController
public class SysLoginController extends AbstractController {
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysUserTokenService sysUserTokenService;
	@Autowired
	private SysCaptchaService sysCaptchaService;
	@Autowired
	private SysConfigService sysConfigService;

	
	
	
	@GetMapping("webname")
	public R getwebname()
	{
		
		String webnameString=sysConfigService.getValue("config","webname");
		if(null!=webnameString&&!webnameString.equals(""))
		{
			return R.ok(webnameString);
		}else {
			return R.ok("伯乐助学");
		}
		
	}
	

	
	
	/**
	 * 验证码
	 */
	@GetMapping("captcha.jpg")
	public void captcha(HttpServletResponse response, String uuid) throws ServletException, IOException {
		response.setHeader("Cache-Control", "no-store, no-cache");
		response.setContentType("image/png");

		// 获取图片验证码
		BufferedImage image = sysCaptchaService.getCaptcha(uuid);

		ServletOutputStream out = response.getOutputStream();
		ImageIO.write(image, "jpg", out);
		IOUtils.closeQuietly(out);
	}

	/**
	 * 登录
	 */
	@PostMapping("/sys/login")
	public Map<String, Object> login(@RequestBody SysLoginForm form) throws IOException {
		boolean captcha = sysCaptchaService.validate(form.getUuid(), form.getCaptcha());
		if (!captcha) {
			return R.error("验证码不正确");
		}

		// 用户信息
		SysUserEntity user = sysUserService.queryByUserName(form.getUsername());

		// 账号不存在、密码错误
		if (user == null || !user.getPassword().equals(new Sha256Hash(form.getPassword(), user.getSalt()).toHex())) {
			return R.error("账号或密码不正确");
		}

		// 账号锁定
		if (user.getStatus() == 0) {
			return R.error("账号已被锁定,请联系管理员");
		}

		// 生成token，并保存到数据库
		R r = sysUserTokenService.createToken(user.getUserId());
		return r;
	}

	/**
	 * 退出
	 */
	@PostMapping("/sys/logout")
	public R logout() {
		sysUserTokenService.logout(getUserId());
		return R.ok();
	}

}