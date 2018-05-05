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
import com.src.api.dao.TbOrderDetailDao;
import com.src.api.entity.TbOrderDetail;
import com.src.api.service.TbOrderDetailService;

@Service("tbOrderDetailService")
public class TbOrderDetailServiceImpl extends BaseServiceImpl<TbOrderDetail, Long>implements TbOrderDetailService{
	@Autowired
	TbOrderDetailDao tbOrderDetailDao;

	@Override
	public BaseDao<TbOrderDetail, Long> getGenericDao() {
		return tbOrderDetailDao;
	}

	@Override
	public List<Map<String, Object>> findProductByOrder(String memberId,
			String page) {
		StringBuffer sbSql = new StringBuffer();
		sbSql.append("SELECT d.tod_id,d.tod_head_id,d.tod_product_id,d.tod_count,");
		sbSql.append("d.tod_price,d.tod_add_date,d.tod_end_time,d.tod_customer_id,");
		sbSql.append("o.toh_id,o.toh_number,o.toh_type,o.toh_company_id,o.toh_amount,");
//		sbSql.append("o.toh_buy_date,");
		sbSql.append("o.tho_memo,o.toh_add_date,o.toh_add_person,o.toh_status,o.toh_contanct_person,");
		sbSql.append("o.toh_contact_phone,o.toh_address,");
		sbSql.append("(SELECT GROUP_CONCAT(ps.tps_spec_value SEPARATOR ' ') FROM tb_product_spec ps");
		sbSql.append(",tb_order_detail od1 WHERE ps.tps_sku_id=od1.tod_sku_id AND ");
		sbSql.append("od1.tod_head_id=o.toh_id AND od1.tod_type=1) specValue,");
		sbSql.append("(SELECT p.pname FROM china_province p WHERE o.toh_prov_id=p.pid) as tcProv,");
		sbSql.append("(SELECT y.cname FROM china_city y WHERE y.cid=o.toh_city_id) as tcCity,");
//		sbSql.append("(SELECT u.oname FROM china_county u WHERE u.oid=c.tc_region_id) as tcRegion,");
		sbSql.append("(SELECT gc.tc_name FROM tb_category gc WHERE gc.tc_id=p.tp_category_id) as categoryName,");
		sbSql.append("p.tp_name,p.tp_warranty,p.tp_logo,p.tp_pics,p.tp_category_id ");
		sbSql.append("FROM tb_order_head o,tb_order_detail d ");
		sbSql.append(" LEFT JOIN  ON d.tod_product_id=p.tp_id ");
		sbSql.append(" LEFT JOIN tb_product p ON d.tod_product_id=p.tp_id ");
		sbSql.append(" WHERE d.tod_head_id = o.toh_id AND o.toh_status>3 ");
		sbSql.append("AND o.toh_customer_id = '"+memberId+"' ");
		sbSql.append("ORDER BY o.toh_add_date LIMIT "+(Integer.valueOf(page)-1)*15+",15");
		return tbOrderDetailDao.searchForMap(sbSql.toString(), null);
	}

	@Override
	public TbOrderDetail findOrderDetail(String productId, String memberId,
			String orderId, String type) {
		TbOrderDetail orderDetail = null;
		StringBuffer sbSql = new StringBuffer();
		sbSql.append("SELECT * FROM tb_order_detail WHERE tod_head_id=? AND tod_type=? ");
		List<Object> values = new ArrayList<Object>();
		values.add(orderId);
		values.add(type);
		List<TbOrderDetail> list = tbOrderDetailDao.search(sbSql.toString(), values);
		if(list.size()>0){
			orderDetail=list.get(0);
		}
		return orderDetail;
	}
	
	
	@Override
    public TbOrderDetail findByProductCode(String productCode, String todType) {
		StringBuffer sbSql = new StringBuffer();
		List<Object> values = new ArrayList<Object>();
		sbSql.append("SELECT * FROM `tb_order_detail` ");
		sbSql.append("WHERE 1=1 ");
		if(!StringUtils.isBlank(productCode)){
			sbSql.append("AND `tod_product_code` = ? ");
			values.add(productCode);
		}
		if(!StringUtils.isBlank(todType)){
			sbSql.append("AND `tod_type` = ? ");//1:商品订单 2:报修配件 3:普通报修订单
			values.add(todType);
		}
		List<TbOrderDetail> list = tbOrderDetailDao.search(sbSql.toString(), values);
		if(list.size()>0)
			return list.get(0);
		return null;
    }

    @Override
    public HashMap<String, Object> getOrderProductList(HashMap<String, Object> params) {


		HashMap<String, Object> json = new HashMap<String, Object>();

		int page = params.get("page") == null ? 0:Integer.parseInt(params.get("page")+"");
		int pageSize = params.get("pageSize") == null ? 0:Integer.parseInt(params.get("pageSize")+"");
		String strTodIds = params.get("todIds")==null?"":params.get("todIds")+""; //订单编号
		String tsoId = params.get("tsoId")==null?"":params.get("tsoId")+""; //订单id
		String type = params.get("type")==null?"":params.get("type")+""; //类型


		StringBuffer sbSql = new StringBuffer();
		sbSql.append("SELECT tp_name,tod_price,tod_count,tp_simple_name,tod_id,tod_head_id,tod_product_code,tod_customer_code ");
		sbSql.append(" from tb_order_detail,tb_product ");
		sbSql.append(" where tod_product_id = tp_id ");
		if(!StringUtils.isBlank(strTodIds))
			sbSql.append(" and tod_id in(" + strTodIds + ")");
		if(!StringUtils.isBlank(tsoId))
			sbSql.append(" and tod_head_id in(" + tsoId + ")");
		if(!StringUtils.isBlank(type))
			sbSql.append(" and tod_type = "+type+" ");

		if (pageSize == 0) {
			List<Map<String, Object>> list = tbOrderDetailDao.searchForMap(sbSql.toString(), null);
			json.put("total", list.size());
			json.put("rows", list);
			json.put("code",100);
			return json;
		}else {
			PageBean<Map<String, Object>> pageBean = new PageBean<Map<String, Object>>(page, pageSize);
			pageBean = tbOrderDetailDao.searchForMap(sbSql.toString(), null, pageBean);

			json.put("total", pageBean.getRowCount());
			json.put("rows", pageBean.getList());
			json.put("code",100);
			return json;
		}
	}


}
