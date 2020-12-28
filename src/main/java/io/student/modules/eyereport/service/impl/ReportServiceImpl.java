package io.student.modules.eyereport.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import io.student.common.utils.Constant;
import io.student.common.validator.ValidatorUtils;
import io.student.common.validator.group.UpdateGroup;
import io.student.modules.datacenter.entity.SchoolstudentEntity;
import io.student.modules.sys.entity.*;
import io.student.modules.datacenter.entity.CameraroomEntity;
import io.student.modules.sys.entity.SysConfigEntity;
import io.student.modules.sys.service.impl.SysDeptServiceImpl;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import io.student.common.utils.POIUtil;
import io.student.common.utils.R;
import io.student.datasources.DataSourceNames;
import io.student.datasources.annotation.DataSource;
import io.student.modules.eyereport.dao.ProDao;
import io.student.modules.sys.dao.SysDeptDao;
import io.student.modules.datacenter.dao.CameraroomDao;
import io.student.modules.sys.dao.SysConfigDao;
import io.student.modules.sys.dao.SysUserDao;
import io.student.modules.sys.dao.SysRoleDao;
import io.student.modules.sys.dao.SysUserRoleDao;
import io.student.modules.datacenter.dao.SchoolstudentDao;
import io.student.modules.eyereport.service.ReportService;
import io.student.modules.sys.service.SysDeptService;
import io.student.modules.datacenter.service.CameraroomService;
import io.student.modules.sys.service.SysConfigService;
import io.student.modules.sys.service.SysUserService;
import io.student.modules.sys.service.SysRoleService;
import io.student.modules.sys.service.SysUserRoleService;
import io.student.modules.datacenter.service.SchoolstudentService;

@Service("reportService")
public class ReportServiceImpl implements ReportService {

	@Autowired
	private ProDao prodao;

	@Autowired
	private SysDeptDao sysdeptdao;

	@Autowired
	private CameraroomDao CameraroomDao;

	@Autowired
	private SysConfigDao SysConfigDao;

	@Autowired
	private SysUserDao SysUserDao;

	@Autowired
	private SysRoleDao SysRoleDao;

	@Autowired
	private SysUserRoleDao SysUserRoleDao;

	@Autowired
	private SchoolstudentDao SchoolstudentDao;

	@Autowired
	private SysDeptService sysDeptService;

	@Autowired
	private CameraroomService CameraroomService;

	@Autowired
	private SysConfigService SysConfigService;

	@Autowired
	private SysUserService SysUserService;

	@Autowired
	private SysRoleService SysRoleService;

	@Autowired
	private SysUserRoleService SysUserRoleService;

	@Autowired
	private SchoolstudentService SchoolstudentService;

	@Override
	@DataSource(name=DataSourceNames.SECOND)
	public List<Map<String, Object>> getcourselist(Map<String, Object> param)
	{
		return prodao.getcourselist(param);
	}
	
