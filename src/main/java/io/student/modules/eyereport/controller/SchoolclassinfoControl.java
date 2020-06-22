package io.student.modules.eyereport.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.student.common.utils.PageUtils;
import io.student.common.utils.R;
import io.student.datasources.DataSourceNames;
import io.student.datasources.DynamicDataSource;
import io.student.modules.datacenter.entity.Kimage;
import io.student.modules.datacenter.entity.Studentimage;
import io.student.modules.datacenter.service.KimageService;
import io.student.modules.datacenter.service.StudentimageService;
import io.student.modules.eyereport.entity.Schoolclassinfo;
import io.student.modules.eyereport.service.SchoolclassinfoService;
import io.student.modules.sys.controller.AbstractController;
import io.student.modules.sys.entity.SysDeptEntity;
import io.student.modules.sys.service.SysDeptService;

@RestController
@RequestMapping("/eyereportdata/schoolclassinfo")
public class SchoolclassinfoControl extends AbstractController {

	@Autowired
	private SchoolclassinfoService schoolclassinfoService;
	@Autowired
	private SysDeptService sysDeptService;
	
	@Autowired
	private KimageService kimageService;
	@Autowired
	private StudentimageService studentimageService;
	
	
	@GetMapping("/list")
	public R list(@RequestParam Map<String, Object> params){
		long deptid = 0;
		try {
			deptid = Long.parseLong(params.get("dept_id").toString());
		} catch (Exception e) {
			deptid = 0;
		}

		// 判断是否root
		if (deptid != 0) {
			List<Long> deptids = sysDeptService.queryDetpIdList(deptid);
			// 判断是否子节点
			if (deptids.size() != 0) {
				return R.error("请选择子节点");
			} else if (deptids.size() == 0) {
				SysDeptEntity deptEntity = sysDeptService.queryObject(deptid);
				params.put("class_name", deptEntity.getName());
				SysDeptEntity deptparent=sysDeptService.queryObject(deptEntity.getParentId());
				params.put("grade_name", deptparent.getName());
			}
		}
		
		
		PageUtils page = schoolclassinfoService.queryPage(params);

		return R.ok().put("page", page);
	}
	
	@GetMapping("/info/{studentnumber}")
	public  R info(@PathVariable(value="studentnumber") String studentnumber)
	{
		try {
			Schoolclassinfo schoolclassinfo=schoolclassinfoService.queryobj(studentnumber);
			
			
			
			return R.ok().put("data", schoolclassinfo);
		} catch (Exception e) {
			return R.error(e.toString());
		}
		
	}
	@PostMapping("updata")
	public R updata(@RequestBody Map<String, Object> param)
	{
		try {
		if(schoolclassinfoService.updata(param))
		{
			return R.ok();
		}else
		{
			return R.error("保存失败");
		}
		}catch (Exception e) {
			return R.error(e.getMessage());
		}
	}
	
	
	
	
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	public R save(@RequestBody Map<String, Object> params)
	{
		try {
		String class_id=params.get("class_id").toString();
		SysDeptEntity sysDeptEntity=sysDeptService.queryObject(Long.parseLong(class_id));
		if(sysDeptEntity==null)
		{
			throw new Exception("班级为空");
		}
		String grade_name=sysDeptService.queryObject(sysDeptEntity.getParentId()).getName();
		params.put("class_name", sysDeptEntity.getName());
		params.put("grade_name", grade_name);
			if(schoolclassinfoService.save(params))
			{
				return R.ok();
			}else {
				return R.error("保存失败");
			}
		} catch (Exception e) {
			return R.error(e.toString());
		}
	}
	@RequestMapping("/update")
	public R update(@RequestBody Map<String, Object> params)
	{
		try {
			String class_id=params.get("class_id").toString();
			SysDeptEntity sysDeptEntity=sysDeptService.queryObject(Long.parseLong(class_id));
			if(sysDeptEntity==null)
			{
				throw new Exception("班级为空");
			}
			String grade_name=sysDeptService.queryObject(sysDeptEntity.getParentId()).getName();
			params.put("class_name", sysDeptEntity.getName());
			params.put("grade_name", grade_name);
			if(schoolclassinfoService.update(params))
			{
				return R.ok();
			}else {
				return R.error("保存失败");
			}
		} catch (Exception e) {
			return R.error(e.toString());
		}
	}
	
	
	@GetMapping("/pho/image")
	public void captcha(HttpServletResponse response, String uuid) throws ServletException, IOException {
		response.setHeader("Cache-Control", "no-store, no-cache");
		response.setContentType("image/jpeg");
		Kimage kimage=kimageService.querybyid(uuid);
		
		ServletOutputStream out = response.getOutputStream();
		out.write(kimage.getImage());
		
	}
	
	
	@GetMapping("/pho/del/{uid}")
	public R delete(@PathVariable(value="uid") String uid)
	{
		try {
			schoolclassinfoService.delpho(uid);
			return R.ok();	
			
		} catch (Exception e) {
			return R.error(e.toString());
		}
		
	}
	
