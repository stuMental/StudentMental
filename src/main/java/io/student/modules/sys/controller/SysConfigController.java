/**
 * 
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package io.student.modules.sys.controller;


import io.student.common.annotation.SysLog;
import io.student.common.exception.RRException;
import io.student.common.utils.PageUtils;
import io.student.common.utils.R;
import io.student.common.validator.ValidatorUtils;
import io.student.modules.sys.entity.SysConfigEntity;
import io.student.modules.sys.service.SysConfigService;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import sun.misc.Regexp;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * 系统配置信息
 * 
 * @author duanxin
 * @email sxz147@163.com
 * @date 2016年12月4日 下午6:55:53
 */
@RestController
@RequestMapping("/sys/config")
public class SysConfigController extends AbstractController {
	@Autowired
	private SysConfigService sysConfigService;
	
	/**
	 * 所有配置列表
	 */
	@GetMapping("/list")
	@RequiresPermissions("sys:config:list")
	public R list(@RequestParam Map<String, Object> params){
		
		PageUtils page = sysConfigService.queryPage(params);

		return R.ok().put("page", page);
	}
	
	
	@GetMapping("typelist")
	public R TypeList()
	{
		return R.ok().put("data", sysConfigService.gettype());
	}
	
	@GetMapping("roomlist")
	public R roomList()
	{
		return R.ok().put("data", sysConfigService.getroom());
	}
	@GetMapping("awardtypelist")
	public R awardtypelist()
	{
		return R.ok().put("data", sysConfigService.getawardtype());
	}
	@GetMapping("awardLevellist")
	public R awardLevellist()
	{
		return R.ok().put("data", sysConfigService.getawardLevel());
	}
	@GetMapping("courselist")
	public R courselist()
	{
		return R.ok().put("data", sysConfigService.getcourselist());
	}
	@GetMapping("mood")
	public R mood()
	{
		return R.ok().put("data", sysConfigService.getmood());
	}
	
	
	/**
	 * 配置信息
	 */
	@GetMapping("/info/{id}")
	@RequiresPermissions("sys:config:info")
	public R info(@PathVariable("id") Long id){
		SysConfigEntity config = sysConfigService.selectById(id);
		
		return R.ok().put("config", config);
	}
	
	/**
	 * 保存配置
	 */
	@SysLog("保存配置")
	@PostMapping("/save")
	@RequiresPermissions("sys:config:save")
	public R save(@RequestBody SysConfigEntity config){
		ValidatorUtils.validateEntity(config);

		if(config.getParamType().equals("mood"))
		{
			if(!config.getParamValue().matches("(0\\d{1}|1\\d{1}|2[0-3]):([0-5]\\d{1})-(0\\d{1}|1\\d{1}|2[0-3]):([0-5]\\d{1})"))
			{
				throw new RRException("课堂时间格式必须是HH:mi-HH:mi");
			}
			if(config.getParamValue().length()!=11)
			{
				throw new RRException("课堂时间格式必须是HH:mi-HH:mi");
			}
			String dateString[]=config.getParamValue().split("-");

		    SimpleDateFormat myFmt1=new SimpleDateFormat("HH:mm"); 
			try {
				if(myFmt1.parse(dateString[0]).after(myFmt1.parse(dateString[1])))
				{
					throw  new RRException("开始时间必须小于结束时间");
				}
			} catch (ParseException e) {
				e.printStackTrace();
				throw  new RRException(e.getMessage());
			}
			
		}
		
		
		sysConfigService.save(config);
		
		return R.ok();
	}
	/**
	 * 修改配置
	 */
	@SysLog("修改配置")
	@PostMapping("/update")
	@RequiresPermissions("sys:config:update")
	public R update(@RequestBody SysConfigEntity config){
		ValidatorUtils.validateEntity(config);
		if(config.getParamType().equals("mood"))
		{
			if(!config.getParamValue().matches("(0\\d{1}|1\\d{1}|2[0-3]):([0-5]\\d{1})-(0\\d{1}|1\\d{1}|2[0-3]):([0-5]\\d{1})"))
			{
				throw new RRException("课堂时间格式必须是HH:mi-HH:mi");
			}
			if(config.getParamValue().length()!=11)
			{
				throw new RRException("课堂时间格式必须是HH:mi-HH:mi");
			}
			String dateString[]=config.getParamValue().split("-");

		    SimpleDateFormat myFmt1=new SimpleDateFormat("HH:mm"); 
			try {
				if(myFmt1.parse(dateString[0]).after(myFmt1.parse(dateString[1])))
				{
					throw  new RRException("开始时间必须小于结束时间");
				}
			} catch (ParseException e) {
				e.printStackTrace();
				throw  new RRException(e.getMessage());
			}
			
		}
		sysConfigService.update(config);
		
		return R.ok();
	}
	
	/**
	 * 删除配置
	 */
	@SysLog("删除配置")
	@PostMapping("/delete")
	@RequiresPermissions("sys:config:delete")
	public R delete(@RequestBody Long[] ids){
		sysConfigService.deleteBatch(ids);
		
		return R.ok();
	}

}
