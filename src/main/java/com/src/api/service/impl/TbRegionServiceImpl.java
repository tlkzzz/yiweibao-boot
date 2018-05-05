package com.src.api.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.src.common.base.entity.PageBean;
import com.src.common.utils.GlobalStatic;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.src.common.base.dao.BaseDao;
import com.src.common.base.service.impl.BaseServiceImpl;
import com.src.api.dao.TbRegionDao;
import com.src.api.dao.TbRegionDetailDao;
import com.src.api.entity.TbCompany;
import com.src.api.entity.TbRegion;
import com.src.api.entity.TbRegionDetail;
import com.src.api.service.TbRegionService;

@Service
public class TbRegionServiceImpl extends BaseServiceImpl<TbRegion, Long>
implements TbRegionService{
	@Autowired
	TbRegionDao tbRegionDao;
	@Autowired
	TbRegionDetailDao tbRegionDetailDao;

	@Override
	public BaseDao<TbRegion, Long> getGenericDao() {
		// TODO Auto-generated method stub
		return tbRegionDao;
	}


	@Override
	public Map<String, Object> getList() {
		String sql = "SELECT tr_id ,tr_name ,tr_freight ,tr_status ,"
				+ "(SELECT GROUP_CONCAT((SELECT cname FROM china_city WHERE cid = trd_city_id) SEPARATOR '、') FROM tb_region_detail WHERE trd_region_id = tr_id) region FROM tb_region WHERE tr_status<>0 order by tr_addtime desc";
		PageBean<Map<String, Object>> pageBean = new PageBean<Map<String, Object>>(1, 10);
		pageBean = tbRegionDao.searchForMap(sql, null, pageBean);
		Map<String, Object> map = new HashMap<>();
		map.put("list", pageBean.getList());
		map.put("count", pageBean.getRowCount());
		map.put("rows", pageBean.getPageCount());
		return map;
	}


	@Override
	@Transactional
	public void addRegionAndDetail(TbRegion tbRegion, List<TbRegionDetail> tbRegionDetailList) {
		tbRegionDao.save(tbRegion);
		for (TbRegionDetail tbRegionDetail : tbRegionDetailList) {
			tbRegionDetail.setTrdRegionId(tbRegion.getTrId());
			tbRegionDetailDao.save(tbRegionDetail);
		}
		
	}


	@Override
	@Transactional
	public void upRegionAndDetail(TbRegion tbRegion, List<TbRegionDetail> tbRegionDetailList) {
		tbRegionDao.update(tbRegion);
		tbRegionDetailDao.execute("delete from tb_region_detail where trd_region_id = "+tbRegion.getTrId());
		for (TbRegionDetail tbRegionDetail : tbRegionDetailList) {
//			TbRegionDetail tbRegionDetailOne = new TbRegionDetail();
//			tbRegionDetailOne.setTrdProviceId(tbRegionDetail.getTrdProviceId());
//			tbRegionDetailOne.setTrdCityId(tbRegionDetail.getTrdCityId());
//			tbRegionDetailOne = tbRegionDetailDao.searchOne(tbRegionDetailOne);
//			if(tbRegionDetailOne==null){
				tbRegionDetail.setTrdRegionId(tbRegion.getTrId());
				tbRegionDetailDao.save(tbRegionDetail);
//			}
		}
		
	}


	@Override
	public Map<String, Object> getByParam(Map<String, String> param, HttpSession session) {
		String trName = param.get("trName");
		String trStatus = param.get("trStatus");
		String page = param.get("page");
		TbCompany tbCompany = (TbCompany)session.getAttribute(GlobalStatic.DEFAULT_LOGIN_SESSION_NAME);//当前登录人
		Integer nowPage = 1;
		
		StringBuffer strSql = new StringBuffer();
		strSql.append("SELECT ");
		strSql.append("tr_id,");//运费区域id
		strSql.append("tr_name,");//区域名称
		strSql.append("tr_freight,");//运费
		strSql.append("tr_status,");//状态
		strSql.append("(SELECT GROUP_CONCAT((SELECT cname FROM china_city WHERE cid = trd_city_id) SEPARATOR '、') FROM tb_region_detail WHERE trd_region_id = tr_id) region ");//区域
		strSql.append("FROM tb_region WHERE tr_adduser = "+tbCompany.getTcId()+" AND tr_status<>0 ");
		if(!StringUtils.isBlank(trName)){
			strSql.append("AND tr_name LIKE '%"+trName+"%' ");
		}
		if(!StringUtils.isBlank(trStatus)){
			strSql.append("AND tr_status = "+trStatus+" ");
		}
		if(!StringUtils.isBlank(page)){
			nowPage = Integer.valueOf(page);
			
		}
		if(StringUtils.isBlank(page)||Integer.valueOf(page)<1){
			nowPage = 1;
		}
		
		strSql.append("ORDER BY tr_addtime DESC "); 
		
		PageBean<Map<String, Object>> pageBean = new PageBean<Map<String, Object>>(nowPage, 10);
		pageBean = tbRegionDao.searchForMap(strSql.toString(), null, pageBean);
		Map<String, Object> map = new HashMap<>();
		for (Map<String, Object> m : pageBean.getList()) {
			m.put("tr_freight", String.format("%.2f", m.get("tr_freight")));
		}
		map.put("list", pageBean.getList());//数据列表
		map.put("rows", pageBean.getPageCount());//总页数
		map.put("rowcount", pageBean.getRowCount());//符合条件的记录数
		map.put("nowPage", nowPage);//当前页
		return map;
	}


	@Override
	public TbRegion findOne(TbRegion tbRegionOne) {
		return tbRegionDao.searchOne(tbRegionOne);
	}
}
