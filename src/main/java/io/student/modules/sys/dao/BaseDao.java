package io.student.modules.sys.dao;


import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * 基础Dao(还需在XML文件里，有对应的SQL语句)
 *
 * @author duanxin
 * @email sxz147@163.com
 * @date 2018/06/12
 */
public interface BaseDao<T>extends BaseMapper<T> {

    void save(T t);

    void save(Map<String, Object> map);

    void saveBatch(List<T> list);

    int update(T t);

    int update(Map<String, Object> map);

    int delete(Object id);

    int delete(Map<String, Object> map);

    int deleteBatch(Object[] id);

    int ustat(Object[] id);

    T queryObject(Object id);

    List<T> queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);

    int queryTotal();
}
