package io.student.modules.eyereport.service.impl;


import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.student.common.utils.PageUtils;
import io.student.common.utils.Query;
import io.student.datasources.DataSourceNames;
import io.student.datasources.annotation.DataSource;
import io.student.modules.datacenter.dao.SchoolperformanceinfoDao;
import io.student.modules.datacenter.entity.Studentimage;
import io.student.modules.eyereport.dao.KimageDao;
import io.student.modules.eyereport.dao.SchoolclassinfoDao;
import io.student.modules.eyereport.dao.StudentimageDao;
import io.student.modules.eyereport.entity.Schoolclassinfo;
import io.student.modules.eyereport.service.CourseService;
import io.student.modules.eyereport.service.SchoolclassinfoService;
import io.student.modules.sys.service.SysDeptService;

@Service("SchoolclassinfoService")
public class SchoolclassinfoServiceImpl extends ServiceImpl<SchoolclassinfoDao,Schoolclassinfo> implements SchoolclassinfoService {
	
	@Autowired
	private SchoolclassinfoDao schoolclassinfoDao;
	@Autowired
	private SysDeptService sysDeptService;
	

	
	@Autowired
	private SchoolperformanceinfoDao schoolperformanceinfoDao;
	
	@Autowired
	private StudentimageDao studentimageDao;
	
	@Autowired
	private KimageDao kimageDao;
	
	@Autowired
	private CourseService courseService;
	
	
	@Override
	@DataSource(name = DataSourceNames.SECOND)
 	public PageUtils queryPage(Map<String, Object> params) {
		String class_name="";
		String grade_name="";
		try {
			class_name=(String)params.get("class_name");
			grade_name=(String)params.get("grade_name");
		} catch (Exception e) {
			// TODO: handle exception
		}
 
		Page<Schoolclassinfo> page = this.selectPage(
				new Query<Schoolclassinfo>(params).getPage(),
				new EntityWrapper<Schoolclassinfo>().eq(StringUtils.isNotBlank(class_name), "class_name", class_name)
				.eq(StringUtils.isNotBlank(grade_name), "grade_name", grade_name)
		);

		return new PageUtils(page);
	}
	
	@Override
	@DataSource(name = DataSourceNames.SECOND)
	public Schoolclassinfo queryobj(String studentnumber)
	{
		return schoolclassinfoDao.selectById(studentnumber);
	}

	@Override
	@DataSource(name = DataSourceNames.SECOND)
	public int insertobj(Schoolclassinfo schoolclassinfo) {
		// TODO Auto-generated method stub
		return schoolclassinfoDao.insert(schoolclassinfo);
	}
	
	@Override
	@DataSource(name = DataSourceNames.SECOND)
	public boolean save(Map<String,Object> params) throws Exception {
		return ((SchoolclassinfoServiceImpl)AopContext.currentProxy()).saves(params);
	}
	
	
	
	@Override
	@DataSource(name=DataSourceNames.SECOND)
	public boolean del(Long[] ids) throws Exception
	{
		return ((SchoolclassinfoServiceImpl)AopContext.currentProxy()).dels(ids);
	}
	
	
	
	@Transactional(rollbackFor=Exception.class)
	public boolean dels(Long[] ids) throws Exception{
		//删除学生表
		schoolclassinfoDao.deleteBatchIds(Arrays.asList(ids));
		Map<String, Object> columnMap=new HashMap<String, Object>();
		for (Long studentnumber : ids) {
			columnMap.put("student_number", studentnumber);
		}
		schoolperformanceinfoDao.deleteByMap(columnMap);
		//schoolawardinfoDao.deleteByMap(columnMap);
		List<Studentimage> studentimages=studentimageDao.selectByMap(columnMap);
		for (Studentimage studentimage : studentimages) {
			kimageDao.deleteById(studentimage.getImageId());
		}
		studentimageDao.deleteByMap(columnMap);
		return true;
	}
	
	
	
