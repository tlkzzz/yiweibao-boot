package com.src.api.service.impl;


import com.src.common.base.dao.BaseDao;
import com.src.common.base.entity.PageBean;
import com.src.common.base.service.impl.BaseServiceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.src.api.dao.TbSettingDao;
import com.src.api.entity.TbSetting;
import com.src.api.service.TbSettingService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("tbSettingService")
public class TbSettingServiceImpl extends BaseServiceImpl<TbSetting, Long> implements TbSettingService {

	@Autowired
	TbSettingDao tbSettingDao;
	@Override
	public BaseDao<TbSetting, Long> getGenericDao() {
		return tbSettingDao;
	}
	@Override
	public List<TbSetting> findByCompanyId(Long companyId,Long cus) {
		String sql = "SELECT * FROM tb_setting WHERE ts_company_id="+companyId+" AND ts_points="+cus;
		// TODO Auto-generated method stub
		return tbSettingDao.search(sql, new ArrayList<Object>());
	}

	@Override
	public Map<String, Object> getByParam(Map<String, String> param) {
		String tspId = param.get("tspId");
		String page = param.get("page");
		String companyId = param.get("companyId");
		Integer nowPage = 1;

		StringBuffer strSql = new StringBuffer();
		strSql.append("SELECT a.* ,s.tsp_name AS pointsName FROM tb_setting a LEFT JOIN tb_service_points s ON a.ts_points=s.tsp_id WHERE ");
		strSql.append(" a.ts_company_id='"+companyId+"' ");
		if(!StringUtils.isBlank(tspId)){
			strSql.append("AND a.ts_points = "+tspId+" ");
		}
		if(!StringUtils.isBlank(page)){
			nowPage = Integer.valueOf(page);
		}
		if(StringUtils.isBlank(page)||Integer.valueOf(page)<1){//分页为空或小于1
			nowPage = 1;
		}
		strSql.append("ORDER BY a.ts_id DESC ");
		PageBean<Map<String, Object>> pageBean = new PageBean<Map<String, Object>>(nowPage, 10);//每页十条数据
		pageBean = tbSettingDao.searchForMap(strSql.toString(), null, pageBean);
		Map<String, Object> map = new HashMap<>();
		map.put("list", pageBean.getList());//数据列表
		map.put("rows", pageBean.getPageCount());//总页数
		map.put("rowcount", pageBean.getRowCount());//符合条件的记录数
		map.put("nowPage",nowPage);//分页
		return map;
	}

}
