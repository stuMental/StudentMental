package io.student.modules.sys.dao;


import org.apache.ibatis.annotations.Mapper;
import io.student.modules.sys.entity.SysDeptEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 部门管理
 * 
 * @author duanxin
 * @email sxz147@163.com
 * @date 2018/06/12
 */
@Mapper
public interface SysDeptDao extends BaseDao<SysDeptEntity> {

    /**
     * 查询子部门ID列表
     * @param parentId  上级部门ID
     */
    List<Long> queryDetpIdList(Long parentId);
    int selectleavel(Long dept_id);

    /**
     * 查询id
     * @param name  部门名称
     * @param preId  父部门id
     */
    Long queryId(@Param("name") String name, @Param("preId") Long preId);
}