	@Transactional(rollbackFor=Exception.class) 
	public boolean saves(Map<String,Object> params) throws Exception {
		
//		Schoolclassinfo  schoolclassinfo= JSON.parseObject(JSON.toJSONString(params.get("schoolclassinfo")),Schoolclassinfo.class);
//		//List<Schoolawardinfo>  Schoolawardinfos=JSON.parseArray(JSON.toJSONString(params.get("studentawaredata")), Schoolawardinfo.class);
//		List<Schoolperformanceinfo> schoolperformanceinfos=JSON.parseArray(JSON.toJSONString(params.get("studentsoredata")),Schoolperformanceinfo.class);
//		List<Studentimage> studentimages=JSON.parseArray(JSON.toJSONString(params.get("studentphodata")),Studentimage.class);
//		
//		Date dt =new Date();
//		schoolclassinfo.setDt(dt);
//		String class_name=params.get("class_name").toString();
//		String grade_name=params.get("grade_name").toString();
//		schoolclassinfo.setClass_name(class_name);
//		schoolclassinfo.setGrade_name(grade_name);
//		if(this.queryobj(schoolclassinfo.getStudent_number())!=null)
//		{
//			throw new Exception("已存在该学生");
//		}
//		
//		
//		this.insertobj(schoolclassinfo);
//		//取学生信息完毕
////		for (Schoolawardinfo schoolawardinfo : Schoolawardinfos) {
////			//验证数据
////			if((schoolawardinfo.getAward_level().equals("")||schoolawardinfo.getAward_level()==null)&&
////					(schoolawardinfo.getAward_name().equals("")||schoolawardinfo.getAward_name()==null)&&
////					(schoolawardinfo.getAward_type().equals("")||schoolawardinfo.getAward_type()==null)&&
////					(schoolawardinfo.getDt()==null))
////			{
////				throw new Exception("数据不能为空");
////			}
////			schoolawardinfo.setStudent_name(schoolclassinfo.getStudent_name());
////			schoolawardinfo.setStudent_number(schoolclassinfo.getStudent_number());
////			schoolawardinfoDao.insert(schoolawardinfo);
////		}
//		//award完毕
//		for (Schoolperformanceinfo schoolperformanceinfo : schoolperformanceinfos) {
//			if((schoolperformanceinfo.getCourse_id().equals("")||schoolperformanceinfo.getCourse_id()==null)&&
//					(schoolperformanceinfo.getCourse_name().equals("")||schoolperformanceinfo.getCourse_name()==null)&&
//					(schoolperformanceinfo.getScore().equals("")||schoolperformanceinfo.getScore()==null)&&
//					(schoolperformanceinfo.getDt()==null))
//			{
//				throw new Exception("数据不能为空");
//			}
////			schoolperformanceinfo.setClass_name(class_name);
////			schoolperformanceinfo.setGrade_name(grade_name);
////			schoolperformanceinfo.setStudent_name(schoolclassinfo.getStudent_name());
//			schoolperformanceinfo.setStudent_number(schoolclassinfo.getStudent_number());
//			schoolperformanceinfoDao.insert(schoolperformanceinfo);
//			
//		}
//		for (Studentimage studentimage : studentimages) {
//			if(studentimage.getImageId()==null)
//			{
//				throw new Exception("数据不能为空");
//			}
//			studentimage.setDt(dt);
//			studentimage.setStudentNumber(schoolclassinfo.getStudent_number());
//			studentimage.setStat("1");
//			studentimageDao.insert(studentimage);
//		}
		return true;
	}
	

