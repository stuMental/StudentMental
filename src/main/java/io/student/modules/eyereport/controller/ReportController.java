package io.student.modules.eyereport.controller;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

import io.student.modules.datacenter.entity.Kimage;
import io.student.modules.datacenter.entity.Studentimage;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;

import io.student.common.exception.RRException;
import io.student.common.utils.POIUtil;
import io.student.common.utils.R;
import io.student.modules.eyereport.service.ReportService;
import io.student.modules.sys.controller.AbstractController;
import io.student.modules.sys.entity.SysDeptEntity;
import io.student.modules.sys.service.SysConfigService;
import io.student.modules.sys.service.SysDeptService;
import io.student.modules.datacenter.service.KimageService;
import io.student.modules.datacenter.service.StudentimageService;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@RestController
@RequestMapping("/report")
public class ReportController extends AbstractController {

	@Autowired
	private ReportService reportService;

	@Autowired
	private SysDeptService sysDeptService;

	@Autowired
	private SysConfigService sysConfigService;

	@Autowired
	private KimageService kimageService;

	@Autowired
	private StudentimageService StudentimageService;
	
	@GetMapping("/tealist")
	public R gettealist()
	{
		return R.ok().put("data",reportService.getteachlist());
	}

	@RequestMapping("/uploadImage")
	public R importImage(@RequestParam Map<String,String> map, @RequestParam("file") MultipartFile file) {
		if (file.isEmpty()) {
			return R.error("上传照片不能为空");
		}
		System.out.println("innnnnnnnnnnnnn2222!");
		// 获取表名
		String type = map.get("tablename").toString();
		System.out.println(type);
		//	存储照片
		if (file.isEmpty()) {
			return R.error("上传文件不能为空");
		}
		try {
			InputStream is = file.getInputStream();
			//	获取文件名即学号
			String studentId = file.getOriginalFilename();
			studentId = studentId.split("\\.")[0];
			studentId = studentId.split("\\-")[0];
			System.out.println(studentId);
			//	获取照片
			ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
			byte[] buff = new byte[100];
			int rc = 0;
			while ((rc = is.read(buff, 0, 100)) > 0) {
				swapStream.write(buff, 0, rc);
			}
			byte[] in_b = swapStream.toByteArray();
			//	存储student_image表
			String uuid = UUID.randomUUID().toString().replace("-", "");
			System.out.println(uuid);
			Studentimage studentImage = new Studentimage();
			studentImage.setStudentNumber(studentId);
			studentImage.setImageId(uuid);
			studentImage.setStat("0");
			int s_id = StudentimageService.insertobj(studentImage);
			if(s_id!=1) {
				return R.error("失败");
			}
			//	存储image表
			Kimage kimage=new Kimage();
			kimage.setImage(in_b);
			kimage.setId(uuid);
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

	@RequestMapping("/uploadlocal")
	public R importxsl(@RequestParam Map<String,String> map, @RequestParam("file") MultipartFile file) {
		if (file.isEmpty()) {
			return R.error("上传文件不能为空");
		}
		System.out.println("innnnnnnnnnnnnn!");

		// 获取表名
		String type = map.get("tablename").toString();
		System.out.println(type);
		String tablename = "";
		switch (type) {
			case "dept":
				tablename = "sys_dept";
				break;
			case "camera":
				tablename = "school_camera_class_info";
				break;
			case "courseName":
				tablename = "sys_config";
				break;
			case "teacher":
				tablename = "sys_user";
				break;
			case "student":
				tablename = "school_student_class_info";
				break;
		}

		//获取excel数据
		try {
			Workbook wb=POIUtil.getWorkBook(file);
			Sheet sheet = wb.getSheetAt(0);
			List<List<Object>> dataList = new ArrayList<>();
			int readRowCount = 0;
			readRowCount = sheet.getPhysicalNumberOfRows();
			//表中无数据
			if (readRowCount == 1) {
				System.out.println("emptyyyyyyyyyy");
				return R.error("数据为空");
			}
			// 解析sheet 的行
			for (int j = sheet.getFirstRowNum() + 1; j < readRowCount; j++) {
				Row row = sheet.getRow(j);
				if (row == null) {
					continue;
				}
				if (row.getFirstCellNum() < 0) {
					continue;
				}
				int readColumnCount = 0;
				readColumnCount = (int) row.getLastCellNum();
				List<Object> rowValue = new LinkedList<Object>();
				// 解析sheet 的列
				for (int k = 0; k < readColumnCount; k++) {
					Cell cell = row.getCell(k);
					rowValue.add(getCellValue(wb, cell));
				}
				//存入数据库
				System.out.println(rowValue);
//				fixme:bug
				System.out.println("dwdwdwdw");
//				Long userId = getUserId();
//				System.out.println("dsdsd" + userId.toString());
//				if(reportService.importxls(rowValue, tablename, userId)!=-1)
				if(reportService.importxls(rowValue, tablename, 0L)==-1)
				{
					return R.error();
				}
			}

//			System.out.println(dataList);
			return R.ok();

		} catch (Exception e) {
			return R.error(e.getMessage());
		}
	}

	//	获取excel单元格的值
	private static Object getCellValue(Workbook wb, Cell cell) {
		Object columnValue = null;
		if (cell != null) {
			DecimalFormat df = new DecimalFormat("0");// 格式化 number
			// String
			// 字符
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化日期字符串
			DecimalFormat nf = new DecimalFormat("0.00");// 格式化数字
			switch (cell.getCellType()) {
				case Cell.CELL_TYPE_STRING:
					columnValue = cell.getStringCellValue();
					break;
				case Cell.CELL_TYPE_NUMERIC:
					if ("@".equals(cell.getCellStyle().getDataFormatString())) {
						columnValue = df.format(cell.getNumericCellValue());
					} else if ("General".equals(cell.getCellStyle().getDataFormatString())) {
						columnValue = nf.format(cell.getNumericCellValue());
					} else {
						columnValue = sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
					}
					break;
				case Cell.CELL_TYPE_BOOLEAN:
					columnValue = cell.getBooleanCellValue();
					break;
				case Cell.CELL_TYPE_BLANK:
					columnValue = "";
					break;
				case Cell.CELL_TYPE_FORMULA:
					// 格式单元格
					FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();
					evaluator.evaluateFormulaCell(cell);
					CellValue cellValue = evaluator.evaluate(cell);
					columnValue = cellValue.getNumberValue();
					break;
				default:
					columnValue = cell.toString();
			}
		}
		return columnValue;
	}

	/**
	 * 根据map中的某个key 去除List中重复的map
	 * 
	 * @author shijing
	 * @param list
	 * @param mapKey
	 * @return
	 */
	private static List<Map<String, Object>> removeRepeatMapByKey(List<Map<String, Object>> list, String mapKey) {

		// 把list中的数据转换成msp,去掉同一id值多余数据，保留查找到第一个id值对应的数据
		List<Map<String, Object>> listMap = new ArrayList<>();
		Map<String, Map> msp = new HashMap<>();
		for (int i = list.size() - 1; i >= 0; i--) {
			Map map = list.get(i);
			String id = (String) map.get(mapKey);
			map.remove(mapKey);
			msp.put(id, map);
		}
		// 把msp再转换成list,就会得到根据某一字段去掉重复的数据的List<Map>
		Set<String> mspKey = msp.keySet();
		for (String key : mspKey) {
			Map newMap = msp.get(key);
			newMap.put(mapKey, key);
			listMap.add(newMap);
		}
		return listMap;
	}

	/**
	 * 删除List中重复元素，并保持顺序
	 * 
	 * @param list
	 *            待去重的list
	 * @return 去重后的list
	 */
	private static <T> List<T> removeDuplicateKeepOrder(List<T> list) {
		Set set = new HashSet();
		List<T> newList = new ArrayList<>();
		for (T element : list) {
			// set能添加进去就代表不是重复的元素
			if (set.add(element))
				newList.add(element);
		}
		list.clear();
		list.addAll(newList);
		return list;
	}

	@RequestMapping("/clasdetail")
	public R getcladetaildata(@RequestBody Map<String, Object> param) {
		Map<String, Object> result = new HashMap<>();

		// 判断报表类型
		try {
			Integer deptid = (Integer) param.get("deptid");
			String date1 = param.get("date1").toString();
			Integer id = (Integer) param.get("id");
			SysDeptEntity sysDeptEntity = sysDeptService.queryObject(deptid.longValue());
			SysDeptEntity syspro = sysDeptService.queryObject(sysDeptEntity.getParentId());

			List<Map<String, Object>> tablepath = new ArrayList<>();
			Map<String, Object> map = new HashMap<>();
			map.put("label", "学号");
			map.put("prop", "student_number");
			tablepath.add(map);
			Map<String, Object> mapp = new HashMap<>();
			mapp.put("label", "姓名");
			mapp.put("prop", "student_name");
			tablepath.add(mapp);
			List<Map<String, Object>> tabledata = new ArrayList<>();
			switch (id) {
			case 1:
				// 情绪状态
				// 设置列名
				Map<String, Object> map1 = new HashMap<>();
				map1.put("label", "情绪状态");
				map1.put("prop", "student_data");
				tablepath.add(map1);
				//tabledata = reportService.getclsdetailqxzt(date1, syspro.getName(), sysDeptEntity.getName());
				// 获取数据

				break;
			case 2:
				Map<String, Object> map2 = new HashMap<>();
				map2.put("label", "人际状态");
				map2.put("prop", "student_data");
				tablepath.add(map2);
				//tabledata = reportService.getclsdetailrjgx(date1, syspro.getName(), sysDeptEntity.getName());

				break;
			case 3:
				Map<String, Object> map3 = new HashMap<>();
				map3.put("label", "学习状态");
				map3.put("prop", "student_data");
				tablepath.add(map3);
				//tabledata = reportService.getclsdetailxxzt(date1, syspro.getName(), sysDeptEntity.getName());

				break;
			case 4:
				Map<String, Object> map4 = new HashMap<>();
				map4.put("label", "精神状态");
				map4.put("prop", "student_data");
				tablepath.add(map4);
				//tabledata = reportService.getclsdetailjszt(date1, syspro.getName(), sysDeptEntity.getName());

				break;
			default:
				break;
			}

			result.put("tablepath", tablepath);
			result.put("tabledata", tabledata);

		} catch (Exception e) {

		}
		return R.ok().put("data", result);
	}

	@RequestMapping("/clas")
	public R getcladata(@RequestBody Map<String, Object> param) {
		Map<String, Object> result = new HashMap<>();

		List<Map<String, Object>> qxtimelines = new ArrayList<>();
		List<Map<String, Object>> jstimelines = new ArrayList<>();
		List<Map<String, Object>> xxtimelines = new ArrayList<>();

		// 判断报表类型
		Integer deptid = (Integer) param.get("deptid");
		String date1 = param.get("date1").toString();
		
		Map<String, Object> deptparam=sysDeptService.getgrade(deptid.longValue());

		SysDeptEntity sysDeptEntity = sysDeptService.queryObject(deptid.longValue());
		deptparam.put("day", date1);

		if (sysDeptEntity.getParentId() == 0) {

			qxtimelines = reportService.getqxtime(deptparam);
			jstimelines = reportService.getjstime(deptparam);
			xxtimelines = reportService.getxxtime(deptparam);

			// 学校报表
			// 获取今日考勤
			List<Map<String, Object>> kqList = reportService.geta(deptparam);
			// 设置列名
			List<Map<String, Object>> cols = reportService.getcols(deptparam);
			result.put("cols", cols);
			// List<Map<String, Object>> kqList2 = new ArrayList<>();
			// for (Map<String, Object> map : kqList) {
			// Map<String, Object> map2 = new HashMap<>();
			// map2.put("grade_name", map.get("grade_name"));
			// map2.put(map.get("time_gap").toString(), map.get("ct"));
			// for (Map<String, Object> mapm : kqList) {
			// if
			// (mapm.get("grade_name").toString().equals(map.get("grade_name").toString()))
			// {
			// map2.put(mapm.get("time_gap").toString(), mapm.get("ct"));
			// }
			//
			// }
			// kqList2.add(map2);
			// }
			result.put("kqList", kqList);
			// 获取情绪
			result.put("qxdata", reportService.getqxdata(deptparam));
			// 获取人际关系
			result.put("rjdata", reportService.getrjdata(deptparam));
			// 学习状态
			result.put("xxdata", reportService.getxxdata(deptparam));
			result.put("jsdata", reportService.getjsdata(deptparam));
			// 学科兴趣
			List<String> xkxqdatax = new ArrayList<>();
			List<String> xkxqdatay = new ArrayList<>();
			List<Map<String, Object>> xkxqdata = reportService.getxkxqdata(deptparam);
			for (Map<String, Object> map : xkxqdata) {
				xkxqdatax.add(map.get("total").toString());
				xkxqdatay.add(map.get("student_interest").toString());
			}
			result.put("xkxqdatax", xkxqdatax);
			result.put("xkxqdatay", xkxqdatay);
			// 预警
			List<Map<String, Object>> qxbj = reportService.getqxbjdata(deptparam);
			List<Map<String, Object>> jsbj = reportService.getjsbjdata(deptparam);
			List<Map<String, Object>> xxbj = reportService.getxxbjdata(deptparam);
			List<Map<String, Object>> yj = new ArrayList<>();
			for (Map<String, Object> map : qxbj) {
				Map<String, Object> yjj = new HashMap<>();
				yjj.put("grade_name", map.get("grade_name").toString());
				yjj.put("qx", map.get("qx").toString());
				Iterator<Map<String, Object>> map2 = jsbj.iterator();
				while (map2.hasNext()) {
					Map<String, Object> map22 = map2.next();
					if (map.get("grade_name").toString().equals(map22.get("grade_name").toString())) {
						yjj.put("js", map22.get("js").toString());
						map2.remove();
					}
				}
				jsbj = Lists.newArrayList(map2);
				Iterator<Map<String, Object>> map3 = xxbj.iterator();
				while (map3.hasNext()) {
					Map<String, Object> map33 = map3.next();
					if (map.get("grade_name").toString().equals(map33.get("grade_name").toString())) {
						yjj.put("xx", map33.get("xx").toString());
						map3.remove();
					}
				}
				xxbj = Lists.newArrayList(map3);
				yj.add(yjj);
			}
			for (Map<String, Object> map : jsbj) {
				Map<String, Object> yjj = new HashMap<>();
				yjj.put("grade_name", map.get("grade_name").toString());
				yjj.put("js", map.get("js").toString());
				Iterator<Map<String, Object>> map3 = xxbj.iterator();
				while (map3.hasNext()) {
					Map<String, Object> map33 = map3.next();
					if (map.get("grade_name").toString().equals(map33.get("grade_name").toString())) {
						yjj.put("xx", map33.get("xx").toString());
						map3.remove();
					}

				}
				xxbj = Lists.newArrayList(map3);
				yj.add(yjj);
			}
			for (Map<String, Object> map : xxbj) {
				Map<String, Object> yjj = new HashMap<>();
				yjj.put("grade_name", map.get("grade_name").toString());
				yjj.put("xx", map.get("xx").toString());
				yj.add(yjj);
			}
			result.put("yjData", yj);
			// 预警cols
			List<Map<String, Object>> yjcols = new ArrayList<>();
			Map<String, Object> col = new HashMap<>();
			col.put("prop", "grade_name");
			col.put("label", "年级");
			yjcols.add(col);
			Map<String, Object> col1 = new HashMap<>();
			col1.put("prop", "qx");
			col1.put("label", "情绪不佳");
			yjcols.add(col1);
			Map<String, Object> col2 = new HashMap<>();
			col2.put("prop", "js");
			col2.put("label", "精神状态不佳");
			yjcols.add(col2);
			Map<String, Object> col3 = new HashMap<>();
			col3.put("prop", "xx");
			col3.put("label", "学习状态不佳");
			yjcols.add(col3);
			result.put("yjcols", yjcols);
			result.put("elcls", false);
		} else {
			SysDeptEntity dept2 = sysDeptService.queryObject(sysDeptEntity.getParentId());
			if (dept2.getParentId()== 0) {
				qxtimelines = reportService.getqxtime(deptparam);
				jstimelines = reportService.getjstime(deptparam);
				xxtimelines = reportService.getxxtime(deptparam);

				// 年级报表
				// 获取今日考勤
				List<Map<String, Object>> kqList = reportService.getagrad(deptparam);
				// 设置列名
				List<Map<String, Object>> cols = reportService.getcolsgrad(deptparam);
				result.put("cols", cols);
				// List<Map<String, Object>> kqList2 = new ArrayList<>();
				// for (Map<String, Object> map : kqList) {
				// Map<String, Object> map2 = new HashMap<>();
				// map2.put("class_name", map.get("class_name"));
				// map2.put(map.get("time_gap").toString(), map.get("ct"));
				// for (Map<String, Object> mapm : kqList) {
				// if
				// (mapm.get("class_name").toString().equals(map.get("class_name").toString()))
				// {
				// map2.put(mapm.get("time_gap").toString(), mapm.get("ct"));
				// }
				//
				// }
				// kqList2.add(map2);
				// }
				result.put("kqList", kqList);
				// 获取情绪
				result.put("qxdata", reportService.getqxdatagrad(deptparam));
				// 获取人际关系
				result.put("rjdata", reportService.getrjdatagrad(deptparam));
				// 学习状态
				result.put("xxdata", reportService.getxxdatagrad(deptparam));
				result.put("jsdata", reportService.getjsdatagrad(deptparam));
				// 学科兴趣
				List<String> xkxqdatax = new ArrayList<>();
				List<String> xkxqdatay = new ArrayList<>();
				List<Map<String, Object>> xkxqdata = reportService.getxkxqdatagrad(deptparam);
				for (Map<String, Object> map : xkxqdata) {
					xkxqdatax.add(map.get("total").toString());
					xkxqdatay.add(map.get("student_interest").toString());
				}
				result.put("xkxqdatax", xkxqdatax);
				result.put("xkxqdatay", xkxqdatay);
				// 预警
				List<Map<String, Object>> qxbj = reportService.getqxbjdatagrad(deptparam);
				List<Map<String, Object>> jsbj = reportService.getjsbjdatagrad(deptparam);
				List<Map<String, Object>> xxbj = reportService.getxxbjdatagrad(deptparam);
				List<Map<String, Object>> yj = new ArrayList<>();
				for (Map<String, Object> map : qxbj) {
					if (map.get("class_name") == null) {
						break;
					}
					Map<String, Object> yjj = new HashMap<>();
					yjj.put("class_name", map.get("class_name").toString());
					yjj.put("qx", map.get("qx").toString());
					for (Map<String, Object> map2 : jsbj) {
						if (map.get("class_name").toString().equals(map2.get("class_name").toString())) {
							yjj.put("js", map2.get("js").toString());
							jsbj.remove(map2);
						}

					}
					for (Map<String, Object> map3 : xxbj) {
						if (map.get("class_name").toString().equals(map3.get("class_name").toString())) {
							yjj.put("xx", map3.get("xx").toString());
							xxbj.remove(map3);
						}
					}
					yj.add(yjj);
				}
				for (Map<String, Object> map : jsbj) {
					Map<String, Object> yjj = new HashMap<>();
					if (map.get("class_name") == null) {
						break;
					}
					yjj.put("class_name", map.get("class_name").toString());
					yjj.put("js", map.get("js").toString());
					for (Map<String, Object> map3 : xxbj) {
						if (map.get("class_name").toString().equals(map3.get("class_name").toString())) {
							yjj.put("xx", map3.get("xx").toString());
							xxbj.remove(map3);
						}
					}
					yj.add(yjj);
				}
				for (Map<String, Object> map : xxbj) {
					if (map.get("class_name") == null) {
						break;
					}
					Map<String, Object> yjj = new HashMap<>();
					yjj.put("class_name", map.get("class_name").toString());
					yjj.put("xx", map.get("xx").toString());
					yj.add(yjj);
				}
				result.put("yjData", yj);
				// 预警cols
				List<Map<String, Object>> yjcols = new ArrayList<>();
				Map<String, Object> col = new HashMap<>();
				col.put("prop", "class_name");
				col.put("label", "班级");
				yjcols.add(col);
				Map<String, Object> col1 = new HashMap<>();
				col1.put("prop", "qx");
				col1.put("label", "情绪不佳");
				yjcols.add(col1);
				Map<String, Object> col2 = new HashMap<>();
				col2.put("prop", "js");
				col2.put("label", "精神状态不佳");
				yjcols.add(col2);
				Map<String, Object> col3 = new HashMap<>();
				col3.put("prop", "xx");
				col3.put("label", "学习状态不佳");
				yjcols.add(col3);
				result.put("yjcols", yjcols);
				result.put("elcls", false);
			} else {
				// 班级报表
				qxtimelines = reportService.getqxtime(deptparam);
				jstimelines = reportService.getjstime(deptparam);
				xxtimelines = reportService.getxxtime(deptparam);
				// 获取今日考勤
				List<Map<String, Object>> kqList = reportService.getacls(deptparam);
				// 设置列名
				List<Map<String, Object>> cols = reportService.getcolscls(deptparam);

				result.put("cols", cols);

				result.put("kqList", kqList);
				// 获取情绪
				result.put("qxdata", reportService.getqxdatacls(deptparam));
				// 获取人际关系
				result.put("rjdata", reportService.getrjdatacls(deptparam));
				// 学习状态
				result.put("xxdata", reportService.getxxdatacls(deptparam));
				result.put("jsdata", reportService.getjsdatacls(deptparam));

				List<String> xkxqdatax = new ArrayList<>();
				List<String> xkxqdatay = new ArrayList<>();
				List<Map<String, Object>> xkxqdata = reportService.getxkxqdatacls(deptparam);
				for (Map<String, Object> map : xkxqdata) {
					xkxqdatax.add(map.get("total").toString());
					xkxqdatay.add(map.get("student_interest").toString());
				}
				result.put("xkxqdatax", xkxqdatax);
				result.put("xkxqdatay", xkxqdatay);
				// 预警
				List<Map<String, Object>> yj = reportService.getyjdatacls(deptparam);

				result.put("yjData", yj);
				// 预警cols
				List<Map<String, Object>> yjcols = new ArrayList<>();
				Map<String, Object> col = new HashMap<>();
				col.put("prop", "name");
				col.put("label", "预警类别");
				yjcols.add(col);
				Map<String, Object> col1 = new HashMap<>();
				col1.put("prop", "value");
				col1.put("label", "学生姓名");
				yjcols.add(col1);
				result.put("yjcols", yjcols);
				result.put("elcls", true);
			}
		}
		// 情绪状态 历史

		List<String> qxtimeline = new ArrayList<>();
		List<BigDecimal> kxdata = new ArrayList<>();
		List<BigDecimal> zcdata = new ArrayList<>();
		List<BigDecimal> dldata = new ArrayList<>();
		for (Map<String, Object> map : qxtimelines) {
			qxtimeline.add(map.get("dt").toString());
		}

		List<String> nqxtimeline = removeDuplicateKeepOrder(qxtimeline);
		for (String string : nqxtimeline) {
			for (Map<String, Object> map : qxtimelines) {
				if (string.equals(map.get("dt").toString())) {
					switch (map.get("student_emotion").toString()) {
					case "0":
						kxdata.add((BigDecimal) map.get("percentage"));
						break;
					case "1":
						zcdata.add((BigDecimal) map.get("percentage"));
						break;
					case "2":
						dldata.add((BigDecimal) map.get("percentage"));
						break;
					default:
						break;
					}

				}
			}
			int line = nqxtimeline.indexOf(string) + 1;
			if (kxdata.size() != line) {
				kxdata.add(new BigDecimal("0"));
			}
			if (zcdata.size() != line) {
				zcdata.add(new BigDecimal("0"));
			}
			if (dldata.size() != line) {
				dldata.add(new BigDecimal("0"));
			}
		}

		result.put("qxtimeline", nqxtimeline);
		result.put("kxdata", kxdata);
		result.put("zcdata", zcdata);
		result.put("dldata", dldata);
		// 精神time

		List<String> jstimeline = new ArrayList<>();
		List<BigDecimal> jjdata = new ArrayList<>();
		List<BigDecimal> jszcdata = new ArrayList<>();
		List<BigDecimal> pbdata = new ArrayList<>();
		for (Map<String, Object> map : qxtimelines) {
			jstimeline.add(map.get("dt").toString());
		}

		List<String> njstimeline = removeDuplicateKeepOrder(jstimeline);
		for (String string : njstimeline) {
			for (Map<String, Object> map : jstimelines) {
				if (string.equals(map.get("dt").toString())) {
					switch (map.get("student_mental_stat").toString()) {
					case "0":
						jjdata.add((BigDecimal) map.get("percentage"));
						break;
					case "1":
						jszcdata.add((BigDecimal) map.get("percentage"));
						break;
					case "2":
						pbdata.add((BigDecimal) map.get("percentage"));
						break;
					default:
						break;
					}

				}
			}
			int line = njstimeline.indexOf(string) + 1;
			if (jjdata.size() != line) {
				jjdata.add(new BigDecimal("0"));
			}
			if (jszcdata.size() != line) {
				jszcdata.add(new BigDecimal("0"));
			}
			if (pbdata.size() != line) {
				pbdata.add(new BigDecimal("0"));
			}
		}

		result.put("jstimeline", njstimeline);
		result.put("jjdata", jjdata);
		result.put("jszcdata", jszcdata);
		result.put("pbdata", pbdata);
		// 学习time

		List<String> xxtimeline = new ArrayList<>();
		List<BigDecimal> fchdata = new ArrayList<>();
		List<BigDecimal> lhdata = new ArrayList<>();
		List<BigDecimal> xxzcdata = new ArrayList<>();
		List<BigDecimal> bjdata = new ArrayList<>();
		for (Map<String, Object> map : xxtimelines) {
			xxtimeline.add(map.get("dt").toString());
		}

		List<String> nxxtimeline = removeDuplicateKeepOrder(xxtimeline);
		for (String string : nxxtimeline) {
			for (Map<String, Object> map : xxtimelines) {
				if (string.equals(map.get("dt").toString())) {
					switch (map.get("student_study_stat").toString()) {
					case "0":
						fchdata.add((BigDecimal) map.get("percentage"));
						break;
					case "1":
						lhdata.add((BigDecimal) map.get("percentage"));
						break;
					case "2":
						xxzcdata.add((BigDecimal) map.get("percentage"));
						break;
					case "3":
						bjdata.add((BigDecimal) map.get("percentage"));
						break;
					default:
						break;
					}

				}
			}
			int line = nxxtimeline.indexOf(string) + 1;
			if (fchdata.size() != line) {
				fchdata.add(new BigDecimal("0"));
			}
			if (lhdata.size() != line) {
				lhdata.add(new BigDecimal("0"));
			}
			if (xxzcdata.size() != line) {
				xxzcdata.add(new BigDecimal("0"));
			}
			if (bjdata.size() != line) {
				bjdata.add(new BigDecimal("0"));
			}
		}

		result.put("xxtimeline", nxxtimeline);
		result.put("fchdata", fchdata);
		result.put("lhdata", lhdata);
		result.put("xxzcdata", xxzcdata);
		result.put("bjdata", bjdata);

		return R.ok().put("data", result);
	}

	@RequestMapping("/diagnosis")
	public R getDiagnosis(@RequestBody Map<String, Object> param) {
		param.putAll(sysDeptService.getgrade(Long.parseLong(param.get("deptid").toString())));
//		System.out.println("aaa" + param);
		Map<String, Object> result = new HashMap<>();
		result.put("class", reportService.getDiagnosisClass(param));
		result.put("student", reportService.getDiagnosisStu(param));
//		System.out.println(result);
		return R.ok().put("data", result);
	}

	@RequestMapping("/pro")
	public R getpros(@RequestBody Map<String, Object> param) {
		Map<String, Object> result = new HashMap<>();
		// 变量
		// 取兴趣
		result.put("xkxq", reportService.student_interest(param));
		// 体艺发展
		result.put("tyfz", reportService.student_award_info(param));
		// 人际关系
		result.put("rjgx", reportService.getstudent_mental_status_ld(param));
		// 情绪状态
		result.put("qxzt", reportService.getlinestudent(param));
		// 表情
		List<Map<String, Object>> bqs = reportService.getbq(param);
		List<String> bqtime = new ArrayList<>();
		List<String> bq = new ArrayList<>();
		for (Map<String, Object> map : bqs) {
			bqtime.add(map.get("dt").toString());
			bq.add(map.get("student_emotion").toString());
		}

		result.put("bq", bq);
		result.put("bqtime", bqtime);
		// 精神状态
		result.put("jsztdata", reportService.getjsztdata(param));
		result.put("xxztdata", reportService.getxxztdata(param));
		// line
		List<Map<String, Object>> jsztlines = reportService.getjsztline(param);
		List<String> jsztline = new ArrayList<>();
		List<String> jsztlinedata = new ArrayList<>();
		for (Map<String, Object> map : jsztlines) {
			jsztline.add(map.get("dt").toString());
			jsztlinedata.add(map.get("student_mental_stat").toString());
		}
		result.put("jsztline", jsztline);
		result.put("jsztlinedata", jsztlinedata);

		List<Map<String, Object>> xxztlines = reportService.getxxztline(param);
		List<String> xxztline = new ArrayList<>();
		List<String> xxztlinedata = new ArrayList<>();
		for (Map<String, Object> map : xxztlines) {
			xxztline.add(map.get("dt").toString());
			xxztlinedata.add(map.get("student_study_stat").toString());
		}

		result.put("xxztline", xxztline);
		result.put("xxztlinedata", xxztlinedata);

		// radar
		Map<String, Object> xszts = reportService.getxszt(param);
		List<String> xszt = new ArrayList<>();
		xszt.add(xszts.get("study").toString());
		xszt.add(xszts.get("mental").toString());
		xszt.add(xszts.get("emotion").toString());
		xszt.add(xszts.get("relationship").toString());

		result.put("xszt", xszt);

		List<String> aditorlegend = new ArrayList<>();
		List<Map<String, Object>> Aditordata = new ArrayList<>();
		List<Map<String, Object>> aditodatas = reportService.getaditor(param);
		for (Map<String, Object> map : aditodatas) {
			aditorlegend.add(map.get("course_name").toString());
			Map<String, Object> adit = new HashMap<>();
			adit.put("name", map.get("course_name").toString());
			adit.put("type", "scatter");
			adit.put("symbolSize", 15);
			List<Object> xys = new ArrayList<>();
			List<Float> xy = new ArrayList<>();
			try {
				xy.add(Float.parseFloat(map.get("study_level").toString()));
			} catch (Exception e) {
				xy.add(null);
				// TODO: handle exception
			}
			try {
				xy.add(Float.parseFloat(map.get("grade_level").toString()));
			} catch (Exception e) {
				xy.add(null);
				// TODO: handle exception
			}
			xys.add(xy);
			adit.put("data", xys);

			Aditordata.add(adit);
		}
		result.put("aditorlegend", aditorlegend);
		result.put("Aditordata", Aditordata);

		return R.ok().put("data", result);
	}

	@RequestMapping("dict/stu/{deptid}")
	public R getstudent(@PathVariable("deptid") long deptid) {
		//SysDeptEntity sysDeptEntity = new SysDeptEntity();
		//SysDeptEntity pardept = new SysDeptEntity();
		//if (deptid != 0) {
		//	sysDeptEntity = sysDeptService.queryObject(deptid);
	//		if (sysDeptEntity.getParentId() != null && sysDeptEntity.getParentId() != 0) {
//				pardept = sysDeptService.queryObject(sysDeptEntity.getParentId());
		//	} else {
	//			pardept.setName(sysDeptEntity.getName());
//				sysDeptEntity.setName(null);
//			}
//		}
//		System.out.println(sysDeptEntity.getName() + pardept.getName());

		return R.ok().put("stu", reportService.getstu(sysDeptService.getgrade(deptid)));
	}

	// @GetMapping("/pro/intre")
	// public R getpro(@RequestParam Map<String, Object> params) {
	// return R.ok(reportService.student_interest(params));
	// }

	// @GetMapping("/pro/award")
	// public R getproaward(@RequestParam Map<String, Object> params) {
	// return R.ok(reportService.student_award_info(params));
	// }

	// @GetMapping("/pro/mental")
	// public R getstudent_mental_status_ld(@RequestParam Map<String, Object>
	// params) {
	// return R.ok().put("data", reportService.getlinestudent(params));
	// }

}
