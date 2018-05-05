package com.src.api.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.src.common.base.dao.BaseDao;
import com.src.common.base.service.impl.BaseServiceImpl;
import com.src.api.dao.TbMachineDetailsDao;
import com.src.api.entity.TbMachineDetails;
import com.src.api.service.TbMachineDetailsService;

@Service("tbMachineDetailsService")
public class TbMachineDetailsServiceImpl extends BaseServiceImpl<TbMachineDetails, Long>
		implements TbMachineDetailsService {

	@Resource
	TbMachineDetailsDao tbMachineDetailsDao;

	@Override
	public BaseDao<TbMachineDetails, Long> getGenericDao() {
		// TODO Auto-generated method stub
		return tbMachineDetailsDao;
	}

	@Override
	public TbMachineDetails getMachineById(long id) {
		String sql = "select * from  tb_machine_details where tmd_machine_id = " + id;
		List<TbMachineDetails> list = tbMachineDetailsDao.search(sql, null);
		if(list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return new TbMachineDetails();
		}
	}
}
