package com.src.api.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import com.src.common.base.entity.PageBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.src.common.base.dao.BaseDao;
import com.src.common.base.service.impl.BaseServiceImpl;
import com.src.api.dao.TbOrderHeadDao;
import com.src.api.entity.TbCustomers;
import com.src.api.entity.TbMachine;
import com.src.api.entity.TbOrderHead;
import com.src.api.service.TbOrderHeadService;

@Service("tbOrderHeadService")
public class TbOrderHeadServiceImpl extends BaseServiceImpl<TbOrderHead, Long>implements TbOrderHeadService {

	@Resource
	TbOrderHeadDao tbOrderHeadDao;

	@Override
	public BaseDao<TbOrderHead, Long> getGenericDao() {
		// TODO Auto-generated method stub
		return tbOrderHeadDao;
	}

	@Override
	public TbOrderHead searchOne(String tohId) {
		String sql = "SELECT * FROM tb_customers, tb_order_head LEFT JOIN tb_machine ON toh_good_id = tm_id  "
				+ "WHERE (toh_status = 1 or toh_status = 2) AND toh_customer_id = tc_id AND toh_id = "+ tohId;
		return tbOrderHeadDao.search(sql, null).get(0);
	}

	@Override
	public Boolean canDelete(TbMachine t) {
		String sql = "SELECT * FROM tb_order_head t WHERE toh_status = 1 AND t.toh_good_id = " + t.getTmId();
		List<TbOrderHead> tgList = tbOrderHeadDao.search(sql, null);
		if (tgList.equals(null) || tgList.size() == 0)
			return true;
		else
			return false;
	}

	@Override
	public Boolean canDelete(TbCustomers t) {
		String sql = "SELECT * FROM tb_order_head t WHERE toh_status = 1 AND t.toh_customer_id = " + t.getTcId();
		List<TbOrderHead> tcList = tbOrderHeadDao.search(sql, null);
		if (tcList.equals(null) || tcList.size() == 0)
			return true;
		else
			return false;
	}

	@Override
	public List<TbOrderHead> selectList(String tcId) {
		String sql = "SELECT * FROM tb_order_head,tb_machine "
				+ "WHERE toh_status = 1 AND toh_good_id = tm_id AND toh_customer_id = " + tcId;
		return tbOrderHeadDao.search(sql, null);
	}

	@Override
	public HashMap<String, Object> findForJson(HashMap<String, String> params) {
		HashMap<String, Object> json = new HashMap<String, Object>();

		int page = params.get("page") == null ? 0 : Integer.parseInt(params.get("page"));
		int pageSize = params.get("pageSize") == null ? 0 : Integer.parseInt(params.get("pageSize"));
		StringBuilder sql = new StringBuilder("Select a.*,m.tm_name,c.tc_name,c.tc_number,  ");
		sql.append("(select tw_name from tb_words where tw_id=m.tm_brand) tm_brand,");
		sql.append("(select tw_name from tb_words where tw_id=m.tm_needle) tm_needle,");
		sql.append("(select tw_name from tb_words where tw_id=m.tm_electric) tm_electric,");
		sql.append("(select tw_name from tb_words where tw_id=m.tm_type) tm_type from ");
		sql.append(" tb_order_head a left join tb_machine m on  toh_good_id = tm_id left join tb_customers c on toh_customer_id = tc_id where toh_status != 0  ");
		List<Object> values = new ArrayList<Object>();

		if (params.get("p_type") != null && !params.get("p_type").equalsIgnoreCase("")) {
			sql.append(" and toh_type =" + params.get("p_type"));
		}
		if (params.get("tcServicePoint") != null && !params.get("tcServicePoint").equalsIgnoreCase("")) {
			sql.append(" and tc_service_point =" + params.get("tcServicePoint"));
		}
		if (params.get("tcName") != null && !params.get("tcName").equalsIgnoreCase("")) {
			sql.append(" and tc_name like '%" + params.get("tcName") + "%'");
		}
		if (params.get("tohNumber") != null && !params.get("tohNumber").equalsIgnoreCase("")) {
			sql.append(" and toh_number like '%" + params.get("tohNumber") + "%'");
		}
		if (params.get("tgName") != null && !params.get("tgName").equalsIgnoreCase("")) {
			sql.append(" and tm_name like '%" + params.get("tgName") + "%'");
		}
		if (!StringUtils.isBlank(params.get("buyStartDate"))) {
			sql.append(" and DATE_FORMAT(toh_buy_date, '%Y-%c-%d') >= DATE_FORMAT(?, '%Y-%c-%d')");
			values.add(params.get("buyStartDate"));
		}
		if (!StringUtils.isBlank(params.get("buyEndDate"))) {
			sql.append(" and DATE_FORMAT(toh_buy_date, '%Y-%c-%d') <= DATE_FORMAT(?, '%Y-%c-%d')");
			values.add(params.get("buyEndDate"));
		}

		if (pageSize == 0) {
			List<TbOrderHead> list = tbOrderHeadDao.search(sql.toString(), values);
			json.put("total", list.size());
			json.put("rows", list);
			return json;
		} else {
			PageBean<TbOrderHead> pageBean = new PageBean<TbOrderHead>(page, pageSize);
			if (params.get("orderBy") != null) {
				String orderby = params.get("orderBy");
				if (orderby.indexOf("tohBuyDate") > -1)
					orderby = orderby.replaceAll("tohBuyDate", "toh_buy_date");
				if (orderby.indexOf("tcName") > -1)
					orderby = orderby.replaceAll("tcName", "tc_name");
				if (orderby.indexOf("tohAddDate") > -1)
					orderby = orderby.replaceAll("tohAddDate", "toh_add_date");
				pageBean.setOrderBy(orderby);
			}
			if (params.get("orderType") != null)
				pageBean.setOrderType(params.get("orderType"));
			pageBean = tbOrderHeadDao.search(sql.toString(), values, pageBean);

			json.put("total", pageBean.getRowCount());
			json.put("rows", pageBean.getList());
			return json;
		}
	}

}
