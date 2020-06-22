package io.student.modules.datacenter.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;

import io.student.common.exception.RRException;
import io.student.common.utils.PageUtils;
import io.student.common.utils.R;
import io.student.modules.datacenter.entity.SchoolstudentEntity;
import io.student.modules.datacenter.service.SchoolstudentService;
import io.student.modules.sys.controller.AbstractController;
import io.student.modules.sys.service.SysDeptService;

@RestController
@RequestMapping("/datacenter/schoolstudent")
public class SchoolstudentController extends AbstractController {
	@Autowired
	private SysDeptService sysDeptService;

	@Autowired
	private SchoolstudentService schoolstudentService;

	@GetMapping("/list")
	public R list(@RequestParam Map<String, Object> params) {

		PageUtils page = schoolstudentService.queryPage(params);

		return R.ok().put("page", page);
	}

	@PostMapping("/delete")
	public R delete(@RequestBody String[] ids) {
		schoolstudentService.deleteBatch(ids);
		return R.ok();
	}

	@GetMapping("/dict")
	public R dict(@RequestParam Map<String, Object> params) {
		return R.ok().put("data", schoolstudentService.dict(params));
	}

	@PostMapping("updata")
	public R updata(@RequestBody Map<String, Object> param) {
		SchoolstudentEntity schoolstudentEntity = JSON.parseObject(JSON.toJSONString(param.get("schoolclassinfo")),
				SchoolstudentEntity.class);
		if (sysDeptService.selectleavel(schoolstudentEntity.getDeptId()) != 3) {
			throw new RRException("班级应该是三层，清选择对应的班级");
		}
		String saveString = (String) param.get("save");
		if (saveString!=null&&saveString.equals("1")) {
			SchoolstudentEntity schoolstudentEntities = schoolstudentService
					.selectById(schoolstudentEntity.getStudentNumber());
			if (schoolstudentEntities != null) {
				throw new RRException("存在相同学号学生");
			}
		}

		try {
			if (schoolstudentService.updata(param)) {
				return R.ok();
			} else {
				return R.error("保存失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return R.error(e.getMessage());
		}
	}
}
