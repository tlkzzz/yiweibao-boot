package com.src.api.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.src.common.base.dao.BaseDao;
import com.src.common.base.service.impl.BaseServiceImpl;
import com.src.api.dao.TbCompanySetDao;
import com.src.api.entity.TbCompanySet;
import com.src.api.service.TbCompanySetService;

@Transactional
@Service("tbCompanySetService")
public class TbCompanySetServiceImpl extends BaseServiceImpl<TbCompanySet, Long> implements TbCompanySetService{
	
	@Resource
	TbCompanySetDao tbCompanySetDao;
	
	@Override
	public BaseDao<TbCompanySet, Long> getGenericDao() {
		return tbCompanySetDao;
	}

	@Override
	public TbCompanySet findByCompanyid(String id){
		StringBuffer sql=new StringBuffer();
		sql.append(" SELECT * FROM tb_company_set WHERE tcs_company_id="+id);
		List<TbCompanySet> list=tbCompanySetDao.search(sql.toString(),null);
		if(list.size()>0){
			return list.get(0);
		}else{
			return new TbCompanySet();
		}
	}
	
	
}
