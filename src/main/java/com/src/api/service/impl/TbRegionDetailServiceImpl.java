package com.src.api.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.src.common.base.dao.BaseDao;
import com.src.common.base.service.impl.BaseServiceImpl;
import com.src.api.dao.TbRegionDetailDao;
import com.src.api.entity.TbRegionDetail;
import com.src.api.service.TbRegionDetailService;

@Service
public class TbRegionDetailServiceImpl extends BaseServiceImpl<TbRegionDetail, Long>
implements TbRegionDetailService{

	@Autowired
	TbRegionDetailDao tbRegionDetailDao;
	
	@Override
	public BaseDao<TbRegionDetail, Long> getGenericDao() {
		// TODO Auto-generated method stub
		return tbRegionDetailDao;
	}

	@Override
	public List<Map<String, Object>> getByTrId(Long trId) {
		String sql = "SELECT trd_city_id cityId,"
				+ "(SELECT cname FROM  china_city WHERE cid = trd_city_id) cityName "
				+ "FROM tb_region_detail WHERE trd_region_id = "+trId;
		return tbRegionDetailDao.searchForMap(sql, null);
	}

	@Override
	public void delBytrId(Long trId) {
		String sql = "delete from tb_region_detail where trd_region_id = "+trId;
		tbRegionDetailDao.execute(sql);
		
	}

	@Override
	public TbRegionDetail findOne(TbRegionDetail tbRegionDetailOne) {
		// TODO Auto-generated method stub
		return tbRegionDetailDao.searchOne(tbRegionDetailOne);
	}

	@Override
	public List<TbRegionDetail> findByCompanyAndCity(Integer valueOf, Long tcId) {
		String sql = "SELECT * FROM tb_region_detail WHERE trd_city_id = "+valueOf+" "
				+ "AND trd_region_id IN (SELECT tr_id FROM tb_region WHERE tr_status= 1 AND tr_adduser = "+tcId+")";
		return tbRegionDetailDao.search(sql, null);
	}
}
