package com.src.api.service.impl;

import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.src.common.base.entity.PageBean;
import com.src.common.entity.ShiroUser;
import com.src.common.entity.Userinfo;
import com.src.common.service.UserinfoService;
import com.src.common.utils.StringUtil;
import com.src.common.utils.hjtech.util.LogTool;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.src.common.base.dao.BaseDao;
import com.src.common.base.service.impl.BaseServiceImpl;
import com.src.api.dao.TbCompanyCustomerDao;
import com.src.api.dao.TbCustomersDao;
import com.src.api.entity.TbCompanyCustomer;
import com.src.api.entity.TbCustomers;
import com.src.api.service.TbCustomersService;
import com.src.api.service.TbOrderHeadService;

@Service("tbCustomersService")
public class TbCustomersServiceImpl extends BaseServiceImpl<TbCustomers, Long>implements TbCustomersService {
//	@Value("#{config['vcode_pass']}")
//	public String vcode_pass="";
	@Resource
	TbCustomersDao tbcustomersDao;
	@Resource
	UserinfoService userinfoService;
	@Resource
	TbCompanyCustomerDao tbCompanyCustomerDao;
	@Resource
	TbOrderHeadService tbOrderHeadService;

	@Override
	public BaseDao<TbCustomers, Long> getGenericDao() {
		// TODO Auto-generated method stub
		return tbcustomersDao;
	}

	@Override
	public TbCustomers searchOne(TbCustomers t) {
		return tbcustomersDao.searchOne(t);
	}

	@Override
	public List<TbCustomers> selectList(String tcServicePoint) {
		String sql = "select * from tb_customers where tc_status = 1";
		if (!tcServicePoint.equalsIgnoreCase("")) {
			sql += " and tc_service_point = " + tcServicePoint;
		}
		return tbcustomersDao.search(sql, null);
	}

	@Override
	public HashMap<String, Object> findForJson(HashMap<String, String> params) {
		HashMap<String, Object> json = new HashMap<String, Object>();

		int page = params.get("page") == null ? 0 : Integer.parseInt(params.get("page"));
		int pageSize = params.get("pageSize") == null ? 0 : Integer.parseInt(params.get("pageSize"));
		StringBuffer sbSql = new StringBuffer();
		
		sbSql.append("select t.*,c.cname,p.pname,(select tw_name from tb_words where tw_id=t.tc_type) tw_type,sp.tsp_name  from tb_customers t left join china_city c on t.tc_city_id=c.cid left join china_province p on t.tc_prov_id = p.pid left join tb_service_points sp on sp.tsp_id=t.tc_service_point  where 1=1 ");
		if (params.get("tcServicePoint") != null && !params.get("tcServicePoint").equalsIgnoreCase("")) {
			sbSql.append(" and tc_service_point = " + params.get("tcServicePoint"));
		}
		if (params.get("p_type") != null && !params.get("p_type").equalsIgnoreCase("")) {
			sbSql.append(" and tc_status = " + params.get("p_type"));
		} else {
			sbSql.append(" and (tc_status = 1 or tc_status = 2) ");
		}
		if (params.get("tcProvId") != null && !params.get("tcProvId").equalsIgnoreCase("")) {
			sbSql.append(" and tc_prov_id = " + params.get("tcProvId"));
		}
		if (params.get("tcCityId") != null && !params.get("tcCityId").equalsIgnoreCase("")) {
			sbSql.append(" and tc_city_id = " + params.get("tcCityId"));
		}
		if (params.get("tcName") != null && !params.get("tcName").equalsIgnoreCase("")) {
			sbSql.append(" and tc_name like  '%" + params.get("tcName") + "%'");
		}

		List<Object> values = new ArrayList<Object>();

		if (pageSize == 0) {
			List<TbCustomers> list = tbcustomersDao.search(sbSql.toString(), values);
			json.put("total", list.size());
			json.put("rows", list);
			return json;
		} else {
			PageBean<TbCustomers> pageBean = new PageBean<TbCustomers>(page, pageSize);
			if (params.get("orderBy") != null)
				pageBean.setOrderBy("tc_add_date");
			if (params.get("orderType") != null)
				pageBean.setOrderType(params.get("orderType"));
			pageBean = tbcustomersDao.search(sbSql.toString(), values, pageBean);

			json.put("total", pageBean.getRowCount());
			json.put("rows", pageBean.getList());
			return json;
		}
	}

	@Override
	public List<TbCustomers> findLoginUser() {
		return tbcustomersDao.searchp("SELECT c.* FROM tb_customers c WHERE c.tc_status = 1", null);
	}