	@Override
	@DataSource(name=DataSourceNames.SECOND)
	public List<Map<String, Object>> getteachlist()
	{
		return prodao.getteachlist();
	}
	
	
	@Override
	@DataSource(name = DataSourceNames.SECOND)
//	public int importxls(List<Map<String, String>> list, String type) {
	public int importxls(List<Object> list, String type, Long userId) {
		System.out.println("ssssssss");
		switch (type) {
			//班年级信息
			case "sys_dept":
				Long preId = 0L;
//				System.out.println(list.size());
				try {
					for(int i = 0; i < list.size(); i ++) {
//						System.out.println(i);
						//获取部门id
						String name = list.get(i).toString();
						Long id = sysDeptService.queryId(name, preId);
						if(id == null) {
							//添加部门
							SysDeptEntity root = new SysDeptEntity();
							root.setDeptId(0L);
							root.setName(name);
							root.setParentId(preId);
							root.setOpen(true);
							root.setOrderNum(0);
							sysDeptService.save(root);
//							System.out.println("yesss!!!");
							preId = sysDeptService.queryId(name, preId);
						} else {
							preId = id;
						}
					}
					return 0;
				} catch (Exception e){
					return -1;
				}

			//摄像头信息
			case "school_camera_class_info":
				//获取列信息
				String roomAddr = list.get(0).toString();
				String cameraAddr = list.get(1).toString();
				String dockerName = list.get(2).toString();
				try {
					//获取教室
					SysConfigEntity room = SysConfigDao.queryByKey("room", roomAddr);
					System.out.println(room);
					if(room == null) {
						//添加教室
						SysConfigEntity root = new SysConfigEntity();
						root.setParamType("room");
						root.setParamKey(roomAddr);
						root.setParamValue(roomAddr);
						SysConfigService.save(root);
						System.out.println("yesss!!!");
					}
					//获取摄像头
					Long id = CameraroomDao.queryCameraId(roomAddr, cameraAddr, dockerName);
					System.out.println(id);
					if(id == null) {
						//添加摄像头
						CameraroomEntity root = new CameraroomEntity();
						root.setCameraAddr(cameraAddr);
						root.setRoomId(roomAddr);
						root.setRoomAddr(roomAddr);
						root.setDocker(dockerName);
						CameraroomService.save(root);
						System.out.println("yesss!!!");
						return 0;
					}
				} catch (Exception e){
					return -1;
				}

			//课程名称
			case "sys_config":
				//获取列信息
				String courseName = list.get(0).toString();
				try {
					//获取课程实体id
					SysConfigEntity course = SysConfigDao.queryByKey("course", courseName);
					System.out.println(course);
					if(course == null) {
						//添加课程实体
						SysConfigEntity root = new SysConfigEntity();
						root.setParamType("course");
						root.setParamKey(courseName);
						root.setParamValue(courseName);
						SysConfigService.save(root);
						System.out.println("yesss!!!");
					}
					return 0;
				} catch (Exception e){
					return -1;
				}

			//教师信息
			case "sys_user":
				System.out.println("ffffbfbfbff");
				//获取列信息
				String userName = list.get(0).toString();
				String teacherName = list.get(1).toString();
				String schoolName = list.get(2).toString();
				String gradeName = list.get(3).toString();
				String roomName = list.get(4).toString();
				String role = list.get(5).toString();
				System.out.println("ffff" + role);
				try {
					//获取教师id
					SysUserEntity teacher = SysUserDao.queryByUserName(userName);
					System.out.println(teacher);
					Long teacherId;

					if(teacher == null) {
						//添加教师
						SysUserEntity root = new SysUserEntity();
						root.setUsername(userName);
						if(teacherName == "") {
							root.setName(userName);
						} else {
							root.setName(teacherName);
						}
						root.setPassword(userName);
						root.setStatus(1);
//						todo:当前用户id
//						root.setCreateUserId(userId);
						//查询班年级id
						Long schoolId = sysDeptService.queryId(schoolName, 0L);
						System.out.println("schoolId: " + schoolId);
						Long gradeId = sysDeptService.queryId(gradeName, schoolId);
						System.out.println("gradeId: " + gradeId);
						Long roomId = sysDeptService.queryId(roomName, gradeId);
						System.out.println("roomId: " + roomId);
						root.setDeptId(roomId);
						System.out.println(root);
						SysUserService.save(root, null);
						System.out.println("yesss!!!");
						SysUserEntity newteacher = SysUserDao.queryByUserName(userName);
						System.out.println(newteacher);
						teacherId = newteacher.getUserId();
					} else {
						teacherId = teacher.getUserId();
					}
					System.out.println(teacherId);
					//存入sys_user_role
					Long userRoleId = 0L;
					Long roleId = 0L;
					System.out.println(role);
					System.out.println(role.equals("管理员"));
					if(role.equals("管理员")) {
						//查询管理员role_id
						roleId = SysRoleDao.queryRoleId("管理员");
						userRoleId = SysUserRoleDao.queryUserRoleId(teacherId, roleId);
					} else if (role.equals("教师角色")){
						//查询教师role_id
						roleId = SysRoleDao.queryRoleId("教师角色");
						userRoleId = SysUserRoleDao.queryUserRoleId(teacherId, roleId);
					}
					System.out.println(roleId);
					System.out.println(userRoleId);
					if(userRoleId == null) {
						//添加user_role对应关系
						SysUserRoleEntity root = new SysUserRoleEntity();
						root.setUserId(teacherId);
						root.setRoleId(roleId);
						SysUserRoleDao.insert(root);
						System.out.println("yesss!!!");
					}
					return 0;
				} catch (Exception e){
					return -1;
				}
			//学生信息
			case "school_student_class_info":
				//获取列信息
				String studentId = list.get(0).toString();
				String studentName = list.get(1).toString();
				String studentSchoolName = list.get(2).toString();
				String studentGradeName = list.get(3).toString();
				String studentRoomName = list.get(4).toString();
				try {
					//查询班年级id
					Long schoolId = sysDeptService.queryId(studentSchoolName, 0L);
					System.out.println("schoolId: " + schoolId);
					Long gradeId = sysDeptService.queryId(studentGradeName, schoolId);
					System.out.println("gradeId: " + gradeId);
					Long roomId = sysDeptService.queryId(studentRoomName, gradeId);
					System.out.println("roomId: " + roomId);
					//删除已有学生实体
					SchoolstudentDao.deleteById(studentId);
					//添加实体
					SchoolstudentEntity root = new SchoolstudentEntity();
					root.setStudentNumber(studentId);
					root.setStudentName(studentName);
					root.setDeptId(roomId);
					root.setDeptName(studentRoomName);
					root.setIsphon("0");
					SchoolstudentService.save(root);
					System.out.println("yesss!!!");
					return 0;
				} catch (Exception e){
					return -1;
				}
//			case "school_student_class_info":
//				prodao.deleteschool_student();
//				return prodao.insertschool_student(list);
//			case "school_course_info":
//				prodao.deleteschool_course();
//				return prodao.insertschool_course(list);
//			case "school_performance_info":
//				return prodao.insertschool_performance(list);
//			case "school_award_info":
//				return prodao.insertschool_award(list);
			default:
				return -1;
		}
	}

//	@Override
//	@DataSource(name = DataSourceNames.SECOND)
//	public int importxlsbatch(List<Map<String, String>> list, String type) {
//		switch (type) {
//			case "school_camera_class_info":
//				prodao.deleteschool_camera();
//				return prodao.insertschool_camera(list);
//			case "school_student_class_info":
//				prodao.deleteschool_student();
//				return prodao.insertschool_student(list);
//			case "school_course_info":
//				prodao.deleteschool_course();
//				return prodao.insertschool_course(list);
//			case "school_performance_info":
//				return prodao.insertschool_performance(list);
//			case "school_award_info":
//				return prodao.insertschool_award(list);
//			default:
//				return -1;
//		}
//	}

