package io.student.modules.datacenter.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.student.modules.eyereport.entity.Schoolcourseinfo;
import io.student.modules.eyereport.service.SchoolcourseinfoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import io.student.common.exception.RRException;
import io.student.common.utils.PageUtils;
import io.student.common.utils.Query;
import io.student.datasources.DataSourceNames;
import io.student.datasources.annotation.DataSource;
import io.student.modules.datacenter.dao.SchoolstudentDao;
import io.student.modules.datacenter.entity.SchoolawardinfoEntity;
import io.student.modules.datacenter.entity.Schoolperformanceinfo;
import io.student.modules.datacenter.entity.SchoolstudentEntity;
import io.student.modules.datacenter.entity.Studentimage;
import io.student.modules.datacenter.service.SchoolawardinfoService;
import io.student.modules.datacenter.service.SchoolperformanceinfoService;
import io.student.modules.datacenter.service.SchoolstudentService;
import io.student.modules.datacenter.service.StudentimageService;
import io.student.modules.eyereport.dao.KimageDao;
import io.student.modules.eyereport.dao.StudentimageDao;
import io.student.modules.eyereport.entity.CourseInfo;
import io.student.modules.eyereport.service.impl.SchoolclassinfoServiceImpl;
import io.student.modules.sys.service.SysDeptService;
import io.student.modules.datacenter.dao.SchoolawardinfoDao;
import io.student.modules.datacenter.dao.SchoolperformanceinfoDao;
import io.student.modules.datacenter.dao.StudentCourseInfoDao;

@Service("SchoolstudentService")
public class SchoolstudentServiceImpl extends ServiceImpl<SchoolstudentDao, SchoolstudentEntity>
		implements SchoolstudentService {

	@Autowired
	private SysDeptService SysDeptService;
	@Autowired
	private KimageDao kimageDao;
	@Autowired
	private StudentimageDao studentimageDao;
	@Autowired
	private SchoolawardinfoService schoolawardinfoService;
	@Autowired
	private SchoolperformanceinfoService schoolperformanceinfoService;
	@Autowired
	private StudentimageService StudentimageService;
	@Autowired
	private SchoolawardinfoDao SchoolawardinfoDao;
	@Autowired
	private SchoolperformanceinfoDao SchoolperformanceinfoDao;
	@Autowired
	private StudentCourseInfoDao StudentCourseInfoDao;

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		String deptId = (String) params.get("deptId");
		String isphon = (String) params.get("isphon");
		String studentnumber = (String) params.get("student_number");
		String deptidsString ="";
		if(StringUtils.isNotBlank(deptId))
		{
		 deptidsString = SysDeptService.getSubDeptIdList(Long.valueOf(deptId));
		}
		Page<SchoolstudentEntity> page = this.selectPage(new Query<SchoolstudentEntity>(params).getPage(),
				new EntityWrapper<SchoolstudentEntity>()
						.like(StringUtils.isNotBlank(studentnumber), "student_number", studentnumber)
						.eq(StringUtils.isNotBlank(isphon), "isphon", isphon)
						.in(StringUtils.isNotBlank(deptidsString), "dept_id", deptidsString));

		return new PageUtils(page);
	}

