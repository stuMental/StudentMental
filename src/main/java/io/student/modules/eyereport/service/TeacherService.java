package io.student.modules.eyereport.service;

import com.baomidou.mybatisplus.service.IService;
import io.student.modules.datacenter.entity.SchoolstudentEntity;

import java.util.List;
import java.util.Map;

public interface TeacherService {
    List<Map<String, Object>> getrtch(Map<String, Object> param);
    List<Map<String, Object>> getssxw(Map<String, Object> param);
    List<Map<String, Object>> getxsxw(Map<String, Object> param);
    List<Map<String, Object>> getjsxw(Map<String, Object> param);
    List<Map<String, Object>> getqxzt(Map<String, Object> param);
}
