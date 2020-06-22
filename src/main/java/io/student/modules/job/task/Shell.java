package io.student.modules.job.task;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import ch.qos.logback.core.PropertyDefinerBase;

@Component("shell")
public class Shell  extends PropertyDefinerBase {

	private Logger log = LoggerFactory.getLogger(getClass());
	private String path="";
	
	//ExecutorService executor = Executors.newFixedThreadPool(20);
	
	
	@Override
	public String getPropertyValue() {
		
		return this.path;
	}

	/**
	 * 脚本文件具体执行及脚本执行过程探测
	 * 
	 * @param script 脚本文件绝对路径
	 * @throws Exception
	 */
	private void call(String script,String logpath) throws Exception {
		this.path=logpath;
		File file=new File(logpath);
		if(!file.exists())
		{
			file.createNewFile();
		}	
		
		FileOutputStream oStream=new FileOutputStream(file);
		//FileAppender fileAppender = (FileAppender) log.getRootLogger().getAppender("file");//获取FileAppender对象
//		 fileAppender.setFile(logpath);//重新设置输出日志的路径和文件名
//		 fileAppender.activateOptions();//使设置的FileAppender起作用
			String cmd = "sh " + script;
			// 执行脚本并等待脚本执行完成
			Process process = Runtime.getRuntime().exec(cmd, null, new File(script).getParentFile());

			// 写出脚本执行中的过程信息
			BufferedReader infoInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
			BufferedReader errorInput = new BufferedReader(new InputStreamReader(process.getErrorStream()));
			String line = "";
			while ((line = infoInput.readLine()) != null) {
				oStream.write(line.getBytes());
				log.info(line);
			}
			while ((line = errorInput.readLine()) != null) {
				oStream.write(line.getBytes());
				log.error(line);
			}
			infoInput.close();
			errorInput.close();
			oStream.close();
			
			if(process.waitFor()!=0)
			{
				throw new Exception("shell "+script+"执行失败");
			}
		
			

//			// 启动独立线程等待process执行完成
//
//			//executor.submit(() -> {
//					CommandWaitForThread commandThread = new CommandWaitForThread(cmd);
//					commandThread.start();
//
//					while (!commandThread.isFinish()) {
//						log.info("shell " + script + " 还未执行完毕,10s后重新探测");
//						Thread.sleep(10000);
//					}
//
//					// 检查脚本执行结果状态码
//					if (commandThread.getExitValue() != 0) {
//						throw new Exception("shell " + script + "执行失败,exitValue = " + commandThread.getExitValue());
//					}
//					log.info("shell " + script + "执行成功,exitValue = " + commandThread.getExitValue());
//				
//
//			//});

	
	}

//	public class CommandWaitForThread extends Thread {
//
//		private String cmd;
//		private boolean finish = false;
//		private int exitValue = -1;
//
//		public CommandWaitForThread(String cmd) {
//			this.cmd = cmd;
//		}
//
//		public void run() {
//			try {
//				// 执行脚本并等待脚本执行完成
//				Process process = Runtime.getRuntime().exec(cmd);
//
//				// 写出脚本执行中的过程信息
//				BufferedReader infoInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
//				BufferedReader errorInput = new BufferedReader(new InputStreamReader(process.getErrorStream()));
//				String line = "";
//				while ((line = infoInput.readLine()) != null) {
//					log.info(line);
//				}
//				while ((line = errorInput.readLine()) != null) {
//					log.error(line);
//				}
//				infoInput.close();
//				errorInput.close();
//
//				// 阻塞执行线程直至脚本执行完成后返回
//				this.exitValue = process.waitFor();
//			} catch (Throwable e) {
//				log.error("CommandWaitForThread accure exception,shell " + cmd, e);
//				exitValue = 110;
//			} finally {
//				finish = true;
//			}
//		}
//
//		public boolean isFinish() {
//			return finish;
//		}
//
//		public void setFinish(boolean finish) {
//			this.finish = finish;
//		}
//
//		public int getExitValue() {
//			return exitValue;
//		}
//	}

	

}
