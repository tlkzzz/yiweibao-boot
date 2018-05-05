package com.src.api.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.src.api.dao.TbQrcodeDao;
import com.src.api.entity.TbQrcode;
import com.src.api.service.TbQrcodeService;
import com.src.common.base.entity.PageBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.src.common.base.dao.BaseDao;
import com.src.common.base.service.impl.BaseServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Service
public class TbQrcodeServiceImpl  extends BaseServiceImpl<TbQrcode, Long> implements TbQrcodeService {

	@Autowired
	TbQrcodeDao tbQrcodeDao;
	
	
	
	@Override
	public BaseDao<TbQrcode, Long> getGenericDao() {
		// TODO Auto-generated method stub
		return tbQrcodeDao;
	}


	@Override
	public List<TbQrcode> search(TbQrcode t) {
		// TODO Auto-generated method stub
		return this.tbQrcodeDao.search(t);
	}



	@Override
	public TbQrcode add(TbQrcode t) {
		// TODO Auto-generated method stub
		return this.tbQrcodeDao.save(t);
	}



	@Override
	public TbQrcode edit(TbQrcode t) {
		// TODO Auto-generated method stub
		return this.tbQrcodeDao.update(t);
	}



	@Override
	public int del(Long id) {
		// TODO Auto-generated method stub
		return this.tbQrcodeDao.del(id);
	}


	@Override
	public Map<String, Object> getByParam(Map<String, String> param) {
		String type = param.get("type");
		String name =param.get("tpName");
		String companyId = param.get("companyId");
		int page = param.get("page") == null ? 0:Integer.parseInt(param.get("page"));
		int pageSize = param.get("pageSize") == null ? 0:Integer.parseInt(param.get("pageSize"));

		StringBuffer strSql = new StringBuffer();
		strSql.append("SELECT q.* FROM  tb_qrcode q WHERE q.status='1' ");
		strSql.append(" AND q.company_id='"+companyId+"' ");
		if(!StringUtils.isBlank(name)){
			strSql.append(" AND  q.qrcodenum LIKE '%"+name+"%' ");
		}
		if(!StringUtils.isBlank(type)){
			strSql.append(" AND q.sourcetype = '"+type+"' ");
		}

		strSql.append("ORDER BY q.create_date DESC ");
		PageBean<Map<String, Object>> pageBean = new PageBean<Map<String, Object>>(page, 10);//每页十条数据
		pageBean = tbQrcodeDao.searchForMap(strSql.toString(), null, pageBean);
		Map<String, Object> map = new HashMap<>();
		List<Map<String,Object>>	ab = pageBean.getList();
		map.put("list", pageBean.getList());//数据列表
		map.put("rows", pageBean.getPageCount());//总页数
		map.put("rowcount", pageBean.getRowCount());//符合条件的记录数
		map.put("nowPage",page);//分页
		return map;
	}





	
	
	
}