//	@Override
//	public void deleteBatch(String[] ids) {
//		System.out.println("aaaaaaaa"+ids);
//		this.deleteBatchIds(Arrays.asList(ids));
//
//	}

	@Transactional(rollbackFor = Exception.class)
	public void deleteBatch(String[] ids) {
		System.out.println(Arrays.toString(ids));
//		删除学生信息
		this.deleteBatchIds(Arrays.asList(ids));
//		删除获奖信息
		SchoolawardinfoDao.deleteBatchIds(Arrays.asList(ids));
//		删除成绩信息
		SchoolperformanceinfoDao.deleteBatchIds(Arrays.asList(ids));
//		删除课表信息
		StudentCourseInfoDao.deleteCourseByStudentId(ids[0]);
//		删除标准照信息
		studentimageDao.deleteStudentImageByStudentId(ids[0]);
	}

	@Override
	public void save(SchoolstudentEntity schoolstudentEntity) {
		this.insert(schoolstudentEntity);

	}

	@Override
	public void update(SchoolstudentEntity schoolstudentEntity) {
		this.updateAllColumnById(schoolstudentEntity);

	}

	@Override
	public boolean delpho(String uid) throws Exception {
		return ((SchoolstudentServiceImpl) AopContext.currentProxy()).delphos(uid);
	}

	@Transactional(rollbackFor = Exception.class)
	public boolean delphos(String uid) {

		studentimageDao.deleteById(uid);
		kimageDao.deleteById(uid);
		return true;

	}

	@Override
	public boolean updata(Map<String, Object> param) throws Exception {

		return ((SchoolstudentServiceImpl) AopContext.currentProxy()).updatas(param);
	}

	@Transactional(rollbackFor = Exception.class)
	public boolean updatas(Map<String, Object> params) throws Exception {
		SchoolstudentEntity schoolstudentEntity = JSON.parseObject(JSON.toJSONString(params.get("schoolclassinfo")),
				SchoolstudentEntity.class);
		List<SchoolawardinfoEntity> Schoolawardinfos = JSON
				.parseArray(JSON.toJSONString(params.get("studentawaredata")), SchoolawardinfoEntity.class);
		List<Schoolperformanceinfo> schoolperformanceinfos = JSON
				.parseArray(JSON.toJSONString(params.get("studentsoredata")), Schoolperformanceinfo.class);
		List<Studentimage> studentimages = JSON.parseArray(JSON.toJSONString(params.get("studentphodata")),
				Studentimage.class);
		//
		this.insertOrUpdate(schoolstudentEntity);
		schoolawardinfoService.delete(new EntityWrapper<SchoolawardinfoEntity>().eq("student_number", schoolstudentEntity.getStudentNumber()));
		if (Schoolawardinfos != null && Schoolawardinfos.size() != 0) {
			
			for (SchoolawardinfoEntity schoolawardinfoEntity : Schoolawardinfos) {
				schoolawardinfoEntity.setStudentNumber(schoolstudentEntity.getStudentNumber());
			}
			
			schoolawardinfoService.insertBatch(Schoolawardinfos);
		}
		schoolperformanceinfoService.delete(new EntityWrapper<Schoolperformanceinfo>().eq("student_number", schoolstudentEntity.getStudentNumber()));
		if (schoolperformanceinfos != null && schoolperformanceinfos.size() != 0) {
			for (Schoolperformanceinfo schoolperformanceinfo : schoolperformanceinfos) {
				schoolperformanceinfo.setStudentNumber(schoolstudentEntity.getStudentNumber());
				try {
					Float.parseFloat(schoolperformanceinfo.getScore());
				} catch (Exception e) {
					throw new Exception("分数必须是数字");
				}
			}
			schoolperformanceinfoService.insertBatch(schoolperformanceinfos);
		}
		StudentimageService.delete(new EntityWrapper<Studentimage>().eq("student_number", schoolstudentEntity.getStudentNumber()));
		if (studentimages != null && studentimages.size() != 0) {
			for (Studentimage studentimage : studentimages) {
				studentimage.setStudentNumber(schoolstudentEntity.getStudentNumber());
				studentimage.setStat("0");
			}
			StudentimageService.insertBatch(studentimages);
		}
		return true;
	}

	@Override
	public List<SchoolstudentEntity> dict(Map<String, Object> params) {
		String deptId = (String) params.get("deptId");
		String deptidsString ="";
		if(StringUtils.isNotBlank(deptId))
		{
		 deptidsString = SysDeptService.getSubDeptIdList(Long.valueOf(deptId));
		}
		
		List<SchoolstudentEntity> schoolstudentEntities=this.selectList(new EntityWrapper<SchoolstudentEntity>().in(StringUtils.isNotBlank(deptidsString), "dept_id", deptidsString));
		
		return schoolstudentEntities;
	}
}
