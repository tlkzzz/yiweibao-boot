package com.src.api.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.src.common.base.entity.PageBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.src.common.base.dao.BaseDao;
import com.src.common.base.service.impl.BaseServiceImpl;
import com.src.api.dao.TbSpecDao;
import com.src.api.dao.TbSpecValueDao;
import com.src.api.entity.TbSpec;
import com.src.api.entity.TbSpecValue;
import com.src.api.service.TbSpecService;

@Service
public class TbSpecServiceImpl extends BaseServiceImpl<TbSpec, Long>
implements TbSpecService{

	@Autowired
	TbSpecDao tbSpecDao;
	@Autowired
	TbSpecValueDao tbSpecValueDao;
	
	@Override
	public BaseDao<TbSpec, Long> getGenericDao() {
		// TODO Auto-generated method stub
		return tbSpecDao;
	}

	@Override
	public Map<String, Object> findListByCategory(Long tcId) {
		String sql = "SELECT ts_id ,ts_name ,ts_status ,"
				+ "(SELECT tc_name FROM tb_category WHERE tc_id = ts_category_id) tcName,"
				+ "(SELECT GROUP_CONCAT(tsv_value) FROM tb_spec_value WHERE tsv_spec_id = ts_id) tsvValue "
				+ "FROM tb_spec WHERE ts_status<>0 AND ts_category_id="+tcId+" order by ts_addtime desc";
		PageBean<Map<String, Object>> pageBean = new PageBean<Map<String, Object>>(1, 10);
		pageBean = tbSpecDao.searchForMap(sql, null, pageBean);
		Map<String, Object> map = new HashMap<>();
		map.put("list", pageBean.getList());
		map.put("count", pageBean.getRowCount());
		map.put("rows", pageBean.getPageCount());
		return map;
	}

	@Override
	@Transactional
	public void addSpecAndValue(TbSpec tbSpec, List<TbSpecValue> tbSpecValueList) {
		tbSpecDao.save(tbSpec);
		for (TbSpecValue tbSpecValue : tbSpecValueList) {
			tbSpecValue.setTsvSpecId(tbSpec.getTsId());
			tbSpecValueDao.save(tbSpecValue);
		}
		
	}

	@Override
	public Map<String, Object> findByParam(Map<String, String> param) {
		String tsName = param.get("tsName");
		String tsStatus = param.get("tsStatus");
		String tcId = param.get("tcId");
		String page = param.get("page");//分页
		String companyId = param.get("companyId");
		Integer nowPage = 1;
		StringBuffer sbSql = new StringBuffer("SELECT ts_id ,ts_name ,ts_status ,"
				+ "(SELECT tc_name FROM tb_category WHERE tc_id = ts_category_id) tcName,"
				+ "(SELECT GROUP_CONCAT(tsv_value) FROM tb_spec_value WHERE tsv_spec_id = ts_id) tsvValue "
				+ "FROM tb_spec WHERE ts_status<>0  AND ts_category_id='"+tcId+"' ");
		if(!StringUtils.isBlank(companyId)){
			sbSql.append(" AND ts_adduser ="+companyId+" ");
		}
		if(!StringUtils.isBlank(tsName)){
			sbSql.append(" AND ts_name like '%"+tsName+"%'");
		}
		if(!StringUtils.isBlank(tsStatus)){
			sbSql.append(" AND ts_status="+tsStatus+" ");
		}
		if(!StringUtils.isBlank(page)){
			nowPage = Integer.valueOf(page);
		}
		if(StringUtils.isBlank(page)||Integer.valueOf(page)<1){
			nowPage = 1;
		}
		sbSql.append(" order by ts_addtime desc");
		PageBean<Map<String, Object>> pageBean = new PageBean<Map<String, Object>>(nowPage, 9);
		pageBean = tbSpecDao.searchForMap(sbSql.toString(), null, pageBean);
		Map<String, Object> map = new HashMap<>();
		map.put("list", pageBean.getList());//数据列表
		map.put("rows", pageBean.getPageCount());//总页数
		map.put("rowcount", pageBean.getRowCount());//符合条件的记录数
		map.put("nowPage", nowPage);//当前页
		return map;
	}

	@Override
	public List<Map<String, Object>> getByCategoryId(Long tcId) {
		String sql = "SELECT ts_id tsId,ts_name tsName FROM tb_spec WHERE ts_status = 1 AND ts_category_id ="+tcId;
		return tbSpecDao.searchForMap(sql, null);
	}

	@Override
	public List<TbSpecValue> findValueBytsId(Object object) {
		String sql = "select * from tb_spec_value where tsv_spec_id= "+object;
		return tbSpecValueDao.search(sql, null);
	}

	@Override
	public TbSpec findOne(TbSpec tbSpec) {
		return tbSpecDao.searchOne(tbSpec);
	}

	/* (non-Javadoc)
	 * @see com.src.api.service.TbSpecService#isDel(java.lang.Long)
	 */
	@Override
	public List<Map<String,Object>> findSpecRelated(Long tsId) {
		String sql = "SELECT * FROM tb_product_spec,tb_product WHERE tps_spec_id = "+tsId+" AND tps_product_id = tp_id AND tp_status <> 0";
		return tbSpecDao.searchForMap(sql, null);
		// TODO Auto-generated method stub
	}
}
