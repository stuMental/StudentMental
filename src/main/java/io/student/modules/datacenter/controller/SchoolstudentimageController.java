package io.student.modules.datacenter.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.student.common.utils.R;
import io.student.modules.datacenter.entity.Kimage;
import io.student.modules.datacenter.entity.Studentimage;
import io.student.modules.datacenter.service.KimageService;
import io.student.modules.datacenter.service.SchoolstudentService;
import io.student.modules.datacenter.service.StudentimageService;
import io.student.modules.sys.controller.AbstractController;

@RestController
@RequestMapping("/datacenter/schoolstudentimage")
public class SchoolstudentimageController  extends AbstractController {

	@Autowired
	private StudentimageService studentimageService;
	@Autowired
	private KimageService kimageService;
	@Autowired
	private SchoolstudentService SchoolstudentService;
	
	@GetMapping("/list")
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
			SchoolstudentService.delpho(uid);
			return R.ok();	
			
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
	
	
}
