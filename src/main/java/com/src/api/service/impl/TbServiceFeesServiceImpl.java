package com.src.api.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.src.common.base.entity.PageBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.src.common.base.dao.BaseDao;
import com.src.common.base.service.impl.BaseServiceImpl;
import com.src.api.dao.TbServiceFeesDao;
import com.src.api.entity.TbServiceFees;
import com.src.api.service.TbServiceFeesService;

@Service
public class TbServiceFeesServiceImpl extends BaseServiceImpl<TbServiceFees, Long>
implements TbServiceFeesService{
	
	@Autowired
	TbServiceFeesDao tbServiceFeesDao;
	
	@Override
	public BaseDao<TbServiceFees, Long> getGenericDao() {
		// TODO Auto-generated method stub
		return tbServiceFeesDao;
	}

	@Override
	public Map<String, Object> getByCompanyId(Long tcId) {
		StringBuffer sbSql = new StringBuffer();
		sbSql.append("SELECT tsf_id,tsf_name,");
		sbSql.append("CONCAT('保期内(',CAST(tsf_in_service AS CHAR),'),保期外(',CAST(tsf_out_service AS CHAR),')') price,");
		sbSql.append("DATE_FORMAT(tsf_add_date,'%Y-%m-%d %H:%i:%s') addDate,tsf_status ");
		sbSql.append("FROM tb_service_fees WHERE tsf_status <> 0 AND tsf_company_id = "+tcId+" ");
		sbSql.append("ORDER BY tsf_add_date desc ");
		PageBean<Map<String, Object>> pageBean = new PageBean<Map<String, Object>>(1, 10);
		pageBean = tbServiceFeesDao.searchForMap(sbSql.toString(), null, pageBean);
		Map<String, Object> map = new HashMap<>();
		map.put("list", pageBean.getList());
		map.put("count", pageBean.getRowCount());
		map.put("rows", pageBean.getPageCount());
		return map;
	}

	@Override
	public List<Map<String, Object>> findByCompany(Long tcId) {
		StringBuffer sbSql = new StringBuffer();
		sbSql.append("SELECT tsf_id tsfId,");
		sbSql.append("right(CONCAT(tsf_name,'(',CAST(tsf_month AS CHAR),'个月）'),14) tsfNameAndMonth");
		sbSql.append(" from tb_service_fees WHERE tsf_status = 1 ");
		sbSql.append("AND tsf_company_id = "+tcId);
		return tbServiceFeesDao.searchForMap(sbSql.toString(), null);
	}

	@Override
	public Map<String, Object> getByParam(Map<String, String> param) {
		String companyId = param.get("companyId");
		String tsfName = param.get("tsfName");
		String tsfStatus = param.get("tsfStatus");
		int page = param.get("page") == null ? 0 : Integer.parseInt(param.get("page"));
		int pageSize = param.get("pageSize") == null ? 0 : Integer.parseInt(param.get("pageSize"));


		StringBuffer sbSql = new StringBuffer();
		sbSql.append("SELECT tsf_id,tsf_name,");
		sbSql.append("CONCAT('保修费用(',CAST(tsf_out_service AS CHAR),')') as tsf_price,");//tsf_in_service字段需转为String类型，虽然有些环境下可以不转，但是有些环境不转会报错
		sbSql.append("DATE_FORMAT(tsf_add_date,'%Y-%m-%d %H:%i:%s') addDate,tsf_status ");
		sbSql.append("FROM tb_service_fees WHERE tsf_status <> 0 AND tsf_company_id = "+companyId+" ");
		if(!StringUtils.isBlank(tsfName)){
			sbSql.append(" and tsf_name like '%"+tsfName+"%'");
		}
		if(!StringUtils.isBlank(tsfStatus)){
			sbSql.append(" and tsf_status ="+tsfStatus);
		}

		
		sbSql.append(" ORDER BY tsf_add_date desc"); 
		
		PageBean<Map<String, Object>> pageBean = new PageBean<Map<String, Object>>(page, 10);
		pageBean = tbServiceFeesDao.searchForMap(sbSql.toString(), null, pageBean);
		Map<String, Object> map = new HashMap<>();
		map.put("list", pageBean.getList());//数据列表
		map.put("rows", pageBean.getPageCount());//总页数
		map.put("rowcount", pageBean.getRowCount());//符合条件的记录数
		map.put("nowPage", page);//当前页
		return map;
	}

	@Override
	public TbServiceFees findOne(TbServiceFees tbServiceFeesOne) {
		// TODO Auto-generated method stub
		return tbServiceFeesDao.searchOne(tbServiceFeesOne);
	}
	@Override
	public TbServiceFees findByNameAndFeesId(String tsfName, String feeId, String companyId) {
		StringBuffer sbSql = new StringBuffer();
		sbSql.append("SELECT * FROM tb_service_fees ");
		sbSql.append("WHERE 1=1 ");
		List<Object> values = new ArrayList<Object>();
		if(!StringUtils.isBlank(tsfName)){
			sbSql.append("AND tsf_name = ? AND tsf_status <> 0 ");//验证状态不为删除
			values.add(tsfName);
		}
		if(!StringUtils.isBlank(feeId)){
			sbSql.append("AND tsf_id != ? ");
			values.add(feeId);
		}
		if(!StringUtils.isBlank(companyId)){
			sbSql.append("AND tsf_company_id = ? ");
			values.add(companyId);
		}
		List<TbServiceFees> list = tbServiceFeesDao.search(sbSql.toString(), values);
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.src.api.service.TbServiceFeesService#findRelate(java.lang.String)
	 */
	@Override
	public List<Map<String, Object>> findRelate(String tsfIds) {
		// TODO Auto-generated method stub
		String sql="SELECT * FROM tb_service_fees WHERE tsf_id IN(SELECT tp_fees_ids FROM tb_product WHERE tp_status<>0) AND tsf_id ="+tsfIds;
		return tbServiceFeesDao.searchForMap(sql.toString(), null);
	}
}
