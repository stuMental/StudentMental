package io.student.modules.eyereport.service.impl;

import io.student.datasources.DataSourceNames;
import io.student.datasources.annotation.DataSource;
import io.student.modules.eyereport.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.student.modules.eyereport.dao.ProDao;

import java.util.List;
import java.util.Map;

@Service("teacherService")
public class TeacherServiceImpl implements TeacherService{
    @Autowired
    private ProDao prodao;

    @Override
    @DataSource(name= DataSourceNames.SECOND)
    public List<Map<String, Object>> getrtch(Map<String, Object> param)
    {
        return prodao.getrtch(param);
    }

    @Override
    @DataSource(name= DataSourceNames.SECOND)
    public List<Map<String, Object>> getssxw(Map<String, Object> param)
    {
        return prodao.getssxw(param);
    }

    @Override
    @DataSource(name= DataSourceNames.SECOND)
    public List<Map<String, Object>> getxsxw(Map<String, Object> param)
    {
        return prodao.getxsxw(param);
    }

    @Override
    @DataSource(name= DataSourceNames.SECOND)
    public List<Map<String, Object>> getjsxw(Map<String, Object> param)
    {
        return prodao.getjsxw(param);
    }

    @Override
    @DataSource(name= DataSourceNames.SECOND)
    public List<Map<String, Object>> getqxzt(Map<String, Object> param)
    {
        return prodao.getqxzt(param);
    }
}