	@Override
	@DataSource(name = DataSourceNames.SECOND)
	public List<Map<String, Object>> student_interest(Map<String, Object> params) {
		return prodao.student_interest(params);
	}

	@Override
	@DataSource(name = DataSourceNames.SECOND)
	public List<Map<String, Object>> student_award_info(Map<String, Object> params) {
		return prodao.student_award_info(params);
	}

	@Override
	@DataSource(name = DataSourceNames.SECOND)
	public String getstudent_mental_status_ld(Map<String, Object> params) {
		return prodao.getstudent_mental_status_ld(params);
	}

	@Override
	@DataSource(name = DataSourceNames.SECOND)
	public List<Map<String, Object>> getlinestudent(Map<String, Object> params) {
		return prodao.getlinestudent(params);
	}

	@Override
	@DataSource(name = DataSourceNames.SECOND)
	public List<Map<String, Object>> getstu(Map<String, Object> param) {
		return prodao.getstu(param);
	}

	@Override
	@DataSource(name = DataSourceNames.SECOND)
	public List<Map<String, Object>> geta(Map<String, Object> params) {
		return prodao.geta(params);
	}

	@Override
	@DataSource(name = DataSourceNames.SECOND)
	public List<Map<String, Object>> getcols(Map<String, Object> params) {
		return prodao.getcols(params);
	}