	@Transactional(rollbackFor=Exception.class) 
	public boolean updates(Map<String, Object> params) throws Exception {
//		Schoolclassinfo  schoolclassinfo= JSON.parseObject(JSON.toJSONString(params.get("schoolclassinfo")),Schoolclassinfo.class);
//	//	List<Schoolawardinfo>  Schoolawardinfos=JSON.parseArray(JSON.toJSONString(params.get("studentawaredata")), Schoolawardinfo.class);
//		List<Schoolperformanceinfo> schoolperformanceinfos=JSON.parseArray(JSON.toJSONString(params.get("studentsoredata")),Schoolperformanceinfo.class);
//		List<Studentimage> studentimages=JSON.parseArray(JSON.toJSONString(params.get("studentphodata")),Studentimage.class);
//		
//		Date dt =new Date();
//		schoolclassinfo.setDt(dt);
//		String class_name=params.get("class_name").toString();
//		String grade_name=params.get("grade_name").toString();
//		schoolclassinfo.setClass_name(class_name);
//		schoolclassinfo.setGrade_name(grade_name);
//		schoolclassinfoDao.updateAllColumnById(schoolclassinfo);
//		//取学生信息完毕
//		
////		for (Schoolawardinfo schoolawardinfo : Schoolawardinfos) {
////			//验证数据
////			if((schoolawardinfo.getAward_level().equals("")||schoolawardinfo.getAward_level()==null)&&
////					(schoolawardinfo.getAward_name().equals("")||schoolawardinfo.getAward_name()==null)&&
////					(schoolawardinfo.getAward_type().equals("")||schoolawardinfo.getAward_type()==null)&&
////					(schoolawardinfo.getDt()==null))
////			{
////				throw new Exception("数据不能为空");
////			}
////			schoolawardinfo.setStudent_name(schoolclassinfo.getStudent_name());
////			schoolawardinfo.setStudent_number(schoolclassinfo.getStudent_number());
////			//schoolawardinfoDao.deleteById(schoolawardinfo.getId());
////			schoolawardinfoDao.insert(schoolawardinfo);
////		}
//		//award完毕
//		for (Schoolperformanceinfo schoolperformanceinfo : schoolperformanceinfos) {
//			if((schoolperformanceinfo.getCourse_id().equals("")||schoolperformanceinfo.getCourse_id()==null)&&
//					(schoolperformanceinfo.getCourse_name().equals("")||schoolperformanceinfo.getCourse_name()==null)&&
//					(schoolperformanceinfo.getScore().equals("")||schoolperformanceinfo.getScore()==null)&&
//					(schoolperformanceinfo.getDt()==null))
//			{
//				throw new Exception("数据不能为空");
//			}
////			schoolperformanceinfo.setClass_name(class_name);
////			schoolperformanceinfo.setGrade_name(grade_name);
////			schoolperformanceinfo.setStudent_name(schoolclassinfo.getStudent_name());
//			schoolperformanceinfo.setStudent_number(schoolclassinfo.getStudent_number());
//			//schoolperformanceinfoDao.deleteById(schoolperformanceinfo.getId());
//			schoolperformanceinfoDao.insert(schoolperformanceinfo);
//			
//		}
//		
//		for (Studentimage studentimage : studentimages) {
//			if(studentimage.getImageId()==null)
//			{
//				throw new Exception("数据不能为空");
//			}
//			studentimage.setDt(dt);
//			studentimage.setStudentNumber(schoolclassinfo.getStudent_number());
//			studentimage.setStat("1");
//			studentimageDao.deleteById(studentimage.getImageId());
//			studentimageDao.insert(studentimage);
//		}
		return true;
	}
	

	@Override
	@DataSource(name = DataSourceNames.SECOND)
	public boolean update(Map<String, Object> params) throws Exception {
		return ((SchoolclassinfoServiceImpl)AopContext.currentProxy()).updates(params);
	}

	@Override
	@DataSource(name = DataSourceNames.SECOND)
	public boolean delpho(String uid) throws Exception {
		// TODO Auto-generated method stub
		return ((SchoolclassinfoServiceImpl)AopContext.currentProxy()).delphos(uid);
	}
	@Transactional(rollbackFor=Exception.class) 
	public boolean delphos(String uid) throws Exception{
		
		studentimageDao.deleteById(uid);
		kimageDao.deleteById(uid);
		return true;
		
	}

	
	
