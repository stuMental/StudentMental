package io.student.modules.datacenter.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import io.student.datasources.DataSourceNames;
import io.student.datasources.annotation.DataSource;
import io.student.modules.datacenter.entity.Kimage;
import io.student.modules.datacenter.entity.Studentimage;
import io.student.modules.datacenter.service.KimageService;
import io.student.modules.datacenter.service.StudentimageService;
import io.student.modules.eyereport.dao.StudentimageDao;
import io.student.modules.eyereport.entity.Scamerainfo;

@Service("studentimageservice")
public class StudentimageServiceImpl extends ServiceImpl<StudentimageDao, Studentimage> implements StudentimageService {

	@Autowired
	private StudentimageDao studentimageDao;

	@Autowired
	private KimageService kimageService;

	@Override
	public List<Studentimage> queryall(Map<String, Object> params) {

		return studentimageDao.selectByMap(params);
	}

	@Override
	@Async
	public void resolv(List<String> filenames) throws Exception {
		try {
			int BUFFER_SIZE = 100;
			for (String filename : filenames) {
				String destDirPath = URLDecoder.decode(ResourceUtils.getURL("classpath:").getPath(), "utf-8")
						.replace("webapps/student_service/WEB-INF/classes/", "zips/");
				String path = destDirPath + filename;
				String destdirpath=destDirPath+"file/";
				File f=new File(destdirpath);
				if(!f.exists())
				{
					f.mkdir();
				}
				ZipFile zipfile = null;
				System.out.print(path);
				File zip=new File(path);
				if(!zip.exists())
				{
					continue;
				}
				zipfile = new ZipFile(zip);
				
				Enumeration<?> entries = zipfile.entries();
				while (entries.hasMoreElements()) {
					ZipEntry entry = (ZipEntry) entries.nextElement();
					System.out.println("解压" + entry.getName());
					// 如果是文件夹，就创建个文件夹
					if (entry.isDirectory()) {
						String dirPath = destdirpath + entry.getName();
						File dir = new File(dirPath);
						dir.mkdirs();
					} else {
						// 如果是文件，就先创建一个文件，然后用io流把内容copy过去
						File targetFile = new File(destdirpath + entry.getName());
						// 保证这个文件的父文件夹必须要存在
						if (!targetFile.getParentFile().exists()) {
							targetFile.getParentFile().mkdirs();
						}
						targetFile.createNewFile();
						// 将压缩文件内容写入到这个文件中
						InputStream is = zipfile.getInputStream(entry);
						FileOutputStream fos = new FileOutputStream(targetFile);
						ByteArrayOutputStream swapStream = new ByteArrayOutputStream(); 
						int len;
						byte[] buf = new byte[BUFFER_SIZE];
						while ((len = is.read(buf)) != -1) {
							fos.write(buf, 0, len);
							 swapStream.write(buf, 0, len); 
						}
						
						byte[] in_b = swapStream.toByteArray(); 
						Kimage kimage=new Kimage();
						kimage.setImage(in_b);
						kimage.setId(UUID.randomUUID().toString().replace("-", ""));
						int id=kimageService.insertobj(kimage);
						if(id!=1)
						{
							throw new Exception("插入图片失败");
						}
						Studentimage studentimage=new Studentimage();
						studentimage.setDt(new Date());
						studentimage.setImageId(kimage.getId());
						studentimage.setStat("1");
						String number=targetFile.getParentFile().getName();
						String student_number=number;
						studentimage.setStudentNumber(student_number);
						if(kimageService.inseroo(studentimage)!=1)
						{
							throw new Exception("插入学生失败");
						}
						// 关流顺序，先打开的后关闭
						swapStream.close();
						fos.close();
						is.close();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public int insertobj(Studentimage studentimage) {
		return studentimageDao.insertAllColumn(studentimage);
	}

}