	@Override
	@DataSource(name = DataSourceNames.SECOND)
	public List<Map<String, Object>> getqxdata(Map<String, Object> params) {
		return prodao.getqxdata(params);
	}

	@Override
	@DataSource(name = DataSourceNames.SECOND)
	public List<Map<String, Object>> getrjdata(Map<String, Object> params) {
		return prodao.getrjdata(params);
	}

	@Override
	@DataSource(name = DataSourceNames.SECOND)
	public List<Map<String, Object>> getxxdata(Map<String, Object> params) {
		return prodao.getxxdata(params);
	}

	@Override
	@DataSource(name = DataSourceNames.SECOND)
	public List<Map<String, Object>> getjsdata(Map<String, Object> params) {
		return prodao.getjsdata(params);
	}

	@Override
	@DataSource(name = DataSourceNames.SECOND)
	public List<Map<String, Object>> getxkxqdata(Map<String, Object> params) {
		return prodao.getxkxqdata(params);
	}

	@Override
	@DataSource(name = DataSourceNames.SECOND)
	public List<Map<String, Object>> getqxbjdata(Map<String, Object> params) {
		return prodao.getqxbjdata(params);
	}

	@Override
	@DataSource(name = DataSourceNames.SECOND)
	public List<Map<String, Object>> getjsbjdata(Map<String, Object> params) {
		return prodao.getjsbjdata(params);
	}

	@Override
	@DataSource(name = DataSourceNames.SECOND)
	public List<Map<String, Object>> getxxbjdata(Map<String, Object> params) {
		return prodao.getxxbjdata(params);
	}

	@Override
	@DataSource(name = DataSourceNames.SECOND)
	public List<Map<String, Object>> getagrad(Map<String, Object> params) {
		return prodao.getagrad(params);
	}

	@Override
	@DataSource(name = DataSourceNames.SECOND)
	public List<Map<String, Object>> getcolsgrad(Map<String, Object> params) {
		return prodao.getcolsgrad(params);
	}

	@Override
	@DataSource(name = DataSourceNames.SECOND)
	public List<Map<String, Object>> getqxdatagrad(Map<String, Object> params) {
		return prodao.getqxdatagrad(params);
	}

	@Override
	@DataSource(name = DataSourceNames.SECOND)
	public List<Map<String, Object>> getrjdatagrad(Map<String, Object> params) {
		return prodao.getrjdatagrad(params);
	}

	@Override
	@DataSource(name = DataSourceNames.SECOND)
	public List<Map<String, Object>> getxxdatagrad(Map<String, Object> params) {
		return prodao.getxxdatagrad(params);
	}

	@Override
	@DataSource(name = DataSourceNames.SECOND)
	public List<Map<String, Object>> getjsdatagrad(Map<String, Object> params) {
		return prodao.getjsdatagrad(params);
	}

	@Override
	@DataSource(name = DataSourceNames.SECOND)
	public List<Map<String, Object>> getxkxqdatagrad(Map<String, Object> params) {
		return prodao.getxkxqdatagrad(params);
	}

	@Override
	@DataSource(name = DataSourceNames.SECOND)
	public List<Map<String, Object>> getqxbjdatagrad(Map<String, Object> params) {
		return prodao.getqxbjdatagrad(params);
	}

	@Override
	@DataSource(name = DataSourceNames.SECOND)
	public List<Map<String, Object>> getjsbjdatagrad(Map<String, Object> params) {
		return prodao.getjsbjdatagrad(params);
	}

	@Override
	@DataSource(name = DataSourceNames.SECOND)
	public List<Map<String, Object>> getxxbjdatagrad(Map<String, Object> params) {
		return prodao.getxxbjdatagrad(params);
	}

	//
	@Override
	@DataSource(name = DataSourceNames.SECOND)
	public List<Map<String, Object>> getacls(Map<String, Object> params) {
		return prodao.getacls(params);
	}

