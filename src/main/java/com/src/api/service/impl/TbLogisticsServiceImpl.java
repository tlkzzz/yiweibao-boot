package com.src.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.src.common.base.dao.BaseDao;
import com.src.common.base.service.impl.BaseServiceImpl;
import com.src.api.dao.TbLogisticsDao;
import com.src.api.dao.TbOrderDao;
import com.src.api.entity.TbLogistics;
import com.src.api.entity.TbOrderHead;
import com.src.api.service.TbLogisticsService;

@Service
public class TbLogisticsServiceImpl extends BaseServiceImpl<TbLogistics, Long>
implements TbLogisticsService{

	@Autowired
	TbLogisticsDao tbLogisticsDao;
	@Autowired
	TbOrderDao tbOrderDao;
	@Override
	public BaseDao<TbLogistics, Long> getGenericDao() {
		
		return tbLogisticsDao;
	}
	
	
	@Override
	@Transactional
	public void upLogisticsAndOrder(TbLogistics tbLogistics, TbOrderHead tbOrder)throws Exception {
		
		tbLogisticsDao.save(tbLogistics);
		tbOrderDao.update(tbOrder);
	}
}
