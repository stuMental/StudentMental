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

package io.student.modules.job.controller;

import io.student.common.utils.PageUtils;
import io.student.common.utils.R;
import io.student.modules.job.entity.ScheduleJobLogEntity;
import io.student.modules.job.service.ScheduleJobLogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.net.URLDecoder;
import java.util.Map;

/**
 * 定时任务日志
 *
 * @author Mark sxz147@163.com
 * @since 1.2.0 2016-11-28
 */
@RestController
@RequestMapping("/sys/scheduleLog")
public class ScheduleJobLogController {
	@Autowired
	private ScheduleJobLogService scheduleJobLogService;
	
	/**
	 * 定时任务日志列表
	 */
	@GetMapping("/list")
	@RequiresPermissions("sys:schedule:log")
	public R list(@RequestParam Map<String, Object> params){
		PageUtils page = scheduleJobLogService.queryPage(params);
		
		return R.ok().put("page", page);
	}
	
	/**
	 * 定时任务日志信息
	 */
	@GetMapping("/info/{logId}")
	public R info(@PathVariable("logId") Long logId){
		ScheduleJobLogEntity log = scheduleJobLogService.selectById(logId);
		
		return R.ok().put("log", log);
	}
	
	
	@GetMapping("loginfo/{logId}")
	public R log(@PathVariable("logId") long logId)
	{
		try {
		String logpath=URLDecoder.decode(ResourceUtils.getURL("classpath:").getPath(),"utf-8").replace("webapps/student_service/WEB-INF/classes/", "logs/")+String.valueOf(logId)+".log";
		String text="";
		FileInputStream io=new FileInputStream(new File(logpath));
		byte[] buf = new byte[1024];  
		int length = 0;
		//循环读取文件内容，输入流中将最多buf.length个字节的数据读入一个buf数组中,返回类型是读取到的字节数。
		//当文件读取到结尾时返回 -1,循环结束。
		while((length = io.read(buf)) != -1){   
		 text=text+new String(buf,0,length);
		}
		io.close();
		
		return R.ok().put("data", text);
		
		}catch (Exception e) {
			return R.error(e.getMessage());
		}
		
		}
	
}
