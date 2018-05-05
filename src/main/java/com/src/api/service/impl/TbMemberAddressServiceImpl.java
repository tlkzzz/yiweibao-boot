package com.src.api.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.src.common.base.dao.BaseDao;
import com.src.common.base.service.impl.BaseServiceImpl;
import com.src.api.dao.TbMemberAddressDao;
import com.src.api.entity.TbAddress;
import com.src.api.service.TbMemberAddressService;

@Service
public class TbMemberAddressServiceImpl extends BaseServiceImpl<TbAddress, Long>
implements TbMemberAddressService{

	@Autowired
	TbMemberAddressDao tbMemberAddressDao;
	
	@Override
	public BaseDao<TbAddress, Long> getGenericDao() {
		// TODO Auto-generated method stub
		return tbMemberAddressDao;
	}

	@Override
	public List<Map<String, Object>> findByStatus(Long tmId) {
		String sql = "SELECT tma_id AS tmaId,tma_name AS tmaName, "
				+ "tma_phone AS tmaPhone,tma_postcode AS tmaPostCode, "
				+ "tma_provice_id AS tmaProviceId,p.pname as provice, "
				+ "tma_city_id AS tmaCityId,c.cname as city, "
				+ "tma_county_id as tmaCountId,o.oname as county ,tma_address as tmaAddress,tma_default AS tmaDefault "
				+ "FROM tb_address ,china_province AS p , "
				+ "china_county as o,china_city as c  WHERE "
				+ " tma_provice_id = p.pid AND tma_city_id = c.cid AND "
				+ " tma_county_id=o.oid and  tma_status = 1 and tma_member_id="+tmId
				+ " ORDER BY tma_default DESC";
		return tbMemberAddressDao.searchForMap(sql, null);
	}

	@Override
	public List<Map<String, Object>> getById(Long tmaId) {
		String sql = "SELECT tma_id AS tmaId,tma_name AS tmaName, "
				+ "tma_phone AS tmaPhone,tma_postcode AS tmaPostCode,"
				+ "tma_address as tmaAddress,tma_default as tmaDefault, "
				+ "tma_provice_id AS tmaProviceId,p.pname as provice, "
				+ "tma_city_id AS tmaCityId,c.cname as city, "
				+ "tma_county_id as tmaCountId,o.oname as county "
				+ "FROM tb_address ,china_province AS p , "
				+ "china_county as o,china_city as c WHERE tma_provice_id=p.pid "
				+ "and tma_city_id = c.cid and tma_county_id =o.oid and  tma_id="+tmaId;
		return tbMemberAddressDao.searchForMap(sql, null);
	}

	@Override
	@Transactional
	public void upAddressDefault(TbAddress tbMemberAddress) {
		String  sql = "UPDATE tb_address SET tma_default = 0 WHERE tma_default = 1";
		tbMemberAddressDao.execute(sql);
		if(tbMemberAddress.getTmaId()==null){
			tbMemberAddressDao.save(tbMemberAddress);
		}else{
			tbMemberAddressDao.update(tbMemberAddress);
		}
		
	}

	@Override
	public void updateAddressDefault(TbAddress tbMemberAddress)
			throws Exception {
		String  sql = "UPDATE tb_address SET tma_default = 0 WHERE tma_default = 1 AND tma_member_id=? AND tma_status=1";
		List<Object> values = new ArrayList<Object>();
		values.add(tbMemberAddress.getTmaMemberId());
		tbMemberAddressDao.update(sql, values);
		tbMemberAddress.setTmaDefault(1);//0:不是默认地址 1:默认地址
		tbMemberAddressDao.update(tbMemberAddress);
	}
}
