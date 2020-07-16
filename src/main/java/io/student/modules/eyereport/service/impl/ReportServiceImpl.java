package io.student.modules.eyereport.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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
import io.student.modules.eyereport.service.ReportService;

@Service("reportService")
public class ReportServiceImpl implements ReportService {

	@Autowired
	private ProDao prodao;

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
	public int importxls(List<Map<String, String>> list, String type) {
		switch (type) {
		case "school_camera_class_info":
			prodao.deleteschool_camera();
			return prodao.insertschool_camera(list);
		case "school_student_class_info":
			prodao.deleteschool_student();
			return prodao.insertschool_student(list);
		case "school_course_info":
			prodao.deleteschool_course();
			return prodao.insertschool_course(list);
		case "school_performance_info":
			return prodao.insertschool_performance(list);
		case "school_award_info":
			return prodao.insertschool_award(list);
		default:
			return -1;
		}
	}

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
