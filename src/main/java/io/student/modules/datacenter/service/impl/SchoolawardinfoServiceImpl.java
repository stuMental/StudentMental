package io.student.modules.datacenter.service.impl;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import io.student.common.utils.PageUtils;
import io.student.common.utils.Query;
import io.student.modules.datacenter.dao.SchoolawardinfoDao;
import io.student.modules.datacenter.entity.SchoolawardinfoEntity;
import io.student.modules.datacenter.service.SchoolawardinfoService;

@Service("SchoolawardService")
public class SchoolawardinfoServiceImpl extends ServiceImpl<SchoolawardinfoDao,SchoolawardinfoEntity> implements SchoolawardinfoService {

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		String student_number = (String) params.get("student_number");

		Page<SchoolawardinfoEntity> page = this.selectPage(new Query<SchoolawardinfoEntity>(params).getPage(),
				new EntityWrapper<SchoolawardinfoEntity>()
						.eq(StringUtils.isNotBlank(student_number), "student_number", student_number)
						);

		return new PageUtils(page);
	}

}
