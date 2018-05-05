package com.src.api.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.src.common.base.entity.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.src.common.base.dao.BaseDao;
import com.src.common.base.service.impl.BaseServiceImpl;
import com.src.api.dao.TbCategoryDao;
import com.src.api.entity.TbCategory;
import com.src.api.service.TbCategoryService;

@Service
public class TbCategoryServiceImpl extends BaseServiceImpl<TbCategory, Long>
implements TbCategoryService{
	
	@Autowired
	TbCategoryDao tbCategoryDao;
	
	@Override
	public BaseDao<TbCategory, Long> getGenericDao() {
		// TODO Auto-generated method stub
		return tbCategoryDao;
	}

	@Override
	public List<TbCategory> findList(Long tcId) {
		String sql = "SELECT * from tb_category where tc_status =1 and tc_parent_id = 0 and tc_type = 1 and tc_adduser="+tcId+"";
		return tbCategoryDao.search(sql, null);
	}

	@Override
	public List<TbCategory> getAll(Long tcId) {
		String sql = "SELECT * FROM tb_category WHERE tc_status = 1 AND tc_type=1 AND tc_adduser = "+tcId+"";
		return tbCategoryDao.search(sql, null);
	}

	@Override
	public List<TbCategory> getLimit(Integer categoryPage, String tcId) {
		String sql  = "SELECT * FROM tb_category WHERE tc_status = 1 AND tc_type=1 AND tc_adduser = "+tcId+" limit "+(categoryPage-1)*13+",13";
		return tbCategoryDao.search(sql, null);
	}
	
	@Override
	public HashMap<String, Object> list_order_product(HashMap<String, String> params){
		HashMap<String, Object> json = new HashMap<String, Object>();
		
		int page = params.get("page") == null ? 0:Integer.parseInt(params.get("page"));
		int pageSize = params.get("pageSize") == null ? 0:Integer.parseInt(params.get("pageSize"));
		
		
		
		String strOrderNumber = params.get("ordernumber")==null?"":params.get("ordernumber"); //订单编号
		String strDeviceNum=params.get("device_num")==null?"":params.get("device_num");
		String strCustomerNum = params.get("memo_num")==null?"":params.get("memo_num");
		String strCustomerId =  params.get("customer_id")==null?"":params.get("customer_id");
		String strProductName = params.get("productname")==null?"":params.get("productname");
		
		String strOrderId = params.get("orderid")==null?"":params.get("orderid"); //订单id
	
		
		StringBuffer sbSql = new StringBuffer();
		
		sbSql.append(" select tod_id, b.tc_name,tp_name,a.tc_name customer_name,toh_number,tod_product_id,tod_product_code,tod_customer_code,tod_price,DATE_FORMAT(tod_add_date,'%Y-%m-%d %H:%i:%s') tod_add_date  ");
		
		sbSql.append(" from tb_order_detail,tb_order_head,tb_customers a,tb_product,tb_category b ");
		sbSql.append(" where tod_head_id = toh_id  and toh_customer_id = a.tc_id and tp_id = tod_product_id  and  tp_category_id= b.tc_id ");
	//	sbSql.append(" and toh_status > 4"); //至少是已收货
		sbSql.append(" and tod_type = 1 "); //至少是已收货
		
		
		if(!strCustomerId.equals(""))
			sbSql.append(" and  toh_customer_id =" +strCustomerId ); //至少是已收货
		
		if(!strCustomerNum.equals(""))
			sbSql.append(" and  tod_customer_code LIKE '%" +strCustomerNum +"%' " ); //至少是已收货
		
		
		if(!strDeviceNum.equals(""))
			sbSql.append(" and  tod_product_code LIKE '%" +strDeviceNum +"%' " ); //至少是已收货
		
		if(!strOrderNumber.equals(""))
			sbSql.append(" and  toh_number ='" +strOrderNumber +"' " ); //至少是已收货
		
		if(!strOrderId.equals(""))
			sbSql.append(" and  toh_id ='" +strOrderId +"' " ); //至少是已收货
			
		
		if(!strProductName.equals(""))
			sbSql.append(" and  tp_name like '%" +strProductName +"%' " ); //至少是已收货
			
		
		
		if (pageSize == 0) {
			List<Map<String, Object>> list = tbCategoryDao.searchForMap(sbSql.toString(), null);
			json.put("total", list.size());
			json.put("rows", list);
			return json;
		}else {
			PageBean<Map<String, Object>> pageBean = new PageBean<Map<String, Object>>(page, pageSize);
			pageBean = tbCategoryDao.searchForMap(sbSql.toString(), null, pageBean);
			
			json.put("total", pageBean.getRowCount());
			json.put("rows", pageBean.getList());
			return json;
		}
	}

	@Override
	public TbCategory findOne(TbCategory tbCategoryOne) {
		// TODO Auto-generated method stub
		return tbCategoryDao.searchOne(tbCategoryOne);
	}
}
