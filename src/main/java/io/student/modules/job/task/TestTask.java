package io.student.modules.job.task;

import io.student.modules.job.entity.ScheduleJobEntity;

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

import io.student.modules.sys.entity.SysUserEntity;
import io.student.modules.sys.service.SysUserService;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ch.qos.logback.core.PropertyDefinerBase;

/**
 * 测试定时任务(演示Demo，可删除)
 *
 * testTask为spring bean的名称
 *
 * @author Mark sxz147@163.com
 * @since 1.2.0 2016-11-28
 */
@Component("testTask")
public class TestTask  {
	

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private SysUserService sysUserService;
	
	public void test(String params,String logpath) throws Exception{
//		this.path=logpath;
		File file=new File(logpath);
		if(!file.exists())
		{
			file.createNewFile();
		}
		FileOutputStream oStream=new FileOutputStream(file);
		oStream.write(params.getBytes());
		oStream.close();
		logger.info(logpath);
		logger.info("我是带参数的test方法，正在被执行，参数为：" + params);
		try {
			Thread.sleep(1000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//SysUserEntity user = sysUserService.selectById(1L);
		//System.out.println(ToStringBuilder.reflectionToString(user));
		
	}
	
	
//	public void test2(){
//		logger.info("我是不带参数的test2方法，正在被执行");
//	}
}
