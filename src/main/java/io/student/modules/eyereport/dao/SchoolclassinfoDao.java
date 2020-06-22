package io.student.modules.eyereport.dao;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import io.student.modules.eyereport.entity.Schoolclassinfo;

@Mapper
public interface SchoolclassinfoDao extends  BaseMapper<Schoolclassinfo> {
}
