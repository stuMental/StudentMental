package io.student.modules.datacenter.service.impl;

import java.util.Arrays;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import io.student.common.utils.PageUtils;
import io.student.common.utils.Query;
import io.student.modules.datacenter.dao.CameraroomDao;
import io.student.modules.datacenter.entity.CameraroomEntity;
import io.student.modules.datacenter.service.CameraroomService;

@Service("cameraroomService")
public class CameraroomServiceImpl extends ServiceImpl<CameraroomDao, CameraroomEntity> implements CameraroomService {

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		String cameraAddr = (String) params.get("cameraAddr");
		String roomId = (String) params.get("roomId");
		String docker = (String) params.get("docker");

		Page<CameraroomEntity> page = this.selectPage(new Query<CameraroomEntity>(params).getPage(),
				new EntityWrapper<CameraroomEntity>()
						.like(StringUtils.isNotBlank(cameraAddr), "camera_addr", cameraAddr)
						.eq(StringUtils.isNotBlank(roomId), "room_id", roomId)
						.like(StringUtils.isNotBlank(docker), "docker", docker));

		return new PageUtils(page);
	}

	@Override
	public void save(CameraroomEntity cameraroomEntity) {
		this.insert(cameraroomEntity);
	}
	@Override

	public void update(CameraroomEntity cameraroomEntity)
	{
		this.updateAllColumnById(cameraroomEntity);
	}
	@Override
	public void deleteBatch(Long[] ids) {
		this.deleteBatchIds(Arrays.asList(ids));
	}
}
