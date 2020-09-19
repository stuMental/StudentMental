package io.student.modules.eyereport.controller;

import io.student.common.utils.R;
import io.student.modules.sys.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.student.modules.eyereport.service.TeacherService;
import io.student.modules.sys.service.SysDeptService;
import io.student.modules.eyereport.service.ReportService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/eyereport/teacher")
public class TeacherController extends AbstractController {
    @Autowired
    private SysDeptService sysDeptService;

    @Autowired
    private ReportService reportService;

    @Autowired
    private TeacherService teacherService;

    // 教师报表
    @RequestMapping("/type")
    public R getType(@RequestBody Map<String,Object> param) {
        param.putAll(sysDeptService.getgrade(Long.parseLong(param.get("deptid").toString())));
        // 结果
        Map<String, Object> result = new HashMap<>();
        // 取rt-ch图
        result.put("rtch", teacherService.getrtch(param));
        // 取师生行为序列图
        result.put("ssxw", teacherService.getssxw(param));
        // 学生行为分析图
        result.put("xsxw", teacherService.getxsxw(param));
        // 教师行为分析
        result.put("jsxw", teacherService.getjsxw(param));
        result.put("qxzt", teacherService.getqxzt(param));
        result.put("jxqxline", teacherService.getjxqxline(param));
        result.put("jxzt", teacherService.getjxzt(param));
        result.put("jxztline", teacherService.getjxztline(param));
//        System.out.println(result);
        return R.ok().put("data", result);
    }

}
