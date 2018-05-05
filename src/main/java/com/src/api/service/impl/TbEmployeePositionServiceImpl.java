package com.src.api.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.src.common.base.dao.BaseDao;
import com.src.common.base.service.impl.BaseServiceImpl;
import com.src.api.dao.TbEmployeePositionDao;
import com.src.api.entity.TbEmployeePosition;
import com.src.api.service.TbEmployeePositionService;

@Service("tbEmployeePositionService")
public class TbEmployeePositionServiceImpl extends BaseServiceImpl<TbEmployeePosition, Long>
		implements TbEmployeePositionService {

	@Resource
	TbEmployeePositionDao tbEmployeePositionDao;

	@Override
	public BaseDao<TbEmployeePosition, Long> getGenericDao() {
		// TODO Auto-generated method stub
		return tbEmployeePositionDao;
	}
}
