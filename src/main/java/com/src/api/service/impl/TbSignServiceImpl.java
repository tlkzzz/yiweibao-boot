package com.src.api.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.src.api.dao.TbSignDao;
import com.src.api.entity.TbSign;
import com.src.api.service.TbSignService;
import com.src.common.base.entity.PageBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.src.common.base.dao.BaseDao;
import com.src.common.base.service.impl.BaseServiceImpl;

@Service
public class TbSignServiceImpl  extends BaseServiceImpl<TbSign, Long>implements TbSignService {

	@Autowired
	TbSignDao tbSignDao;
	
	@Override
	public BaseDao<TbSign, Long> getGenericDao() {
		// TODO Auto-generated method stub
		return tbSignDao;
	}
	@Override
	public List<Map<String, Object>> getDateTseId(String tseId, String date) {
		// TODO Auto-generated method stub
		String sql ="SELECT * FROM (SELECT p.tsp_id AS tspId, "
				+ "p.tsp_name AS tpsName,c.tc_name AS compnyName FROM tb_service_points AS p LEFT JOIN  tb_company AS c ON p.tsp_company_id = c.tc_id) AS a LEFT JOIN"
				+ "(SELECT e.tse_id AS tseId,e.tse_name AS tesName, e.tse_photo AS tesPhoto,e.tse_unitid AS tseUnitid,s.* FROM tb_service_employee e LEFT JOIN tb_sign s ON e.tse_id=s.tse_id  WHERE e.tse_status=1) AS b ON a.tspId =b.tseUnitid "
				+ "WHERE b.tseId ="+tseId;
		if(!StringUtils.isBlank(date)){  
			sql +=" AND b.sign_date = '"+date+"'";
		} 
		return tbSignDao.searchForMap(sql, null);
	}
	@Override
	public TbSign add(TbSign t) {
		// TODO Auto-generated method stub
		return tbSignDao.save(t);
	}
	@Override
	public TbSign edit(TbSign t) {
		// TODO Auto-generated method stub
		return tbSignDao.update(t);
	}
	@Override
	public List<TbSign> findByTseidANDSignDate(String tseId,String date,String pointsId) {
		// TODO Auto-generated method stub
		String sql ="SELECT * FROM tb_sign WHERE  tse_id="+tseId+" AND sign_date= '"+date+"' AND points_id="+ pointsId;
		
		return tbSignDao.search(sql, null);
	}
	@Override
	public List<Map<String, Object>> getCount(String startTime, String endTime, String tseId, String pointsId,int type) {
		// TODO Auto-generated method stub
		String  sql ="SELECT * FROM tb_sign WHERE  tse_id="+tseId+" AND points_id="+pointsId+" AND  sign_date BETWEEN '"+startTime+"' AND '"+endTime+"' ";
		if(type==1){
			
		}else if(type==2){
			sql +=" AND am_is_sign=1 AND pm_is_sign =1 ";
		}else if(type==3){
			sql += "AND  am_is_sign =0 OR pm_is_sign =0 ";
		}else if(type==4){
			sql += "AND am_is_sign=2 OR pm_is_sign=2 ";
		}
		
		return tbSignDao.searchForMap(sql, null);
	}

	@Override
	public Map<String, Object> getByParam(Map<String, String> param) {
		String name =param.get("name");
		String startTime =param.get("startTime");
		String endTime =param.get("endTime");
		String page = param.get("page");
		String companyId = param.get("companyId");
		Integer nowPage = 1;

		StringBuffer strSql = new StringBuffer();
		strSql.append("\n" +
				"SELECT * FROM tb_sign s LEFT JOIN \n" +
				"\n" +
				"(SELECT e.tse_name AS pName ,e.tse_id  AS pId  FROM  " +
				"`tb_service_points` p LEFT JOIN  `tb_service_employee` e ON p.tsp_id= e.tse_unitid" +
				" WHERE p.tsp_company_id='"+companyId+"' AND p.tsp_status='1' AND e.tse_status='1') AS t  ON s.tse_id= t.pId WHERE 1=1 ");
		if(!StringUtils.isBlank(name)){
			strSql.append(" AND  t.pName LIKE '%"+name+"%' ");
		}
		if(!StringUtils.isBlank(startTime) &&!StringUtils.isBlank(endTime)){
			strSql.append(" AND s.sign_date  BETWEEN  '"+startTime+"'  AND '"+endTime+ "'");
		}

//		if(!StringUtil.isBlank(tspId)){
//			strSql.append("AND a.service_points_id = "+tspId+" ");
//		}
		if(!StringUtils.isBlank(page)){
			nowPage = Integer.valueOf(page);
		}
		if(StringUtils.isBlank(page)||Integer.valueOf(page)<1){//分页为空或小于1
			nowPage = 1;
		}
		strSql.append(" ORDER BY s.sign_date DESC ");
		PageBean<Map<String, Object>> pageBean = new PageBean<Map<String, Object>>(nowPage, 10);//每页十条数据
		pageBean = tbSignDao.searchForMap(strSql.toString(), null, pageBean);
		Map<String, Object> map = new HashMap<>();
		map.put("list", pageBean.getList());//数据列表
		map.put("rows", pageBean.getPageCount());//总页数
		map.put("rowcount", pageBean.getRowCount());//符合条件的记录数
		map.put("nowPage",nowPage);//分页
		return map;
	}


}