	@Override
	public String getMaxId() {
		// TODO Auto-generated method stub
		String sql = "select  CONCAT('W', LPAD((right(ifnull(max(tc_number),'W000000'), 6) + 1), 6, '0')) tc_number from tb_customers where tc_number REGEXP 'W[[:digit:]]{6}'";
		return tbcustomersDao.getDouble(sql);
	}

	@Override
	public TbCustomers searchOne(String acount) {
		// TODO Auto-generated method stub
		String sql = "select * from tb_customers where tc_status = 1 and tc_login_user = ?";
		List<Object> values = new ArrayList<Object>();
		values.add(acount);
		List<TbCustomers> list = tbcustomersDao.search(sql, values);
		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public HashMap<String, Object> findForJsonOfMember(
			HashMap<String, String> params) {
		HashMap<String, Object> json = new HashMap<String, Object>();

		int page = params.get("page") == null ? 0 : Integer.parseInt(params.get("page"));
		int pageSize = params.get("pageSize") == null ? 0 : Integer.parseInt(params.get("pageSize"));
		LogTool.WriteLog("pageSize:"+pageSize);
		String AddDate = params.get("AddDate");
		String AddDateEnd = params.get("AddDateEnd");
		String provice = params.get("provice");
		String city = params.get("city");
		String country = params.get("country");
		String tcStatus = params.get("tcStatus");
		String companyId = params.get("companyId");
		String order = params.get("order");
		String tseUnitid = params.get("tseUnitid");
		StringBuffer sbSql = new StringBuffer();
		sbSql.append("SELECT c.tc_photo tcPhoto ,c.tc_id tcId,c.tc_name tcName,c.tc_login_user tcLoginUser,");
		sbSql.append("c.tc_level tcLevel,");
		sbSql.append("concat(d.pname,e.cname,f.oname,c.tc_address) address,");
//		sbSql.append("c.tc_address address,");
		sbSql.append("c.tc_person tcPerson,");
		sbSql.append("c.tc_mobile tcMobile,DATE_FORMAT(c.tc_add_date,'%Y-%m-%d %H:%i:%s') tcAddDate,");
		sbSql.append("c.tc_status tcStatus,tsp_name AS tspName FROM ");
		sbSql.append("tb_customers c ");
		sbSql.append("LEFT JOIN china_province d ON d.pid=c.tc_prov_id ");
		sbSql.append("LEFT JOIN china_city e ON c.tc_city_id=e.cid ");
		sbSql.append("LEFT JOIN china_county f ON c.tc_region_id = f.oid ");
		sbSql.append("LEFT JOIN tb_service_points  ON c.tc_service_point = tsp_id ");
		sbSql.append("where c.tc_status <> 0 ");
		List<Object> values = new ArrayList<Object>();
		if (params.get("tcName") != null && !params.get("tcName").equalsIgnoreCase("")) {
			sbSql.append(" and c.tc_name like  '%" + params.get("tcName") + "%'");
		}
		if (AddDate != null && !AddDate.equalsIgnoreCase("")) {
			sbSql.append(" AND DATE_FORMAT(tc_add_date,'%Y-%m-%d') >= DATE_FORMAT('"+AddDate+"','%Y-%m-%d')");
		} 
		
		if (AddDateEnd!= null && !AddDateEnd.equalsIgnoreCase("")) {
			sbSql.append(" AND DATE_FORMAT(tc_add_date,'%Y-%m-%d') <= DATE_FORMAT('"+AddDateEnd+"','%Y-%m-%d')");
		}
		if (!StringUtils.isBlank(companyId)) {
			sbSql.append(" and c.tc_company_id = "+companyId+"");//单位id
		}
		if (!StringUtils.isBlank(tseUnitid)) {
			sbSql.append(" and c.tc_service_point = "+tseUnitid+"");//单位id
		}
		if (!StringUtils.isBlank(provice)) {
			sbSql.append(" and c.tc_prov_id = "+provice+"");
		}
		if (!StringUtils.isBlank(city)) {
			sbSql.append(" and c.tc_city_id = "+city+"");
		}
		if (!StringUtils.isBlank(country)) {
			sbSql.append(" and c.tc_region_id = "+country+"");
		}
		if (!StringUtils.isBlank(tcStatus)) {
			LogTool.WriteLog("tcStatus:"+tcStatus);
			if(tcStatus.equals("-1")){
				sbSql.append("");
			}else{
				sbSql.append(" and c.tc_status = "+tcStatus+" ");//客户状态
			}
		}
		if (!StringUtils.isBlank(order)) {
			sbSql.append(" order by c.tc_add_date ? ");
			values.add(order);
		}else{
			sbSql.append(" order by c.tc_add_date desc ");
		}
		

		if (pageSize == 0) {
			List<TbCustomers> list = tbcustomersDao.search(sbSql.toString(), values);
			//json.put("pageCount", StringUtil.getPageCount(list.size(),pageSize));
			json.put("total", list.size());//数据数量
			json.put("rows", list);
			return json;
		} else {
			PageBean<Map<String, Object>> pageBean = new PageBean<Map<String, Object>>(page, pageSize);
			pageBean = tbcustomersDao.searchForMap(sbSql.toString(), null, pageBean);
			json.put("pageCount", StringUtil.getPageCount(pageBean.getRowCount(),pageSize));
			json.put("total", pageBean.getRowCount());//数据数量
			json.put("rows", pageBean.getList());
			return json;
		}
	}

	@Override
	@Transactional
	public void saveMember(TbCustomers userinfo, Userinfo userinfo2, ShiroUser loginUser, TbCompanyCustomer tbCompanyCustomer)
			throws Exception {
		tbcustomersDao.save(userinfo);
		userinfo2.setAccount(userinfo.getTcLoginUser());
		userinfo2.setPassword(userinfo.getTcLoginPass().toUpperCase());
		userinfo2.setCreateTime(new Timestamp(new Date().getTime()));
		userinfo2.setCreateUserId(loginUser.getId());
		userinfo2.setCreateUserName(loginUser.getAccount());
		userinfo2.setGroupId(9);
		userinfo2.setName(userinfo.getTcName());
		userinfo2.setDeleteMark(1);
		userinfoService.save(userinfo2);
		tbCompanyCustomer.setTccCustomerId(userinfo.getTcId());
		tbCompanyCustomerDao.save(tbCompanyCustomer);
	}

	@Override
	public void updateMemberStatus(TbCustomers tbcustomers, Userinfo userinfo)
			throws Exception {
		tbcustomersDao.update(tbcustomers);
		userinfoService.update(userinfo);
	}

	@Override
	public HashMap<String, Object> findForJsonMemberOrder(
			HashMap<String, String> params) {
		HashMap<String, Object> json = new HashMap<String, Object>();
		int page = params.get("page") == null ? 0 : Integer.parseInt(params.get("page"));
		int pageSize = params.get("pageSize") == null ? 0 : Integer.parseInt(params.get("pageSize"));
		System.out.println("pageSize:"+pageSize);
		String AddDate = params.get("AddDate");
		String AddDateEnd = params.get("AddDateEnd");
		String tcId = params.get("tcId");
		String orderStatus = params.get("orderStatus");
		String orderNumber = params.get("orderNumber");
		String companyName = params.get("companyName");
		String order = params.get("order");
		StringBuffer sbSql = new StringBuffer();
		sbSql.append("SELECT o.toh_number tohNumber,");
		sbSql.append("(SELECT tcc.tc_name FROM tb_company tcc WHERE o.toh_company_id = tcc.tc_id ) tcName,");
		sbSql.append("m.tc_name,m.tc_mobile tcMobile,concat(d.pname,e.cname,f.oname,a.tma_address) address,");
		sbSql.append("DATE_FORMAT(o.toh_add_date,'%Y-%m-%d %H:%i:%s') tohAddDate,o.toh_status tohStatus,");
		sbSql.append("o.toh_amount tohAmount,a.tma_name tmaName FROM tb_order_head o,tb_address a,");
		sbSql.append("china_province d, china_city e,china_county f,tb_customers m WHERE o.toh_address_id = a.tma_id ");
		sbSql.append("AND a.tma_provice_id = d.pid AND a.tma_city_id = e.cid AND a.tma_county_id=f.oid AND ");
		sbSql.append("m.tc_id=o.toh_customer_id AND toh_status<>0  AND o.toh_customer_id="+tcId);
		
		if (params.get("orderStatus") != null && !params.get("orderStatus").equalsIgnoreCase("")) {
//			sql += " and c.tc_name like  '%" + params.get("orderStatus") + "%'";
			
			System.out.println("orderStatus:"+orderStatus);
			if(orderStatus.equals("-1")){
				sbSql.append("");
			}else{
				sbSql.append(" and o.toh_status ='" +orderStatus+ "'");
			}
		}
		if (params.get("orderNumber") != null && !params.get("orderNumber").equalsIgnoreCase("")) {
			sbSql.append(" and o.toh_number like '%" +orderNumber+ "%'");
		}
		if (companyName!= null && !companyName.equalsIgnoreCase("")) {
			sbSql.append(" and o.toh_company_id=  " +companyName);
		}
		if (AddDate != null && !AddDate.equalsIgnoreCase("")) {
			sbSql.append(" AND DATE_FORMAT(o.toh_add_date,'%Y-%m-%d') >= DATE_FORMAT('"+AddDate+"','%Y-%m-%d')");
		} 
		
		if (AddDateEnd!= null && !AddDateEnd.equalsIgnoreCase("")) {
			sbSql.append(" AND DATE_FORMAT(o.toh_add_date,'%Y-%m-%d') <= DATE_FORMAT('"+AddDateEnd+"','%Y-%m-%d')");
		}
		
		if (!StringUtils.isBlank(order)) {
			sbSql.append(" order by o.toh_add_date " + order);
		}
		List<Object> values = new ArrayList<Object>();

		if (pageSize == 0) {
			List<Map<String, Object>> list = tbcustomersDao.searchForMap(sbSql.toString(), values);
			json.put("total", list.size());
			json.put("rows", list);
			return json;
		} else {
			PageBean<Map<String, Object>> pageBean = new PageBean<Map<String, Object>>(page, pageSize);
			pageBean = tbcustomersDao.searchForMap(sbSql.toString(), null, pageBean);
			
			json.put("total", pageBean.getRowCount());
			json.put("rows", pageBean.getList());
			return json;
		}
	}

	@Override
	public TbCustomers findOne(TbCustomers tbCustomers) {
		return tbcustomersDao.searchOne(tbCustomers);
	}

	@Override
	public void saveCustomer(TbCustomers tbCustomer, Userinfo customerUser, TbCompanyCustomer companyCustomer)
			throws Exception {
		tbcustomersDao.save(tbCustomer);
		userinfoService.save(customerUser);
		companyCustomer.setTccCustomerId(tbCustomer.getTcId());//会员id
		tbCompanyCustomerDao.save(companyCustomer);
	}

	@Override
	public String deleteCustomersByIds(String[] arrTcIds) throws Exception {
		for (int i = 0; i < arrTcIds.length; i++) {
			TbCustomers tbCustomers = tbcustomersDao.get(Long.valueOf(arrTcIds[i]));
			if(tbCustomers == null){
				return "101";//帐号不存在
			}
			Userinfo userinfo = new Userinfo();
			userinfo.setAccount(tbCustomers.getTcLoginUser());
			userinfo.setName(tbCustomers.getTcName());
			userinfo.setPassword(tbCustomers.getTcLoginPass());
			userinfo.setGroupId(3);//会员（tb_customer）
			userinfo.setDeleteMark(1);

			Userinfo search = userinfoService.findUserinfo(userinfo);
			TbCustomers tempBean = tbcustomersDao.get(Long.valueOf(arrTcIds[i]));
			tempBean.setTcStatus(0);
			if (tbOrderHeadService.canDelete(tempBean)) {//查询该客户（会员）是否有订单
				
				if (search != null) {
					search.setDeleteMark(3);
					tbCustomers.setTcStatus(0);
					userinfoService.updateMemberStatus(search,tbCustomers);
				}
				tbcustomersDao.update(tempBean);
			} else {
				return "103";//会员有订单不能删除
			}
		}
		return "100";
	}

	@Override
	public TbCustomers findCustomerByAccountAndCompanyId(Long tcId,
			String tcLoginUser) {
		StringBuffer sbSql = new StringBuffer();
		sbSql.append("select * from tb_customers where tc_status <> 0 and tc_login_user = ?");
		sbSql.append(" AND tc_company_id = ? ");
		List<Object> values = new ArrayList<Object>();
		values.add(tcLoginUser);
		values.add(tcId);
		List<TbCustomers> list = tbcustomersDao.search(sbSql.toString(), values);
		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public void updateMemberPass(TbCustomers customers, Userinfo customerUser)
			throws Exception {
		tbcustomersDao.update(customers);
		if(customerUser != null){
			userinfoService.update(customerUser);
		}
		/**
		 * 发送短信
		 */
//		Client client = new Client(new URL("http://smsapi.hjtechcn.cn:6080/smsWs/sms.ws?wsdl"));
//		Object[] o = client.invoke("sendSMS", new Object[]{"tuzi-saas",StringUtil.getMD5Str(vcode_pass),customers.getTcMobile(),"尊敬的客户，您已重置密码为："+123456+"。【易维保】","10659800","shcmcc"});
//		System.out.println("短信发送："+o[0].toString());
	}
}