	@Transactional(rollbackFor=Exception.class)
	public boolean updatas(Map<String, Object> params) throws Exception
	{
//		Map<String, Object> df=(Map<String, Object>)params.get("schoolclassinfo");
//		String studentNumber=(String)df.get("student_number");
//		String studentName=(String)df.get("student_name");
//		
//		CourseInfo courseInfo=courseService.getcourseinfo(studentNumber);
//		
//		
//		//List<Schoolawardinfo>  Schoolawardinfos=JSON.parseArray(JSON.toJSONString(params.get("studentawaredata")), Schoolawardinfo.class);
//		List<Schoolperformanceinfo> schoolperformanceinfos=JSON.parseArray(JSON.toJSONString(params.get("studentsoredata")),Schoolperformanceinfo.class);
//		List<Studentimage> studentimages=JSON.parseArray(JSON.toJSONString(params.get("studentphodata")),Studentimage.class);
//		
//		
//		Map<String, Object> columnMap=new HashMap<>();
//		columnMap.put("student_number", studentNumber);
//		studentimageDao.deleteByMap(columnMap);
//		schoolperformanceinfoDao.deleteByMap(columnMap);
//		//schoolawardinfoDao.deleteByMap(columnMap);
//		
//		
//		Date dt =new Date();
////		for (Schoolawardinfo schoolawardinfo : Schoolawardinfos) {
////			//验证数据
////			if((schoolawardinfo.getAward_level().equals("")||schoolawardinfo.getAward_level()==null)&&
////					(schoolawardinfo.getAward_name().equals("")||schoolawardinfo.getAward_name()==null)&&
////					(schoolawardinfo.getAward_type().equals("")||schoolawardinfo.getAward_type()==null)&&
////					(schoolawardinfo.getDt()==null))
////			{
////				throw new Exception("数据不能为空");
////			}
////			schoolawardinfo.setClassName(courseInfo.getClassName());
////			schoolawardinfo.setGradeName(courseInfo.getGradeName());
////			schoolawardinfo.setCollegeName(courseInfo.getCollegeName());
////			schoolawardinfo.setStudent_name(studentName);
////			schoolawardinfo.setStudent_number(studentNumber);
////			schoolawardinfoDao.insert(schoolawardinfo);
////		}
//		//award完毕
//		for (Schoolperformanceinfo schoolperformanceinfo : schoolperformanceinfos) {
//			if((schoolperformanceinfo.getCourse_id().equals("")||schoolperformanceinfo.getCourse_id()==null)&&
//					(schoolperformanceinfo.getCourse_name().equals("")||schoolperformanceinfo.getCourse_name()==null)&&
//					(schoolperformanceinfo.getScore().equals("")||schoolperformanceinfo.getScore()==null)&&
//					(schoolperformanceinfo.getDt()==null))
//			{
//				throw new Exception("数据不能为空");
//			}
////			schoolperformanceinfo.setClass_name(courseInfo.getClassName());
////			schoolperformanceinfo.setGrade_name(courseInfo.getGradeName());
////			schoolperformanceinfo.setCollegeName(courseInfo.getCollegeName());
////			schoolperformanceinfo.setStudent_name(studentName);
//			schoolperformanceinfo.setStudent_number(studentNumber);
//			schoolperformanceinfoDao.insert(schoolperformanceinfo);
//			
//		}
//		for (Studentimage studentimage : studentimages) {
//			if(studentimage.getImageId()==null)
//			{
//				throw new Exception("数据不能为空");
//			}
//			studentimage.setDt(dt);
//			studentimage.setStudentNumber(studentNumber);
//			studentimage.setStat("1");
//			studentimageDao.insert(studentimage);
//		}
		return true;
	}
	
	
	@Override
	@DataSource(name = DataSourceNames.SECOND)
	public boolean updata(Map<String, Object> param) throws Exception {
		
		return ((SchoolclassinfoServiceImpl)AopContext.currentProxy()).updatas(param);
	}
	
	
	
	

}
