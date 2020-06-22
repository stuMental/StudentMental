package io.student.common.aspect;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.student.common.annotation.DataFilter;
import io.student.common.exception.RRException;
import io.student.common.utils.Constant;
import io.student.common.utils.ShiroUtils;
import io.student.modules.sys.entity.SysUserEntity;
import io.student.modules.sys.service.SysDeptService;
import io.student.modules.sys.service.SysRoleDeptService;
import io.student.modules.sys.service.SysUserRoleService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 数据过滤，切面处理类
 * @author duanxin
 * @email sxz147@163.com
 * @date 2018/06/12
 */
@Aspect
@Component
public class DataFilterAspect {
    @Autowired
    private SysDeptService sysDeptService;
    
    @Autowired
    private SysRoleDeptService sysRoleDeptService;
    
    @Autowired
    private SysUserRoleService sysUserRoleService;
    

    @Pointcut("@annotation(io.student.common.annotation.DataFilter)")
    public void dataFilterCut() {

    }

    @Before("dataFilterCut()")
    public void dataFilter(JoinPoint point) throws Throwable {
        Object params = point.getArgs()[0];
        if(params != null && params instanceof Map){
            SysUserEntity user = ShiroUtils.getUserEntity();

            //如果不是超级管理员，则只能查询本部门及子部门数据
            if(user.getUserId() != Constant.SUPER_ADMIN){
                Map map = (Map)params;
                map.put("filterSql", getFilterSQL(user, point));
            }

            return ;
        }

        throw new RRException("要实现数据权限接口的参数，只能是Map类型，且不能为NULL");
    }

    /**
     * 获取数据过滤的SQL
     */
    private String getFilterSQL(SysUserEntity user, JoinPoint point){
        MethodSignature signature = (MethodSignature) point.getSignature();
        DataFilter dataFilter = signature.getMethod().getAnnotation(DataFilter.class);
        //获取表的别名
        String tableAlias = dataFilter.tableAlias();
        if(StringUtils.isNotBlank(tableAlias)){
            tableAlias +=  ".";
        }

        //获取子部门ID
        String subDeptIds = sysDeptService.getSubDeptIdList(user.getDeptId());
        String[] subdepts=subDeptIds.split(",");
        List<Long> subdept =new ArrayList<Long>();
        for (String dept : subdepts) {
        	subdept.add(Long.parseLong(dept));
		}
        //获取数据权限
        List<Long> roles= sysUserRoleService.queryRoleIdList(user.getUserId());
        for (Long role : roles) {
         List<Long> depts=sysRoleDeptService.queryDeptIdList(role);
         subdept.addAll(depts);
		}
       String subDeptIdss=StringUtils.join(subdept.stream().distinct().collect(Collectors.toList()),",");
        //sysRoleDeptService.

        StringBuilder filterSql = new StringBuilder();
        filterSql.append("and (");
        filterSql.append(tableAlias).append("dept_id in(").append(subDeptIdss).append(")");

        //没有本部门数据权限，也能查询本人数据
        if(dataFilter.user()){
            filterSql.append(" or ").append(tableAlias).append("user_id=").append(user.getUserId());
        }
        filterSql.append(")");

        return filterSql.toString();
    }
}
