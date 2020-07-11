package io.student.modules.eyereport.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface ProDao  {
	List<Map<String, Object>> getktztxxfx(Map<String, Object> param);
	List<Map<String, Object>> getrtch(Map<String, Object> param);
	List<Map<String, Object>> getssxw(Map<String, Object> param);
	List<Map<String, Object>> getxsxw(Map<String, Object> param);
	List<Map<String, Object>> getjsxw(Map<String, Object> param);
	List<Map<String, Object>> getqxzt(Map<String, Object> param);
	List<Map<String, Object>> getjxqxline(Map<String, Object> param);
	List<Map<String, Object>> getjxzt(Map<String, Object> param);
	List<Map<String, Object>> getjxztline(Map<String, Object> param);

	List<Map<String, Object>> getkthdx(Map<String, Object> param);
	List<Map<String, Object>> getkthdxqx(Map<String, Object> param);
	List<Map<String, Object>> getktzzd(Map<String, Object> param);
	List<Map<String, Object>> getktzzdqx(Map<String, Object> param);
	List<Map<String, Object>> getktjjx(Map<String, Object> param);
	List<Map<String, Object>> getktjjxqx(Map<String, Object> param);
	List<Map<String, Object>> getxsjszt(Map<String, Object> param);
	List<Map<String, Object>> getxsjsztqx(Map<String, Object> param);
	
	List<Map<String, Object>> getxsxxzt(Map<String, Object> param);
	List<Map<String, Object>> getxsxxztqx(Map<String, Object> param);
	List<Map<String, Object>> getxsqxzts(Map<String, Object> param);
	List<Map<String, Object>> getxsqxztqx(Map<String, Object> param);
	List<Map<String, Object>> getRadarjxxg(Map<String, Object> param);
	List<Map<String, Object>> getstatistics();
	List<Map<Object, Object>> getstatisticspage(Map<String, Object> param);
	List<Map<String, Object>> getcourselist(Map<String, Object> param);
	List<Map<String, Object>> getteachlist();
	
	int deleteschool_camera();
	int deleteschool_student();
	int deleteschool_course();
	
	int insertschool_camera(List<Map<String, String>> list);
	int insertschool_student(List<Map<String, String>> list);
	int insertschool_course(List<Map<String, String>> list);
	int insertschool_performance(List<Map<String, String>> list);
	int insertschool_award(List<Map<String, String>> list);
	
	List<Map<String, Object>> student_interest(Map<String, Object> params);
	
	List<Map<String, Object>> student_award_info(Map<String, Object> params);

	String getstudent_mental_status_ld(Map<String, Object>params);
	
	List<Map<String, Object>> getlinestudent(Map<String, Object>  params);
	
	List<Map<String, Object>> getstu(Map<String, Object> param);
	
	List<Map<String, Object>> getbq(Map<String, Object> params);
	List<Map<String, Object>> getjsztdata(Map<String, Object> params);
	List<Map<String, Object>> getxxztdata(Map<String, Object> params);
	List<Map<String, Object>> getjsztline(Map<String, Object> params);
	List<Map<String, Object>> getxxztline(Map<String, Object> params);
	Map<String, Object> getxszt(Map<String, Object> params);
	List<Map<String, Object>> getaditor(Map<String, Object> params);
	
	//学校
	List<Map<String, Object>> geta(Map<String, Object> params);
	List<Map<String, Object>> getcols(Map<String, Object> params);
	List<Map<String, Object>> getqxdata(Map<String, Object> params);
	List<Map<String, Object>> getrjdata(Map<String, Object> params);
	List<Map<String, Object>> getxxdata(Map<String, Object> params);
	List<Map<String, Object>> getjsdata(Map<String, Object> params);
	List<Map<String, Object>> getxkxqdata(Map<String, Object> params);
	List<Map<String, Object>> getqxbjdata(Map<String, Object> params);
	List<Map<String, Object>> getjsbjdata(Map<String, Object> params);
	List<Map<String, Object>> getxxbjdata(Map<String, Object> params);
	//年级

	List<Map<String, Object>> getagrad(Map<String, Object> params);
	List<Map<String, Object>> getcolsgrad(Map<String, Object> params);
	List<Map<String, Object>> getqxdatagrad(Map<String, Object> params);
	List<Map<String, Object>> getrjdatagrad(Map<String, Object> params);
	List<Map<String, Object>> getxxdatagrad(Map<String, Object> params);
	List<Map<String, Object>> getjsdatagrad(Map<String, Object> params);
	List<Map<String, Object>> getxkxqdatagrad(Map<String, Object> params);
	List<Map<String, Object>> getqxbjdatagrad(Map<String, Object> params);
	List<Map<String, Object>> getjsbjdatagrad(Map<String, Object> params);
	List<Map<String, Object>> getxxbjdatagrad(Map<String, Object> params);
	//班级
	List<Map<String, Object>> getacls(Map<String, Object> params);
	List<Map<String, Object>> getcolscls(Map<String, Object> params);
	List<Map<String, Object>> getqxdatacls(Map<String, Object> params);
	List<Map<String, Object>> getrjdatacls(Map<String, Object> params);
	List<Map<String, Object>> getxxdatacls(Map<String, Object> params);
	List<Map<String, Object>> getjsdatacls(Map<String, Object> params);
	List<Map<String, Object>> getxkxqdatacls(Map<String, Object> params);
	List<Map<String, Object>> getyjdatacls(Map<String, Object> params);
	//timeline
	List<Map<String, Object>> getqxtime(Map<String, Object> params);
	List<Map<String, Object>> getjstime(Map<String, Object> params);
	List<Map<String, Object>> getxxtime(Map<String, Object> params);
	List<Map<String, Object>> getclsdetailqxzt(Map<String, Object> params);
	List<Map<String, Object>> getclsdetailrjgx(Map<String, Object> params);
	List<Map<String, Object>> getclsdetailxxzt(Map<String, Object> params);
	List<Map<String, Object>> getclsdetailjszt(Map<String, Object> params);
	
	
}
