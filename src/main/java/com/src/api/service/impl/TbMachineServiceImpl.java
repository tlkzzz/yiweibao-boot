package com.src.api.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.src.common.base.entity.PageBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.src.common.base.dao.BaseDao;
import com.src.common.base.service.impl.BaseServiceImpl;
import com.src.api.dao.TbMachineDao;
import com.src.api.entity.TbMachine;
import com.src.api.service.TbMachineService;

@Service("tbMachineService")
public class TbMachineServiceImpl extends BaseServiceImpl<TbMachine, Long>implements TbMachineService {

	@Resource
	TbMachineDao tbMachineDao;

	@Override
	public BaseDao<TbMachine, Long> getGenericDao() {
		// TODO Auto-generated method stub
		return tbMachineDao;
	}

	@Override
	public TbMachine searchOne(TbMachine t) {
		return tbMachineDao.searchOne(t);
	}

	@Override
	public List<TbMachine> selectList() {
		String sql = "select * from tb_machine, tb_machine_details  where tmd_machine_id = tm_id ";
		return tbMachineDao.search(sql, null);
	}

	@Override
	public List<TbMachine> canSelectGoods(HashMap<String, String> params) {
		String sql = "select t.* from tb_machine t,tb_order_head where tm_status = 1 and tm_id = toh_good_id and toh_customer_id="
				+ params.get("tcId");
		return tbMachineDao.search(sql, null);
	}

	@Override
	public HashMap<String, Object> findForJson(HashMap<String, String> params) {
		HashMap<String, Object> json = new HashMap<String, Object>();

		int page = params.get("page") == null ? 0 : Integer.parseInt(params.get("page"));
		int pageSize = params.get("pageSize") == null ? 0 : Integer.parseInt(params.get("pageSize"));

		StringBuilder sql = new StringBuilder("select m.*,");
		sql.append("(select tw_name from tb_words where tw_id=m.tm_brand) brand_name,");
		sql.append("(select tw_name from tb_words where tw_id=m.tm_type) type_name,");
		sql.append("(select tw_name from tb_words where tw_id=m.tm_gating) gating_name,");
		sql.append("(select tw_name from tb_words where tw_id=m.tm_electric) electric_name,");
		sql.append("(select tw_name from tb_words where tw_id=m.tm_servo) servo_name ,");
		sql.append("(select tw_name from tb_words where tw_id=m.tm_size) msize ,");
		sql.append("(select tw_name from tb_words where tw_id=m.tm_needle) needle, ");
		sql.append(" DATE_FORMAT(tM_addtime,'%Y-%m-%d %H:%i:%s') as tm_add_time ");
	
		sql.append(" from tb_machine m where tm_status != 0 and tm_from = 1 ");
		List<Object> values = new ArrayList<Object>();

		if (params.get("tmCode") != null && !params.get("tmCode").equalsIgnoreCase("")) {
			sql.append(" and tm_code like '%" + params.get("tmCode") + "%'");
		}
		if (params.get("tmName") != null && !params.get("tmName").equalsIgnoreCase("")) {
			sql.append(" and tm_name like '%" + params.get("tmName") + "%'");
		}
		if (!StringUtils.isBlank(params.get("startDate"))) {
			sql.append(" and tm_addtime > DATE_FORMAT(?, '%Y-%c-%d %H:%i:%s')");
			values.add(params.get("startDate"));
		}
		if (!StringUtils.isBlank(params.get("endDate"))) {
			sql.append(" and tm_addtime < DATE_FORMAT(?, '%Y-%c-%d %H:%i:%s')");
			values.add(params.get("endDate"));
		}
		if (!StringUtils.isBlank(params.get("tmBrand"))) {
			sql.append(" and tm_brand = ?");
			values.add(params.get("tmBrand"));
		}

		System.out.println("SQL->" + sql.toString());
		if (pageSize == 0) {
			List<Map<String,Object>> list = tbMachineDao.searchForMap(sql.toString(), values);
			json.put("total", list.size());
			json.put("rows", list);
			return json;
		} else {
			PageBean<Map<String,Object>> pageBean = new PageBean<Map<String,Object>>(page, pageSize);
			if (params.get("orderBy") != null)
				pageBean.setOrderBy("tm_addtime");
			if (params.get("orderType") != null)
				pageBean.setOrderType(params.get("orderType"));
			pageBean = tbMachineDao.searchForMap(sql.toString(), values, pageBean);

			json.put("total", pageBean.getRowCount());
			json.put("rows", pageBean.getList());
			return json;
		}
	}

	@Override
	public List<TbMachine> canSelectGoods() {
		// TODO Auto-generated method stub
		String sql = "select * from tb_machine  where tm_status = 1 ";
		return tbMachineDao.search(sql, null);
	}

	@Override
	public String getMaxId() {
		// TODO Auto-generated method stub
		String sql = "select  CONCAT('M', LPAD((right(ifnull(max(tm_code),'M000000'), 6) + 1), 6, '0')) tc_number from tb_machine where tm_code REGEXP 'M[[:digit:]]{6}'";
		return tbMachineDao.getDouble(sql);
	}

	@Override
	public Long getMachineByName(String name) {
		// TODO Auto-generated method stub
		String sql = "select * from tb_machine  where tm_status = 1 and tm_from = 2 and tm_name = ?";
		List<Object> values = new ArrayList<Object>();
		values.add(name);
		List<TbMachine> list =  tbMachineDao.search(sql, values);
		if(list != null && list.size() > 0) {
			return list.get(0).getTmId();
		} else {
			return 0l;
		}
	}
}