	@GetMapping("/pho/list")
	public R pholist(@RequestParam Map<String, Object> params){
		try {
			Map<String, Object> param=new HashMap<String, Object>();
			param.put("student_number", params.get("student_number"));
			
			
			List<Studentimage> studentimage =studentimageService.queryall(param);
			
			
			
			return R.ok().put("data", studentimage);
			} catch (Exception e) {
			return R.error(e.toString());
		}
		
	}
	
	@PostMapping("/resolv")
	public R resolv(@RequestBody List<String> filenames)
	{
		try {
		studentimageService.resolv(filenames);
		}catch (Exception e) {
		return R.error(e.getMessage());
		}
		return R.ok();
		
	}
	
	@RequestMapping("/uploadall")
	public R uploads(@RequestParam("file")MultipartFile file)
	{
		if(file.isEmpty())
		{
			return R.error("上传文件不能为空");
		}
		try {
			String filename=UUID.randomUUID().toString().replace("-", "")+file.getName()+".zip";
			String path=URLDecoder.decode(ResourceUtils.getURL("classpath:").getPath(),"utf-8").replace("webapps/student_service/WEB-INF/classes/", "zips/")+filename;
			File filetmp=new  File(path);
			if(!filetmp.getParentFile().exists())
			{
				filetmp.getParentFile().mkdir();
			}
			
			if(!filetmp.exists())
			{
				filetmp.createNewFile();
			}
			FileOutputStream oStream=new FileOutputStream(filetmp);
			InputStream is = file.getInputStream();
			byte[] buff = new byte[100]; 
			int rc = 0; 
			while ((rc = is.read(buff, 0, 100)) > 0) { 
				oStream.write(buff, 0, rc); 
			} 
			oStream.close();
			is.close();
		return R.ok().put("filename", filename);
		
		} catch (Exception e) {
			return R.error(e.toString());
		}
	}
	
	
	
	
	@RequestMapping("/uploadlocal")
	public R importxsl(@RequestParam("file") MultipartFile file) {
		if (file.isEmpty()) {
			return R.error("上传文件不能为空");
		}
		try {
			InputStream is = file.getInputStream();
			ByteArrayOutputStream swapStream = new ByteArrayOutputStream(); 
			byte[] buff = new byte[100]; 
			int rc = 0; 
			while ((rc = is.read(buff, 0, 100)) > 0) { 
			    swapStream.write(buff, 0, rc); 
			} 
			byte[] in_b = swapStream.toByteArray(); 
			Kimage kimage=new Kimage();
			kimage.setImage(in_b);
			kimage.setId(UUID.randomUUID().toString().replace("-", ""));
			int id=kimageService.insertobj(kimage);
			if(id!=1)
			{
				return R.error("失败");
			}
			
			return R.ok().put("data", kimage.getId());
		} catch (Exception e) {
			return R.error(e.toString());
		}
	
	}
	
	
	@PostMapping("/delete")
	public R delete(@RequestBody Long[] ids)
	{
		try {
			
			if(schoolclassinfoService.del(ids))
			{
				return  R.ok();
			}else
			{
				return R.error();
			}
		} catch (Exception e) {
			return R.error(e.toString());
		}
		
		
		
	}
	
	
	
	
}
