package io.student.modules.eyereport.dao;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import io.student.modules.eyereport.entity.Schoolcourseinfo;

@Mapper
public interface SchoolcourseinfoDao extends  BaseMapper<Schoolcourseinfo> {
}