	@Override
	@DataSource(name = DataSourceNames.SECOND)
	public List<Map<String, Object>> getcolscls(Map<String, Object> params) {
		return prodao.getcolscls(params);
	}

	@Override
	@DataSource(name = DataSourceNames.SECOND)
	public List<Map<String, Object>> getqxdatacls(Map<String, Object> params) {
		return prodao.getqxdatacls(params);
	}

	@Override
	@DataSource(name = DataSourceNames.SECOND)
	public List<Map<String, Object>> getrjdatacls(Map<String, Object> params) {
		return prodao.getrjdatacls(params);
	}

	@Override
	@DataSource(name = DataSourceNames.SECOND)
	public List<Map<String, Object>> getxxdatacls(Map<String, Object> params) {
		return prodao.getxxdatacls(params);
	}

	@Override
	@DataSource(name = DataSourceNames.SECOND)
	public List<Map<String, Object>> getjsdatacls(Map<String, Object> params) {
		return prodao.getjsdatacls(params);
	}

	@Override
	@DataSource(name = DataSourceNames.SECOND)
	public List<Map<String, Object>> getxkxqdatacls(Map<String, Object> params) {
		return prodao.getxkxqdatacls(params);
	}

	@Override
	@DataSource(name = DataSourceNames.SECOND)
	public List<Map<String, Object>> getyjdatacls(Map<String, Object> params) {
		return prodao.getyjdatacls(params);
	}

	@Override
	@DataSource(name = DataSourceNames.SECOND)
	public List<Map<String, Object>> getqxtime(Map<String, Object> params) {
		return prodao.getqxtime(params);
	}

	@Override
	@DataSource(name = DataSourceNames.SECOND)
	public List<Map<String, Object>> getjstime(Map<String, Object> params) {
		return prodao.getjstime(params);
	}

	@Override
	@DataSource(name = DataSourceNames.SECOND)
	public List<Map<String, Object>> getxxtime(Map<String, Object> params) {
		return prodao.getxxtime(params);
	}

	

	@Override
	@DataSource(name = DataSourceNames.SECOND)
	public List<Map<String, Object>> getbq(Map<String, Object> param) {
		return prodao.getbq(param);
	}

	@Override
	@DataSource(name = DataSourceNames.SECOND)
	public List<Map<String, Object>> getjsztdata(Map<String, Object> param) {
		return prodao.getjsztdata(param);
	}

	@Override
	@DataSource(name = DataSourceNames.SECOND)
	public List<Map<String, Object>> getxxztdata(Map<String, Object> param) {
		return prodao.getxxztdata(param);
	}

	@Override
	@DataSource(name = DataSourceNames.SECOND)
	public List<Map<String, Object>> getjsztline(Map<String, Object> param) {
		return prodao.getjsztline(param);
	}

	@Override
	@DataSource(name = DataSourceNames.SECOND)
	public List<Map<String, Object>> getxxztline(Map<String, Object> param) {
		return prodao.getxxztline(param);
	}

	@Override
	@DataSource(name = DataSourceNames.SECOND)
	public Map<String, Object> getxszt(Map<String, Object> param) {
		return prodao.getxszt(param);
	}

	@Override
	@DataSource(name = DataSourceNames.SECOND)
	public List<Map<String, Object>> getaditor(Map<String, Object> param) {
		return prodao.getaditor(param);
	}

	@Override
	@DataSource(name = DataSourceNames.SECOND)
	public List<Map<String, Object>> getclsdetailqxzt(Map<String, Object> params) {
		return prodao.getclsdetailqxzt(params);
	}

	@Override
	@DataSource(name = DataSourceNames.SECOND)
	public List<Map<String, Object>> getclsdetailrjgx(Map<String, Object> params) {
		return prodao.getclsdetailrjgx(params);
	}

	@Override
	@DataSource(name = DataSourceNames.SECOND)
	public List<Map<String, Object>> getclsdetailxxzt(Map<String, Object> params) {
		return prodao.getclsdetailxxzt(params);
	}

