package io.student.modules.datacenter.service;

import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

import io.student.common.utils.PageUtils;
import io.student.modules.datacenter.entity.SchoolawardinfoEntity;

public interface SchoolawardinfoService  extends IService<SchoolawardinfoEntity>{
	PageUtils queryPage(Map<String, Object> params);
}