	@Override
	@DataSource(name = DataSourceNames.SECOND)
	public List<Map<String, Object>> getclsdetailjszt(Map<String, Object> params) {
		return prodao.getclsdetailjszt(params);
	}

	@Override
	@DataSource(name = DataSourceNames.SECOND)
	public List<Map<String, Object>> getstatistics() {
		// TODO Auto-generated method stub
		return prodao.getstatistics();
	}

	@Override
	@DataSource(name = DataSourceNames.SECOND)
	public List<Map<Object, Object>> getstatisticspage(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return prodao.getstatisticspage(param);
	}

	@Override
	@DataSource(name = DataSourceNames.SECOND)
	public List<Map<String, Object>> getRadarjxxg(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return prodao.getRadarjxxg(param);
	}
	@Override
	@DataSource(name=DataSourceNames.SECOND)
	public List<Map<String, Object>> getxsqxzts(Map<String, Object> param)
	{
		return prodao.getxsqxzts(param);
	}
	@Override
	@DataSource(name=DataSourceNames.SECOND)
	public List<Map<String, Object>> getxsqxztqx(Map<String, Object> param)
	{
		return prodao.getxsqxztqx(param);
	}
	@Override
	@DataSource(name=DataSourceNames.SECOND)
	public List<Map<String, Object>> getxsxxzt(Map<String, Object> param)
	{
		return prodao.getxsxxzt(param);
	}
	@Override
	@DataSource(name=DataSourceNames.SECOND)
	public List<Map<String, Object>> getxsxxztqx(Map<String, Object> param)
	{
		return prodao.getxsxxztqx(param);
	}
	
	@Override
	@DataSource(name=DataSourceNames.SECOND)
	public List<Map<String, Object>> getxsjszt(Map<String, Object> param)
	{
		return prodao.getxsjszt(param);
	}
	@Override
	@DataSource(name=DataSourceNames.SECOND)
	public List<Map<String, Object>> getxsjsztqx(Map<String, Object> param)
	{
		return prodao.getxsjsztqx(param);
	}
	@Override
	@DataSource(name=DataSourceNames.SECOND)
	public List<Map<String, Object>> getktjjx(Map<String, Object> param)
	{
		return prodao.getktjjx(param);
	}
	@Override
	@DataSource(name=DataSourceNames.SECOND)
	public List<Map<String, Object>> getktjjxqx(Map<String, Object> param)
	{
		return prodao.getktjjxqx(param);
	}
	@Override
	@DataSource(name=DataSourceNames.SECOND)
	public List<Map<String, Object>> getktzzd(Map<String, Object> param)
	{
		return prodao.getktzzd(param);
	}
	@Override
	@DataSource(name=DataSourceNames.SECOND)
	public List<Map<String, Object>> getktzzdqx(Map<String, Object> param)
	{
		return prodao.getktzzdqx(param);
	}
	@Override
	@DataSource(name=DataSourceNames.SECOND)
	public List<Map<String, Object>> getkthdx(Map<String, Object> param)
	{
		return prodao.getkthdx(param);
	}
	@Override
	@DataSource(name=DataSourceNames.SECOND)
	public List<Map<String, Object>> getkthdxqx(Map<String, Object> param)
	{
		return prodao.getkthdxqx(param);
	}
	@Override
	@DataSource(name=DataSourceNames.SECOND)
	public List<Map<String, Object>> getktztxxfx(Map<String, Object> param)
	{
		return prodao.getktztxxfx(param);
	}

	@Override
	@DataSource(name= DataSourceNames.SECOND)
	public List<Map<String, Object>> getDiagnosisClass(Map<String, Object> param)
	{
		return prodao.getDiagnosisClass(param);
	}

	@Override
	@DataSource(name= DataSourceNames.SECOND)
	public List<Map<String, Object>> getDiagnosisStu(Map<String, Object> param)
	{
		return prodao.getDiagnosisStu(param);
	}
}
